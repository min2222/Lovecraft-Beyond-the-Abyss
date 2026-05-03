package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.ChainTrapMawEntity;
import com.min01.beyondtheabyss.entity.deepabyss.CorpseAnglerEntity;
import com.min01.beyondtheabyss.entity.deepabyss.DeepAbyssPortalEntity;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusBodyEntity;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusHeadEntity;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusTailEntity;
import com.min01.beyondtheabyss.entity.deepabyss.FulgastraEntity;
import com.min01.beyondtheabyss.entity.deepabyss.GhidruthEntity;
import com.min01.beyondtheabyss.entity.deepabyss.GloomfishEntity;
import com.min01.beyondtheabyss.entity.deepabyss.GnasherEntity;
import com.min01.beyondtheabyss.entity.deepabyss.LithoshrimpEntity;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity;
import com.min01.beyondtheabyss.entity.deepabyss.NecroshellEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentBoneEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SpineWormBodyEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SpineWormHeadEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SplittedFulgastraEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SubmarineEntity;
import com.min01.beyondtheabyss.entity.endlessdesert.DuneDevourerBodyEntity;
import com.min01.beyondtheabyss.entity.endlessdesert.DuneDevourerHeadEntity;
import com.min01.beyondtheabyss.entity.endlessdesert.DuneDevourerTailEntity;
import com.min01.beyondtheabyss.entity.mirroredcity.ObserverEntity;
import com.min01.beyondtheabyss.entity.mirroredcity.OverseerEntity;
import com.min01.beyondtheabyss.entity.projectile.EnergyBallEntity;
import com.min01.beyondtheabyss.entity.projectile.MissileEntity;
import com.min01.beyondtheabyss.entity.projectile.MutavoreCystEntity;
import com.min01.beyondtheabyss.entity.projectile.PutridBubbleEntity;
import com.min01.beyondtheabyss.entity.projectile.ToothBulletEntity;

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
	public static final RegistryObject<EntityType<BTACameraShakeEntity>> BTA_CAMERA_SHAKE = registerEntity("bta_camera_shake", EntityType.Builder.<BTACameraShakeEntity>of(BTACameraShakeEntity::new, MobCategory.MISC).sized(0.0F, 0.0F));
	public static final RegistryObject<EntityType<SubmarineEntity>> SUBMARINE = registerEntity("submarine", createBuilder(SubmarineEntity::new, MobCategory.MISC).sized(6.0F, 6.0F));
	public static final RegistryObject<EntityType<ChainTrapMawEntity>> CHAIN_TRAP_MAW = registerEntity("chain_trap_maw", createBuilder(ChainTrapMawEntity::new, MobCategory.MISC).sized(0.25F, 0.25F));
	public static final RegistryObject<EntityType<FallingStoneEntity>> FALLING_STONE = registerEntity("falling_stone", createBuilder(FallingStoneEntity::new, MobCategory.MISC).sized(1.0F, 1.0F));
	public static final RegistryObject<EntityType<DeepAbyssPortalEntity>> DEEP_ABYSS_PORTAL = registerEntity("deep_abyss_portal", createBuilder(DeepAbyssPortalEntity::new, MobCategory.MISC).clientTrackingRange(100).sized(10.9375F, 0.8125F));
	
	//projectile
	public static final RegistryObject<EntityType<EnergyBallEntity>> ENERGY_BALL = registerEntity("energy_ball", EntityType.Builder.<EnergyBallEntity>of(EnergyBallEntity::new, MobCategory.MISC).sized(0.375F, 0.375F));
	public static final RegistryObject<EntityType<PutridBubbleEntity>> PUTRID_BUBBLE = registerEntity("putrid_bubble", EntityType.Builder.<PutridBubbleEntity>of(PutridBubbleEntity::new, MobCategory.MISC).sized(0.75F, 0.75F));
	public static final RegistryObject<EntityType<ToothBulletEntity>> TOOTH_BULLET = registerEntity("tooth_bullet", EntityType.Builder.<ToothBulletEntity>of(ToothBulletEntity::new, MobCategory.MISC).sized(0.1875F, 0.25F));
	public static final RegistryObject<EntityType<MutavoreCystEntity>> MUTAVORE_CYST = registerEntity("mutavore_cyst", createBuilder(MutavoreCystEntity::new, MobCategory.MISC).sized(0.625F, 0.625F));
	public static final RegistryObject<EntityType<MissileEntity>> MISSILE = registerEntity("missile", EntityType.Builder.<MissileEntity>of(MissileEntity::new, MobCategory.MISC).sized(0.75F, 0.75F));

	//living
	public static final RegistryObject<EntityType<MysteriousGuyEntity>> MYSTERIOUS_GUY = registerEntity("myterious_guy", createBuilder(MysteriousGuyEntity::new, MobCategory.CREATURE).sized(0.75F, 2.0F));
	public static final RegistryObject<EntityType<GhidruthEntity>> GHIDRUTH = registerEntity("ghidruth", createBuilder(GhidruthEntity::new, MobCategory.WATER_CREATURE).clientTrackingRange(100).sized(5.2F, 4.5F));
	public static final RegistryObject<EntityType<GnasherEntity>> GNASHER = registerEntity("gnasher", createBuilder(GnasherEntity::new, MobCategory.WATER_CREATURE).sized(0.875F, 0.75F));
	public static final RegistryObject<EntityType<SiamserpentHeadEntity>> SIAMSERPENT_HEAD = registerEntity("siamserpent_head", createBuilder(SiamserpentHeadEntity::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F).clientTrackingRange(100));
 	public static final RegistryObject<EntityType<SiamserpentBoneEntity>> SIAMSERPENT_BONE = registerEntity("siamserpent_bone", createBuilder(SiamserpentBoneEntity::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F).clientTrackingRange(100));
	public static final RegistryObject<EntityType<SpineWormHeadEntity>> SPINE_WORM_HEAD = registerEntity("spine_worm_head", createBuilder(SpineWormHeadEntity::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F));
	public static final RegistryObject<EntityType<SpineWormBodyEntity>> SPINE_WORM_BODY = registerEntity("spine_worm_body", createBuilder(SpineWormBodyEntity::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F));
	public static final RegistryObject<EntityType<GloomfishEntity>> GLOOMFISH = registerEntity("gloomfish", createBuilder(GloomfishEntity::new, MobCategory.WATER_CREATURE).sized(0.4F, 0.4F));
	public static final RegistryObject<EntityType<CorpseAnglerEntity>> CORPSE_ANGLER = registerEntity("corpse_angler", createBuilder(CorpseAnglerEntity::new, MobCategory.WATER_CREATURE).sized(3.0F, 4.75F));
	public static final RegistryObject<EntityType<MutavoreEntity>> MUTAVORE = registerEntity("mutavore", createBuilder(MutavoreEntity::new, MobCategory.WATER_CREATURE).sized(3.0F, 3.0F));
	public static final RegistryObject<EntityType<FulgastraEntity>> FULGASTRA = registerEntity("fulgastra", createBuilder(FulgastraEntity::new, MobCategory.WATER_CREATURE).sized(4.875F, 1.5625F));
	public static final RegistryObject<EntityType<SplittedFulgastraEntity>> SPLITTED_FULGASTRA = registerEntity("splitted_fulgastra", createBuilder(SplittedFulgastraEntity::new, MobCategory.WATER_CREATURE).sized(1.625F, 1.4375F));
	public static final RegistryObject<EntityType<NecroshellEntity>> NECROSHELL = registerEntity("necroshell", createBuilder(NecroshellEntity::new, MobCategory.WATER_CREATURE).sized(1.0F, 1.0F));
	public static final RegistryObject<EntityType<LithoshrimpEntity>> LITHOSHRIMP = registerEntity("lithoshrimp", createBuilder(LithoshrimpEntity::new, MobCategory.WATER_CREATURE).sized(0.5625F, 0.4375F));
	public static final RegistryObject<EntityType<ForneusHeadEntity>> FORNEUS_HEAD = registerEntity("forneus_head", createBuilder(ForneusHeadEntity::new, MobCategory.WATER_CREATURE).sized(4.0F, 4.0F).clientTrackingRange(100));
	public static final RegistryObject<EntityType<ForneusBodyEntity>> FORNEUS_BODY = registerEntity("forneus_body", createBuilder(ForneusBodyEntity::new, MobCategory.WATER_CREATURE).sized(4.0F, 4.0F).clientTrackingRange(100));
	public static final RegistryObject<EntityType<ForneusTailEntity>> FORNEUS_TAIL = registerEntity("forneus_tail", createBuilder(ForneusTailEntity::new, MobCategory.WATER_CREATURE).sized(4.0F, 4.0F).clientTrackingRange(100));
	
	public static final RegistryObject<EntityType<OverseerEntity>> OVERSEER = registerEntity("overseer", createBuilder(OverseerEntity::new, MobCategory.MONSTER).sized(4.0F, 1.75F));
	public static final RegistryObject<EntityType<ObserverEntity>> OBSERVER = registerEntity("observer", createBuilder(ObserverEntity::new, MobCategory.MONSTER).sized(0.4375F, 0.4375F));
	
	public static final RegistryObject<EntityType<DuneDevourerHeadEntity>> DUNE_DEVOURER_HEAD = registerEntity("dune_devourer_head", createBuilder(DuneDevourerHeadEntity::new, MobCategory.MONSTER).sized(5.0F, 6.25F).fireImmune().clientTrackingRange(100));
	public static final RegistryObject<EntityType<DuneDevourerBodyEntity>> DUNE_DEVOURER_BODY = registerEntity("dune_devourer_body", createBuilder(DuneDevourerBodyEntity::new, MobCategory.MONSTER).sized(5.0F, 6.25F).fireImmune().clientTrackingRange(100));
	public static final RegistryObject<EntityType<DuneDevourerTailEntity>> DUNE_DEVOURER_TAIL = registerEntity("dune_devourer_tail", createBuilder(DuneDevourerTailEntity::new, MobCategory.MONSTER).sized(5.0F, 6.25F).fireImmune().clientTrackingRange(100));
	
	public static <T extends Entity> EntityType.Builder<T> createBuilder(EntityType.EntityFactory<T> factory, MobCategory category)
	{
		return EntityType.Builder.<T>of(factory, category);
	}
	
	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) 
	{
		return ENTITY_TYPES.register(name, () -> builder.build(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name).toString()));
	}
}
