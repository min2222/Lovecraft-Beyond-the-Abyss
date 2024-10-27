package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyBoneFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyBoneSpikeFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyCoralTreeFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleyFossilFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.DeathValleySpineFeature;
import com.min01.beyondtheabyss.world.feature.deepabyss.ListFeatureConfiguration;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAFeatures 
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPINES = FEATURES.register("spines", () -> new DeathValleySpineFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ListFeatureConfiguration>> BONES = FEATURES.register("bones", () -> new DeathValleyBoneFeature(ListFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ListFeatureConfiguration>> BONE_SPIKES = FEATURES.register("bone_spikes", () -> new DeathValleyBoneSpikeFeature(ListFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ListFeatureConfiguration>> CORAL_TREES = FEATURES.register("coral_trees", () -> new DeathValleyCoralTreeFeature(ListFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ListFeatureConfiguration>> FOSSILS = FEATURES.register("fossils", () -> new DeathValleyFossilFeature(ListFeatureConfiguration.CODEC));
}
