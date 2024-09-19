package com.min01.beyondtheabyss.item.armor;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

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
	public AbstractDivingSetItem(ArmorMaterial material, ArmorItem.Type type, float swimSpeed) 
	{
		super(material, type, new Item.Properties());
		this.swimSpeed = swimSpeed;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
	{
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getAttributeModifiers(EquipmentSlot.FEET, stack));
		builder.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.randomUUID(), "Swim Speed", this.swimSpeed, AttributeModifier.Operation.ADDITION));
		return slot == EquipmentSlot.FEET && this.type.getSlot() == EquipmentSlot.FEET ? builder.build() : super.getAttributeModifiers(slot, stack);
	}
}
