package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class UpdatePlayerAnimationPacket 
{
	public final UUID uuid;
	public final int animationState;
	public final int animationTick;
	public final boolean isState;

	public UpdatePlayerAnimationPacket(UUID uuid, int animationState, int animationTick, boolean isState) 
	{
		this.uuid = uuid;
		this.animationState = animationState;
		this.animationTick = animationTick;
		this.isState = isState;
	}

	public UpdatePlayerAnimationPacket(FriendlyByteBuf buf)
	{
		this.uuid = buf.readUUID();
		this.animationState = buf.readInt();
		this.animationTick = buf.readInt();
		this.isState = buf.readBoolean();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.uuid);
		buf.writeInt(this.animationState);
		buf.writeInt(this.animationTick);
		buf.writeBoolean(this.isState);
	}

	public static class Handler 
	{
		public static boolean onMessage(UpdatePlayerAnimationPacket message, Supplier<NetworkEvent.Context> ctx)
		{
			ctx.get().enqueueWork(() ->
			{
				if(ctx.get().getDirection().getReceptionSide().isClient()) 
				{
					BTAUtil.getClientLevel(level -> 
					{
						Entity entity = BTAUtil.getEntityByUUID(level, message.uuid);
						if(entity instanceof Player player)
						{
							player.getCapability(BTACapabilities.PLAYER_ANIMATION).ifPresent(t -> 
							{
								if(message.isState)
								{
									t.setAnimationState(message.animationState);
								}
								else
								{
									t.setAnimationTick(message.animationTick);
								}
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