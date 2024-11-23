package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTABiomes 
{
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(Registry.BIOME_REGISTRY, BeyondtheAbyss.MODID);
	
	public static final ResourceKey<Biome> DEATH_VALLEY_KEY = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, "death_valley"));
	public static final ResourceKey<Biome> SPIRE_HOLLOW_KEY = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, "spire_hollow"));
	
	public static final RegistryObject<Biome> SPIRE_HOLLOW = BIOMES.register("spire_hollow", () -> spireHollow());
	
	public static Biome spireHollow()
	{
		AmbientMoodSettings moodSettings = new AmbientMoodSettings(SoundEvents.AMBIENT_CAVE, 6000, 8, 2);
		BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder().skyColor(924212).fogColor(464172).waterColor(922932).waterFogColor(1052723).ambientMoodSound(moodSettings).build();
		return new Biome.BiomeBuilder().temperature(0.5F).downfall(0.5F).precipitation(Precipitation.NONE).specialEffects(specialEffects).mobSpawnSettings(new MobSpawnSettings.Builder().build()).generationSettings(new BiomeGenerationSettings.Builder().build()).build();
	}
}
