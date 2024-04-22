package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.multipart.util.CompoundOrientedBox;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

@Mixin(VoxelShape.class)
public class MixinVoxelShape
{
    @Inject(method = "collide", at = @At("HEAD"), cancellable = true)
    private void hook(final Direction.Axis axis, final AABB box, final double maxDist, final CallbackInfoReturnable<Double> cir)
    {
        if (box instanceof CompoundOrientedBox ob)
        {
            cir.setReturnValue(ob.calculateMaxDistance(axis, VoxelShape.class.cast(this), maxDist));
        }
    }
}
