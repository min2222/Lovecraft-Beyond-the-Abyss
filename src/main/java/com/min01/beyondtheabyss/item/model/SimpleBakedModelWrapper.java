package com.min01.beyondtheabyss.item.model;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.BakedModelWrapper;

public class SimpleBakedModelWrapper extends BakedModelWrapper<BakedModel> 
{
	private final BakedModel bakedModel;
	
	public SimpleBakedModelWrapper(BakedModel guiModel, BakedModel bakedModel) 
	{
		super(guiModel);
		this.bakedModel = bakedModel;
	}
	
	@Override
	public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) 
	{
		switch(cameraTransformType)
		{
		case FIRST_PERSON_LEFT_HAND:
			return this.bakedModel.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		case FIRST_PERSON_RIGHT_HAND:
			return this.bakedModel.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		case FIXED:
			return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		case GROUND:
			return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		case GUI:
			return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		case HEAD:
			return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		case NONE:
			return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		case THIRD_PERSON_LEFT_HAND:
			return this.bakedModel.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		case THIRD_PERSON_RIGHT_HAND:
			return this.bakedModel.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		default:
			return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
		}
	}
}
