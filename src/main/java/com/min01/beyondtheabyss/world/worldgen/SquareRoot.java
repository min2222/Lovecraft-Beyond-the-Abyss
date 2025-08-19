package com.min01.beyondtheabyss.world.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

//https://github.com/klinbee/More-Density-Functions/blob/1.20.1/common/src/main/java/com/klinbee/moredensityfunctions/densityfunctions/SquareRoot.java
public record SquareRoot(DensityFunction arg, double minOutput, double maxOutput, DensityFunction errorArg) implements DensityFunction 
{
    private static final MapCodec<SquareRoot> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("argument").forGetter(SquareRoot::arg),
                            Codec.DOUBLE.fieldOf("min_output").forGetter(SquareRoot::minOutput),
                            Codec.DOUBLE.fieldOf("max_output").forGetter(SquareRoot::maxOutput),
                            DensityFunction.HOLDER_HELPER_CODEC.fieldOf("error_argument").forGetter(SquareRoot::errorArg)).apply(instance, SquareRoot::new));
    
    public static final KeyDispatchDataCodec<SquareRoot> CODEC = KeyDispatchDataCodec.of(MAP_CODEC);

    private static double eval(double density) 
    {
        return StrictMath.sqrt(density);
    }

    @Override
    public double compute(FunctionContext pos)
    {
        double discriminantValue = this.arg.compute(pos);

        if(discriminantValue < 0) 
        {
            return this.errorArg.compute(pos);
        }
        return eval(discriminantValue);
    }

    @Override
    public void fillArray(double[] densities, ContextProvider applier)
    {
        applier.fillAllDirectly(densities, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor)
    {
        return visitor.apply(new SquareRoot(this.arg.mapAll(visitor), this.minOutput, this.maxOutput, this.errorArg.mapAll(visitor)));
    }

    @Override
    public double minValue()
    {
        if(this.arg.minValue() < 0) 
        {
            return this.errorArg.minValue();
        }
        return eval(this.arg.minValue());
    }

    @Override
    public double maxValue() 
    {
        if(this.arg.maxValue() < 0)
        {
            return this.errorArg.maxValue();
        }
        return eval(this.arg.maxValue());
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() 
    {
        return CODEC;
    }
}