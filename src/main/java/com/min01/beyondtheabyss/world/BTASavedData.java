package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateAbyssPortalActivationPacket;
import com.min01.beyondtheabyss.network.UpdateAbyssPortalPosPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class BTASavedData extends SavedData
{
	public static final String NAME = "bta_data";
	protected boolean isHutGenerated;
	protected boolean isDragonKilled;
	protected boolean isGhidruthSpawned;
	protected boolean isAbyssPortalActivated;
	protected BlockPos hutPos = BlockPos.ZERO;
	protected BlockPos abyssPortalPos = BlockPos.ZERO;
	
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
    	data.setHutGenerated(nbt.getBoolean("isHutGenerated"));
    	data.setDragonKilled(nbt.getBoolean("isDragonKilled"));
    	data.setAbyssPortalActivated(nbt.getBoolean("isAbyssPortalActivated"));
    	data.setGhidruthSpawned(nbt.getBoolean("isGhidruthSpawned"));
    	data.setHutPos(NbtUtils.readBlockPos(nbt.getCompound("HutPos")));
    	data.setAbyssPortalPos(NbtUtils.readBlockPos(nbt.getCompound("AbyssPortalPos")));
        return data;
    }
	
	@Override
	public CompoundTag save(CompoundTag nbt)
	{
		nbt.putBoolean("isHutGenerated", this.isHutGenerated);
		nbt.putBoolean("isDragonKilled", this.isDragonKilled);
		nbt.putBoolean("isAbyssPortalActivated", this.isAbyssPortalActivated);
		nbt.putBoolean("isGhidruthSpawned", this.isGhidruthSpawned);
		nbt.put("HutPos", NbtUtils.writeBlockPos(this.hutPos));
		nbt.put("AbyssPortalPos", NbtUtils.writeBlockPos(this.abyssPortalPos));
		return nbt;
	}
	
	public void setAbyssPortalActivated(boolean value)
	{
		this.isAbyssPortalActivated = value;
		BTANetwork.sendToAll(new UpdateAbyssPortalActivationPacket(value));
		this.setDirty();
	}
	
	public void setHutGenerated(boolean value)
	{
		this.isHutGenerated = value;
		this.setDirty();
	}
	
	public void setDragonKilled(boolean value)
	{
		this.isDragonKilled = value;
		this.setDirty();
	}
	
	public void setGhidruthSpawned(boolean value)
	{
		this.isGhidruthSpawned = value;
		this.setDirty();
	}
	
	public void setHutPos(BlockPos value)
	{
		this.hutPos = value;
		this.setDirty();
	}
	
	public BlockPos getHutPos()
	{
		return this.hutPos;
	}
	
	public void setAbyssPortalPos(BlockPos value)
	{
		this.abyssPortalPos = value;
		BTANetwork.sendToAll(new UpdateAbyssPortalPosPacket(value));
		this.setDirty();
	}
	
	public BlockPos getAbyssPortalPos()
	{
		return this.abyssPortalPos;
	}
	
	public boolean isAbyssPortalActivated()
	{
		return this.isAbyssPortalActivated;
	}
	
	public boolean isHutGenerated()
	{
		return this.isHutGenerated;
	}
	
	public boolean isDragonKilled()
	{
		return this.isDragonKilled;
	}
	
	public boolean isGhidruthSpawned()
	{
		return this.isGhidruthSpawned;
	}
}
