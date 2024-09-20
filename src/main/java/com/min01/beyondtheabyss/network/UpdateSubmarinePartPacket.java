package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class UpdateSubmarinePartPacket 
{
	private final int entityId;
	private final int array;
	private final Vec3 pos;

	public UpdateSubmarinePartPacket(Entity entity, Vec3 pos, int array) 
	{
		this.entityId = entity.getId();
		this.pos = pos;
		this.array = array;
	}

	public UpdateSubmarinePartPacket(FriendlyByteBuf buf)
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
		public static boolean onMessage(UpdateSubmarinePartPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				ServerPlayer player = ctx.get().getSender();
				if(player != null)
				{
					ServerLevel serverLevel = player.getLevel();
					Entity entity = serverLevel.getEntity(message.entityId);
					if(entity instanceof EntitySubmarine submarine) 
					{
						submarine.posArray[message.array] = message.pos;
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
