package com.min01.beyondtheabyss.world.deepabyss;

import javax.annotation.Nullable;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.IForgeDimensionSpecialEffects;

public class DeepAbyssSkyRenderer implements IForgeDimensionSpecialEffects
{
	private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
	private final Minecraft minecraft = Minecraft.getInstance();
	private final ClientLevel level = this.minecraft.level;
	
	@Nullable
	private VertexBuffer skyBuffer;

	@Nullable
	private VertexBuffer darkBuffer;
	
	@Nullable
	private VertexBuffer starBuffer;
	
	public DeepAbyssSkyRenderer()
	{
		this.createLightSky();
		this.createDarkSky();
		this.createStars();
	}
	
	private void createLightSky()
	{
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		if (this.skyBuffer != null) 
		{
			this.skyBuffer.close();
		}

		this.skyBuffer = new VertexBuffer();
		BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = buildSkyDisc(bufferbuilder, 16.0F);
		this.skyBuffer.bind();
		this.skyBuffer.upload(bufferbuilder$renderedbuffer);
		VertexBuffer.unbind();
	}
	
	private void createStars() 
	{
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		if (this.starBuffer != null) 
		{
			this.starBuffer.close();
		}

		this.starBuffer = new VertexBuffer();
		BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.drawStars(bufferbuilder);
		this.starBuffer.bind();
		this.starBuffer.upload(bufferbuilder$renderedbuffer);
		VertexBuffer.unbind();
	}
	
	private void createDarkSky()
	{
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		if (this.darkBuffer != null) 
		{
			this.darkBuffer.close();
		}

		this.darkBuffer = new VertexBuffer();
		BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = buildSkyDisc(bufferbuilder, -16.0F);
		this.darkBuffer.bind();
		this.darkBuffer.upload(bufferbuilder$renderedbuffer);
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
			if (d4 < 1.0D && d4 > 0.01D)
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
	
	private static BufferBuilder.RenderedBuffer buildSkyDisc(BufferBuilder p_234268_, float p_234269_)
	{
		float f = Math.signum(p_234269_) * 512.0F;
		RenderSystem.setShader(GameRenderer::getPositionShader);
		p_234268_.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
		p_234268_.vertex(0.0D, (double)p_234269_, 0.0D).endVertex();

		for(int i = -180; i <= 180; i += 45) 
		{
			p_234268_.vertex((double)(f * Mth.cos((float)i * ((float)Math.PI / 180F))), (double)p_234269_, (double)(512.0F * Mth.sin((float)i * ((float)Math.PI / 180F)))).endVertex();
		}

		return p_234268_.end();
	}
	
	public void renderSky(PoseStack p_202424_, Matrix4f p_202425_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_) 
	{
		if (!p_202428_) 
		{
			RenderSystem.disableTexture();
			Vec3 vec3 = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), p_202426_);
			float f = (float)vec3.x;
			float f1 = (float)vec3.y;
			float f2 = (float)vec3.z;
            FogRenderer.levelFogColor();
            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
            ShaderInstance shaderinstance = RenderSystem.getShader();
            this.skyBuffer.bind();
            this.skyBuffer.drawWithShader(p_202424_.last().pose(), p_202425_, shaderinstance);
            VertexBuffer.unbind();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            RenderSystem.enableTexture();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            p_202424_.pushPose();
            float f11 = 1.0F - this.level.getRainLevel(p_202426_);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
            p_202424_.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
            p_202424_.mulPose(Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(p_202426_) * 360.0F));
            Matrix4f matrix4f1 = p_202424_.last().pose();
            float f12 = 20.0F;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, MOON_LOCATION);
            int k = this.level.getMoonPhase();
            int l = k % 4;
            int i1 = k / 4 % 2;
            float f13 = (float)(l + 0) / 4.0F;
            float f14 = (float)(i1 + 0) / 2.0F;
            float f15 = (float)(l + 1) / 4.0F;
            float f16 = (float)(i1 + 1) / 2.0F;
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
            bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
            bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
            bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
            BufferUploader.drawWithShader(bufferbuilder.end());
            RenderSystem.disableTexture();
            float f10 = this.level.getStarBrightness(p_202426_) * f11;
            if (f10 > 0.0F) 
            {
            	RenderSystem.setShaderColor(f10, f10, f10, f10);
            	FogRenderer.setupNoFog();
            	this.starBuffer.bind();
            	this.starBuffer.drawWithShader(p_202424_.last().pose(), p_202425_, GameRenderer.getPositionShader());
            	VertexBuffer.unbind();
            	p_202429_.run();
            }
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            p_202424_.popPose();
            RenderSystem.disableTexture();
            RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
            double d0 = this.minecraft.player.getEyePosition(p_202426_).y - this.level.getLevelData().getHorizonHeight(this.level);
            if (d0 < 0.0D) 
            {
            	p_202424_.pushPose();
                p_202424_.translate(0.0D, 12.0D, 0.0D);
                this.darkBuffer.bind();
                this.darkBuffer.drawWithShader(p_202424_.last().pose(), p_202425_, shaderinstance);
                VertexBuffer.unbind();
                p_202424_.popPose();
            }

            if (this.level.effects().hasGround()) 
            {
            	RenderSystem.setShaderColor(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0F);
            } 
            else 
            {
            	RenderSystem.setShaderColor(f, f1, f2, 1.0F);
            }

            RenderSystem.enableTexture();
            RenderSystem.depthMask(true);
		}
	}
	
	@Override
	public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog)
	{
		return true;
	}
}
