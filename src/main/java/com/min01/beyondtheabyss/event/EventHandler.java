package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalBulbray;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalHermitCrab;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAmarumGhost;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFallenDiver;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.deepabyss.EntityPhasmozoa;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormBody;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTADataProvider;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.data.event.GatherDataEvent;
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
		ItemStack water = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);
		ItemStack healing = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING);
		BrewingRecipeRegistry.addRecipe(Ingredient.of(water), Ingredient.of(BTAItems.VAMPIRE_MEMBRANE.get()), healing);
	}
	
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) 
    {
    	event.put(BTAEntities.GHIDRUTH.get(), EntityGhidruth.createAttributes().build());
    	event.put(BTAEntities.DEEP_VAMPIRE.get(), EntityDeepVampire.createAttributes().build());
    	event.put(BTAEntities.RUNIC_FISH.get(), EntityRunicFish.createAttributes().build());
    	event.put(BTAEntities.LATCHER.get(), EntityLatcher.createAttributes().build());
    	event.put(BTAEntities.ABYSSAL_HERMIT_CRAB.get(), EntityAbyssalHermitCrab.createAttributes().build());
    	event.put(BTAEntities.ABYSSAL_BULBRAY.get(), EntityAbyssalBulbray.createAttributes().build());
    	event.put(BTAEntities.SUBMARINE.get(), EntitySubmarine.createLivingAttributes().build());
    	event.put(BTAEntities.PHASMOZOA.get(), EntityPhasmozoa.createAttributes().build());
    	event.put(BTAEntities.AMARUM_GHOST.get(), EntityAmarumGhost.createAttributes().build());
    	event.put(BTAEntities.GNASHER.get(), EntityGnasher.createAttributes().build());
    	event.put(BTAEntities.SIAMSERPENT_HEAD.get(), EntitySiamserpentHead.createAttributes().build());
    	event.put(BTAEntities.SIAMSERPENT_BONE.get(), EntitySiamserpentBone.createAttributes().build());
    	event.put(BTAEntities.FALLEN_DIVER.get(), EntityFallenDiver.createAttributes().build());
    	event.put(BTAEntities.SPINE_WORM_HEAD.get(), EntitySpineWormHead.createAttributes().build());
    	event.put(BTAEntities.SPINE_WORM_BODY.get(), EntitySpineWormBody.createAttributes().build());
    	event.put(BTAEntities.MUTAVORE.get(), EntityMutavore.createAttributes().build());
    }
    
    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event)
    {
    	event.register(BTAEntities.DEEP_VAMPIRE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityDeepVampire::checkDeepVampireSpawnRules, Operation.AND);
    	event.register(BTAEntities.RUNIC_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRunicFish::checkRunicFishSpawnRules, Operation.AND);
    	event.register(BTAEntities.LATCHER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityLatcher::checkLatcherSpawnRules, Operation.AND);
    	event.register(BTAEntities.ABYSSAL_HERMIT_CRAB.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAbyssalHermitCrab::checkHermitCrabSpawnRules, Operation.AND);
    	event.register(BTAEntities.ABYSSAL_BULBRAY.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAbyssalBulbray::checkBulbraySpawnRules, Operation.AND);
    	event.register(BTAEntities.AMARUM_GHOST.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAmarumGhost::checkAmarumGhostSpawnRules, Operation.AND);
    	event.register(BTAEntities.GNASHER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGnasher::checkGnasherSpawnRules, Operation.AND);
    	event.register(BTAEntities.SIAMSERPENT_HEAD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySiamserpentHead::checkSiamserpentSpawnRules, Operation.AND);
    	event.register(BTAEntities.FALLEN_DIVER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFallenDiver::checkFallenDiverSpawnRules, Operation.AND);
    }
    
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        DataGenerator dataGenerator = event.getGenerator();
        dataGenerator.addProvider(event.includeServer(), new BTADataProvider(dataGenerator.getPackOutput(), event.getLookupProvider()));
    }
}
