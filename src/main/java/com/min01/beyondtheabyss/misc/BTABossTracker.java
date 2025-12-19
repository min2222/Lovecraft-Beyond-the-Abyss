package com.min01.beyondtheabyss.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.world.BTASavedData;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class BTABossTracker
{
	public static final List<BTABossState> STATES = new ArrayList<>();
	public static final BTABossState GHIDRUTH = new BTABossState(() -> BTAEntities.GHIDRUTH.get());
	
	public static void init()
	{
		register(GHIDRUTH);
	}
	
	public static void register(BTABossState state)
	{
		STATES.add(state);
	}
	
	public static BTABossState getStateByName(String name)
	{
		for(BTABossState state : STATES)
		{
			if(ForgeRegistries.ENTITY_TYPES.getKey(state.getType()).toString().equals(name))
			{
				return state;
			}
		}
		return GHIDRUTH;
	}
	
	public static void load(BTASavedData data, CompoundTag tag)
	{
		ListTag list = tag.getList("BossStates", 10);
		for(int i = 0; i < list.size(); i++)
		{
			CompoundTag nbt = list.getCompound(i);
			String name = nbt.getString("Name");
			BTABossState state = getStateByName(name);
			data.setBossSpawned(state, nbt.getBoolean("isSpawned"));
			data.setBossDefeated(state, nbt.getBoolean("isDefeated"));
		}
	}
	
	public static void save(CompoundTag tag)
	{
		ListTag list = new ListTag();
		new ArrayList<>(STATES).forEach(t -> 
		{
			CompoundTag nbt = new CompoundTag();
			nbt.putBoolean("isSpawned", t.isSpawned);
			nbt.putBoolean("isDefeated", t.isDefeated);
			nbt.putString("Name", ForgeRegistries.ENTITY_TYPES.getKey(t.getType()).toString());
			list.add(nbt);
		});
		tag.put("BossStates", list);
	}
	
	public static class BTABossState
	{
		private boolean isSpawned;
		private boolean isDefeated;
		private final EntityType<?> type;
		
		public BTABossState(Supplier<EntityType<?>> supplier) 
		{
			this.type = supplier.get();
		}
		
		public EntityType<?> getType() 
		{
			return this.type;
		}
		
		public void setSpawned(boolean isSpawned) 
		{
			this.isSpawned = isSpawned;
		}
		
		public void setDefeated(boolean isDefeated) 
		{
			this.isDefeated = isDefeated;
		}
		
		public boolean isSpawned() 
		{
			return this.isSpawned;
		}
		
		public boolean isDefeated()
		{
			return this.isDefeated;
		}
	}
}
