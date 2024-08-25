package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBone;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentMiddleBone;
import com.min01.beyondtheabyss.entity.renderer.IModel;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SiamserpentBoneRenderer extends EntityRenderer<EntitySiamserpentBone> implements IModel<EntitySiamserpentBone>
{
	private final ModelSiamserpentBone model;
	private final ModelSiamserpentMiddleBone middleModel;
	
	public SiamserpentBoneRenderer(Context p_174008_) 
	{
		super(p_174008_);
		this.model = new ModelSiamserpentBone(p_174008_.bakeLayer(ModelSiamserpentBone.LAYER_LOCATION));
		this.middleModel = new ModelSiamserpentMiddleBone(p_174008_.bakeLayer(ModelSiamserpentMiddleBone.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntitySiamserpentBone p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		p_114488_.pushPose();
		float f = Mth.rotLerp(p_114487_, p_114485_.yBodyRotO, p_114485_.yBodyRot);
		float f1 = Mth.rotLerp(p_114487_, p_114485_.yHeadRotO, p_114485_.yHeadRot);
		float f2 = f1 - f;
        float f6 = Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot());
        float f8 = Mth.lerp(p_114487_, p_114485_.animationSpeedOld, p_114485_.animationSpeed);
        float f7 = p_114485_.tickCount + p_114487_;
        float f5 = p_114485_.animationPosition - p_114485_.animationSpeed * (1.0F - p_114487_);
		BTAClientUtil.setupRotations(p_114485_, p_114488_, f7, f, p_114487_);
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0, -1.5F, 0);
		VertexConsumer consumer = p_114489_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_114485_)));
		if(p_114485_.getVariant() == 0 || p_114485_.getVariant() == 1)
		{
			this.model.setupAnim(p_114485_, f5, f8, f7, f2, f6);
			this.model.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
		else if(p_114485_.getVariant() == 2)
		{
			this.middleModel.setupAnim(p_114485_, f5, f8, f7, f2, f6);
			this.middleModel.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
		p_114488_.popPose();
	}
	
	@Override
	public HierarchicalModel<EntitySiamserpentBone> getModel(EntitySiamserpentBone entity)
	{
		return entity.getVariant() == 2 ? this.middleModel : this.model;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentBone p_114482_) 
	{
		switch(p_114482_.getVariant())
		{
		case 0:
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_0.png");
		case 1:
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_bone_1.png");
		case 2:
			return new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_middlebone.png");
		}
		return null;
	}
}
