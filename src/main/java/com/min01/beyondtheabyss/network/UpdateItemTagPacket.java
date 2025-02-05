package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateItemTagPacket
{
	public final int entityId;
	public final ItemStack stack;

	public UpdateItemTagPacket(Entity entity, ItemStack stack) 
	{
		this.entityId = entity.getId();
		this.stack = stack;
	}

	public UpdateItemTagPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.stack = buf.readItem();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeItem(this.stack);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateItemTagPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getSender() != null)
				{
					ServerPlayer player = ctx.get().getSender();
		            Entity entity = player.level.getEntity(message.entityId);
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
		                if(to != null && stack.getTag() != null)
		                {
		                    to.setTag(stack.getTag());
		                }
		            }
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
