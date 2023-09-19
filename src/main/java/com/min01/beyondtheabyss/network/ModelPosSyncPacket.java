package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ModelPosSyncPacket
{
	private final int entityId;
	private final float x;
	private final float y;
	private final float z;
	
	public ModelPosSyncPacket(Entity entity, float x, float y, float z) 
	{
		this.entityId = entity.getId();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ModelPosSyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.x = buf.readFloat();
		this.y = buf.readFloat();
		this.z = buf.readFloat();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeFloat(this.x);
		buf.writeFloat(this.y);
		buf.writeFloat(this.z);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(ModelPosSyncPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels())
				{
					Entity entity = level.getEntity(message.entityId);
					if(entity instanceof EntityGhidruth ghidruth)
					{
						ghidruth.setTailPosX(message.x);
						ghidruth.setTailPosY(message.y);
						ghidruth.setTailPosZ(message.z);
					}
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
