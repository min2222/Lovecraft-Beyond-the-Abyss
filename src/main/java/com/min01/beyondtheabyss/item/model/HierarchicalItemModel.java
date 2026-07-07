package com.min01.beyondtheabyss.item.model;

import java.util.Optional;

import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;

public abstract class HierarchicalItemModel extends Model
{
	public HierarchicalItemModel()
	{
		super(RenderType::entityCutoutNoCull);
	}
	
	public abstract void setupAnim(ItemStack stack, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch);

	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha)
	{
		this.root().render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
	}

	public abstract ModelPart root();	

	public Optional<ModelPart> getAnyDescendantWithName(String name) 
	{
		return this.root().getAllParts().filter(t -> 
		{
			return t.hasChild(name);
		}).findFirst().map(t ->
		{
			return t.getChild(name);
		});
	}

	public void animate(ItemStack stack, SmoothAnimationState state, AnimationDefinition definition, float ageInTicks)
	{
		if(definition == null)
			return;
		state.animateItem(this, definition, ageInTicks);
	}
}
