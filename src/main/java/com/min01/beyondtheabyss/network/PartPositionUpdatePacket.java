package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
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
	private final int array;
	private final Vec3 pos;

	public PartPositionUpdatePacket(Entity entity, Vec3 pos, int array) 
	{
		this.entityId = entity.getId();
		this.pos = pos;
		this.array = array;
	}

	public PartPositionUpdatePacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.pos = BTAEntityDataSerializers.readVec3(buf);
		this.array = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		BTAEntityDataSerializers.writeVec3(buf, this.pos);
		buf.writeInt(this.array);
	}

	public static class Handler 
	{
		public static boolean onMessage(PartPositionUpdatePacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityId);
					if(entity instanceof AbstractBTAMob mob) 
					{
						mob.posArray[message.array] = message.pos;
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
