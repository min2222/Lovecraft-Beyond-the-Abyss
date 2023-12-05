package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BTACreativeTabs
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
			return new ItemStack(BTAItems.GHIDRUTH_SCALE.get());
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
			return new ItemStack(BTAItems.GUIDING_CLAM.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_FOODS = new CreativeModeTab("abyss_foods") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.RAW_GHIDRUTH_FLESH.get());
		}
	};
	
	public static final CreativeModeTab ABYSS_BLOCKS = new CreativeModeTab("abyss_blocks") 
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(BTAItems.ABYSSAL_ALTAR.get());
		}
	};
}
