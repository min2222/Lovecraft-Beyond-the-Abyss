package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.deepabyss.AbstractMultipartDeepAbyssMob;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class PartPositionUpdatePacket 
{
	private final int entityId;

	private final Vec3 headPos;
	private final Vec3 bodyPos;
	private final Vec3 tailPos;

	private PartPosType partType;

	public enum PartPosType 
	{
		TAIL, BODY, HEAD
	}

	public PartPositionUpdatePacket(Entity entity, Vec3 headPos, Vec3 bodyPos, Vec3 tailPos, PartPosType type) 
	{
		this.entityId = entity.getId();
		this.headPos = headPos;
		this.bodyPos = bodyPos;
		this.tailPos = tailPos;
		this.partType = type;
	}

	public PartPositionUpdatePacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.headPos = BTAEntityDataSerializers.readVec3(buf);
		this.bodyPos = BTAEntityDataSerializers.readVec3(buf);
		this.tailPos = BTAEntityDataSerializers.readVec3(buf);
		this.partType = PartPosType.values()[buf.readInt()];
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		BTAEntityDataSerializers.writeVec3(buf, this.headPos);
		BTAEntityDataSerializers.writeVec3(buf, this.bodyPos);
		BTAEntityDataSerializers.writeVec3(buf, this.tailPos);
		buf.writeInt(this.partType.ordinal());
	}

	public static class Handler 
	{
		public static boolean onMessage(PartPositionUpdatePacket message, Supplier<NetworkEvent.Context> ctx)
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
							mob.setTailPos(message.tailPos);
						case BODY:
							mob.setBodyPos(message.bodyPos);
						case HEAD:
							mob.setHeadPos(message.headPos);
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
