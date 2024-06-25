package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IllusionCapability;
import com.min01.beyondtheabyss.event.ClientEventHandler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class IllusionSyncPacket 
{
	private final int entityId;
	
	public IllusionSyncPacket(Entity entity, IllusionCapability cap) 
	{
		this.entityId = entity.getId();
	}

	public IllusionSyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(IllusionSyncPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Entity entity = ClientEventHandler.MC.level.getEntity(message.entityId);
				if(entity instanceof Player player)
				{
					player.getCapability(BTACapabilities.ILLUSION).ifPresent(cap -> 
					{
						
					});
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
