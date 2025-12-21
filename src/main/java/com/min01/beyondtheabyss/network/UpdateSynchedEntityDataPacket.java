package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.ISynchedEntityData;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class UpdateSynchedEntityDataPacket 
{
	public final int id;
	public final UUID entityUUID;

	public UpdateSynchedEntityDataPacket(int id, UUID uuid)
	{
		this.id = id;
		this.entityUUID = uuid;
	}

	public static UpdateSynchedEntityDataPacket read(FriendlyByteBuf buf)
	{
		return new UpdateSynchedEntityDataPacket(buf.readInt(), buf.readUUID());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeInt(this.id);
		buf.writeUUID(this.entityUUID);
	}

	public static boolean handle(UpdateSynchedEntityDataPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			if(ctx.get().getDirection().getReceptionSide().isServer()) 
			{
				Entity entity = BTAUtil.getEntityByUUID(ctx.get().getSender().level, message.entityUUID);
				if(entity instanceof ISynchedEntityData data)
				{
					data.onHandle(message.id);
				}
			}
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}
