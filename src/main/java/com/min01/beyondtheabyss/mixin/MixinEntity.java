package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.multipart.entity.IMultipart;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

@Mixin(Entity.class)
public abstract class MixinEntity 
{
    @Inject(method = "getBoundingBox", at = @At("RETURN"), cancellable = true)
    private void getBoundingBox(CallbackInfoReturnable<AABB> cir)
    {
        if(Entity.class.cast(this) instanceof IMultipart multipart)
        {
            cir.setReturnValue(multipart.getCompoundBoundingBox(cir.getReturnValue()));
        }
    }
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci)
    {
    	DeepAbyssUtil.handleSubmarineCollision(Entity.class.cast(this));
    }
    
    @Inject(method = "setPosRaw", at = @At("TAIL"))
    private void setPosRaw(double x, double y, double z, CallbackInfo ci)
    {
        if(Entity.class.cast(this) instanceof IMultipart multipart)
        {
        	multipart.onSetPos(x, y, z);
        }
    }

    @Inject(method = "isInWater", at = @At("TAIL"), cancellable = true)
    private void isInWater(CallbackInfoReturnable<Boolean> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		if(living.hasEffect(BTAEffects.AIR_SWIM.get()))
    		{
    			cir.setReturnValue(true);
    		}
    		else if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(false);
    		}
    	}
    }
    
    @Inject(method = "getEyeInFluidType", at = @At("TAIL"), cancellable = true, remap = false)
    private void getEyeInFluidType(CallbackInfoReturnable<FluidType> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		if(living.hasEffect(BTAEffects.AIR_SWIM.get()))
    		{
    			cir.setReturnValue(ForgeMod.WATER_TYPE.get());
    		}
    		else if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(ForgeMod.EMPTY_TYPE.get());
    		}
    	}
    }
}
