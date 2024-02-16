package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.deepabyss.living.EntityGhidruth;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ModelPosSyncPacket 
{
	private final int entityId;

	private final float x;

	private final float y;

	private final float z;

	private PosType posType;

	public enum PosType 
	{
		TAIL, BODY, HEAD
	}

	public ModelPosSyncPacket(Entity entity, float x, float y, float z, PosType type) 
	{
		this.entityId = entity.getId();
		this.x = x;
		this.y = y;
		this.z = z;
		this.posType = type;
	}

	public ModelPosSyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.x = buf.readFloat();
		this.y = buf.readFloat();
		this.z = buf.readFloat();
		this.posType = PosType.values()[buf.readInt()];
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeFloat(this.x);
		buf.writeFloat(this.y);
		buf.writeFloat(this.z);
		buf.writeInt(this.posType.ordinal());
	}

	public static class Handler 
	{
		public static boolean onMessage(ModelPosSyncPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityId);
					if (entity instanceof EntityGhidruth) 
					{
						EntityGhidruth ghidruth = (EntityGhidruth) entity;
						switch (message.posType) 
						{
						case TAIL:
							ghidruth.setTailPos(new Vec3(message.x, message.y, message.z));
						case BODY:
							ghidruth.setBodyPos(new Vec3(message.x, message.y, message.z));
						case HEAD:
							ghidruth.setHeadPos(new Vec3(message.x, message.y, message.z));
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
