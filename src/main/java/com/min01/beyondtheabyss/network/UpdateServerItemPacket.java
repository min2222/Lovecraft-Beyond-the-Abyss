package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.item.IServerUpdate;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateServerItemPacket 
{
	private final UUID entityUUID;
	private final ItemStack stack;

	public UpdateServerItemPacket(Entity entity, ItemStack stack) 
	{
		this.entityUUID = entity.getUUID();
		this.stack = stack;
	}

	public UpdateServerItemPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
		this.stack = buf.readItem();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		buf.writeItem(this.stack);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateServerItemPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isServer())
				{
					Entity entity = BTAUtil.getEntityByUUID(ctx.get().getSender().level, message.entityUUID);
					if(entity instanceof LivingEntity living) 
					{
		                ItemStack stackFrom = message.stack;
		                ItemStack to = null;
		                if(living.getItemInHand(InteractionHand.MAIN_HAND).is(stackFrom.getItem()))
		                {
		                    to = living.getItemInHand(InteractionHand.MAIN_HAND);
		                }
		                else if(living.getItemInHand(InteractionHand.OFF_HAND).is(stackFrom.getItem()))
		                {
		                    to = living.getItemInHand(InteractionHand.OFF_HAND);
		                }
		                if(to != null && to.getItem() instanceof IServerUpdate server)
		                {
		                	server.onServerUpdate(living, stackFrom);
		                }
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
