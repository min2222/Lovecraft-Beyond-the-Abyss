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
    		event.addCapability(ItemAnimationCapabilityImpl.ID, new ItemAnimationCapabilityImpl(stack));
    	}
	}
	
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event)
	{
    	Entity entity = event.getObject();
		if(entity instanceof Player player)
		{
			event.addCapability(PlayerAnimationCapabilityImpl.ID, new PlayerAnimationCapabilityImpl(player));
			event.addCapability(PlayerTickCountCapabilityImpl.ID, new PlayerTickCountCapabilityImpl(player));
		}
	}
}
