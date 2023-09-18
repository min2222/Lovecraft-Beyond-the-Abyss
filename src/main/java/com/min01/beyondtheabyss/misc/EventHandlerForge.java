package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.AbyssCapabilities;
import com.min01.beyondtheabyss.capabilities.IArmorAbilityCapability;
import com.min01.beyondtheabyss.item.AbyssItems;
import com.min01.beyondtheabyss.world.AbyssWorlds;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.FORGE)
public class EventHandlerForge 
{
	@SubscribeEvent
	public static void changeDimension(LivingTickEvent event)
	{ 	
		LivingEntity entity = event.getEntity();
        boolean abyss = entity.getLevel().dimension().location().getPath().equals("deep_abyss");
        boolean overworld = entity.getLevel().dimension().location().getPath().equals("overworld");
        
    	entity.getCapability(AbyssCapabilities.ARMOR_ABILITY).ifPresent(IArmorAbilityCapability::update);
    	
    	if(entity instanceof Player player)
    	{
    		for(InteractionHand hands : InteractionHand.values())
    		{
    			ItemStack stack = player.getItemInHand(hands);
    			stack.getCapability(AbyssCapabilities.ITEM_ANIMATION).ifPresent(cap ->
    			{
    				cap.setPlayer(player);
    				cap.update();
    			});
    		}
    	}
    	
        if (!entity.level.isClientSide && entity instanceof ServerPlayer && entity.getY() <= 0 && overworld && entity.level.getBiome(entity.blockPosition()).containsTag(BiomeTags.IS_DEEP_OCEAN)) 
        {
            MinecraftServer server = entity.level.getServer();
            ServerPlayer thePlayer = (ServerPlayer) entity;
            if (!abyss) 
            {
                ServerLevel dimWorld = server.getLevel(AbyssWorlds.DEEP_ABYSS);
                if (dimWorld != null) 
                {
                	if(thePlayer.isEyeInFluidType(Fluids.WATER.getFluidType()))
                	{
                        teleportEntity(thePlayer, dimWorld, new BlockPos(entity.getX(), 800, entity.getZ()));
                	}
                }
            }
        }
        
        if (!entity.level.isClientSide && entity instanceof ServerPlayer && abyss && entity.getY() >= 1020)
        {
            MinecraftServer server = entity.level.getServer();
            ServerPlayer thePlayer = (ServerPlayer) entity;
            ServerLevel dimWorld = server.getLevel(Level.OVERWORLD);
            if (dimWorld != null)
            {
            	teleportEntity(thePlayer, dimWorld, new BlockPos(entity.getX(), 5, entity.getZ()));
            }
        }
	}
	
    private static Entity teleportEntity(Entity entity, ServerLevel endpointWorld, BlockPos endpoint)
    {
        if (entity.getLevel().dimension().location().getPath().equals("deep_abyss"))
        {
        	
        } 
        else
        {
            if (entity instanceof Player && ((Player) entity).getSleepingPos().isPresent()) 
            {
                BlockPos bedPos = ((Player) entity).getSleepingPos().get();
                endpoint = bedPos;
                entity.moveTo(bedPos.getX() + 0.5D, bedPos.getY() + 1.5D, bedPos.getZ() + 0.5D, 0.0F, 0.0F);
            } 
            else 
            {
                BlockPos height = entity.level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, new BlockPos(entity.position()));
                endpoint = height;
                entity.moveTo(height.getX() + 0.5D, height.getY() + 0.5D, height.getZ() + 0.5D, entity.getYRot(), 0.0F);
            }
        }
        
        if (entity instanceof ServerPlayer) 
        {
        	ServerPlayer player = (ServerPlayer) entity;
            player.teleportTo(endpointWorld, endpoint.getX() + 0.5D, endpoint.getY() + 0.5D, endpoint.getZ() + 0.5D, entity.getYRot(), entity.getXRot());
            return player;
        }

        entity.unRide();
        entity.changeDimension(endpointWorld);
        Entity teleportedEntity = entity.getType().create(endpointWorld);
        if (teleportedEntity == null) 
        {
            return entity;
        }
        teleportedEntity.restoreFrom(entity);
        teleportedEntity.moveTo(endpoint.getX() + 0.5D, endpoint.getY() + 0.5D, endpoint.getZ() + 0.5D, entity.getYRot(), entity.getXRot());
        teleportedEntity.setYHeadRot(entity.getYRot());
        endpointWorld.addDuringTeleport(teleportedEntity);
        return teleportedEntity;
    }
    
    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event)
    {
    	Player player = event.getEntity();
    	if(player.isEyeInFluidType(Fluids.WATER.getFluidType()))
    	{
        	if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == AbyssItems.ADVANCED_DIVING_SUIT.get())
        	{
        		event.setNewSpeed(event.getOriginalSpeed() * 5F);
        	}
        	else if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == AbyssItems.GHIDRUTH_DIVING_SUIT.get())
        	{
        		event.setNewSpeed(event.getOriginalSpeed() * 7F);
        	}
    	}
    }
}
