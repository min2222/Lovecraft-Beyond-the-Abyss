package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.event.ClientEventHandlerForge;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class IllusionAddPacket 
{
	private final UUID entityUUID;
	
	public IllusionAddPacket(Entity entity) 
	{
		this.entityUUID = entity.getUUID();
	}

	public IllusionAddPacket(FriendlyByteBuf buf)
	{
		this.entityUUID = buf.readUUID();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.entityUUID);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(IllusionAddPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				for(ServerLevel serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels())
				{
					Entity entity = BTAUtil.getEntityByUUID(serverLevel, message.entityUUID);
					if(entity instanceof ServerPlayer serverPlayer)
					{
						EntityGhidruth ghidruth = BTAEntities.GHIDRUTH.get().create(serverPlayer.level);
						serverPlayer.getCapability(BTACapabilities.ILLUSION).ifPresent(t -> 
						{
							t.setIllusion(ghidruth);
						});
					}
				}
				if(BTAUtil.getEntityByUUID(ClientEventHandlerForge.MC.level, message.entityUUID) instanceof Player player)
				{
					EntityGhidruth ghidruth = BTAEntities.GHIDRUTH.get().create(player.level);
					player.getCapability(BTACapabilities.ILLUSION).ifPresent(t -> 
					{
						t.setIllusion(ghidruth);
					});
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
