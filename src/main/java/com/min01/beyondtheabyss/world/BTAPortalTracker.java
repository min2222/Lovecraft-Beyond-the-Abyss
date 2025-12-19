package com.min01.beyondtheabyss.world;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.misc.BTAResourceKeys;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class BTAPortalTracker 
{
	public static final BTAPortal DEEP_ABYSS_PORTAL = new BTAPortal(BTAResourceKeys.BTAStructures.DEEP_ABYSS_PORTAL);
	
	public static final List<BTAPortal> PORTALS = new ArrayList<>();
	
	public static void init()
	{
		register(DEEP_ABYSS_PORTAL);
	}
	
	public static void register(BTAPortal portal)
	{
		PORTALS.add(portal);
	}
	
	public static BTAPortal getPortalByName(String name)
	{
		for(BTAPortal portal : PORTALS)
		{
			if(portal.getKey().location().toString().equals(name))
			{
				return portal;
			}
		}
		return DEEP_ABYSS_PORTAL;
	}
	
	public static void load(BTASavedData data, CompoundTag tag)
	{
		ListTag list = tag.getList("Portals", 10);
		for(int i = 0; i < list.size(); i++)
		{
			CompoundTag nbt = list.getCompound(i);
			BTAPortal portal = getPortalByName(nbt.getString("Key"));
			boolean isActivated =  nbt.getBoolean("isActivated");
			data.setPortalActivated(portal, isActivated);
		}
	}
	
	public static void save(CompoundTag tag)
	{
		ListTag list = new ListTag();
		new ArrayList<>(PORTALS).forEach(t -> 
		{
			CompoundTag nbt = new CompoundTag();
			nbt.putBoolean("isActivated", t.isActivated);
			nbt.putString("Key", t.getKey().location().toString());
			list.add(nbt);
		});
		tag.put("Portals", list);
	}
	
	public static class BTAPortal
	{
		private boolean isActivated;
		
		private final ResourceKey<Structure> key;
		
		public BTAPortal(ResourceKey<Structure> key) 
		{
			this.key = key;
		}
		
		public ResourceKey<Structure> getKey() 
		{
			return this.key;
		}
		
		public void setActivated(boolean isActivated)
		{
			this.isActivated = isActivated;
		}
		
		public boolean isActivated() 
		{
			return this.isActivated;
		}
	}
}
