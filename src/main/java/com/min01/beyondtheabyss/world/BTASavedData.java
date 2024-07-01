package com.min01.beyondtheabyss.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class BTASavedData extends SavedData
{
	public static final String NAME = "bta_data";
	
	private boolean isUnderwaterBaseGenerated;
	
    public static BTASavedData get(Level level, ResourceKey<Level> dimension)
    {
        if(level instanceof ServerLevel) 
        {
            ServerLevel serverLevel = level.getServer().getLevel(dimension);
            DimensionDataStorage storage = serverLevel.getDataStorage();
            BTASavedData data = storage.computeIfAbsent(BTASavedData::load, BTASavedData::new, NAME);
            if(data != null)
            {
                data.setDirty();
            }
            return data;
        }
        return null;
    }

    public static BTASavedData load(CompoundTag nbt) 
    {
    	BTASavedData data = new BTASavedData();
        data.isUnderwaterBaseGenerated = nbt.getBoolean("isUnderwaterBaseGenerated");
        return data;
    }
	
	@Override
	public CompoundTag save(CompoundTag p_77763_)
	{
		p_77763_.putBoolean("isUnderwaterBaseGenerated", this.isUnderwaterBaseGenerated);
		return p_77763_;
	}

	public boolean isUnderwaterBaseGenerated() 
	{
		return this.isUnderwaterBaseGenerated;
	}

	public void setUnderwaterBaseGenerated(boolean isUnderwaterBaseGenerated)
	{
		this.isUnderwaterBaseGenerated = isUnderwaterBaseGenerated;
	}
}
