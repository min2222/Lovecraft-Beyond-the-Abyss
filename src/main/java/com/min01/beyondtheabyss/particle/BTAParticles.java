package com.min01.beyondtheabyss.particle;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.mojang.serialization.Codec;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAParticles
{
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<ParticleType<DustCloudParticle.DustCloudParticleOption>> DUST_CLOUD = PARTICLES.register("dust_cloud", () -> new ParticleType<DustCloudParticle.DustCloudParticleOption>(false, DustCloudParticle.DustCloudParticleOption.DESERIALIZER) 
	{
        @Override
        public Codec<DustCloudParticle.DustCloudParticleOption> codec()
        {
            return DustCloudParticle.DustCloudParticleOption.CODEC(DUST_CLOUD.get());
        }
    });	
}
