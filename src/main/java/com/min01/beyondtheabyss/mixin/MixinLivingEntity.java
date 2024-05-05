package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity
{
	@Inject(at = @At("HEAD"), method = "canStandOnFluid", cancellable = true)
	private void canStandOnFluid(FluidState state, CallbackInfoReturnable<Boolean> cir)
	{
		if(BTAUtil.isInsideSubmarine(LivingEntity.class.cast(this)))
		{
			cir.setReturnValue(true);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "isAffectedByFluids", cancellable = true)
	protected void isAffectedByFluids(CallbackInfoReturnable<Boolean> cir)
	{
		if(BTAUtil.isInsideSubmarine(LivingEntity.class.cast(this)))
		{
			cir.setReturnValue(false);
		}
	}
	
	@Override
	protected void updateSwimming(CallbackInfo ci) 
	{
		if(BTAUtil.isInsideSubmarine(LivingEntity.class.cast(this)))
		{
			ci.cancel();
		}
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidType;isAir()Z"), method = "aiStep")
	private boolean aiStep(FluidType instance)
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			return false;
		}
		else if(BTAUtil.isInsideSubmarine(LivingEntity.class.cast(this)))
		{
			return true;
		}
		return instance.isAir();
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidType;isAir()Z"), method = "baseTick")
	private boolean baseTick(FluidType instance)
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			return false;
		}
		else if(BTAUtil.isInsideSubmarine(LivingEntity.class.cast(this)))
		{
			return true;
		}
		return instance.isAir();
	}
	
	@Override
	protected void isInWater(CallbackInfoReturnable<Boolean> ci)
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			ci.setReturnValue(true);
		}
		else if(BTAUtil.isInsideSubmarine(LivingEntity.class.cast(this)))
		{
			ci.setReturnValue(false);
		}
	}
	
	@Override
	protected void isOnGround(CallbackInfoReturnable<Boolean> ci) 
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			ci.setReturnValue(false);
		}
		else if(BTAUtil.isInsideSubmarine(LivingEntity.class.cast(this)))
		{
			ci.setReturnValue(true);
		}
	}
	
	@Override
	protected void isEyeInFluid(TagKey<Fluid> p_204030_, CallbackInfoReturnable<Boolean> ci)
	{
		if(LivingEntity.class.cast(this).hasEffect(BTAEffects.AIR_SWIM.get()) && p_204030_ == FluidTags.WATER)
		{
			ci.setReturnValue(true);
		}
		else if(BTAUtil.isInsideSubmarine(LivingEntity.class.cast(this)))
		{
			ci.setReturnValue(false);
		}
	}
}
