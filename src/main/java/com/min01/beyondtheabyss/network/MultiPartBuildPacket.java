package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.deepabyss.AbstractMultipartDeepAbyssMob;
import com.min01.beyondtheabyss.entity.multipart.ClientEntityPartBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MultiPartBuildPacket 
{
	private final int entityId;

	public MultiPartBuildPacket(Entity entity) 
	{
		this.entityId = entity.getId();
	}

	public MultiPartBuildPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}

	public static class Handler 
	{
		public static boolean onMessage(MultiPartBuildPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityId);
					if(entity instanceof AbstractMultipartDeepAbyssMob<?> mob) 
					{
				    	ClientEntityPartBuilder<?> clientBuilder = mob.getClientPartBuilder();
				    	mob.partBuilder.hitbox = clientBuilder.buildHitBox();
				    	mob.partBuilder.parts.putAll(clientBuilder.parts);
				    	mob.partBuilder.partOffset.putAll(clientBuilder.partOffset);
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
