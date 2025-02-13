package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateAltarItemPacket 
{
	public final int entityId;
	public final ItemStack stack;
	public final BlockPos pos;

	public UpdateAltarItemPacket(Entity entity, ItemStack stack, BlockPos pos) 
	{
		this.entityId = entity.getId();
		this.stack = stack;
		this.pos = pos;
	}

	public UpdateAltarItemPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.stack = buf.readItem();
		this.pos = buf.readBlockPos();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeItem(this.stack);
		buf.writeBlockPos(this.pos);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateAltarItemPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					BTAUtil.getClientLevel(level -> 
					{
						Entity entity = level.getEntity(message.entityId);
						if(entity.level.getBlockEntity(message.pos) instanceof RiftwellingAltarBlockEntity altar)
						{
							altar.setItem(message.stack);
						}
					});
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
