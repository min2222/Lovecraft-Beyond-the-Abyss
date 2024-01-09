package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.entity.deepabyss.living.EntityGhidruth;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ModelDataSyncPacket 
{
	private final int entityId;

	private final float x;

	private final float y;

	private final float z;

	private ModelType modelType;

	public enum ModelType 
	{
		TAIL_ROT, HEAD_ROT
	}

	public ModelDataSyncPacket(Entity entity, float x, float y, float z, ModelType type) 
	{
		this.entityId = entity.getId();
		this.x = x;
		this.y = y;
		this.z = z;
		this.modelType = type;
	}

	public ModelDataSyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.x = buf.readFloat();
		this.y = buf.readFloat();
		this.z = buf.readFloat();
		this.modelType = ModelType.values()[buf.readInt()];
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeFloat(this.x);
		buf.writeFloat(this.y);
		buf.writeFloat(this.z);
		buf.writeInt(this.modelType.ordinal());
	}

	public static class Handler 
	{
		public static boolean onMessage(ModelDataSyncPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) 
				{
					Entity entity = level.getEntity(message.entityId);
					if (entity instanceof EntityGhidruth) 
					{
						EntityGhidruth ghidruth = (EntityGhidruth) entity;
						switch (message.modelType) 
						{
						case TAIL_ROT:
							ghidruth.setTailRotation(new BlockPos(message.x, message.y, message.z));
						case HEAD_ROT:
							ghidruth.setHeadRotation(new BlockPos(message.x, message.y, message.z));
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
