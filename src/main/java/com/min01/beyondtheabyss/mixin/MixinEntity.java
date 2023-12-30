package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;

@Mixin(Entity.class)
public class MixinEntity 
{
	@Inject(at = @At("HEAD"), method = "isInWater", cancellable = true)
	private void isInWater(CallbackInfoReturnable<Boolean> ci)
	{
		if(Entity.class.cast(this) instanceof LivingEntity living)
		{
			if(living.hasEffect(BTAEffects.AIR_SWIM.get()))
			{
				ci.setReturnValue(true);
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "isOnGround", cancellable = true)
	private void isOnGround(CallbackInfoReturnable<Boolean> ci)
	{
		if(Entity.class.cast(this) instanceof LivingEntity living)
		{
			if(living.hasEffect(BTAEffects.AIR_SWIM.get()))
			{
				ci.setReturnValue(false);
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "isEyeInFluid", cancellable = true)
	private void isEyeInFluid(TagKey<Fluid> p_204030_, CallbackInfoReturnable<Boolean> ci)
	{
		if(Entity.class.cast(this) instanceof LivingEntity living)
		{
			if(living.hasEffect(BTAEffects.AIR_SWIM.get()) && p_204030_ == FluidTags.WATER)
			{
				ci.setReturnValue(true);
			}
		}
	}
}
