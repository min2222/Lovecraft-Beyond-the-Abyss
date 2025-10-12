package com.min01.beyondtheabyss.event;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Stopwatch;
import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.misc.BTALootTables;
import com.min01.beyondtheabyss.misc.BTAResourceKeys;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.world.BTASavedData;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.mojang.datafixers.util.Pair;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingDrownEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.FORGE)
public class EventHandlerForge 
{
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) 
    {
        copyRegionFiles(event.getServer());
    }
	
    public static void copyRegionFiles(MinecraftServer server)
    {
        if(server == null || server.isDedicatedServer()) 
        	return;
	    Path baseDir = FMLPaths.CONFIGDIR.get().resolve("beyondtheabyss");
	    File baseDirFile = baseDir.toFile();
	    if(baseDirFile.exists())
	    {
	        System.out.println("Config folder 'beyondtheabyss' already exists. Skipping region file copy.");
	        return;
	    }
	    Path outputDir = baseDir.resolve("region");
	    outputDir.toFile().mkdirs();
	    ResourceManager resourceManager = server.getResourceManager();
	    try
	    {
	        Map<ResourceLocation, Resource> resources = resourceManager.listResources("region", path -> path.getPath().endsWith(".mca"));
	        for(Entry<ResourceLocation, Resource> entry : resources.entrySet())
	        {
	        	ResourceLocation location = entry.getKey();
	        	Resource resource = entry.getValue();
                try(InputStream in = resource.open()) 
                {
                    String fileName = Path.of(location.getPath()).getFileName().toString();
                    Path outputFile = outputDir.resolve(fileName);
                    Files.copy(in, outputFile, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Copied region file: " + fileName);
                }
	        }
	    } 
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	}
    
    @SubscribeEvent
    public static void onLivingBreath(LivingBreatheEvent event)
    {
    	LivingEntity entity = event.getEntity();
    	if(entity.level.dimension() == BTAWorlds.MOON || entity.level.dimension() == BTAWorlds.OUTER_SPACE)
    	{
    		//TODO can breath if wear space helmet;
    		event.setCanBreathe(false);
    	}
    }
    
    @SubscribeEvent
    public static void onEntityMount(EntityMountEvent event)
    {
    	if(event.isDismounting())
    	{
    		if(event.getEntityBeingMounted() instanceof EntitySpineWormHead)
    		{
    			if(event.getEntityMounting().isShiftKeyDown())
    			{
            		event.setCanceled(true);
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public static void onLivingDrown(LivingDrownEvent event)
    {
    	LivingEntity entity = event.getEntity();
    	if(entity.level.dimension() == BTAWorlds.MOON || entity.level.dimension() == BTAWorlds.OUTER_SPACE)
    	{
    		if(!entity.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && event.isDrowning())
    		{
    			event.setBubbleCount(0);
    		}
    	}
    	if(BTAUtil.canSwimInAir(entity))
    	{
			event.setBubbleCount(0);
    	}
    }
    
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) 
    {
    	Level level = (Level) event.getLevel();
    	if(level.dimension() == Level.OVERWORLD && !level.isClientSide && event.getEntity() instanceof Player player)
    	{
    		BTASavedData data = BTASavedData.get(level);
			if(data.getHutPos().equals(BlockPos.ZERO))
			{
				Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
				HolderSet<Structure> holderset = registry.getHolder(BTAResourceKeys.BTAStructures.HUT).map((p_214491_) -> 
				{
					return HolderSet.direct(p_214491_);
				}).get();
				BlockPos blockpos = player.blockPosition();
				ServerLevel serverlevel = (ServerLevel) level;
				Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
				Pair<BlockPos, Holder<Structure>> pair = serverlevel.getChunkSource().getGenerator().findNearestMapStructure(serverlevel, holderset, blockpos, 100, false);
				stopwatch.stop();
				if(pair != null)
				{
					data.setHutPos(pair.getFirst());
					data.setHutGenerated(true);
				}
			}
    	}
    }
    
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		for(int i = 0; i < event.player.getInventory().getContainerSize(); i++)
		{
			ItemStack stack = event.player.getInventory().getItem(i);
			if(stack.getItem() instanceof IAnimatableItem)
			{
				stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
				{
					t.setEntity(event.player);
					t.tick();
				});
			}
		}
		BTAUtil.tickPlayerAnimation(event.player);
	}
	
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event)
    {
        if(event.getName().toString().matches("minecraft:chests/buried_treasure")) 
        {
        	event.getTable().addPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootTableReference.lootTableReference(BTALootTables.GUIDING_CLAM)).build());
        }
    }

	@SubscribeEvent
	public static void onLivingTick(LivingTickEvent event)
	{
		LivingEntity entity = event.getEntity();
		if(entity.hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			entity.setOnGround(false);
			entity.resetFallDistance();
		}
	}
    
    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event)
    {
    	Player player = event.getEntity();
    	if(player.isEyeInFluidType(Fluids.WATER.getFluidType()))
    	{
        	if(player.getItemBySlot(EquipmentSlot.CHEST).getItem() == BTAItems.FELMETAL_DIVING_SUIT.get())
        	{
        		event.setNewSpeed(event.getOriginalSpeed() * 4.0F);
        	}
    	}
    }
}
