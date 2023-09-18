package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AbyssEntityType
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<EntityType<EntityGhidruth>> GHIDRUTH = registerWithSize(EntityGhidruth::new, "ghidruth", MobCategory.MONSTER, 4.5F, 3F);
	
    public static <T extends Entity> RegistryObject<EntityType<T>> registerWithSize(EntityType.EntityFactory<T> factory, String name, MobCategory category, float width, float height) 
    {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<T>of(factory, category).sized(width, height).build(new ResourceLocation(BeyondtheAbyss.MODID, name).toString()));
    }
}
