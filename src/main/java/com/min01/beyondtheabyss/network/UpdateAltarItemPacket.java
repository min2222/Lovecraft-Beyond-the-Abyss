package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.blockentity.deepabyss.RiftwellingAltarBlockEntity;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class UpdateAltarItemPacket 
{
	public final ItemStack stack;
	public final BlockPos pos;

	public UpdateAltarItemPacket(ItemStack stack, BlockPos pos) 
	{
		this.stack = stack;
		this.pos = pos;
	}

	public static UpdateAltarItemPacket read(FriendlyByteBuf buf)
	{
		return new UpdateAltarItemPacket(buf.readItem(), buf.readBlockPos());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeItem(this.stack);
		buf.writeBlockPos(this.pos);
	}

	public static boolean handle(UpdateAltarItemPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			if(ctx.get().getDirection().getReceptionSide().isClient()) 
			{
				BTAUtil.getClientLevel(level -> 
				{
					if(level.getBlockEntity(message.pos) instanceof RiftwellingAltarBlockEntity altar)
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
