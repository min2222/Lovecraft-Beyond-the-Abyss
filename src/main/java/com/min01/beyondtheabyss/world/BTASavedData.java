package com.min01.beyondtheabyss.world;

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
	protected BlockPos hutPos = BlockPos.ZERO;
	
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
    	data.setHutPos(NbtUtils.readBlockPos(nbt.getCompound("HutPos")));
        return data;
    }
	
	@Override
	public CompoundTag save(CompoundTag nbt)
	{
		nbt.putBoolean("isHutGenerated", this.isHutGenerated);
		nbt.put("HutPos", NbtUtils.writeBlockPos(this.hutPos));
		return nbt;
	}
	
	public void setHutGenerated(boolean value)
	{
		this.isHutGenerated = value;
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
	
	public boolean isHutGenerated()
	{
		return this.isHutGenerated;
	}
}
