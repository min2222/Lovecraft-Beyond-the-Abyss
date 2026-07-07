package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.ItemAnimationCapabilityImpl;
import com.min01.beyondtheabyss.item.animation.ItemAnimations;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateItemAnimationPacket 
{
    public final UUID entityUUID;
    public final long instanceId;
    public final int animationState;
    public final int animationTick;

	public UpdateItemAnimationPacket(UUID entityUUID, long instanceId, int animationState, int animationTick) 
	{
		this.entityUUID = entityUUID;
		this.instanceId = instanceId;
		this.animationState = animationState;
		this.animationTick = animationTick;
	}

	public static UpdateItemAnimationPacket read(FriendlyByteBuf buf)
	{
		return new UpdateItemAnimationPacket(buf.readUUID(), buf.readLong(), buf.readInt(), buf.readInt());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		buf.writeLong(this.instanceId);
		buf.writeInt(this.animationState);
		buf.writeInt(this.animationTick);
	}

	public static boolean handle(UpdateItemAnimationPacket message, Supplier<NetworkEvent.Context> ctx)
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
						ItemStack stack = ItemAnimations.findStackByInstanceId(living, message.instanceId);
						if(!stack.isEmpty())
						{
						    stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).ifPresent(cap -> cap.sync(message.animationState, message.animationTick));
						}
					}
				});
			}
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}