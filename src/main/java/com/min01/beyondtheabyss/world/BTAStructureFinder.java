package com.min01.beyondtheabyss.world;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.TriConsumer;

import com.google.common.base.Stopwatch;
import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.misc.BTAResourceKeys;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.datafixers.util.Pair;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;

public class BTAStructureFinder
{
	public static final Map<ResourceKey<Structure>, TriConsumer<BlockPos, BTASavedData, ServerLevel>> MAP = new HashMap<>(); 
	
	public static void init()
	{
		register(BTAResourceKeys.BTAStructures.DEEP_ABYSS_PORTAL, (k, v, s) ->
		{
			BlockPos pos = k.offset(13, 0, 1);
    		int y = BTAUtil.getSpecificGroundPos(s, pos.getX(), pos.getY() + 50, pos.getZ(), BTABlocks.ORIVINE.get()).getY();
			v.setStructurePos(BTAResourceKeys.BTAStructures.DEEP_ABYSS_PORTAL, new BlockPos(pos.getX(), y + 1, pos.getZ()));
			v.setPortalActivated(BTAPortalTracker.DEEP_ABYSS_PORTAL, false);
		});
		register(BTAResourceKeys.BTAStructures.HUT, (k, v, s) -> 
		{
			v.setStructurePos(BTAResourceKeys.BTAStructures.HUT, BTAUtil.getGroundPos(s, k.getX(), k.getY() + 50, k.getZ()));
		});
	}
	
	public static void register(ResourceKey<Structure> structure, TriConsumer<BlockPos, BTASavedData, ServerLevel> consumer)
	{
		MAP.put(structure, consumer);
	}
	
	public static void load(BTASavedData data, CompoundTag tag)
	{
		ListTag list = tag.getList("Structures", 10);
		for(int i = 0; i < list.size(); i++)
		{
			CompoundTag nbt = list.getCompound(i);
			BlockPos pos = NbtUtils.readBlockPos(nbt.getCompound("BlockPos"));
			data.setStructurePos(BTAResourceKeys.BTAStructures.getKeyByName(nbt.getString("Key")), pos);
		}
	}
	
	public static void save(CompoundTag tag, Map<ResourceKey<Structure>, BlockPos> structureMap)
	{
		ListTag list = new ListTag();
		for(Map.Entry<ResourceKey<Structure>, BlockPos> entry : structureMap.entrySet())
		{
			ResourceKey<Structure> key = entry.getKey();
			BlockPos blockPos = entry.getValue();
			CompoundTag nbt = new CompoundTag();
			nbt.put("BlockPos", NbtUtils.writeBlockPos(blockPos));
			nbt.putString("Key", key.location().toString());
			list.add(nbt);
		}
		tag.put("Structures", list);
	}
	
	public static void find(ServerLevel level, BlockPos blockPos)
	{
		for(Map.Entry<ResourceKey<Structure>, TriConsumer<BlockPos, BTASavedData, ServerLevel>> entry : MAP.entrySet())
		{
			ResourceKey<Structure> key = entry.getKey();
			TriConsumer<BlockPos, BTASavedData, ServerLevel> consumer = entry.getValue();
			Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
			HolderSet<Structure> holerSet = registry.getHolder(key).map(t -> 
			{
				return HolderSet.direct(t);
			}).get();
			Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
			Pair<BlockPos, Holder<Structure>> pair = level.getChunkSource().getGenerator().findNearestMapStructure(level, holerSet, blockPos, 100, false);
			stopwatch.stop();
			if(pair != null)
			{
	    		BTASavedData data = BTASavedData.get(level);
				if(data != null && data.getStructurePos(key).equals(BlockPos.ZERO))
				{
					consumer.accept(pair.getFirst(), data, level);
				}
			}
		}
	}
}
