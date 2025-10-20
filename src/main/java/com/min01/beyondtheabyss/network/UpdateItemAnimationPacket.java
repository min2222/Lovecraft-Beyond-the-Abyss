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

public class UpdateItemAnimationPacket 
{
	public final ItemStack stack;
	public final UUID entityUUID;
	public final int animationState;
	public final int animationTick;

	public UpdateItemAnimationPacket(ItemStack stack, UUID entityUUID, int animationState, int animationTick) 
	{
		this.stack = stack;
		this.entityUUID = entityUUID;
		this.animationState = animationState;
		this.animationTick = animationTick;
	}

	public UpdateItemAnimationPacket(FriendlyByteBuf buf)
	{
		this.stack = buf.readItem();
		this.entityUUID = buf.readUUID();
		this.animationState = buf.readInt();
		this.animationTick = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeItem(this.stack);
		buf.writeUUID(this.entityUUID);
		buf.writeInt(this.animationState);
		buf.writeInt(this.animationTick);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateItemAnimationPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient())
				{
					BTAUtil.getClientLevel(level -> 
					{
						Entity entity = BTAUtil.getEntityByUUID(level, message.entityUUID);
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
			                if(to != null)
			                {
			                	to.getCapability(BTACapabilities.ITEM_ANIMATION).ifPresent(t -> 
			                	{
			    					t.setAnimationState(message.animationState);
			    					t.setAnimationTick(message.animationTick);
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