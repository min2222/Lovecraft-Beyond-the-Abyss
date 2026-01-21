package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.shader.BTAWorldShader;
import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class UpdateChunkCachePacket 
{
	public final long pos;

	public UpdateChunkCachePacket(long pos) 
	{
		this.pos = pos;
	}

	public static UpdateChunkCachePacket read(FriendlyByteBuf buf)
	{
		return new UpdateChunkCachePacket(buf.readLong());
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeLong(this.pos);
	}

	public static boolean handle(UpdateChunkCachePacket message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			if(ctx.get().getDirection().getReceptionSide().isClient()) 
			{
				for(BTAWorldShader shader : BTAWorldShader.WORLD_SHADERS)
				{
					if(shader.world != BTAWorlds.EVERGREEN || shader.chunkCache.containsKey(message.pos))
					{
						continue;
					}
					shader.chunkCache.put(message.pos, true);
				}
			}
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}
