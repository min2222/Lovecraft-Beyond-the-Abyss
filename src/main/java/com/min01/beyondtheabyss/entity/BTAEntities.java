package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepAbyssPortal;
import com.min01.beyondtheabyss.entity.deepabyss.living.EntityGhidruth;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAEntities
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BeyondtheAbyss.MODID);
	
	//misc
	public static final RegistryObject<EntityType<EntityDeepAbyssPortal>> DEEP_ABYSS_PORTAL = registerWithSize(EntityDeepAbyssPortal::new, "deep_abyss_portal", MobCategory.MISC, 3F, 0.25F);
	public static final RegistryObject<EntityType<EntityBTACameraShake>> BTA_CAMERA_SHAKE = registerWithoutSize(EntityBTACameraShake::new, "bta_camera_shake", MobCategory.MISC);
	
	//living
	public static final RegistryObject<EntityType<EntityGhidruth>> GHIDRUTH = registerWithSize(EntityGhidruth::new, "ghidruth", MobCategory.WATER_CREATURE, 5.2F, 4.5F);
	
    public static <T extends Entity> RegistryObject<EntityType<T>> registerWithoutSize(EntityType.EntityFactory<T> factory, String name, MobCategory category)
    {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<T>of(factory, category).build(new ResourceLocation(BeyondtheAbyss.MODID, name).toString()));
    }
    
    public static <T extends Entity> RegistryObject<EntityType<T>> registerWithSize(EntityType.EntityFactory<T> factory, String name, MobCategory category, float width, float height) 
    {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.<T>of(factory, category).sized(width, height).build(new ResourceLocation(BeyondtheAbyss.MODID, name).toString()));
    }
}
