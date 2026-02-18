package com.min01.beyondtheabyss.world.worldgen;

import com.mojang.serialization.MapCodec;

import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

//https://github.com/klinbee/More-Density-Functions/blob/1.20.1/common/src/main/java/com/klinbee/moredensityfunctions/densityfunctions/ZPos.java
public class ZPos implements DensityFunction 
{
    private static final MapCodec<ZPos> MAP_CODEC = MapCodec.unit(new ZPos());

    public static final KeyDispatchDataCodec<ZPos> CODEC = KeyDispatchDataCodec.of(MAP_CODEC);

    @Override
    public double compute(FunctionContext pos)
    {
        return pos.blockZ();
    }

    @Override
    public void fillArray(double[] densities, ContextProvider applier)
    {
        applier.fillAllDirectly(densities, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) 
    {
        return visitor.apply(new ZPos());
    }

    @Override
    public double minValue() 
    {
        return -30_000_000D;
    }

    @Override
    public double maxValue() 
    {
        return 30_000_000D;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() 
    {
        return CODEC;
    }
}