package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.item.deepabyss.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateSkeletalGunbladeItemPacket 
{
	public final ItemStack stack;
	public final UUID entityUUID;

	public UpdateSkeletalGunbladeItemPacket(ItemStack stack, UUID entityUUID) 
	{
		this.stack = stack;
		this.entityUUID = entityUUID;
	}

	public static UpdateSkeletalGunbladeItemPacket read(FriendlyByteBuf buf)
	{
		return new UpdateSkeletalGunbladeItemPacket(buf.readItem(), buf.readUUID());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeItem(this.stack);
		buf.writeUUID(this.entityUUID);
	}

	public static boolean handle(UpdateSkeletalGunbladeItemPacket message, Supplier<NetworkEvent.Context> ctx)
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
	                if(to != null)
	                {
	                	if(to.getItem() instanceof SkeletalGunbladeItem item)
	                	{
	                		item.releaseUsingServer(to, living);
	                	}
	                }
				}
			}
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}