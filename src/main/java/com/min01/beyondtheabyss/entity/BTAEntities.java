package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityChainTrapMaw;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepAbyssPortal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusBody;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusTail;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFulgastra;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGloomfish;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosBody;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntityKormosTail;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormBody;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySplittedFulgastra;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentBone;
import com.min01.beyondtheabyss.entity.deepabyss.EntityTwinserpentHead;
import com.min01.beyondtheabyss.entity.mirroredcity.EntityOverseer;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.projectile.EntityEnergyBall;
import com.min01.beyondtheabyss.entity.projectile.EntityForneusMagic;
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
	public static final RegistryObject<EntityType<EntityForneusMagic>> FORNEUS_MAGIC = registerEntity("forneus_magic", EntityType.Builder.<EntityForneusMagic>of(EntityForneusMagic::new, MobCategory.MISC).sized(0.5F, 0.5F));
	public static final RegistryObject<EntityType<EntityEnergyBall>> ENERGY_BALL = registerEntity("energy_ball", EntityType.Builder.<EntityEnergyBall>of(EntityEnergyBall::new, MobCategory.MISC).sized(0.375F, 0.375F));

	//living
	public static final RegistryObject<EntityType<EntitySolomon>> SOLOMON = registerEntity("solomon", createBuilder(EntitySolomon::new, MobCategory.CREATURE).sized(0.75F, 2.0F));
	public static final RegistryObject<EntityType<EntityGhidruth>> GHIDRUTH = registerEntity("ghidruth", createBuilder(EntityGhidruth::new, MobCategory.WATER_CREATURE).sized(5.2F, 4.5F));
	public static final RegistryObject<EntityType<EntityGnasher>> GNASHER = registerEntity("gnasher", createBuilder(EntityGnasher::new, MobCategory.WATER_CREATURE).sized(0.875F, 0.75F));
	public static final RegistryObject<EntityType<EntityTwinserpentHead>> TWINSERPENT_HEAD = registerEntity("twinserpent_head", createBuilder(EntityTwinserpentHead::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F).setTrackingRange(100).setUpdateInterval(1));
 	public static final RegistryObject<EntityType<EntityTwinserpentBone>> TWINSERPENT_BONE = registerEntity("twinserpent_bone", createBuilder(EntityTwinserpentBone::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntitySpineWormHead>> SPINE_WORM_HEAD = registerEntity("spine_worm_head", createBuilder(EntitySpineWormHead::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F));
	public static final RegistryObject<EntityType<EntitySpineWormBody>> SPINE_WORM_BODY = registerEntity("spine_worm_body", createBuilder(EntitySpineWormBody::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F));
	public static final RegistryObject<EntityType<EntityGloomfish>> GLOOMFISH = registerEntity("gloomfish", createBuilder(EntityGloomfish::new, MobCategory.WATER_CREATURE).sized(0.4F, 0.4F));
	public static final RegistryObject<EntityType<EntityKormosHead>> KORMOS_HEAD = registerEntity("kormos_head", createBuilder(EntityKormosHead::new, MobCategory.WATER_CREATURE).sized(5.0F, 4.125F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntityKormosBody>> KORMOS_BODY = registerEntity("kormos_body", createBuilder(EntityKormosBody::new, MobCategory.WATER_CREATURE).sized(5.0F, 4.125F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntityKormosTail>> KORMOS_TAIL = registerEntity("kormos_tail", createBuilder(EntityKormosTail::new, MobCategory.WATER_CREATURE).sized(5.0F, 4.125F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntityCorpseAngler>> CORPSE_ANGLER = registerEntity("corpse_angler", createBuilder(EntityCorpseAngler::new, MobCategory.WATER_CREATURE).sized(3.0F, 4.75F));
	public static final RegistryObject<EntityType<EntityMutavore>> MUTAVORE = registerEntity("mutavore", createBuilder(EntityMutavore::new, MobCategory.WATER_CREATURE).sized(3.0F, 3.0F));
	public static final RegistryObject<EntityType<EntityFulgastra>> FULGASTRA = registerEntity("fulgastra", createBuilder(EntityFulgastra::new, MobCategory.WATER_CREATURE).sized(4.875F, 1.5625F));
	public static final RegistryObject<EntityType<EntitySplittedFulgastra>> SPLITTED_FULGASTRA = registerEntity("splitted_fulgastra", createBuilder(EntitySplittedFulgastra::new, MobCategory.WATER_CREATURE).sized(1.625F, 1.4375F));
	public static final RegistryObject<EntityType<EntityForneusHead>> FORNEUS_HEAD = registerEntity("forneus_head", createBuilder(EntityForneusHead::new, MobCategory.WATER_CREATURE).sized(4.0F, 4.0F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntityForneusBody>> FORNEUS_BODY = registerEntity("forneus_body", createBuilder(EntityForneusBody::new, MobCategory.WATER_CREATURE).sized(4.0F, 4.0F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
	public static final RegistryObject<EntityType<EntityForneusTail>> FORNEUS_TAIL = registerEntity("forneus_tail", createBuilder(EntityForneusTail::new, MobCategory.WATER_CREATURE).sized(4.0F, 4.0F).clientTrackingRange(100).setTrackingRange(100).setUpdateInterval(1));
	
	public static final RegistryObject<EntityType<EntityOverseer>> OVERSEER = registerEntity("overseer", createBuilder(EntityOverseer::new, MobCategory.MONSTER).sized(4.0F, 1.75F));
	
	public static <T extends Entity> EntityType.Builder<T> createBuilder(EntityType.EntityFactory<T> factory, MobCategory category)
	{
		return EntityType.Builder.<T>of(factory, category);
	}
	
	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) 
	{
		return ENTITY_TYPES.register(name, () -> builder.build(new ResourceLocation(BeyondtheAbyss.MODID, name).toString()));
	}
}
