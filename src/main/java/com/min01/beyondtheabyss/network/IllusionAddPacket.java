package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class IllusionAddPacket 
{
	private final int entityId;
	
	public IllusionAddPacket(Entity entity) 
	{
		this.entityId = entity.getId();
	}

	public IllusionAddPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(IllusionAddPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					Minecraft.getInstance().doRunTask(() -> 
					{
						Entity entity = BTAClientUtil.MC.level.getEntity(message.entityId);
						if(entity instanceof Player player)
						{
							EntityGhidruth ghidruth = BTAEntities.GHIDRUTH.get().create(player.level);
							player.getCapability(BTACapabilities.ILLUSION).ifPresent(t -> 
							{
								t.setIllusion(ghidruth);
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
