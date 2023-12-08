package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.IBTAAbilitiesCapability;
import com.min01.beyondtheabyss.capabilities.IItemAnimationCapability;
import com.min01.beyondtheabyss.entity.BTAEntityType;
import com.min01.beyondtheabyss.entity.deepabyss.living.EntityGhidruth;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.MOD)
public class EventHandler 
{
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) 
    {
    	event.put(BTAEntityType.GHIDRUTH.get(), EntityGhidruth.createAttributes().build());
    }
    
	@SubscribeEvent
	public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event)
	{
		event.register(BTAEntityType.GHIDRUTH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGhidruth::checkGhidruthSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
	}
	
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) 
    {
    	event.register(IItemAnimationCapability.class);
    	event.register(IBTAAbilitiesCapability.class);
    }
}
