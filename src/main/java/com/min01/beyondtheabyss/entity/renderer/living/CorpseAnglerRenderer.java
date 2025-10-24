package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.entity.model.ModelCorpseAngler;
import com.min01.beyondtheabyss.entity.renderer.layer.GlowingLayer;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class CorpseAnglerRenderer extends MobRenderer<EntityCorpseAngler, ModelCorpseAngler>
{
	public CorpseAnglerRenderer(Context p_174304_)
	{
		super(p_174304_, new ModelCorpseAngler(p_174304_.bakeLayer(ModelCorpseAngler.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GlowingLayer<>(this, this.model, new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/corpse_angler_layer.png")));
	}
	
	@Override
	public void render(EntityCorpseAngler p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_)
	{
		super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
		Vec3 pos = BTAClientUtil.getWorldPosition(p_115455_, this.model.root(), new Vec3(0, p_115455_.yBodyRot, 0), "corpse_angler", "angler", "1", "2", "3", "4", "5", "6", "Bait", "Gnasher");
		p_115455_.posArray[0] = pos;
		BTANetwork.sendToServer(new UpdatePosArrayPacket(p_115455_, pos, 0));
	}
	
	@Override
	protected void setupRotations(EntityCorpseAngler p_116226_, PoseStack p_116227_, float p_116228_, float p_116229_, float p_116230_) 
	{
		super.setupRotations(p_116226_, p_116227_, p_116228_, p_116229_, p_116230_);
		if(!p_116226_.isInWater()) 
		{
			p_116227_.translate(1.5F, 0.0F, 0.0F);
			p_116227_.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
	}

	@Override
	public ResourceLocation getTextureLocation(EntityCorpseAngler p_115812_) 
	{
		return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/corpse_angler.png");
	}
}
