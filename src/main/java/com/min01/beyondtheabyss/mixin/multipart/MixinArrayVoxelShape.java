package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.world.phys.shapes.ArrayVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

@Mixin(ArrayVoxelShape.class)
public interface MixinArrayVoxelShape {

    @Invoker(value = "<init>")
    static ArrayVoxelShape init(final DiscreteVoxelShape shape, final DoubleList xPoints, final DoubleList yPoints, final DoubleList zPoints) {
        throw new AssertionError();
    }
}
