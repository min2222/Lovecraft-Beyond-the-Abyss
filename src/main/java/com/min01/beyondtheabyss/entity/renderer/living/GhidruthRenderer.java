package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class GhidruthRenderer extends MobRenderer<EntityGhidruth, ModelGhidruth>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth.png");
	public static final ResourceLocation LAYER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_layer.png");
	public static final ResourceLocation DASH_LAYER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_dash_eye_layer.png");
	public static final String[] TAIL = new String[] {"root2", "Head", "Body", "RearBody", "Tail", "TailPos"};
	public static final String[] BODY = new String[] {"root2", "Head", "Body", "RearBody", "RearBodyPos"};
	public static final String[] HEAD = new String[] {"root2", "Head", "HeadPos"};
	
	public GhidruthRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelGhidruth(p_174304_.bakeLayer(ModelGhidruth.LAYER_LOCATION)), 0);
		this.addLayer(new GlowingLayer<>(this, this.model, LAYER));
		this.addLayer(new GlowingLayer<>(this, this.model, DASH_LAYER));
	}
	
	@Override
	protected void scale(EntityGhidruth p_115314_, PoseStack p_115315_, float p_115316_)
	{
		p_115315_.scale(1.5F, 1.5F, 1.5F);
		p_115315_.translate(0, -0.1F, 0);
	}
	
	@Override
	public void render(EntityGhidruth p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		Vec3 rotation = new Vec3(-p_115455_.getXRot(), p_115455_.yHeadRot, 0.0F);
		Vec3 tailPos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, TAIL);
		Vec3 bodyPos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, BODY);
		Vec3 headPos = BTAClientUtil.getWorldPositionOfMultiPart(p_115455_, this.model.root(), rotation, HEAD);
		p_115455_.posArray[2] = tailPos;
		p_115455_.posArray[1] = bodyPos;
		p_115455_.posArray[0] = headPos;
	    BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, tailPos, 2));
	    BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, bodyPos, 1));
	    BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, headPos, 0));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGhidruth p_115812_)
	{
		return TEXTURE;
	}
}
