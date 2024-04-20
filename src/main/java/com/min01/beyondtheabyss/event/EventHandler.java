package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.IBTAAbilitiesCapability;
import com.min01.beyondtheabyss.capabilities.IItemAnimationCapability;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.deepabyss.EntityRunicFish;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.MOD)
public class EventHandler 
{
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) 
    {
    	event.put(BTAEntities.GHIDRUTH.get(), EntityGhidruth.createAttributes().build());
    	event.put(BTAEntities.DEEP_VAMPIRE.get(), EntityDeepVampire.createAttributes().build());
    	event.put(BTAEntities.RUNIC_FISH.get(), EntityRunicFish.createAttributes().build());
    }
	
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) 
    {
    	event.register(IItemAnimationCapability.class);
    	event.register(IBTAAbilitiesCapability.class);
    }
    
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event)
    {
    	event.register(BTAEntities.DEEP_VAMPIRE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityDeepVampire::checkDeepVampireSpawnRules, Operation.AND);
    	event.register(BTAEntities.RUNIC_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRunicFish::checkRunicFishSpawnRules, Operation.AND);
    }
}
