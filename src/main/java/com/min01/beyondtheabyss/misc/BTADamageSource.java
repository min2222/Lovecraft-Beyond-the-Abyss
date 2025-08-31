package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

public class BTADamageSource
{
    public static final ResourceKey<DamageType> GHIDRUTH_FLESH = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, "ghidruth_flesh"));
    public static final ResourceKey<DamageType> ELECTRONIC = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, "electronic"));
    
    public static DamageSource causeGhidruthFleshDamage(RegistryAccess registryAccess)
    {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(GHIDRUTH_FLESH), (LivingEntity) null);
    }
    
    public static DamageSource causeElectronicDamage(RegistryAccess registryAccess, LivingEntity living)
    {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(ELECTRONIC), living);
    }
}
