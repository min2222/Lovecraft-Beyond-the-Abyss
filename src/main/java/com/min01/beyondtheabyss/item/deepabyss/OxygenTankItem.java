package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.item.armor.AbstractDivingSetItem;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class OxygenTankItem extends Item
{
	public OxygenTankItem() 
	{
		super(new Item.Properties());
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) 
	{
		ItemStack armorStack = p_41433_.getItemBySlot(EquipmentSlot.CHEST);
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		if(!stack.isEmpty() && !armorStack.isEmpty())
		{
			if(armorStack.getItem() instanceof AbstractDivingSetItem item)
			{
				AbstractDivingSetItem.setOxygen(armorStack, item.getMaxOxygen());
				if(!p_41433_.getAbilities().instabuild)
				{
					stack.shrink(1);
				}
				return InteractionResultHolder.success(stack);
			}
		}
		return super.use(p_41432_, p_41433_, p_41434_);
	}
}
