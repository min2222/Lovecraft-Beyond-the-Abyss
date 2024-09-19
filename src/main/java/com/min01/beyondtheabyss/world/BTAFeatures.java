package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.structure.feature.deepabyss.DeepAbyssBoneFeature;
import com.min01.beyondtheabyss.world.structure.feature.deepabyss.DeepAbyssCoralClawFeature;
import com.min01.beyondtheabyss.world.structure.feature.deepabyss.DeepAbyssCoralMushroomFeature;
import com.min01.beyondtheabyss.world.structure.feature.deepabyss.DeepAbyssCoralTreeFeature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAFeatures 
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CORAL_TREE = FEATURES.register("coral_tree", () -> new DeepAbyssCoralTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CORAL_MUSHROOM = FEATURES.register("coral_mushroom", () -> new DeepAbyssCoralMushroomFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CORAL_CLAW = FEATURES.register("coral_claw", () -> new DeepAbyssCoralClawFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BONES = FEATURES.register("bones", () -> new DeepAbyssBoneFeature(NoneFeatureConfiguration.CODEC));
}
