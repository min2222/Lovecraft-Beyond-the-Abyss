package com.min01.beyondtheabyss.shader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class BTAWorldShader
{
	public final Long2BooleanMap chunkCache = new Long2BooleanOpenHashMap();
	public final Matrix4f inverseMat = new Matrix4f();
	public final ExecutorService maskExecutor = Executors.newSingleThreadExecutor();

	public final ResourceKey<Level> world;
	public final Supplier<ExtendedPostChain> shader;
	public final ResourceKey<Biome> biome;
	public final boolean usesBiomeMask;
	public final String samplerName;

	public int maskTextureId = -1;

	public volatile boolean maskUploadPending;
	public volatile int maskOriginMinX;
	public volatile int maskOriginMinZ;
	public BlockPos maskFillCenter;

	public ByteBuffer maskPixelBuffer = MemoryUtil.memAlloc(MASK_TEXELS * MASK_TEXELS * 4);
	public boolean isProcessing;

	public static final int MASK_BLOCKS_XZ = 512;
	public static final int MASK_TEXELS = 128;
	public static final float MASK_Y_MIN = -64.0F;
	public static final float MASK_Y_MAX = 448.0F;
	private static final int MASK_REBUILD_DISTANCE_BLOCKS = 24;

	public static final List<BTAWorldShader> WORLD_SHADERS = new ArrayList<>();
	public static final ThreadLocal<BlockPos.MutableBlockPos> THREAD_POS = ThreadLocal.withInitial(BlockPos.MutableBlockPos::new);

	public BTAWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader)
	{
		this(world, shader, null, false, "");
	}

	public BTAWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader, ResourceKey<Biome> biome, boolean usesBiomeMask, String samplerName)
	{
		this.world = world;
		this.shader = shader;
		this.biome = biome;
		this.usesBiomeMask = usesBiomeMask;
		this.samplerName = samplerName;
	}

	public void render(PoseStack mtx, float frameTime, Camera camera)
	{
		if(!BTAConfig.worldShaders.get())
		{
			return;
		}
		ClientLevel level = BTAClientUtil.MC.level;
		ResourceKey<Level> dimension = level.dimension();
		Entity camEntity = BTAClientUtil.MC.cameraEntity;
		if(camEntity != null)
		{
			double x = Mth.lerp((double)frameTime, camEntity.xOld, camEntity.getX());
			double y = Mth.lerp((double)frameTime, camEntity.yOld, camEntity.getY());
			double z = Mth.lerp((double)frameTime, camEntity.zOld, camEntity.getZ());

			Vec3 camPos = camera.getPosition();
			Vec3 playerPos = new Vec3(x, y, z);
			Vec3 pos = playerPos.subtract(camPos);

			if(dimension == this.world)
			{
				mtx.pushPose();
				if(this.usesBiomeMask)
				{
					BlockPos camBlock = camera.getBlockPosition();
					if(this.needsMaskRebuild(camBlock) && !this.isProcessing)
					{
						this.requestUpdate(camBlock, level);
					}
					this.apply(mtx, frameTime, camBlock);
				}
				else
				{
					mtx.translate(pos.x, pos.y, pos.z);
					this.apply(mtx, frameTime, camera.getBlockPosition());
				}
				mtx.popPose();
			}
		}
	}

	private boolean needsMaskRebuild(BlockPos camBlock)
	{
		if(this.maskFillCenter == null)
		{
			return true;
		}
		int dx = Math.abs(camBlock.getX() - this.maskFillCenter.getX());
		int dz = Math.abs(camBlock.getZ() - this.maskFillCenter.getZ());
		return dx > MASK_REBUILD_DISTANCE_BLOCKS || dz > MASK_REBUILD_DISTANCE_BLOCKS;
	}

	public void requestUpdate(BlockPos centerPos, ClientLevel level)
	{
		if(this.isProcessing || this.biome == null)
		{
			return;
		}
		this.isProcessing = true;
		BlockPos centerImmutable = centerPos.immutable();
		int startX = centerImmutable.getX() - MASK_BLOCKS_XZ / 2;
		int startZ = centerImmutable.getZ() - MASK_BLOCKS_XZ / 2;
		this.maskOriginMinX = startX;
		this.maskOriginMinZ = startZ;

		CompletableFuture.runAsync(() ->
		{
			byte[] tempArray = new byte[MASK_TEXELS * MASK_TEXELS * 4];
			BlockPos.MutableBlockPos mutablePos = THREAD_POS.get();
			int span = MASK_BLOCKS_XZ - 1;
			int denom = MASK_TEXELS - 1;

			for(int zi = 0; zi < MASK_TEXELS; zi++)
			{
				int worldZ = startZ + (denom <= 0 ? 0 : Mth.floor((float)zi * (float)span / (float)denom));
				for(int xi = 0; xi < MASK_TEXELS; xi++)
				{
					int worldX = startX + (denom <= 0 ? 0 : Mth.floor((float)xi * (float)span / (float)denom));
					int surface = level.getHeight(Heightmap.Types.MOTION_BLOCKING, worldX, worldZ);
					mutablePos.set(worldX, surface + 1, worldZ);

					boolean canApply = level.getBiome(mutablePos).is(this.biome);

					int idx = (zi * MASK_TEXELS + xi) * 4;
					if(canApply)
					{
						tempArray[idx] = (byte)255;
						tempArray[idx + 1] = 0;
						tempArray[idx + 2] = 0;
						float t = (surface - MASK_Y_MIN) / (MASK_Y_MAX - MASK_Y_MIN);
						t = Mth.clamp(t, 0f, 1f);
						tempArray[idx + 3] = (byte)Mth.floor(t * 255f);
					}
					else
					{
						tempArray[idx] = 0;
						tempArray[idx + 1] = 0;
						tempArray[idx + 2] = 0;
						tempArray[idx + 3] = 0;
					}
				}
			}

			BTAClientUtil.MC.execute(() ->
			{
				this.maskFillCenter = centerImmutable;
				this.maskPixelBuffer.clear();
				this.maskPixelBuffer.put(tempArray);
				this.maskPixelBuffer.flip();
				this.maskUploadPending = true;
				this.isProcessing = false;
			});
		}, this.maskExecutor);
	}

	public int getOrCreateMaskTextureId(PoseStack stack)
	{
		if(this.maskTextureId == -1)
		{
			this.maskTextureId = TextureUtil.generateTextureId();
			RenderSystem.bindTexture(this.maskTextureId);
			GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GlStateManager._texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

			RenderSystem.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);
			GlStateManager._texImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_RGBA8, MASK_TEXELS, MASK_TEXELS, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (IntBuffer)null);
			RenderSystem.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);
		}
		return this.maskTextureId;
	}

	private void flushMaskUpload()
	{
		if(!this.maskUploadPending || this.maskTextureId == -1)
		{
			return;
		}
		RenderSystem.bindTexture(this.maskTextureId);
		RenderSystem.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);
		GlStateManager._texSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, MASK_TEXELS, MASK_TEXELS, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, MemoryUtil.memAddress(this.maskPixelBuffer));
		RenderSystem.pixelStore(GL11.GL_UNPACK_ALIGNMENT, 4);
		this.maskUploadPending = false;
	}

	public void apply(PoseStack mtx, float frameTime, BlockPos camBlock)
	{
		Minecraft minecraft = BTAClientUtil.MC;

		ExtendedPostChain shaderChain = this.shader.get();
		EffectInstance shader = shaderChain.getMainShader();

		if(shader != null)
		{
			shader.safeGetUniform("iResolution").set(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
			shader.setSampler("ImageSampler", () -> minecraft.getTextureManager().getTexture(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/misc/rgba_noise_medium.png")).getId());
			if(this.usesBiomeMask)
			{
				this.flushMaskUpload();
				shader.setSampler(this.samplerName + "MaskSampler", () -> this.getOrCreateMaskTextureId(mtx));
				Vec3 cam = minecraft.gameRenderer.getMainCamera().getPosition();
				shader.safeGetUniform("MaskOriginXZ").set((float)this.maskOriginMinX - (float)cam.x, (float)this.maskOriginMinZ - (float)cam.z);
				shader.safeGetUniform("MaskExtentXZ").set((float)MASK_BLOCKS_XZ, (float)MASK_BLOCKS_XZ);
				shader.safeGetUniform("MaskYMin").set(MASK_Y_MIN);
				shader.safeGetUniform("MaskYMax").set(MASK_Y_MAX);
				shader.safeGetUniform("CameraWorldY").set((float)cam.y);
			}
			shader.safeGetUniform("InverseTransformMatrix").set(BTAClientUtil.getInverseTransformMatrix(this.inverseMat, mtx.last().pose()));
			shader.safeGetUniform("iTime").set((((float)(minecraft.level.getGameTime() % 2400000)) + frameTime) / 20.0F);
			shaderChain.process(frameTime);
			minecraft.getMainRenderTarget().bindWrite(false);
		}
	}

	public static void registerWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader, ResourceKey<Biome> biome, String samplerName)
	{
		registerWorldShader(world, shader, biome, true, samplerName);
	}

	public static void registerWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader)
	{
		registerWorldShader(world, shader, null, false, "");
	}

	public static void registerWorldShader(ResourceKey<Level> world, Supplier<ExtendedPostChain> shader, ResourceKey<Biome> biome, boolean usesBiomeMask, String samplerName)
	{
		BTAWorldShader worldShader = new BTAWorldShader(world, shader, biome, usesBiomeMask, samplerName);
		WORLD_SHADERS.add(worldShader);
	}
}
