package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IPlayerAnimationCapability;
import com.min01.beyondtheabyss.capabilities.PlayerAnimationCapabilityImpl;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

public class UpdatePlayerAnimationPacket 
{
	public final UUID uuid;
	public final IPlayerAnimationCapability cap;

	public UpdatePlayerAnimationPacket(UUID uuid, IPlayerAnimationCapability cap) 
	{
		this.uuid = uuid;
		this.cap = cap;
	}

	public UpdatePlayerAnimationPacket(FriendlyByteBuf buf)
	{
		this.uuid = buf.readUUID();
		IPlayerAnimationCapability cap = new PlayerAnimationCapabilityImpl();
		cap.deserializeNBT(buf.readNbt());
		this.cap = cap;
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.uuid);
		buf.writeNbt(this.cap.serializeNBT());
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
						if(entity instanceof LivingEntity living)
						{
							living.getCapability(BTACapabilities.PLAYER_ANIMATION).ifPresent(t -> 
							{
								t.setTag(message.cap.getTag());
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
