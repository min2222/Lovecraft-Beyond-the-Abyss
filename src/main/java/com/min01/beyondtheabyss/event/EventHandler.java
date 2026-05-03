package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.MysteriousGuyEntity;
import com.min01.beyondtheabyss.entity.deepabyss.CorpseAnglerEntity;
import com.min01.beyondtheabyss.entity.deepabyss.ForneusHeadEntity;
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
import com.min01.beyondtheabyss.entity.endlessdesert.DuneDevourerHeadEntity;
import com.min01.beyondtheabyss.entity.mirroredcity.ObserverEntity;
import com.min01.beyondtheabyss.entity.mirroredcity.OverseerEntity;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTABossTracker;
import com.min01.beyondtheabyss.world.BTAStructureFinder;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.MOD)
public class EventHandler 
{
	@SubscribeEvent
	public static void onFMLCommonSetup(FMLCommonSetupEvent event)
	{
		BTAEffects.init();
		BTAStructureFinder.init();
		BTABossTracker.init();
	}
	
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) 
    {
    	event.put(BTAEntities.MYSTERIOUS_GUY.get(), MysteriousGuyEntity.createAttributes().build());
    	
    	//deep abyss
    	event.put(BTAEntities.GHIDRUTH.get(), GhidruthEntity.createAttributes().build());
    	event.put(BTAEntities.GNASHER.get(), GnasherEntity.createAttributes().build());
    	event.put(BTAEntities.SIAMSERPENT_HEAD.get(), SiamserpentHeadEntity.createAttributes().build());
    	event.put(BTAEntities.SIAMSERPENT_BONE.get(), SiamserpentBoneEntity.createAttributes().build());
    	event.put(BTAEntities.SPINE_WORM_HEAD.get(), SpineWormHeadEntity.createAttributes().build());
    	event.put(BTAEntities.SPINE_WORM_BODY.get(), SpineWormBodyEntity.createAttributes().build());
    	event.put(BTAEntities.GLOOMFISH.get(), GloomfishEntity.createAttributes().build());
    	event.put(BTAEntities.CORPSE_ANGLER.get(), CorpseAnglerEntity.createAttributes().build());
    	event.put(BTAEntities.MUTAVORE.get(), MutavoreEntity.createAttributes().build());
    	event.put(BTAEntities.FULGASTRA.get(), FulgastraEntity.createAttributes().build());
    	event.put(BTAEntities.SPLITTED_FULGASTRA.get(), SplittedFulgastraEntity.createAttributes().build());
    	event.put(BTAEntities.NECROSHELL.get(), NecroshellEntity.createAttributes().build());
    	event.put(BTAEntities.LITHOSHRIMP.get(), LithoshrimpEntity.createAttributes().build());
    	event.put(BTAEntities.FORNEUS_HEAD.get(), ForneusHeadEntity.createAttributes().build());
    	event.put(BTAEntities.FORNEUS_BODY.get(), ForneusHeadEntity.createAttributes().build());
    	event.put(BTAEntities.FORNEUS_TAIL.get(), ForneusHeadEntity.createAttributes().build());
    	
    	//mirrored city
    	event.put(BTAEntities.OVERSEER.get(), OverseerEntity.createAttributes().build());
    	event.put(BTAEntities.OBSERVER.get(), ObserverEntity.createAttributes().build());
    	
    	//endless desert
    	event.put(BTAEntities.DUNE_DEVOURER_HEAD.get(), DuneDevourerHeadEntity.createAttributes().build());
    	event.put(BTAEntities.DUNE_DEVOURER_BODY.get(), DuneDevourerHeadEntity.createAttributes().build());
    	event.put(BTAEntities.DUNE_DEVOURER_TAIL.get(), DuneDevourerHeadEntity.createAttributes().build());
    }
    
    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event)
    {
    	event.register(BTAEntities.GNASHER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GnasherEntity::checkGnasherSpawnRules, Operation.AND);
    	event.register(BTAEntities.SIAMSERPENT_HEAD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SiamserpentHeadEntity::checkSiamserpentSpawnRules, Operation.AND);
    	event.register(BTAEntities.SPINE_WORM_HEAD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpineWormHeadEntity::checkSpineWormSpawnRules, Operation.AND);
     	event.register(BTAEntities.GLOOMFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GloomfishEntity::checkGloomfishSpawnRules, Operation.AND);
     	event.register(BTAEntities.CORPSE_ANGLER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CorpseAnglerEntity::checkCorpseAnglerSpawnRules, Operation.AND);
     	event.register(BTAEntities.MUTAVORE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MutavoreEntity::checkMutavoreSpawnRules, Operation.AND);
     	event.register(BTAEntities.FULGASTRA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FulgastraEntity::checkFulgastraSpawnRules, Operation.AND);
     	event.register(BTAEntities.NECROSHELL.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NecroshellEntity::checkNecroshellSpawnRules, Operation.AND);
     	event.register(BTAEntities.LITHOSHRIMP.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LithoshrimpEntity::checkLithoshrimpSpawnRules, Operation.AND);
     	
     	event.register(BTAEntities.OBSERVER.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ObserverEntity::checkObserverSpawnRules, Operation.AND);
    }
    
    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event)
    {
    	if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
    	{
    		event.accept(BTAItems.MYSTERIOUS_GUY_SPAWN_EGG.get());
    	}
    }
}
