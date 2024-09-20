package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class UpdateMultiPartPacket 
{
	private final UUID entityUUID;

	public UpdateMultiPartPacket(Entity entity) 
	{
		this.entityUUID = entity.getUUID();
	}

	public UpdateMultiPartPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateMultiPartPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				ServerPlayer player = ctx.get().getSender();
				if(player != null)
				{
					ServerLevel serverLevel = player.getLevel();
					Entity entity = serverLevel.getEntity(message.entityUUID);
					if(entity instanceof AbstractBTAMonster mob) 
					{
				    	EntityPartBuilder<?> partBuilder = mob.partBuilder;
				    	partBuilder.clientTick(1.0F);
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}