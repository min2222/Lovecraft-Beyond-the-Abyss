package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;

@Mixin(Entity.class)
public abstract class MixinEntity 
{
	@Inject(at = @At("HEAD"), method = "isInWater", cancellable = true)
	protected void isInWater(CallbackInfoReturnable<Boolean> ci)
	{
		
	}
	
	@Inject(at = @At("HEAD"), method = "isOnGround", cancellable = true)
	protected void isOnGround(CallbackInfoReturnable<Boolean> ci)
	{
		
	}
	
	@Inject(at = @At("HEAD"), method = "isEyeInFluid", cancellable = true)
	protected void isEyeInFluid(TagKey<Fluid> p_204030_, CallbackInfoReturnable<Boolean> ci)
	{
		
	}
}
