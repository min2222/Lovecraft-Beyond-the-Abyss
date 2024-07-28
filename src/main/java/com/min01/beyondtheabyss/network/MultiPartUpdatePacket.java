package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.cerbon.EntityPart;
import com.min01.beyondtheabyss.entity.deepabyss.AbstractMultipartDeepAbyssMob;
import com.min01.beyondtheabyss.entity.multipart.ClientEntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
				if(ctx.get().getDirection().getReceptionSide().isServer())
				{
					for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
					{
						Entity entity = level.getEntity(message.entityId);
						if(entity instanceof AbstractMultipartDeepAbyssMob<?> mob) 
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
					            	partBuilder.entityParts.put(entityPart, name);
					            }
						    	partBuilder.allParts.put(new Vec3(part.x, part.y, part.z), new Vec3(part.xRot, part.yRot, part.zRot));
					    	});
						}
					}
				}
				else
				{
					Minecraft.getInstance().doRunTask(() -> 
					{
						Level level = BTAClientUtil.MC.level;
						Entity entity = level.getEntity(message.entityId);
						if(entity instanceof AbstractMultipartDeepAbyssMob<?> mob) 
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
					            	partBuilder.entityParts.put(entityPart, name);
					            }
						    	partBuilder.allParts.put(new Vec3(part.x, part.y, part.z), new Vec3(part.xRot, part.yRot, part.zRot));
					    	});
						}
					});
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
