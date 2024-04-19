package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.deepabyss.AbstractMultipartDeepAbyssMob;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class PartRotationUpdatePacket 
{
	private final int entityId;

	private final float headRot;
	private final float bodyRot;
	private final float tailRot;

	private PartType partType;

	public enum PartType 
	{
		TAIL, BODY, HEAD
	}

	public PartRotationUpdatePacket(Entity entity, float headRot, float bodyRot, float tailRot, PartType type) 
	{
		this.entityId = entity.getId();
		this.headRot = headRot;
		this.bodyRot = bodyRot;
		this.tailRot = tailRot;
		this.partType = type;
	}

	public PartRotationUpdatePacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.headRot = buf.readFloat();
		this.bodyRot = buf.readFloat();
		this.tailRot = buf.readFloat();
		this.partType = PartType.values()[buf.readInt()];
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeFloat(this.headRot);
		buf.writeFloat(this.bodyRot);
		buf.writeFloat(this.tailRot);
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
					if (entity instanceof AbstractMultipartDeepAbyssMob mob) 
					{
						switch (message.partType) 
						{
						case TAIL:
							mob.setTailRot(message.tailRot);
						case BODY:
							mob.setBodyRot(message.bodyRot);
						case HEAD:
							mob.setHeadRot(message.headRot);
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
