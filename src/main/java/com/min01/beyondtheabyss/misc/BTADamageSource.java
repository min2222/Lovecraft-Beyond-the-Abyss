package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class BTADamageSource
{
    public static final ResourceKey<DamageType> GHIDRUTH_FLESH = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, "ghidruth_flesh"));
    public static final ResourceKey<DamageType> ELECTRONIC = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, "electronic"));
    public static final ResourceKey<DamageType> PUTRID = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, "putrid"));
    public static final ResourceKey<DamageType> TOOTH = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, "tooth"));
    public static final ResourceKey<DamageType> GOLDEN_TOOTH = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, "golden_tooth"));
    public static final ResourceKey<DamageType> SHRAPNEL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BeyondtheAbyss.MODID, "shrapnel"));
    
    public static DamageSource causeGhidruthFleshDamage(RegistryAccess registryAccess)
    {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(GHIDRUTH_FLESH), (Entity) null);
    }
    
    public static DamageSource causeElectronicDamage(RegistryAccess registryAccess, Entity entity)
    {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(ELECTRONIC), entity);
    }
    
    public static DamageSource causePutridDamage(RegistryAccess registryAccess, Entity entity)
    {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(PUTRID), entity);
    }
    
    public static DamageSource causeToothDamage(RegistryAccess registryAccess, Entity entity)
    {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(TOOTH), entity);
    }
    
    public static DamageSource causeGoldenToothDamage(RegistryAccess registryAccess, Entity entity)
    {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(GOLDEN_TOOTH), entity);
    }
    
    public static DamageSource causeShrapnelDamage(RegistryAccess registryAccess, Entity entity)
    {
        return new DamageSource(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(SHRAPNEL), entity);
    }
}
