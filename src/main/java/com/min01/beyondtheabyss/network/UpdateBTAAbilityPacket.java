package com.min01.beyondtheabyss.network;

import java.util.function.Supplier;

import com.min01.beyondtheabyss.capabilities.BTAAbilityImpl.BTAAbility;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class UpdateBTAAbilityPacket 
{
	private final int entityId;
	private final BTAAbility ability;
	private final PacketType type;
	private final int tickCount;
	
	public static enum PacketType
	{
		ADD,
		REMOVE,
		TICK;
	}
	
	public UpdateBTAAbilityPacket(Entity entity, BTAAbility ability, int tickCount, PacketType type) 
	{
		this.entityId = entity.getId();
		this.ability = ability;
		this.tickCount = tickCount;
		this.type = type;
	}

	public UpdateBTAAbilityPacket(FriendlyByteBuf buf)
	{
		this.entityId = buf.readInt();
		this.ability = BTAAbility.read(buf);
		this.type = PacketType.values()[buf.readInt()];
		this.tickCount = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(this.entityId);
		this.ability.write(buf);
		buf.writeInt(this.type.ordinal());
		buf.writeInt(this.tickCount);
	}
	
	public static class Handler 
	{
		public static boolean onMessage(UpdateBTAAbilityPacket message, Supplier<NetworkEvent.Context> ctx) 
		{
			ctx.get().enqueueWork(() ->
			{
				Entity entity = BTAClientUtil.MC.level.getEntity(message.entityId);
				if(entity instanceof Player player)
				{
					entity.getCapability(BTACapabilities.BTA_ABILITY).ifPresent(cap -> 
					{
						BTAAbility ability = message.ability;
						int tickCount = message.tickCount;
						switch(message.type)
						{
						case ADD:
							cap.addAbility(ability);
							break;
						case REMOVE:
							cap.removeAbility(ability);
							break;
						case TICK:
							cap.setTickCount(ability, tickCount);
							break;
						default:
							break;
						}
					});
				}
			});

			ctx.get().setPacketHandled(true);
			return true;
		}
	}
}
