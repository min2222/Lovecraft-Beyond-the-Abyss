package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.cerbon.CompoundOrientedBox;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

@Mixin(VoxelShape.class)
public class MixinVoxelShape
{
    @Inject(method = "collide", at = @At("HEAD"), cancellable = true)
    private void collide(Direction.Axis axis, AABB box, double maxDist, CallbackInfoReturnable<Double> cir)
    {
        if(box instanceof CompoundOrientedBox ob)
        {
            cir.setReturnValue(ob.calculateMaxDistance(axis, VoxelShape.class.cast(this), maxDist));
        }
    }
}
