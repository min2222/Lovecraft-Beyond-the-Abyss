package com.min01.beyondtheabyss.particle;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAParticles
{
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<SimpleParticleType> WATER = PARTICLES.register("water", () -> new SimpleParticleType(false));
}
