package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.multipart.entity.MultipartAwareEntity;
import com.min01.beyondtheabyss.multipart.entity.MultipartEntity;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;

@Mixin(Entity.class)
public abstract class MixinEntity 
{
	@Inject(at = @At("HEAD"), method = "updateSwimming", cancellable = true)
	protected void updateSwimming(CallbackInfo ci)
	{
		
	}
	
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
	
    @Inject(method = "getBoundingBox", at = @At("RETURN"), cancellable = true)
    private void getBoundingBox(CallbackInfoReturnable<AABB> cir)
    {
        if(Entity.class.cast(this) instanceof MultipartEntity multipart)
        {
            cir.setReturnValue(multipart.getCompoundBoundingBox(cir.getReturnValue()));
        }
    }

    @Inject(method = "setPosRaw", at = @At("TAIL"))
    private void setPosRaw(double x, double y, double z, CallbackInfo ci)
    {
        if(Entity.class.cast(this) instanceof MultipartAwareEntity multipart)
        {
        	multipart.onSetPos(x, y, z);
        }
    }
}
