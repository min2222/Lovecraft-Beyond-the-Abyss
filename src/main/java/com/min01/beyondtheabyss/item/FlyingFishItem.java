package com.min01.beyondtheabyss.item;

import java.util.List;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class FlyingFishItem extends Item
{
	public FlyingFishItem() 
	{
		super(new Item.Properties().stacksTo(1).rarity(BTAItems.RARITY_DEEP_ABYSS));
	}
	
	@Override
	public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) 
	{
		pInteractionTarget.addEffect(new MobEffectInstance(BTAEffects.AIR_SWIM.get(), 100000, 0));
		return InteractionResult.SUCCESS;
	}
	
	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) 
	{
		pTooltipComponents.add(Component.translatable("item.beyondtheabyss.flying_fish.desc1").withStyle(ChatFormatting.AQUA));
		pTooltipComponents.add(Component.translatable("item.beyondtheabyss.flying_fish.desc2").withStyle(ChatFormatting.AQUA));
	}
}
