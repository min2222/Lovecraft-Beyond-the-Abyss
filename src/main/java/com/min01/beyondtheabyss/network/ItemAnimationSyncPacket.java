package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.IItemAnimationCapability;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.misc.ClientEventHandler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class ItemAnimationSyncPacket 
{
	private final int entityId;
	private final int animationId;
	private final ItemStack stack;
	
	public ItemAnimationSyncPacket(Entity entity, ItemStack stack, IItemAnimationCapability cap) 
	{
		this.entityId = entity.getId();
		this.stack = stack;
		this.animationId = cap.getAnimationId();
	}

	public ItemAnimationSyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.stack = buf.readItem();
		this.animationId = buf.readInt();	
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeItem(this.stack);
		buf.writeInt(this.animationId);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(ItemAnimationSyncPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Entity entity = ClientEventHandler.MC.level.getEntity(message.entityId);
				if(entity instanceof Player player)
				{
					for(InteractionHand hands : InteractionHand.values())
					{
						ItemStack stack = player.getItemInHand(hands);
						stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(cap -> 
						{
							cap.setAnimationId(message.animationId);
						});	
					}
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
