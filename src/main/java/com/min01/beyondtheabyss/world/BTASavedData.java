package com.min01.beyondtheabyss.world;

import java.util.HashMap;
import java.util.Map;

import com.min01.beyondtheabyss.misc.BTABossTracker;
import com.min01.beyondtheabyss.misc.BTABossTracker.BTABossState;
import com.min01.beyondtheabyss.world.BTAPortalTracker.BTAPortal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class BTASavedData extends SavedData
{
	public static final String NAME = "bta_data";
	protected boolean isDragonKilled;
	protected final Map<ResourceKey<Structure>, BlockPos> structureMap = new HashMap<>();
	
    public static BTASavedData get(Level level)
    {
        if(level instanceof ServerLevel serverLevel) 
        {
            DimensionDataStorage storage = serverLevel.getDataStorage();
            BTASavedData data = storage.computeIfAbsent(BTASavedData::load, BTASavedData::new, NAME);
            return data;
        }
        return null;
    }

    public static BTASavedData load(CompoundTag nbt) 
    {
    	BTASavedData data = new BTASavedData();
    	data.setDragonKilled(nbt.getBoolean("isDragonKilled"));
    	BTAPortalTracker.load(data, nbt);
    	BTAStructureFinder.load(data, nbt);
    	BTABossTracker.load(data, nbt);
        return data;
    }
	
	@Override
	public CompoundTag save(CompoundTag nbt)
	{
		nbt.putBoolean("isDragonKilled", this.isDragonKilled);
		BTAPortalTracker.save(nbt);
		BTAStructureFinder.save(nbt, this.structureMap);
    	BTABossTracker.save(nbt);
		return nbt;
	}
	
	public void setDragonKilled(boolean value)
	{
		this.isDragonKilled = value;
		this.setDirty();
	}
	
	public boolean isDragonKilled()
	{
		return this.isDragonKilled;
	}
	
	public void setStructurePos(ResourceKey<Structure> structure, BlockPos pos)
	{
		this.structureMap.put(structure, pos);
		this.setDirty();
	}
	
	public BlockPos getStructurePos(ResourceKey<Structure> structure)
	{
		return this.structureMap.getOrDefault(structure, BlockPos.ZERO);
	}
	
	public void setPortalActivated(BTAPortal portal, boolean value)
	{
		portal.setActivated(value);
		this.setDirty();
	}
	
	public boolean isPortalActivated(BTAPortal portal)
	{
		return portal.isActivated();
	}
	
	public void setBossSpawned(BTABossState state, boolean value)
	{
		state.setSpawned(value);
		this.setDirty();
	}
	
	public boolean isBossSpawned(BTABossState state)
	{
		return state.isSpawned();
	}
	
	public void setBossDefeated(BTABossState state, boolean value)
	{
		state.setDefeated(value);
		this.setDirty();
	}
	
	public boolean isBossDefeated(BTABossState state)
	{
		return state.isDefeated();
	}
}