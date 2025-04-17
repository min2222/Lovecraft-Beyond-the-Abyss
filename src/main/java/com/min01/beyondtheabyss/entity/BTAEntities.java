package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalBulbray;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalHermitCrab;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAmarumGhost;
import com.min01.beyondtheabyss.entity.deepabyss.EntityChainTrapMaw;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepAbyssPortal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFallenDiver;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGloomfish;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosBody;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosTail;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.deepabyss.EntityPhasmozoa;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormBody;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.projectile.EntityPutridBubble;
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
	public static final RegistryObject<EntityType<EntitySubmarine>> SUBMARINE = registerEntity("submarine", createBuilder(EntitySubmarine::new, MobCategory.MISC).sized(6.0F, 6.0F));
	public static final RegistryObject<EntityType<EntityChainTrapMaw>> CHAIN_TRAP_MAW = registerEntity("chain_trap_maw", createBuilder(EntityChainTrapMaw::new, MobCategory.MISC).sized(0.25F, 0.25F));

	//projectile
	public static final RegistryObject<EntityType<EntityThrownHarpoon>> THROWN_HARPOON = registerEntity("thrown_harpoon", EntityType.Builder.<EntityThrownHarpoon>of(EntityThrownHarpoon::new, MobCategory.MISC).sized(0.5F, 0.5F));
	public static final RegistryObject<EntityType<EntityPutridBubble>> PUTRID_BUBBLE = registerEntity("putrid_bubble", createBuilder(EntityPutridBubble::new, MobCategory.MISC).sized(0.75F, 0.75F));
	
	//living
	public static final RegistryObject<EntityType<EntityGhidruth>> GHIDRUTH = registerEntity("ghidruth", createBuilder(EntityGhidruth::new, MobCategory.WATER_CREATURE).sized(5.2F, 4.5F));
	public static final RegistryObject<EntityType<EntityDeepVampire>> DEEP_VAMPIRE = registerEntity("deep_vampire", createBuilder(EntityDeepVampire::new, MobCategory.WATER_CREATURE).sized(0.5F, 0.7F));
	public static final RegistryObject<EntityType<EntityRunicFish>> RUNIC_FISH = registerEntity("runic_fish", createBuilder(EntityRunicFish::new, MobCategory.WATER_CREATURE).sized(0.5F, 0.7F));
	public static final RegistryObject<EntityType<EntityLatcher>> LATCHER = registerEntity("latcher", createBuilder(EntityLatcher::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.3F));
	public static final RegistryObject<EntityType<EntityAbyssalHermitCrab>> ABYSSAL_HERMIT_CRAB = registerEntity("abyssal_hermit_crab", createBuilder(EntityAbyssalHermitCrab::new, MobCategory.WATER_CREATURE).sized(1.2F, 1.0F));
	public static final RegistryObject<EntityType<EntityAbyssalBulbray>> ABYSSAL_BULBRAY = registerEntity("abyssal_bulbray", createBuilder(EntityAbyssalBulbray::new, MobCategory.WATER_CREATURE).sized(3.5F, 0.8F));
	public static final RegistryObject<EntityType<EntityPhasmozoa>> PHASMOZOA = registerEntity("phasmozoa", createBuilder(EntityPhasmozoa::new, MobCategory.WATER_CREATURE).sized(1.2F, 2.4F));
	public static final RegistryObject<EntityType<EntityAmarumGhost>> AMARUM_GHOST = registerEntity("amarum_ghost", createBuilder(EntityAmarumGhost::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.25F));
	public static final RegistryObject<EntityType<EntityGnasher>> GNASHER = registerEntity("gnasher", createBuilder(EntityGnasher::new, MobCategory.WATER_CREATURE).sized(0.875F, 0.75F));
	public static final RegistryObject<EntityType<EntitySiamserpentHead>> SIAMSERPENT_HEAD = registerEntity("siamserpent_head", createBuilder(EntitySiamserpentHead::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F).setTrackingRange(100).setUpdateInterval(1));
 	public static final RegistryObject<EntityType<EntitySiamserpentBone>> SIAMSERPENT_BONE = registerEntity("siamserpent_bone", createBuilder(EntitySiamserpentBone::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntityFallenDiver>> FALLEN_DIVER = registerEntity("fallen_diver", createBuilder(EntityFallenDiver::new, MobCategory.WATER_CREATURE).sized(0.6F, 1.95F));
	public static final RegistryObject<EntityType<EntitySpineWormHead>> SPINE_WORM_HEAD = registerEntity("spine_worm_head", createBuilder(EntitySpineWormHead::new, MobCategory.WATER_CREATURE).sized(0.75F, 0.6875F));
	public static final RegistryObject<EntityType<EntitySpineWormBody>> SPINE_WORM_BODY = registerEntity("spine_worm_body", createBuilder(EntitySpineWormBody::new, MobCategory.WATER_CREATURE).sized(0.75F, 0.6875F));
	public static final RegistryObject<EntityType<EntityMutavore>> MUTAVORE = registerEntity("mutavore", createBuilder(EntityMutavore::new, MobCategory.WATER_CREATURE).sized(2.9375F, 2.125F));
	public static final RegistryObject<EntityType<EntityGloomfish>> GLOOMFISH = registerEntity("gloomfish", createBuilder(EntityGloomfish::new, MobCategory.WATER_CREATURE).sized(0.4F, 0.4F));
	public static final RegistryObject<EntityType<EntityKormosHead>> KORMOS_HEAD = registerEntity("kormos_head", createBuilder(EntityKormosHead::new, MobCategory.WATER_CREATURE).sized(5.0F, 4.125F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntityKormosBody>> KORMOS_BODY = registerEntity("kormos_body", createBuilder(EntityKormosBody::new, MobCategory.WATER_CREATURE).sized(5.0F, 4.125F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntityKormosTail>> KORMOS_TAIL = registerEntity("kormos_tail", createBuilder(EntityKormosTail::new, MobCategory.WATER_CREATURE).sized(5.0F, 4.125F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
 	public static final RegistryObject<EntityType<EntityCorpseAngler>> CORPSE_ANGLER = registerEntity("corpse_angler", createBuilder(EntityCorpseAngler::new, MobCategory.WATER_CREATURE).sized(3.0F, 4.75F));
	
	public static <T extends Entity> EntityType.Builder<T> createBuilder(EntityType.EntityFactory<T> factory, MobCategory category)
	{
		return EntityType.Builder.<T>of(factory, category);
	}
	
	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) 
	{
		return ENTITY_TYPES.register(name, () -> builder.build(new ResourceLocation(BeyondtheAbyss.MODID, name).toString()));
	}
}
