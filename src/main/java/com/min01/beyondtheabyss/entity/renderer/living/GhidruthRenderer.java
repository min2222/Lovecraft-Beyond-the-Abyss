package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.renderer.layer.BloomLayer;
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
	public GhidruthRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelGhidruth(p_174304_.bakeLayer(ModelGhidruth.LAYER_LOCATION)), 0.5F);
		this.addLayer(new BloomLayer<>(this, this.model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_layer.png")));
		this.addLayer(new BloomLayer<>(this, this.model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_dash_eye_layer.png")));
	}
	
	@Override
	protected void scale(EntityGhidruth p_115314_, PoseStack p_115315_, float p_115316_)
	{
		p_115315_.scale(1.5F, 1.5F, 1.5F);
	}
	
	@Override
	public void render(EntityGhidruth p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) 
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		Vec3 tailPos = BTAClientUtil.getWorldPosition(p_115455_, this.model.root(), new Vec3(0, p_115455_.yBodyRot, 0), new String[] {"ghidruth", "Head", "Body", "RearBody"});
		p_115455_.posArray[0] = tailPos;
		BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, tailPos, 0));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGhidruth p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth.png");
	}
}
