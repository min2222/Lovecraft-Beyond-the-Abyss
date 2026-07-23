package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.min01.beyondtheabyss.util.MirroredCityUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.min01.solomonlib.gravity.GravityAPI;
import com.min01.solomonlib.gravity.GravityCapabilityImpl;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

@Mixin(value = Entity.class, priority = -20000)
public class MixinEntity
{
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci)
    {
    	Entity entity = (Entity) (Object) this;
		if(entity instanceof ItemEntity item)
		{
			if(item.level.dimension() == BTAWorlds.DEEP_ABYSS)
			{
				item.setDeltaMovement(item.getDeltaMovement().subtract(0, 0.01F, 0));
			}
		}
		if(MirroredCityUtil.isUpsideDown(entity))
		{
			GravityCapabilityImpl cap = GravityAPI.getGravityCapability(entity);
			cap.noAnimation = entity.tickCount <= 2;
			cap.noPositionAdjust = entity.tickCount <= 2;
			cap.applyGravityDirectionEffect(Direction.UP, null, Double.MAX_VALUE);
		}
    }

	@Inject(method = "tick", at = @At("TAIL"))
	private void tickTail(CallbackInfo ci) 
	{
		BTAUtil.updateGravity((Entity)(Object)this);
	}
	
	@Inject(method = "checkBelowWorld", at = @At("HEAD"), cancellable = true)
	private void checkBelowWorld(CallbackInfo ci) 
	{
    	Entity entity = (Entity) (Object) this;
		if(entity.level.dimension() == BTAWorlds.OUTER_SPACE)
		{
			ci.cancel();
		}
	}

    @Inject(method = "isInWater", at = @At("HEAD"), cancellable = true)
    private void isInWater(CallbackInfoReturnable<Boolean> cir)
    {
    	Entity entity = (Entity) (Object) this;
    	if(entity instanceof LivingEntity living)
    	{
    		if(BTAUtil.canSwimInAir(living))
    		{
    			cir.setReturnValue(true);
    		}
    		if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(false);
    		}
    	}
    }
    
    @Inject(method = "getFluidTypeHeight", at = @At("HEAD"), cancellable = true, remap = false)
    private void getFluidTypeHeight(FluidType type, CallbackInfoReturnable<Double> cir)
    {
    	Entity entity = (Entity) (Object) this;
    	if(entity instanceof LivingEntity living)
    	{
    		if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(0.0D);
    		}
    	}
    }
    
    @Inject(method = "isInFluidType", at = @At("HEAD"), cancellable = true, remap = false)
    private void isInFluidType(CallbackInfoReturnable<Boolean> cir)
    {
    	Entity entity = (Entity) (Object) this;
    	if(entity instanceof LivingEntity living)
    	{
    		if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(false);
    		}
    	}
    }
    
    @Inject(method = "getEyeInFluidType", at = @At("HEAD"), cancellable = true, remap = false)
    private void getEyeInFluidType(CallbackInfoReturnable<FluidType> cir)
    {
    	Entity entity = (Entity) (Object) this;
    	if(entity instanceof LivingEntity living)
    	{
    		if(BTAUtil.canSwimInAir(living))
    		{
    			cir.setReturnValue(ForgeMod.WATER_TYPE.get());
    		}
    		if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(ForgeMod.EMPTY_TYPE.get());
    		}
    	}
    }
}
