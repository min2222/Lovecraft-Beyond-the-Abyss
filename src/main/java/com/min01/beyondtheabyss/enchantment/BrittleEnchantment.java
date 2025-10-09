package com.min01.beyondtheabyss.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class BrittleEnchantment extends Enchantment
{
	public BrittleEnchantment() 
	{
		super(Rarity.UNCOMMON, BTAEnchantments.TOOTH_SHOTGUN, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}
	
	@Override
	public int getMaxLevel() 
	{
		return 3;
	}
	
	@Override
	public boolean isAllowedOnBooks() 
	{
		return false;
	}
	
	@Override
	public boolean isTradeable()
	{
		return false;
	}
}
