package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTAAbilityImpl.BTAAbilities;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.event.ClientEventHandler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class BTAAbilitySyncPacket 
{
	private final int entityId;
	private final int abilityId;
	private final int tickCount;
	
	public BTAAbilitySyncPacket(Entity entity, BTAAbilities ability, int tickCount) 
	{
		this.entityId = entity.getId();
		this.abilityId = ability.id;
		this.tickCount = tickCount;
	}

	public BTAAbilitySyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.abilityId = buf.readInt();	
		this.tickCount = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeInt(this.abilityId);
		buf.writeInt(this.tickCount);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(BTAAbilitySyncPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Entity entity = ClientEventHandler.MC.level.getEntity(message.entityId);
				if(entity instanceof Player player)
				{
					entity.getCapability(BTACapabilities.BTA_ABILITY).ifPresent(cap -> 
					{
						BTAAbilities ability = BTAAbilities.byId(message.abilityId);
						if(ability != BTAAbilities.NONE)
						{
							cap.addAbility(ability);
							cap.setTickCount(ability, message.tickCount);
						}
					});
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
