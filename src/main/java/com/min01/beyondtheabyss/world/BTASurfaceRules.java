package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.worldgen.DeathValleyRuleSource;
import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTASurfaceRules 
{
    public static final DeferredRegister<Codec<? extends RuleSource>> RULE_SOURCES = DeferredRegister.create(Registries.MATERIAL_RULE, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<Codec<DeathValleyRuleSource>> DEATH_VALLEY = RULE_SOURCES.register("death_valley", () -> DeathValleyRuleSource.CODEC.codec());
}
