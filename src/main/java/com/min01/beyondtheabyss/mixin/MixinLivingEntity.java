package com.min01.beyondtheabyss.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;

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
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidType;isAir()Z"), method = "baseTick")
	private boolean baseTick(FluidType instance)
	{
		if(!(LivingEntity.class.cast(this) instanceof EntitySubmarine))
		{
			List<EntitySubmarine> list = LivingEntity.class.cast(this).level.getEntitiesOfClass(EntitySubmarine.class, LivingEntity.class.cast(this).getBoundingBox());
			if(list.size() > 0)
			{
				return true;
			}
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
		
		if(!(LivingEntity.class.cast(this) instanceof EntitySubmarine))
		{
			List<EntitySubmarine> list = LivingEntity.class.cast(this).level.getEntitiesOfClass(EntitySubmarine.class, LivingEntity.class.cast(this).getBoundingBox());
			if(list.size() > 0)
			{
				ci.setReturnValue(false);
			}
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
		
		if(!(LivingEntity.class.cast(this) instanceof EntitySubmarine))
		{
			List<EntitySubmarine> list = LivingEntity.class.cast(this).level.getEntitiesOfClass(EntitySubmarine.class, LivingEntity.class.cast(this).getBoundingBox());
			if(list.size() > 0)
			{
				ci.setReturnValue(false);
			}
		}
	}
}
