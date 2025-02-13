package com.min01.beyondtheabyss.network;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder.Part;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class BuildMultiPartPacket 
{
	private final UUID entityUUID;
	public final Map<String, Vec3> partOffset;
	public final Map<String, String> parts;
	public final Map<String, Part> partMap;

	public BuildMultiPartPacket(Entity entity, Map<String, Vec3> partOffset, Map<String, String> parts, Map<String, Part> partMap) 
	{
		this.entityUUID = entity.getUUID();
		this.partOffset = partOffset;
		this.parts = parts;
		this.partMap = partMap;
	}

	public BuildMultiPartPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
		this.partOffset = buf.readMap(t -> t.readUtf(), t -> BTAEntityDataSerializers.readVec3(t));
		this.parts = buf.readMap(t -> t.readUtf(), t -> t.readUtf());
		this.partMap = buf.readMap(t -> t.readUtf(), t -> Part.read(t));
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		buf.writeMap(this.partOffset, (t, u) -> t.writeUtf(u), (t, u) -> BTAEntityDataSerializers.writeVec3(t, u));
		buf.writeMap(this.parts, (t, u) -> t.writeUtf(u), (t, u) -> t.writeUtf(u));
		buf.writeMap(this.partMap, (t, u) -> t.writeUtf(u), (t, u) -> Part.write(t, u));
	}

	public static class Handler 
	{
		public static boolean onMessage(BuildMultiPartPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				ServerPlayer player = ctx.get().getSender();
				if(player != null)
				{
					ServerLevel serverLevel = player.getLevel();
					Entity entity = serverLevel.getEntity(message.entityUUID);
					if(entity instanceof IMultipart mob) 
					{
						EntityPartBuilder<?> builder = mob.getPartBuilder();
						builder.hitbox = builder.buildHitBox();
						builder.partOffset.clear();
						builder.parts.clear();
						builder.partMap.clear();
						builder.partOffset.putAll(message.partOffset);
						builder.parts.putAll(message.parts);
						builder.partMap.putAll(message.partMap);
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}