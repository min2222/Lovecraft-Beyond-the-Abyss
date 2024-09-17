package com.min01.beyondtheabyss.item.armor;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

public abstract class AbstractDivingSetItem extends ArmorItem
{
	protected final float swimSpeed;
	public AbstractDivingSetItem(ArmorMaterial material, EquipmentSlot slot, float swimSpeed) 
	{
		super(material, slot, new Item.Properties().tab(DeepAbyssTabs.ABYSS_ARMORS));
		this.swimSpeed = swimSpeed;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
	{
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getAttributeModifiers(EquipmentSlot.FEET, stack));
		builder.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.randomUUID(), "Swim Speed", this.swimSpeed, AttributeModifier.Operation.ADDITION));
		return slot == EquipmentSlot.FEET && this.slot == EquipmentSlot.FEET ? builder.build() : super.getAttributeModifiers(slot, stack);
	}
}
