package com.min01.beyondtheabyss.mixin;

import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class MixinLivingEntity 
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
}
