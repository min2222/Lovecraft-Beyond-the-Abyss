package com.min01.beyondtheabyss.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class VampireDaggerItem extends Item
{
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	   
	public VampireDaggerItem(Item.Properties properties) 
	{
		super(properties.tab(DeepAbyssTabs.ABYSS_WEAPONS));
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 4.0D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -1.0D, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
	}
	
	@Override
	public boolean hurtEnemy(ItemStack p_43390_, LivingEntity p_43391_, LivingEntity p_43392_) 
	{
		p_43390_.hurtAndBreak(1, p_43392_, (p_43414_) ->
		{
			p_43414_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		p_43392_.heal(0.25F);
		if(Math.random() <= 0.1)
		{
			p_43391_.addEffect(new MobEffectInstance(BTAEffects.BLEEDING.get(), 40));
		}
		return true;
	}
	
	@Override
	public boolean mineBlock(ItemStack p_43399_, Level p_43400_, BlockState p_43401_, BlockPos p_43402_, LivingEntity p_43403_) 
	{
		if((double)p_43401_.getDestroySpeed(p_43400_, p_43402_) != 0.0D) 
		{
			p_43399_.hurtAndBreak(2, p_43403_, (p_43385_) ->
			{
				p_43385_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43383_)
	{
		return p_43383_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43383_);
	}
}
