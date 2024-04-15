package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class PartRotationUpdatePacket 
{
	private final int entityId;

	private final float rot;

	private PartType partType;

	public enum PartType 
	{
		TAIL, BODY, HEAD
	}

	public PartRotationUpdatePacket(Entity entity, float rot, PartType type) 
	{
		this.entityId = entity.getId();
		this.rot = rot;
		this.partType = type;
	}

	public PartRotationUpdatePacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.rot = buf.readFloat();
		this.partType = PartType.values()[buf.readInt()];
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeFloat(this.rot);
		buf.writeInt(this.partType.ordinal());
	}

	public static class Handler 
	{
		public static boolean onMessage(PartRotationUpdatePacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityId);
					if (entity instanceof EntityGhidruth ghidruth) 
					{
						switch (message.partType) 
						{
						case TAIL:
							ghidruth.setTailRot(message.rot);
						case BODY:
							ghidruth.setBodyRot(message.rot);
						case HEAD:
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
