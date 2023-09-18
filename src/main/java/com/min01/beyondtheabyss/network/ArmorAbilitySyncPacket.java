package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.AbyssCapabilities;
import com.min01.beyondtheabyss.capabilities.ArmorAbilityCapabilityHandler.AbyssArmorAbilities;
import com.min01.beyondtheabyss.misc.ClientEventHandler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class ArmorAbilitySyncPacket 
{
	private final int entityId;
	private final int abilityId;
	
	public ArmorAbilitySyncPacket(Entity entity, AbyssArmorAbilities ability) 
	{
		this.entityId = entity.getId();
		this.abilityId = ability.id;
	}

	public ArmorAbilitySyncPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.abilityId = buf.readInt();	
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		buf.writeInt(this.abilityId);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(ArmorAbilitySyncPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Entity entity = ClientEventHandler.MC.level.getEntity(message.entityId);
				if(entity instanceof Player player)
				{
					entity.getCapability(AbyssCapabilities.ARMOR_ABILITY).ifPresent(cap -> 
					{
						cap.setAbility(AbyssArmorAbilities.byId(message.abilityId));
					});
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
