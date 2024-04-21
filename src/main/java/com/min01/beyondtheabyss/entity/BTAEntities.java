package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalBulbray;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalHermitCrab;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.misc.EntityDeepAbyssPortal;
import com.min01.beyondtheabyss.entity.projectile.EntityThrownHarpoon;

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
	public static final RegistryObject<EntityType<EntityDeepAbyssPortal>> DEEP_ABYSS_PORTAL = registerEntity("deep_abyss_portal", createBuilder(EntityDeepAbyssPortal::new, MobCategory.MISC).sized(3F, 0.25F));
	public static final RegistryObject<EntityType<EntityBTACameraShake>> BTA_CAMERA_SHAKE = registerEntity("bta_camera_shake", createBuilder(EntityBTACameraShake::new, MobCategory.MISC));
	
	//projectile
	public static final RegistryObject<EntityType<EntityThrownHarpoon>> THROWN_HARPOON = registerEntity("thrown_harpoon", EntityType.Builder.<EntityThrownHarpoon>of(EntityThrownHarpoon::new, MobCategory.MISC).sized(0.5F, 0.5F));
	
	//living
	public static final RegistryObject<EntityType<EntityGhidruth>> GHIDRUTH = registerEntity("ghidruth", createBuilder(EntityGhidruth::new, MobCategory.WATER_CREATURE).sized(5.2F, 4.5F));
	public static final RegistryObject<EntityType<EntityDeepVampire>> DEEP_VAMPIRE = registerEntity("deep_vampire", createBuilder(EntityDeepVampire::new, MobCategory.WATER_CREATURE).sized(0.5F, 0.7F));
	public static final RegistryObject<EntityType<EntityRunicFish>> RUNIC_FISH = registerEntity("runic_fish", createBuilder(EntityRunicFish::new, MobCategory.WATER_CREATURE).sized(0.5F, 0.7F));
	public static final RegistryObject<EntityType<EntityLatcher>> LATCHER = registerEntity("latcher", createBuilder(EntityLatcher::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.3F));
	public static final RegistryObject<EntityType<EntityAbyssalHermitCrab>> ABYSSAL_HERMIT_CRAB = registerEntity("abyssal_hermit_crab", createBuilder(EntityAbyssalHermitCrab::new, MobCategory.WATER_CREATURE).sized(1.2F, 1.0F));
	public static final RegistryObject<EntityType<EntityAbyssalBulbray>> ABYSSAL_BULBRAY = registerEntity("abyssal_bulbray", createBuilder(EntityAbyssalBulbray::new, MobCategory.WATER_CREATURE).sized(1.6F, 0.8F));
	
	public static <T extends Entity> EntityType.Builder<T> createBuilder(EntityType.EntityFactory<T> factory, MobCategory category)
	{
		return EntityType.Builder.<T>of(factory, category);
	}
	
	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) 
	{
		return ENTITY_TYPES.register(name, () -> builder.build(new ResourceLocation(BeyondtheAbyss.MODID, name).toString()));
	}
}
