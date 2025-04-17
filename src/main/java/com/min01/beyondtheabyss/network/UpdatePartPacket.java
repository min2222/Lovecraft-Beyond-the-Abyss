package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.multipart.EntityPartBuilder.Part;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class UpdatePartPacket 
{
	private final UUID entityUUID;
	
	public final String name;
	
	public float x;
	public float y;
	public float z;
	
	public float xRot;
	public float yRot;
	public float zRot;

	public UpdatePartPacket(Entity entity, String name, float x, float y, float z, float xRot, float yRot, float zRot) 
	{
		this.entityUUID = entity.getUUID();
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.xRot = xRot;
		this.yRot = yRot;
		this.zRot = zRot;
	}

	public UpdatePartPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
		this.name = buf.readUtf();
		this.x = buf.readFloat();
		this.y = buf.readFloat();
		this.z = buf.readFloat();
		this.xRot = buf.readFloat();
		this.yRot = buf.readFloat();
		this.zRot = buf.readFloat();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		buf.writeUtf(this.name);
		buf.writeFloat(this.x);
		buf.writeFloat(this.y);
		buf.writeFloat(this.z);
		buf.writeFloat(this.xRot);
		buf.writeFloat(this.yRot);
		buf.writeFloat(this.zRot);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdatePartPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isServer())
				{
					Entity entity = BTAUtil.getEntityByUUID(ctx.get().getSender().level, message.entityUUID);
					if(entity instanceof IMultipart multipart) 
					{
						Part part = multipart.getPartBuilder().partMap.get(message.name);
						if(part != null)
						{
							part.tick(message.x, message.y, message.z, message.xRot, message.yRot, message.zRot);
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
