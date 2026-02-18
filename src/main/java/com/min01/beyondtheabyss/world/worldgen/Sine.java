package com.min01.beyondtheabyss.world.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

//https://github.com/klinbee/More-Density-Functions/blob/1.20.1/common/src/main/java/com/klinbee/moredensityfunctions/densityfunctions/Sine.java
public record Sine(DensityFunction arg) implements DensityFunction
{
    private static final MapCodec<Sine> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("argument").forGetter(Sine::arg)).apply(instance, Sine::new));

    public static final KeyDispatchDataCodec<Sine> CODEC = KeyDispatchDataCodec.of(MAP_CODEC);

    private static double eval(double density) 
    {
        return StrictMath.sin(density);
    }

    @Override
    public double compute(FunctionContext pos) 
    {
        return eval(this.arg.compute(pos));
    }

    @Override
    public void fillArray(double[] densities, ContextProvider applier)
    {
        applier.fillAllDirectly(densities, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) 
    {
        return visitor.apply(new Sine(this.arg.mapAll(visitor)));
    }

    @Override
    public double minValue()
    {
        return -1;
    }

    @Override
    public double maxValue()
    {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() 
    {
        return CODEC;
    }
}
