package com.min01.beyondtheabyss.item.model;

import java.util.Optional;

import com.min01.beyondtheabyss.item.animation.KeyframeItemAnimations;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;

public abstract class HierarchicalItemModel extends Model
{
	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
	
	public HierarchicalItemModel()
	{
		super(RenderType::entityCutoutNoCull);
	}
	
	public abstract void setupAnim(ItemStack p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_);

	@Override
	public void renderToBuffer(PoseStack p_170625_, VertexConsumer p_170626_, int p_170627_, int p_170628_, float p_170629_, float p_170630_, float p_170631_, float p_170632_) 
	{
		this.root().render(p_170625_, p_170626_, p_170627_, p_170628_, p_170629_, p_170630_, p_170631_, p_170632_);
	}

	public abstract ModelPart root();

	public Optional<ModelPart> getAnyDescendantWithName(String p_233394_) 
	{
		return this.root().getAllParts().filter((p_233400_) -> 
		{
			return p_233400_.hasChild(p_233394_);
		}).findFirst().map((p_233397_) ->
		{
			return p_233397_.getChild(p_233394_);
		});
	}

	protected void animate(ItemStack stack, String name, AnimationDefinition p_233383_, float p_233384_)
	{
		this.animate(stack, name, p_233383_, p_233384_, 1.0F);
	}

	protected void animate(ItemStack stack, String name, AnimationDefinition p_233387_, float p_233388_, float p_233389_) 
	{
		AnimationState state = BTAUtil.getItemAnimation(stack, name);
		state.updateTime(p_233388_, p_233389_);	
		state.ifStarted((p_233392_) ->
		{
			BTAUtil.writeAnimationTime(stack.getOrCreateTag(), name, p_233392_);
			KeyframeItemAnimations.animate(this, p_233387_, p_233392_.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE);
		});
	}
}
