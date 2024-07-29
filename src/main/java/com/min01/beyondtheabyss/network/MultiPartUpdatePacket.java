package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.cerbon.EntityPart;
import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.multipart.ClientEntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder.Part;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MultiPartUpdatePacket 
{
	private final int entityId;

	public MultiPartUpdatePacket(Entity entity) 
	{
		this.entityId = entity.getId();
	}

	public MultiPartUpdatePacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}

	public static class Handler 
	{
		public static boolean onMessage(MultiPartUpdatePacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityId);
					if(entity instanceof AbstractBTAMob mob) 
					{
				    	EntityPartBuilder<?> partBuilder = mob.partBuilder;
				    	ClientEntityPartBuilder<?> clientBuilder = mob.getClientPartBuilder();
				    	clientBuilder.tick(1.0F);
				    	clientBuilder.model.root().getAllParts().forEach(part -> 
				    	{
				            String name = clientBuilder.getModelPartName(clientBuilder.model.root(), part);
				            EntityPart entityPart = partBuilder.hitbox.getPart(name);
				            if(entityPart != null)
				            {
					            partBuilder.allParts.add(new Part(name, entityPart, part.x, part.y, part.z, part.xRot, part.yRot, part.zRot));
				            }
				    	});
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
