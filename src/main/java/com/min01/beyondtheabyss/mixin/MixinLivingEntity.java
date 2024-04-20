package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity
{
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidType;isAir()Z"), method = "aiStep")
	private boolean aiStep(FluidType instance)
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			return false;
		}
		else
		{
			return instance.isAir();
		}
	}
	
	@Override
	protected void isInWater(CallbackInfoReturnable<Boolean> ci)
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			ci.setReturnValue(true);
		}
	}
	
	@Override
	protected void isOnGround(CallbackInfoReturnable<Boolean> ci) 
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			ci.setReturnValue(false);
		}
	}
	
	@Override
	protected void isEyeInFluid(TagKey<Fluid> p_204030_, CallbackInfoReturnable<Boolean> ci)
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()) && p_204030_ == FluidTags.WATER)
		{
			ci.setReturnValue(true);
		}
	}
}
