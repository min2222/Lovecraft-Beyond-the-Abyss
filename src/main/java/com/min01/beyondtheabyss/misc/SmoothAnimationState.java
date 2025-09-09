package com.min01.beyondtheabyss.misc;

import org.joml.Vector3f;

import com.min01.beyondtheabyss.animation.KeyframePlayerAnimations;
import com.min01.beyondtheabyss.item.animation.KeyframeItemAnimations;
import com.min01.beyondtheabyss.item.model.HierarchicalItemModel;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmoothAnimationState extends AnimationState
{
	public static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
	
	public float factorOld;
	public float factor = 1.0F;
	public float threshold;
	
	public SmoothAnimationState() 
	{
		this(0.9F);
	}
	
	public SmoothAnimationState(float threshold)
	{
		this.threshold = threshold;
	}
	
	public void updateWhen(boolean updateWhen, int tickCount)
	{
	    this.factorOld = this.factor;
	    float target = updateWhen ? 0.0F : 1.0F;
	    float lerpSpeed = 0.4F;
	    this.factor += (target - this.factor) * lerpSpeed;
	    this.factor = Mth.clamp(this.factor, 0.0F, 1.0F);
	    this.animateWhen(this.factor <= this.threshold + 0.0001F, tickCount);
	}
	
	public float factor(float partialTicks)
	{
		return Mth.lerp(partialTicks, this.factorOld, this.factor);
	}
	
	@OnlyIn(Dist.CLIENT)
	public <T extends LivingEntity> void animatePlayer(T entity, String name, PlayerModel<T> model, AnimationDefinition definition, float ageInTicks) 
	{
		this.updateTime(ageInTicks, 1.0F);
		this.ifStarted(t -> 
		{
			float totalFactor = this.factor(BTAClientUtil.MC.getFrameTime());
			KeyframePlayerAnimations.animate(model, definition, t.getAccumulatedTime(), 1.0F - totalFactor, ANIMATION_VECTOR_CACHE);
		});
	}
	
	@OnlyIn(Dist.CLIENT)
	public void animateItem(ItemStack stack, String name, HierarchicalItemModel model, AnimationDefinition definition, float ageInTicks) 
	{
		this.updateTime(ageInTicks, 1.0F);
		this.ifStarted(t -> 
		{
			float totalFactor = this.factor(BTAClientUtil.MC.getFrameTime());
			KeyframeItemAnimations.animate(model, definition, t.getAccumulatedTime(), 1.0F - totalFactor, ANIMATION_VECTOR_CACHE);
		});
	}

	@OnlyIn(Dist.CLIENT)
	public void animate(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks) 
	{
		this.animate(model, definition, ageInTicks, 0.0F);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void animate(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float factor) 
	{
		this.updateTime(ageInTicks, 1.0F);
		this.ifStarted(t -> 
		{
			float totalFactor = factor + this.factor(BTAClientUtil.MC.getFrameTime());
			KeyframeAnimations.animate(model, definition, t.getAccumulatedTime(), 1.0F - totalFactor, ANIMATION_VECTOR_CACHE);
		});
	}
}