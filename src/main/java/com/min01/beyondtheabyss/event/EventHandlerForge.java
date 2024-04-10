package com.min01.beyondtheabyss.event;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IBTAAbilitiesCapability;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTALootTables;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.FORGE)
public class EventHandlerForge 
{
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event)
    {
        if(event.getName().toString().matches("minecraft:chests/shipwreck_supply")) 
        {
        	event.getTable().addPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootTableReference.lootTableReference(BTALootTables.GUIDING_CLAM)).build());
        }
    }
    
	@SubscribeEvent
	public static void onLivingTick(LivingTickEvent event)
	{ 	
		LivingEntity entity = event.getEntity();
        
    	entity.getCapability(BTACapabilities.BTA_ABILITY).ifPresent(IBTAAbilitiesCapability::update);
    	
		for(InteractionHand hands : InteractionHand.values())
		{
			ItemStack stack = entity.getItemInHand(hands);
			stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(cap ->
			{
				cap.setEntity(entity);
				cap.update();
			});
		}
		
		if(entity.hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			entity.resetFallDistance();
		}
	}
    
    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event)
    {
    	Player player = event.getEntity();
    	if(player.isEyeInFluidType(Fluids.WATER.getFluidType()))
    	{
        	if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == BTAItems.ADVANCED_DIVING_SUIT.get())
        	{
        		event.setNewSpeed(event.getOriginalSpeed() * 5F);
        	}
        	else if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == BTAItems.GHIDRUTH_DIVING_SUIT.get())
        	{
        		event.setNewSpeed(event.getOriginalSpeed() * 7F);
        	}
    	}
    }
}
