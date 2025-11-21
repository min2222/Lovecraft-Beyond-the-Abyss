package com.min01.beyondtheabyss.entity.renderer;

import java.util.Random;

import org.joml.Vector4f;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.projectile.EntityForneusMagic;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ForneusMagicRenderer extends EntityRenderer<EntityForneusMagic>
{
	public final Random random = new Random();
	public ForneusMagicRenderer(Context p_174008_)
	{
		super(p_174008_);
	}
	
	@Override
	public void render(EntityForneusMagic p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
        float time = (BTAClientUtil.MC.level.getGameTime() + p_114487_) / 20.0F;
		if(p_114485_.tickCount >= 100 && p_114485_.cylAlpha > 0.0F)
		{
		    p_114488_.pushPose();
			Vector4f color = new Vector4f(1, 1, 1, Math.max(p_114485_.cylAlpha - 0.5F, 0.0F));
		    Vector4f color2 = new Vector4f(1, 1, 1, p_114485_.cylAlpha);
		    if(p_114485_.rot != null)
		    {
		        Vec3 pos = BTAUtil.getLookPos(p_114485_.rot, Vec3.ZERO, 0, 0, p_114485_.spSize);
		        p_114488_.translate(pos.x, pos.y, pos.z);
		        p_114488_.mulPose(Axis.YP.rotationDegrees(-p_114485_.rot.y));
		        p_114488_.mulPose(Axis.XP.rotationDegrees(p_114485_.rot.x));
		    }
	        BTAClientUtil.drawCylinder(p_114485_.cylRadius, p_114485_.cylLength, 24, p_114488_, p_114489_, color, LightTexture.FULL_BRIGHT, RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/water.png")), Vec3.ZERO, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		    BTAClientUtil.drawCylinder(p_114485_.cylRadius + 0.05F, p_114485_.cylLength, 24, p_114488_, p_114489_, color2, LightTexture.FULL_BRIGHT, RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/caustics2.png")), Vec3.ZERO, 0.5F, 1.5F, 0.5F, 0.35F, 1.0F, time / 1.5F);
		    BTAClientUtil.drawCylinder(p_114485_.cylRadius + 0.1F, p_114485_.cylLength, 24, p_114488_, p_114489_, color2, LightTexture.FULL_BRIGHT, RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/caustics.png")), Vec3.ZERO, 0.5F, 1.5F, 0.5F, 0.35F, 1.0F, time);
		    p_114488_.popPose();
		}
	    
		if(p_114485_.spAlpha > 0.0F)
		{
		    p_114488_.pushPose();
		    BTAClientUtil.drawSphere(p_114485_.spSize, 24, 24, -0.5F, p_114488_, p_114489_, new Vec3(1, 1, 1), Math.max(p_114485_.spAlpha - 0.5F, 0.0F), LightTexture.FULL_BRIGHT, RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/water.png")), time, Vec3.ZERO);
		    BTAClientUtil.drawSphere(p_114485_.spSize + 0.05F, 24, 24, -0.5F, p_114488_, p_114489_, new Vec3(1, 1, 1), p_114485_.spAlpha, LightTexture.FULL_BRIGHT, RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/caustics2.png")), time / 1.5F, Vec3.ZERO);
		    BTAClientUtil.drawSphere(p_114485_.spSize + 0.1F, 24, 24, -0.5F, p_114488_, p_114489_, new Vec3(1, 1, 1), p_114485_.spAlpha, LightTexture.FULL_BRIGHT, RenderType.entityTranslucent(new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/caustics.png")), time, Vec3.ZERO);
		    p_114488_.popPose();
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityForneusMagic p_114482_)
	{
		return null;
	}
}
