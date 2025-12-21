package com.min01.beyondtheabyss.item.deepabyss;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

public abstract class AbstractDivingSetItem extends ArmorItem
{
	public static final String OXYGEN = "Oxygen";
	public final Multimap<Attribute, AttributeModifier> attributeModifiers;
	
	public AbstractDivingSetItem(ArmorMaterial material, ArmorItem.Type type, float swimSpeed) 
	{
		super(material, type, new Item.Properties());
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.randomUUID(), "Swim Speed", swimSpeed, AttributeModifier.Operation.ADDITION));
		builder.putAll(this.getDefaultAttributeModifiers(EquipmentSlot.FEET));
		this.attributeModifiers = builder.build();
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
	{
		if(slot == this.type.getSlot() && this.type.getSlot() == EquipmentSlot.FEET)
		{
			return this.attributeModifiers;
		}
		return super.getAttributeModifiers(slot, stack);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) 
	{
		//TODO oxygen system;
		if(this.type == Type.HELMET)
		{
			player.setAirSupply(player.getMaxAirSupply());
		}
	}
    
    public abstract int getMaxOxygen();
}
