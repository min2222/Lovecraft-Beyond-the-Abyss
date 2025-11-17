package com.min01.beyondtheabyss.event;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Stopwatch;
import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySpineWormHead;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTALootTables;
import com.min01.beyondtheabyss.misc.BTAResourceKeys;
import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.misc.ChatTicker;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateStoneSkinEffectPacket;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.min01.beyondtheabyss.world.BTASavedData;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.mojang.datafixers.util.Pair;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingDrownEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod.EventBusSubscriber(modid = BeyondtheAbyss.MODID, bus = Bus.FORGE)
public class EventHandlerForge 
{
	public static final Map<ResourceKey<Level>, ChatTicker> CHAT_MAP = new HashMap<>();
	
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) 
    {
        copyRegionFiles(event.getServer());
    }
	
    public static void copyRegionFiles(MinecraftServer server)
    {
        if(server == null) 
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
	    catch(IOException e)
	    {
	        e.printStackTrace();
	    }
	}
    
    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) 
    {
    	LivingEntity living = event.getEntity();
    	MobEffectInstance instance = event.getEffectInstance();
    	if(instance != null && instance.getEffect() == BTAEffects.STONE_SKIN.get() && !living.level.isClientSide)
    	{
    		BTANetwork.sendToAll(new UpdateStoneSkinEffectPacket(living.getUUID(), instance.getAmplifier(), instance.getDuration(), false));
    	}
    }
    
    @SubscribeEvent
    public static void onMobEffectRemove(MobEffectEvent.Remove event) 
    {
    	LivingEntity living = event.getEntity();
    	MobEffectInstance instance = event.getEffectInstance();
    	if(instance != null && instance.getEffect() == BTAEffects.STONE_SKIN.get() && !living.level.isClientSide)
    	{
    		BTANetwork.sendToAll(new UpdateStoneSkinEffectPacket(living.getUUID(), instance.getAmplifier(), instance.getDuration(), true));
    	}
    }
    
    @SubscribeEvent
    public static void onMobEffectExpired(MobEffectEvent.Expired event) 
    {
    	LivingEntity living = event.getEntity();
    	MobEffectInstance instance = event.getEffectInstance();
    	if(instance != null && instance.getEffect() == BTAEffects.STONE_SKIN.get() && !living.level.isClientSide)
    	{
    		BTANetwork.sendToAll(new UpdateStoneSkinEffectPacket(living.getUUID(), instance.getAmplifier(), instance.getDuration(), true));
    	}
    }
    
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent event)
    {
    	if(event.level instanceof ServerLevel serverLevel)
    	{
    		if(event.phase == TickEvent.Phase.START)
    		{
    			if(CHAT_MAP.containsKey(serverLevel.dimension()))
    			{
    				ChatTicker ticker = CHAT_MAP.get(serverLevel.dimension());
    				ticker.tick();
    			}
    			CHAT_MAP.values().removeIf(t -> t.tickCount > 120);
    			EntityTickList list = serverLevel.entityTickList;
    			list.forEach(t ->
    			{
    				if(!t.isRemoved() && t.getType().is(BTATags.BTAEntity.FAR_RANGE_TICKING))
    				{
    					DistanceManager manager = serverLevel.getChunkSource().chunkMap.getDistanceManager();
    					if(!manager.inEntityTickingRange(t.chunkPosition().toLong())) 
    					{
    						serverLevel.getChunkSource().updateChunkForced(t.chunkPosition(), true);
    					}
    				}
    			});
    		}
    	}
    }
    
    @SubscribeEvent
    public static void onLivingBreath(LivingBreatheEvent event)
    {
    	LivingEntity entity = event.getEntity();
    	if(entity.level.dimension() == BTAWorlds.MOON || entity.level.dimension() == BTAWorlds.OUTER_SPACE)
    	{
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
    			boolean flag = event.getEntityMounting() instanceof Player player ? !player.getAbilities().instabuild : true;
    			if(event.getEntityMounting().isShiftKeyDown() && flag)
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
    	if(BTAUtil.canSwimInAir(entity) || DeepAbyssUtil.isInsideSubmarine(entity))
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
			Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
			BlockPos blockPos = player.blockPosition();
			ServerLevel serverLevel = (ServerLevel) level;
			if(data.getHutPos().equals(BlockPos.ZERO))
			{
				HolderSet<Structure> holderset = registry.getHolder(BTAResourceKeys.BTAStructures.HUT).map((p_214491_) -> 
				{
					return HolderSet.direct(p_214491_);
				}).get();
				Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
				Pair<BlockPos, Holder<Structure>> pair = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel, holderset, blockPos, 100, false);
				stopwatch.stop();
				if(pair != null)
				{
					data.setHutPos(pair.getFirst());
					data.setHutGenerated(true);
				}
			}
			if(data.getAbyssPortalPos().equals(BlockPos.ZERO))
			{
				HolderSet<Structure> holderset = registry.getHolder(BTAResourceKeys.BTAStructures.DEEP_ABYSS_PORTAL).map((p_214491_) -> 
				{
					return HolderSet.direct(p_214491_);
				}).get();
				Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
				Pair<BlockPos, Holder<Structure>> pair = serverLevel.getChunkSource().getGenerator().findNearestMapStructure(serverLevel, holderset, blockPos, 100, false);
				stopwatch.stop();
				if(pair != null)
				{
					BlockPos pos = pair.getFirst().offset(13, 0, 1);
		    		int y = BTAUtil.getGroundPos(serverLevel, pos.getX(), pos.getY() + 100, pos.getZ(), -1).getY();
					data.setAbyssPortalPos(level.dimension(), new BlockPos(pos.getX(), y - 14, pos.getZ()));
					data.setAbyssPortalActivated(level.dimension(), false);
				}
			}
    	}
    }
    
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		BTAUtil.tickItemAnimation(event.player);
		BTAUtil.tickPlayerAnimation(event.player);
		BTAUtil.tickPlayerTickCount(event.player);
	}
	
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event)
    {
        if(event.getName().toString().matches("minecraft:chests/buried_treasure")) 
        {
        	event.getTable().addPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootTableReference.lootTableReference(BTALootTables.CLAM_OF_GUIDANCE)).build());
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
		BTASavedData data = BTASavedData.get(entity.level);
		if(data != null)
		{
			if(!data.getAbyssPortalPos().equals(BlockPos.ZERO) && data.isAbyssPortalActivated())
			{
				BlockPos portalPos = data.getAbyssPortalPos().above();
				Vec3 pos = Vec3.atCenterOf(portalPos);
				AABB aabb1 = new AABB(-2.5F, 0.0F, -0.5F, 2.5F, 13.0F, 0.5F).move(portalPos);
				AABB aabb2 = new AABB(-6.5F, -2.5F, -0.5F, 6.5F, 2.5F, 0.5F).move(portalPos.above(7));
				AABB corner1 = new AABB(-1.0F, -1.5F, -0.5F, 1.0F, 1.5F, 0.5F).move(pos.add(3.5F, 11.0F, 0.0F));
				AABB corner2 = new AABB(-1.0F, -1.5F, -0.5F, 1.0F, 1.5F, 0.5F).move(pos.add(-3.5F, 11.0F, 0.0F));
				AABB corner3 = new AABB(-1.5F, -1.5F, -0.5F, 1.5F, 1.5F, 0.5F).move(pos.add(3.5F, 3.0F, 0.0F));
				AABB corner4 = new AABB(-1.5F, -1.5F, -0.5F, 1.5F, 1.5F, 0.5F).move(pos.add(-3.5F, 3.0F, 0.0F));
				boolean corners = entity.getBoundingBox().intersects(corner1) || entity.getBoundingBox().intersects(corner2) || entity.getBoundingBox().intersects(corner3) || entity.getBoundingBox().intersects(corner4);
				if(entity.getBoundingBox().intersects(aabb1) || entity.getBoundingBox().intersects(aabb2) || corners)
				{
					BTAUtil.teleportEntityToDimension(entity, entity.getServer().getLevel(BTAWorlds.DEEP_ABYSS), BlockPos.containing(0, 100, 0));
				}
			}
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
