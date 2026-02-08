package com.min01.beyondtheabyss.network;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.multipart.EntityBounds;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder.Part;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class BuildMultipartPacket 
{
	private final UUID entityUUID;
	
	public final Map<String, Vec3> partOffset;
	public final Map<String, String> parts;
	public final Map<String, Part> partMap;
	public final EntityBounds bounds;

	public BuildMultipartPacket(UUID entityUUID, Map<String, Vec3> partOffset, Map<String, String> parts, Map<String, Part> partMap, EntityBounds bounds) 
	{
		this.entityUUID = entityUUID;
		this.partOffset = partOffset;
		this.parts = parts;
		this.partMap = partMap;
		this.bounds = bounds;
	}

	public static BuildMultipartPacket read(FriendlyByteBuf buf)
	{
	    return new BuildMultipartPacket(buf.readUUID(), buf.readMap(t -> t.readUtf(), t -> BTAEntityDataSerializers.readVec3(t)), buf.readMap(t -> t.readUtf(), t -> t.readUtf()), buf.readMap(t -> t.readUtf(), t -> Part.read(t)), EntityBounds.read(buf));
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
		buf.writeMap(this.partOffset, (t, u) -> t.writeUtf(u), (t, u) -> BTAEntityDataSerializers.writeVec3(t, u));
		buf.writeMap(this.parts, (t, u) -> t.writeUtf(u), (t, u) -> t.writeUtf(u));
		buf.writeMap(this.partMap, (t, u) -> t.writeUtf(u), (t, u) -> Part.write(t, u));
		this.bounds.write(buf);
	}

	public static boolean handle(BuildMultipartPacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			if(ctx.get().getDirection().getReceptionSide().isServer())
			{
				Entity entity = BTAUtil.getEntityByUUID(ctx.get().getSender().level, message.entityUUID);
				if(entity instanceof IMultipart multipart)
				{
					EntityPartBuilder<?> partBuilder = multipart.getPartBuilder();
					partBuilder.partOffset.clear();
					partBuilder.parts.clear();
					partBuilder.partMap.clear();
					partBuilder.partOffset.putAll(message.partOffset);
					partBuilder.parts.putAll(message.parts);
					partBuilder.partMap.putAll(message.partMap);
					partBuilder.hitbox = message.bounds;
				}
			}
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}
