package com.min01.beyondtheabyss.event;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IBTAAbilitiesCapability;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTALootTables;
import com.min01.beyondtheabyss.multipart.entity.MultipartAwareEntity;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.FORGE)
public class EventHandlerForge 
{
	@SubscribeEvent
	public static void onLevelTick(LevelTickEvent event)
	{
		if(event.level instanceof ServerLevel level)
		{
			level.getAllEntities().forEach(t -> 
			{
				if(t instanceof ItemEntity item)
				{
					if(item.level.dimension().location().getPath().equals("deep_abyss"))
					{
						item.setDeltaMovement(item.getDeltaMovement().subtract(0, 0.01F, 0));
					}
				}
			});
		}
	}
	
	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteractSpecific event)
	{
		Player player = event.getEntity();
		Entity entity = event.getTarget();
		if(entity instanceof MultipartAwareEntity multipart)
		{
			event.setCanceled(true);
			Vec3 pos = player.getEyePosition(0);
            Vec3 dir = player.getViewVector(0);
            double reach = player.getReachDistance();
            String part = multipart.getBounds().raycast(pos, pos.add(dir.scale(reach)));
            if(part == null)
            	return;
			event.setCancellationResult(multipart.interact(player, event.getHand(), part));
		}
	}
	
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
			entity.setOnGround(false);
			entity.resetFallDistance();
		}
		
		if(BTAUtil.isInsideSubmarine(entity))
		{
			entity.setOnGround(true);
			entity.resetFallDistance();
		}
		
        List<Entity> list = entity.level.getEntities(entity, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1.0E-7D), EntitySelector.NO_SPECTATORS);
        list.forEach(t -> 
        {
        	if(t instanceof EntitySubmarine)
        	{
        		entity.setOnGround(true);
        		entity.resetFallDistance();
        	}
        });
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
