package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.model.ModelMutavore;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class MutavoreRenderer extends MobRenderer<EntityMutavore, ModelMutavore>
{
	public MutavoreRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelMutavore(p_174304_.bakeLayer(ModelMutavore.LAYER_LOCATION)), 0.5F);
	}
	
	@Override
	public void render(EntityMutavore p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_)
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		
		Vec3 minePos = BTAClientUtil.getWorldPosition(p_115455_, this.model.root(), new Vec3(0, p_115455_.yBodyRot, 0), new String[] {"body", "mound", "chunk3", "mine"});
		Vec3 minePos2 = BTAClientUtil.getWorldPosition(p_115455_, this.model.root(), new Vec3(0, p_115455_.yBodyRot, 0), new String[] {"body", "mound", "chunk2", "mine2"});
		Vec3 minePos3 = BTAClientUtil.getWorldPosition(p_115455_, this.model.root(), new Vec3(0, p_115455_.yBodyRot, 0), new String[] {"body", "mound", "chunk2", "mine3"});
		Vec3 minePos4 = BTAClientUtil.getWorldPosition(p_115455_, this.model.root(), new Vec3(0, p_115455_.yBodyRot, 0), new String[] {"body", "mound", "chunk3", "mine4"});
		p_115455_.posArray[0] = minePos;
		p_115455_.posArray[1] = minePos2;
		p_115455_.posArray[2] = minePos3;
		p_115455_.posArray[3] = minePos4;
		BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, minePos, 0));
		BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, minePos2, 1));
		BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, minePos3, 2));
		BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, minePos4, 3));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMutavore p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/mutavore.png");
	}
}
