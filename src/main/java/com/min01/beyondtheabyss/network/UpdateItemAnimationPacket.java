package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IItemAnimationCapability;
import com.min01.beyondtheabyss.capabilities.ItemAnimationCapabilityImpl;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateItemAnimationPacket 
{
	public final UUID uuid;
	public final ItemStack stack;
	public final IItemAnimationCapability cap;

	public UpdateItemAnimationPacket(UUID uuid, ItemStack stack, IItemAnimationCapability cap) 
	{
		this.uuid = uuid;
		this.stack = stack;
		this.cap = cap;
	}

	public UpdateItemAnimationPacket(FriendlyByteBuf buf)
	{
		this.uuid = buf.readUUID();
		this.stack = buf.readItem();
		IItemAnimationCapability cap = new ItemAnimationCapabilityImpl();
		cap.deserializeNBT(buf.readNbt());
		this.cap = cap;
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.uuid);
		buf.writeItem(this.stack);
		buf.writeNbt(this.cap.serializeNBT());
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
			                		t.setTag(message.cap.getTag());
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
