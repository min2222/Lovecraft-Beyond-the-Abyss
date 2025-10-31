package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateAbyssPortalActivationPacket;
import com.min01.beyondtheabyss.network.UpdateAbyssPortalPosPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class BTASavedData extends SavedData
{
	public static final String NAME = "bta_data";
	protected boolean isHutGenerated;
	protected boolean isDragonKilled;
	protected boolean isAbyssPortalActivated;
	protected BlockPos hutPos = BlockPos.ZERO;
	protected BlockPos abyssPortalPos = BlockPos.ZERO;
	
    public static BTASavedData get(Level level)
    {
        if(level instanceof ServerLevel serverLevel) 
        {
            DimensionDataStorage storage = serverLevel.getDataStorage();
            BTASavedData data = storage.computeIfAbsent(t -> load(serverLevel, t), BTASavedData::new, NAME);
            return data;
        }
        return null;
    }

    public static BTASavedData load(Level level, CompoundTag nbt) 
    {
    	BTASavedData data = new BTASavedData();
    	data.setHutGenerated(nbt.getBoolean("isHutGenerated"));
    	data.setDragonKilled(nbt.getBoolean("isDragonKilled"));
    	data.setAbyssPortalActivated(level.dimension(), nbt.getBoolean("isAbyssPortalActivated"));
    	data.setHutPos(NbtUtils.readBlockPos(nbt.getCompound("HutPos")));
    	data.setAbyssPortalPos(level.dimension(), NbtUtils.readBlockPos(nbt.getCompound("AbyssPortalPos")));
        return data;
    }
	
	@Override
	public CompoundTag save(CompoundTag nbt)
	{
		nbt.putBoolean("isHutGenerated", this.isHutGenerated);
		nbt.putBoolean("isDragonKilled", this.isDragonKilled);
		nbt.putBoolean("isAbyssPortalActivated", this.isAbyssPortalActivated);
		nbt.put("HutPos", NbtUtils.writeBlockPos(this.hutPos));
		nbt.put("AbyssPortalPos", NbtUtils.writeBlockPos(this.abyssPortalPos));
		return nbt;
	}
	
	public void setAbyssPortalActivated(ResourceKey<Level> dimension, boolean value)
	{
		this.isAbyssPortalActivated = value;
		BTANetwork.sendToAll(new UpdateAbyssPortalActivationPacket(dimension, value));
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
	
	public void setHutPos(BlockPos value)
	{
		this.hutPos = value;
		this.setDirty();
	}
	
	public BlockPos getHutPos()
	{
		return this.hutPos;
	}
	
	public void setAbyssPortalPos(ResourceKey<Level> dimension, BlockPos value)
	{
		this.abyssPortalPos = value;
		BTANetwork.sendToAll(new UpdateAbyssPortalPosPacket(dimension, value));
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
}
