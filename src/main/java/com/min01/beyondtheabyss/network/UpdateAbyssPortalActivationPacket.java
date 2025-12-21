package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.event.ClientEventHandlerForge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class UpdateAbyssPortalActivationPacket 
{
	public final boolean isActivated;

	public UpdateAbyssPortalActivationPacket(boolean isActivated) 
	{
		this.isActivated = isActivated;
	}

	public static UpdateAbyssPortalActivationPacket read(FriendlyByteBuf buf)
	{
		return new UpdateAbyssPortalActivationPacket(buf.readBoolean());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeBoolean(this.isActivated);
	}

	public static boolean handle(UpdateAbyssPortalActivationPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			if(ctx.get().getDirection().getReceptionSide().isClient()) 
			{
				ClientEventHandlerForge.ABYSS_PORTAL_ACTIVATED.set(message.isActivated);
			}
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}
