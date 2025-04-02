package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.IPosArray;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class UpdatePosArrayPacket 
{
	private final UUID entityUUID;
	private final int array;
	private final Vec3 pos;

	public UpdatePosArrayPacket(Entity entity, Vec3 pos, int array) 
	{
		this.entityUUID = entity.getUUID();
		this.pos = pos;
		this.array = array;
	}

	public UpdatePosArrayPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
		this.pos = BTAEntityDataSerializers.readVec3(buf);
		this.array = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		BTAEntityDataSerializers.writeVec3(buf, this.pos);
		buf.writeInt(this.array);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdatePosArrayPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient())
				{
					BTAUtil.getClientLevel(t -> 
					{
						Entity entity = BTAUtil.getEntityByUUID(t, message.entityUUID);
						if(entity instanceof IPosArray mob) 
						{
							mob.getPosArray()[message.array] = message.pos;
						}
					});
				}
				else
				{
					Entity entity = BTAUtil.getEntityByUUID(ctx.get().getSender().level, message.entityUUID);
					if(entity instanceof IPosArray mob) 
					{
						mob.getPosArray()[message.array] = message.pos;
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
