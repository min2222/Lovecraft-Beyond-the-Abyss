package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateItemAnimationPacket 
{
	public final UUID uuid;
	public final ItemStack stack;
	public final int animationState;
	public final int animationTick;
	public final boolean isState;

	public UpdateItemAnimationPacket(UUID uuid, ItemStack stack, int animationState, int animationTick, boolean isState) 
	{
		this.uuid = uuid;
		this.stack = stack;
		this.animationState = animationState;
		this.animationTick = animationTick;
		this.isState = isState;
	}

	public UpdateItemAnimationPacket(FriendlyByteBuf buf)
	{
		this.uuid = buf.readUUID();
		this.stack = buf.readItem();
		this.animationState = buf.readInt();
		this.animationTick = buf.readInt();
		this.isState = buf.readBoolean();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.uuid);
		buf.writeItem(this.stack);
		buf.writeInt(this.animationState);
		buf.writeInt(this.animationTick);
		buf.writeBoolean(this.isState);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateItemAnimationPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
                	message.stack.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
                	{
						if(message.isState)
						{
							t.setAnimationState(message.animationState);
						}
						else
						{
							t.setAnimationTick(message.animationTick);
						}
                	});
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}