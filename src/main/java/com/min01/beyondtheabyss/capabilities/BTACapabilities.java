package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.item.animation.IAnimatableItem;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class BTACapabilities
{
	public static void onAttachItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event)
	{
    	ItemStack stack = event.getObject();
    	if(stack.getItem() instanceof IAnimatableItem)
    	{
    		ItemAnimationCapabilityImpl cap = new ItemAnimationCapabilityImpl();
    		cap.setItemStack(stack);
    		event.addCapability(ItemAnimationCapabilityImpl.ID, cap);
    	}
	}
	
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
    	Entity entity = event.getObject();
		if(entity instanceof Player player)
		{
			PlayerAnimationCapabilityImpl cap = new PlayerAnimationCapabilityImpl();
			PlayerTickCountCapabilityImpl cap1 = new PlayerTickCountCapabilityImpl();
			cap.setEntity(player);
			cap1.setEntity(player);
			event.addCapability(PlayerAnimationCapabilityImpl.ID, cap);
			event.addCapability(PlayerTickCountCapabilityImpl.ID, cap1);
		}
	}
}
