package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class UpdateMultiPartPacket 
{
	private final int entityId;

	public UpdateMultiPartPacket(Entity entity) 
	{
		this.entityId = entity.getId();
	}

	public UpdateMultiPartPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateMultiPartPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityId);
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