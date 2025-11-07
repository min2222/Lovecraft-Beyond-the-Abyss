package com.min01.beyondtheabyss.world.effects;

import javax.annotation.Nullable;

import org.joml.Matrix4f;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class OuterSpaceDimensionSpecialEffects extends DimensionSpecialEffects
{
	private static final ResourceLocation[] LOCATIONS = new ResourceLocation[] 
	{
		new ResourceLocation(BeyondtheAbyss.MODID, "textures/environment/outer_space/death_star.png"),
		new ResourceLocation(BeyondtheAbyss.MODID, "textures/environment/outer_space/ice_planet.png"),
		new ResourceLocation(BeyondtheAbyss.MODID, "textures/environment/outer_space/living_planet.png"),
		new ResourceLocation(BeyondtheAbyss.MODID, "textures/environment/outer_space/magma_planet.png"),
		new ResourceLocation(BeyondtheAbyss.MODID, "textures/environment/outer_space/mint_planet.png")
	};
	
	private static final float[] SCALES = new float[LOCATIONS.length];
	private static final Vec3[] ROTATIONS = new Vec3[LOCATIONS.length];
	
	private final RandomSource random = RandomSource.create();
	
	@Nullable
	private VertexBuffer starBuffer;
	
	@Nullable
	private VertexBuffer skyBuffer;
	   
	public OuterSpaceDimensionSpecialEffects()
	{
		super(Float.NaN, false, DimensionSpecialEffects.SkyType.NONE, false, false);
		this.createStars();
		this.createLightSky();
		
		float range = 360.0F;
		for(int i = 0; i < LOCATIONS.length; i++)
		{
			SCALES[i] = Mth.nextFloat(this.random, 5.0F, 25.0F);
			ROTATIONS[i] = new Vec3(Mth.nextFloat(this.random, -range, range), Mth.nextFloat(this.random, -range, range), Mth.nextFloat(this.random, -range, range));
		}
	}

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 p_108908_, float p_108909_)
	{
		return Vec3.ZERO;
	}

	@Override
	public boolean isFoggyAt(int p_108905_, int p_108906_) 
	{
		return false;
	}

	@Override
	public float[] getSunriseColor(float p_108872_, float p_108873_) 
	{
		return null;
	}

	@Override
	public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog)
	{
		FogRenderer.levelFogColor();
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		RenderSystem.depthMask(false);
		RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
		ShaderInstance shaderinstance = RenderSystem.getShader();
		this.skyBuffer.bind();
		this.skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderinstance);
		VertexBuffer.unbind();
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		poseStack.pushPose();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
		Matrix4f matrix4f1 = poseStack.last().pose();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		for(int i = 0; i < LOCATIONS.length; i++)
		{
			float scale = SCALES[i];
			Vec3 rotation = ROTATIONS[i].scale(360.0F);
			poseStack.mulPose(Axis.ZP.rotationDegrees((float) rotation.z));
			poseStack.mulPose(Axis.YP.rotationDegrees((float) rotation.y));
			poseStack.mulPose(Axis.XP.rotationDegrees((float) rotation.x));
			RenderSystem.setShaderTexture(0, LOCATIONS[i]);
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.vertex(matrix4f1, -scale, 100.0F, -scale).uv(0.0F, 0.0F).endVertex();
			bufferbuilder.vertex(matrix4f1, scale, 100.0F, -scale).uv(1.0F, 0.0F).endVertex();
			bufferbuilder.vertex(matrix4f1, scale, 100.0F, scale).uv(1.0F, 1.0F).endVertex();
			bufferbuilder.vertex(matrix4f1, -scale, 100.0F, scale).uv(0.0F, 1.0F).endVertex();
			BufferUploader.drawWithShader(bufferbuilder.end());
		}
		
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		FogRenderer.setupNoFog();
		this.starBuffer.bind();
		this.starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
		VertexBuffer.unbind();
		setupFog.run();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
		poseStack.popPose();
		RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.depthMask(true);
		return true;
	}

	private void createLightSky() 
	{
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		if(this.skyBuffer != null)
		{
			this.skyBuffer.close();
		}

		this.skyBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = buildSkyDisc(bufferbuilder, 16.0F);
		this.skyBuffer.bind();
		this.skyBuffer.upload(bufferbuilder$renderedbuffer);
		VertexBuffer.unbind();
	}

	private static BufferBuilder.RenderedBuffer buildSkyDisc(BufferBuilder p_234268_, float p_234269_)
	{
		float f = Math.signum(p_234269_) * 512.0F;
		RenderSystem.setShader(GameRenderer::getPositionShader);
		p_234268_.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
		p_234268_.vertex(0.0D, (double)p_234269_, 0.0D).endVertex();
		for(int i = -180; i <= 180; i += 45) 
		{
			p_234268_.vertex((double)(f * Mth.cos((float)i * ((float)Math.PI / 180.0F))), (double)p_234269_, (double)(512.0F * Mth.sin((float)i * ((float)Math.PI / 180.0F)))).endVertex();
		}
		return p_234268_.end();
	}
	
	private void createStars() 
	{
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		if(this.starBuffer != null) 
		{
			this.starBuffer.close();
		}
		
		this.starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.drawStars(bufferbuilder);
		this.starBuffer.bind();
		this.starBuffer.upload(bufferbuilder$renderedbuffer);
		VertexBuffer.unbind();
	}
	
	private BufferBuilder.RenderedBuffer drawStars(BufferBuilder p_234260_)
	{
		RandomSource randomsource = RandomSource.create(10842L);
		p_234260_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

		for(int i = 0; i < 1500; ++i)
		{
			double d0 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);
			double d1 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);
			double d2 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);
			double d3 = (double)(0.15F + randomsource.nextFloat() * 0.1F);
			double d4 = d0 * d0 + d1 * d1 + d2 * d2;
			if(d4 < 1.0D && d4 > 0.01D) 
			{
				d4 = 1.0D / Math.sqrt(d4);
				d0 *= d4;
				d1 *= d4;
				d2 *= d4;
				double d5 = d0 * 100.0D;
				double d6 = d1 * 100.0D;
				double d7 = d2 * 100.0D;
				double d8 = Math.atan2(d0, d2);
				double d9 = Math.sin(d8);
				double d10 = Math.cos(d8);
				double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
				double d12 = Math.sin(d11);
				double d13 = Math.cos(d11);
				double d14 = randomsource.nextDouble() * Math.PI * 2.0D;
				double d15 = Math.sin(d14);
				double d16 = Math.cos(d14);

				for(int j = 0; j < 4; ++j)
				{
					double d18 = (double)((j & 2) - 1) * d3;
					double d19 = (double)((j + 1 & 2) - 1) * d3;
					double d21 = d18 * d16 - d19 * d15;
					double d22 = d19 * d16 + d18 * d15;
					double d23 = d21 * d12 + 0.0D * d13;
					double d24 = 0.0D * d12 - d21 * d13;
					double d25 = d24 * d9 - d22 * d10;
					double d26 = d22 * d9 + d24 * d10;
					p_234260_.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
				}
			}
		}
		return p_234260_.end();
	}
}