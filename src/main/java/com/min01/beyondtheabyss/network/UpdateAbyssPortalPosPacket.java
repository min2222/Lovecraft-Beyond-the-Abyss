package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.event.ClientEventHandlerForge;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class UpdateAbyssPortalPosPacket 
{
	public final BlockPos pos;

	public UpdateAbyssPortalPosPacket(BlockPos pos) 
	{
		this.pos = pos;
	}

	public UpdateAbyssPortalPosPacket(FriendlyByteBuf buf)
	{
		this.pos = buf.readBlockPos();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeBlockPos(this.pos);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateAbyssPortalPosPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					ClientEventHandlerForge.ABYSS_PORTAL_POS.set(message.pos);
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
