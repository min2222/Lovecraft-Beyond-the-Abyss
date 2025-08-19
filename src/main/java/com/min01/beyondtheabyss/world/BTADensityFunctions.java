package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.worldgen.Sine;
import com.min01.beyondtheabyss.world.worldgen.SquareRoot;
import com.min01.beyondtheabyss.world.worldgen.XPos;
import com.min01.beyondtheabyss.world.worldgen.ZPos;
import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTADensityFunctions 
{
    public static final DeferredRegister<Codec<? extends DensityFunction>> DENSITY_FUNCTIONS = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Codec<? extends DensityFunction>> SINE = DENSITY_FUNCTIONS.register("sine", () -> Sine.CODEC.codec());
    public static final RegistryObject<Codec<? extends DensityFunction>> SQRT = DENSITY_FUNCTIONS.register("sqrt", () -> SquareRoot.CODEC.codec());
    public static final RegistryObject<Codec<? extends DensityFunction>> X = DENSITY_FUNCTIONS.register("x", () -> XPos.CODEC.codec());
    public static final RegistryObject<Codec<? extends DensityFunction>> Z = DENSITY_FUNCTIONS.register("z", () -> ZPos.CODEC.codec());
}
