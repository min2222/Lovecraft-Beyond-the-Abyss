package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IllusionCapability;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class IllusionSyncPacket 
{
	private final int entityId;
	
	public IllusionSyncPacket(Entity entity) 
	{
		this.entityId = entity.getId();
	}

	public IllusionSyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(IllusionSyncPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Entity illusion = null;
				for(ServerLevel serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels())
				{
					Entity entity = serverLevel.getEntity(message.entityId);
					if(entity instanceof ServerPlayer serverPlayer)
					{
						if(serverPlayer.getCapability(BTACapabilities.ILLUSION).isPresent())
						{
							IllusionCapability cap = serverPlayer.getCapability(BTACapabilities.ILLUSION).orElse(null);
							if(cap.getIllusion() != null)
							{
								illusion = cap.getIllusion();
							}
						}
					}
				}
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					Entity entity = BTAClientUtil.MC.level.getEntity(message.entityId);
					if(entity instanceof Player player)
					{
						if(player.getCapability(BTACapabilities.ILLUSION).isPresent())
						{
							IllusionCapability cap = player.getCapability(BTACapabilities.ILLUSION).orElse(null);
							if(cap.getIllusion() != null && illusion != null)
							{
								cap.getIllusion().setYBodyRot(((Mob)illusion).yBodyRot);
								cap.getIllusion().setYHeadRot(illusion.getYHeadRot());
								cap.getIllusion().setYRot(illusion.getYRot());
								cap.getIllusion().setXRot(illusion.getXRot());
								cap.getIllusion().setPos(illusion.position());
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
