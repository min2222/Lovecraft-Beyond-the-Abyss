package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.EntitySolomon;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.entity.deepabyss.EntityForneusHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFulgastra;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGloomfish;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.deepabyss.EntityNecroshell;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentBone;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormBody;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySplittedFulgastra;
import com.min01.beyondtheabyss.entity.endlessdesert.EntityDuneDevourerHead;
import com.min01.beyondtheabyss.entity.mirroredcity.EntityObserver;
import com.min01.beyondtheabyss.entity.mirroredcity.EntityOverseer;
import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
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
		ItemStack awkward = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD);
		ItemStack disorder = PotionUtils.setPotion(new ItemStack(Items.POTION), BTAEffects.DISORDER_POTION.get());
		ItemStack strongDisorder = PotionUtils.setPotion(new ItemStack(Items.POTION), BTAEffects.STRONG_DISORDER_POTION.get());
		ItemStack coordination = PotionUtils.setPotion(new ItemStack(Items.POTION), BTAEffects.COORDINATION_POTION.get());
		ItemStack strongCoordination = PotionUtils.setPotion(new ItemStack(Items.POTION), BTAEffects.STRONG_COORDINATION_POTION.get());
		BrewingRecipeRegistry.addRecipe(Ingredient.of(awkward), Ingredient.of(BTAItems.GNASHER_EYE.get()), disorder);
		BrewingRecipeRegistry.addRecipe(Ingredient.of(disorder), Ingredient.of(Items.GLOWSTONE_DUST), strongDisorder);
		BrewingRecipeRegistry.addRecipe(Ingredient.of(disorder), Ingredient.of(Items.GLISTERING_MELON_SLICE), coordination);
		BrewingRecipeRegistry.addRecipe(Ingredient.of(coordination), Ingredient.of(Items.GLOWSTONE_DUST), strongCoordination);
	}
	
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) 
    {
    	event.put(BTAEntities.SOLOMON.get(), EntitySolomon.createAttributes().build());
    	
    	//deep abyss
    	event.put(BTAEntities.GHIDRUTH.get(), EntityGhidruth.createAttributes().build());
    	event.put(BTAEntities.GNASHER.get(), EntityGnasher.createAttributes().build());
    	event.put(BTAEntities.SIAMSERPENT_HEAD.get(), EntitySiamserpentHead.createAttributes().build());
    	event.put(BTAEntities.SIAMSERPENT_BONE.get(), EntitySiamserpentBone.createAttributes().build());
    	event.put(BTAEntities.SPINE_WORM_HEAD.get(), EntitySpineWormHead.createAttributes().build());
    	event.put(BTAEntities.SPINE_WORM_BODY.get(), EntitySpineWormBody.createAttributes().build());
    	event.put(BTAEntities.GLOOMFISH.get(), EntityGloomfish.createAttributes().build());
    	event.put(BTAEntities.CORPSE_ANGLER.get(), EntityCorpseAngler.createAttributes().build());
    	event.put(BTAEntities.MUTAVORE.get(), EntityMutavore.createAttributes().build());
    	event.put(BTAEntities.FULGASTRA.get(), EntityFulgastra.createAttributes().build());
    	event.put(BTAEntities.SPLITTED_FULGASTRA.get(), EntitySplittedFulgastra.createAttributes().build());
    	event.put(BTAEntities.NECROSHELL.get(), EntityNecroshell.createAttributes().build());
    	event.put(BTAEntities.FORNEUS_HEAD.get(), EntityForneusHead.createAttributes().build());
    	event.put(BTAEntities.FORNEUS_BODY.get(), EntityForneusHead.createAttributes().build());
    	event.put(BTAEntities.FORNEUS_TAIL.get(), EntityForneusHead.createAttributes().build());
    	
    	//mirrored city
    	event.put(BTAEntities.OVERSEER.get(), EntityOverseer.createAttributes().build());
    	event.put(BTAEntities.OBSERVER.get(), EntityObserver.createAttributes().build());
    	
    	//endless desert
    	event.put(BTAEntities.DUNE_DEVOURER_HEAD.get(), EntityDuneDevourerHead.createAttributes().build());
    	event.put(BTAEntities.DUNE_DEVOURER_BODY.get(), EntityDuneDevourerHead.createAttributes().build());
    	event.put(BTAEntities.DUNE_DEVOURER_TAIL.get(), EntityDuneDevourerHead.createAttributes().build());
    }
    
    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event)
    {
    	event.register(BTAEntities.GNASHER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGnasher::checkGnasherSpawnRules, Operation.AND);
    	event.register(BTAEntities.SIAMSERPENT_HEAD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySiamserpentHead::checkSiamserpentSpawnRules, Operation.AND);
    	event.register(BTAEntities.SPINE_WORM_HEAD.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySpineWormHead::checkSpineWormSpawnRules, Operation.AND);
     	event.register(BTAEntities.GLOOMFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGloomfish::checkGloomfishSpawnRules, Operation.AND);
     	event.register(BTAEntities.CORPSE_ANGLER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCorpseAngler::checkCorpseAnglerSpawnRules, Operation.AND);
     	event.register(BTAEntities.MUTAVORE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMutavore::checkMutavoreSpawnRules, Operation.AND);
     	event.register(BTAEntities.FULGASTRA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFulgastra::checkFulgastraSpawnRules, Operation.AND);
     	event.register(BTAEntities.NECROSHELL.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityNecroshell::checkNecroshellSpawnRules, Operation.AND);
    }
    
    @SubscribeEvent
    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event)
    {
    	if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
    	{
    		event.accept(BTAItems.SOLOMON_SPAWN_EGG.get());
    	}
    }
}
