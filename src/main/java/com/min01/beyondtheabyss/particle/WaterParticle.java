package com.min01.beyondtheabyss.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WaterParticle extends TextureSheetParticle
{
	public static final Vec3 COLOR = Vec3.fromRGB24(29359);
	public static final ParticleRenderType PARTICLE_SHEET_ADDITIVE = new ParticleRenderType()
	{
		@SuppressWarnings("deprecation")
		@Override
		public void begin(BufferBuilder p_107455_, TextureManager p_107456_)
		{
			RenderSystem.depthMask(false);
			RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			p_107455_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
		}
		
		@Override
		public void end(Tesselator p_107458_) 
		{
			p_107458_.end();
            RenderSystem.depthMask(true);
			RenderSystem.disableBlend();
			RenderSystem.defaultBlendFunc();
		}

		@Override
		public String toString()
		{
			return "PARTICLE_SHEET_ADDITIVE";
		}
	};
	
	public WaterParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_) 
	{
		super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
		this.lifetime = 50;
		this.quadSize = 1.5F;
		this.setColor((float)COLOR.x, (float)COLOR.y, (float)COLOR.z);
	}
	
	@Override
	public ParticleRenderType getRenderType() 
	{
		return PARTICLE_SHEET_ADDITIVE;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		this.quadSize -= 0.05F;
	}
	
	@Override
	public int getLightColor(float p_106821_)
	{
		return LightTexture.FULL_BRIGHT;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType>
	{
		private final SpriteSet sprite;

		public Provider(SpriteSet p_108170_) 
		{
			this.sprite = p_108170_;
		}
	      
		@Override
		public Particle createParticle(SimpleParticleType p_107421_, ClientLevel p_107422_, double p_107423_, double p_107424_, double p_107425_, double p_107426_, double p_107427_, double p_107428_) 
		{
			WaterParticle particle = new WaterParticle(p_107422_, p_107423_, p_107424_, p_107425_, p_107426_, p_107427_, p_107428_);
			particle.pickSprite(this.sprite);
			return particle;
		}
	}
}