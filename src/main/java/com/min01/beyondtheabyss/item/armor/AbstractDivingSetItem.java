package com.min01.beyondtheabyss.item.armor;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

public abstract class AbstractDivingSetItem extends ArmorItem
{
	protected final float swimSpeed;
	public static final String OXYGEN = "Oxygen";
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
	
	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_)
	{
		if(this.type == ArmorItem.Type.CHESTPLATE)
		{
			int oxygen = getOxygen(p_41421_);
			p_41423_.add(Component.translatable("item.beyondtheabyss.diving_set.oxygen_left", oxygen, this.getMaxOxygen()).withStyle(ChatFormatting.AQUA));
		}
	}
	
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player)
	{
		int oxygen = getOxygen(stack);
		if(oxygen > 0 && player.isEyeInFluidType(ForgeMod.WATER_TYPE.get()) && !player.getAbilities().instabuild)
		{
			player.setAirSupply(player.getMaxAirSupply());
			int amount = 1;
			if(player.hasEffect(BTAEffects.LUNGSPORE.get()))
			{
				MobEffectInstance effect = player.getEffect(BTAEffects.LUNGSPORE.get());
				amount = effect.getAmplifier() + 2;
			}
			setOxygen(stack, oxygen - amount);
		}
	}
	
    public static void setOxygen(ItemStack stack, int oxygen)
    {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(OXYGEN, oxygen);
    }
	
    public static int getOxygen(ItemStack stack)
    {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getInt(OXYGEN) : stack.getItem() instanceof AbstractDivingSetItem item ? item.getMaxOxygen() : 1200; //1 minutes
    }
    
    public abstract int getMaxOxygen();
}
