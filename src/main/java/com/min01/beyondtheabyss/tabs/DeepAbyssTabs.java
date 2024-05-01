package com.min01.beyondtheabyss.tabs;

import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class DeepAbyssTabs
{
	public static final CreativeModeTab ABYSS_MOBS = new CreativeModeTab("abyss_mobs") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.GHIDRUTH_SPAWN_EGG.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_MATERIALS = new CreativeModeTab("abyss_materials") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.OXYGEN_TANK.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_ARMORS = new CreativeModeTab("abyss_armors") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.DIVING_HELMET.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_MISC = new CreativeModeTab("abyss_misc") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.HEART_OF_FORNEUS.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_FOODS = new CreativeModeTab("abyss_foods") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.RUNIC_FISH.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_BLOCKS = new CreativeModeTab("abyss_blocks") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.DEPTHSTONE.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_WEAPONS = new CreativeModeTab("abyss_weapons") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.RUSTY_HARPOON.get());
		}
	};
}
