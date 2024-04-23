package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.multipart.util.CompoundOrientedBox;

import net.minecraft.world.phys.AABB;

@Mixin(AABB.class)
public class MixinAABB 
{
    @Inject(method = "intersects(Lnet/minecraft/world/phys/AABB;)Z", at = @At("HEAD"), cancellable = true)
    private void hook(final AABB box, final CallbackInfoReturnable<Boolean> cir)
    {
        if (AABB.class.cast(this) instanceof CompoundOrientedBox ob)
        {
            cir.setReturnValue(ob.intersects(box));
        }
    }
}
