package com.min01.beyondtheabyss.item;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class BTAArmorMaterial implements ArmorMaterial
{
	private final String name;
	private final int[] durability;
	private final int[] slotProtections;
	private final int enchantmentValue;
	private final SoundEvent sound;
	private final float toughness;
	private final float knockbackResistance;
	private final Supplier<Ingredient> repairIngredient;

	public BTAArmorMaterial(String name, int[] durability, int[] slotProtections, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) 
	{
		this.name = name;
		this.durability = durability;
		this.slotProtections = slotProtections;
		this.enchantmentValue = enchantmentValue;
		this.sound = sound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = repairIngredient;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot p_40484_) 
	{
		return durability[p_40484_.getIndex()];
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot p_40487_)
	{
		return this.slotProtections[p_40487_.getIndex()];
	}

	@Override
	public int getEnchantmentValue() 
	{
		return this.enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound()
	{
		return this.sound;
	}

	@Override
	public Ingredient getRepairIngredient() 
	{
		return this.repairIngredient.get();
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public float getToughness()
	{
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance()
	{
		return this.knockbackResistance;
	}
}
