package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.NetherWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAWorldCarvers 
{
	public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<WorldCarver<CaveCarverConfiguration>> ABYSS_CAVE = CARVERS.register("abyss_cave", () -> new NetherWorldCarver(CaveCarverConfiguration.CODEC));
}
