package com.min01.beyondtheabyss.network;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class BuildMultiPartPacket 
{
	private final UUID entityUUID;
	public final Map<String, Vec3> partOffset;
	public final Map<String, String> parts;

	public BuildMultiPartPacket(Entity entity, Map<String, Vec3> partOffset, Map<String, String> parts) 
	{
		this.entityUUID = entity.getUUID();
		this.partOffset = partOffset;
		this.parts = parts;
	}

	public BuildMultiPartPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
		this.partOffset = buf.readMap(t -> t.readUtf(), t -> BTAEntityDataSerializers.readVec3(t));
		this.parts = buf.readMap(t -> t.readUtf(), t -> t.readUtf());
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		buf.writeMap(this.partOffset, (t, u) -> t.writeUtf(u), (t, u) -> BTAEntityDataSerializers.writeVec3(t, u));
		buf.writeMap(this.parts, (t, u) -> t.writeUtf(u), (t, u) -> t.writeUtf(u));
	}

	public static class Handler 
	{
		public static boolean onMessage(BuildMultiPartPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for(ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityUUID);
					if(entity instanceof AbstractBTAMonster mob) 
					{
						EntityPartBuilder<?> builder = mob.partBuilder;
				    	mob.partBuilder.hitbox = builder.buildHitBox();
				    	mob.partBuilder.partOffset.putAll(message.partOffset);
				    	mob.partBuilder.parts.putAll(message.parts);
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}