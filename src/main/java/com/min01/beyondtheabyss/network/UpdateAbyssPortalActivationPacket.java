package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.event.ClientEventHandlerForge;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class UpdateAbyssPortalActivationPacket 
{
	public final ResourceKey<Level> dimension;
	public final boolean isActivated;

	public UpdateAbyssPortalActivationPacket(ResourceKey<Level> dimension, boolean isActivated) 
	{
		this.dimension = dimension;
		this.isActivated = isActivated;
	}

	public UpdateAbyssPortalActivationPacket(FriendlyByteBuf buf)
	{
		this.dimension = buf.readResourceKey(Registries.DIMENSION);
		this.isActivated = buf.readBoolean();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeResourceKey(this.dimension);
		buf.writeBoolean(this.isActivated);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateAbyssPortalActivationPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					ClientEventHandlerForge.ABYSS_PORTAL_ACTIVATED.put(message.dimension, message.isActivated);
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
