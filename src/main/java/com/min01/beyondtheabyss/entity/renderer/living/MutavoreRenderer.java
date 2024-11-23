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
		for(int i = 0; i < 5; i++)
		{
			int num = i + 1;
			Vec3 tentaclePos = BTAClientUtil.getWorldPosition(p_115455_, this.model.root(), new Vec3(0.0F, p_115455_.yBodyRot, 0.0F), new String[] {"mutavore", "tentacles", "tentacle" + num});
			p_115455_.posArray[i] = tentaclePos;
		    BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, tentaclePos,i));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMutavore p_115812_)
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/mutavore.png");
	}
}
