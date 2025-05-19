package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateItemTickCountPacket 
{
	public final UUID uuid;
	public final ItemStack stack;
	public final int tickCount;

	public UpdateItemTickCountPacket(UUID uuid, ItemStack stack, int tickCount) 
	{
		this.uuid = uuid;
		this.stack = stack;
		this.tickCount = tickCount;
	}

	public UpdateItemTickCountPacket(FriendlyByteBuf buf)
	{
		this.uuid = buf.readUUID();
		this.stack = buf.readItem();
		this.tickCount = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.uuid);
		buf.writeItem(this.stack);
		buf.writeInt(this.tickCount);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateItemTickCountPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					BTAUtil.getClientLevel(level -> 
					{
						Entity entity = BTAUtil.getEntityByUUID(level, message.uuid);
						if(entity instanceof LivingEntity living)
						{
			                ItemStack stack = message.stack;
			                ItemStack to = null;
			                if(living.getItemInHand(InteractionHand.MAIN_HAND).is(stack.getItem()))
			                {
			                    to = living.getItemInHand(InteractionHand.MAIN_HAND);
			                }
			                else if(living.getItemInHand(InteractionHand.OFF_HAND).is(stack.getItem()))
			                {
			                    to = living.getItemInHand(InteractionHand.OFF_HAND);
			                }
			                if(to != null)
			                {
			                	to.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
			                	{
			                		t.setTickCount(message.tickCount);
			                	});
			                }
						}
					});
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}