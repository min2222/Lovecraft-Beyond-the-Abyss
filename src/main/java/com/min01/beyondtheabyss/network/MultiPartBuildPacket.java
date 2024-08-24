package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MultiPartBuildPacket 
{
	private final UUID entityUUID;

	public MultiPartBuildPacket(Entity entity) 
	{
		this.entityUUID = entity.getUUID();
	}

	public MultiPartBuildPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
	}

	public static class Handler 
	{
		public static boolean onMessage(MultiPartBuildPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityUUID);
					if(entity instanceof AbstractBTAMob mob) 
					{
						EntityPartBuilder<?> builder = mob.partBuilder;
				    	mob.partBuilder.hitbox = builder.buildHitBox();
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
