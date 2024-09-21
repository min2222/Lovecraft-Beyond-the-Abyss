package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.IPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder.Part;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class UpdateMultiPartPacket 
{
	private final UUID entityUUID;
	private final Part part;

	public UpdateMultiPartPacket(Entity entity, Part part) 
	{
		this.entityUUID = entity.getUUID();
		this.part = part;
	}

	public UpdateMultiPartPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
		this.part = Part.read(buf);
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		Part.write(buf, this.part);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdateMultiPartPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				ServerPlayer player = ctx.get().getSender();
				if(player != null)
				{
					ServerLevel serverLevel = player.getLevel();
					Entity entity = serverLevel.getEntity(message.entityUUID);
					if(entity instanceof IPartBuilder mob) 
					{
				    	EntityPartBuilder<?> partBuilder = mob.getPartBuilder();
				    	for(Part part : partBuilder.partMap.values())
				    	{
				    		if(part.name == message.part.name)
				    		{
				    			part.tick(message.part.x, message.part.y, message.part.z, message.part.xRot, message.part.yRot, message.part.zRot);
				    		}
				    	}
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}