package com.min01.beyondtheabyss.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.event.ClientEventHandlerForge;
import com.min01.beyondtheabyss.misc.BTABossBarType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class UpdateBossBarPacket 
{
    private final UUID bossBar;
    private final UUID entity;
    private final BTABossBarType barType;
	
	public UpdateBossBarPacket(UUID bossBar, UUID entity, BTABossBarType barType) 
	{
		this.bossBar = bossBar;
		this.entity = entity;
		this.barType = barType;
	}

	public static UpdateBossBarPacket read(FriendlyByteBuf buf)
	{
		return new UpdateBossBarPacket(buf.readUUID(), buf.readUUID(), BTABossBarType.values()[buf.readInt()]);
	}

	public void write(FriendlyByteBuf buf)
	{
		buf.writeUUID(this.bossBar);
		buf.writeUUID(this.entity);
		buf.writeInt(this.barType.ordinal());
	}
	
	public static boolean handle(UpdateBossBarPacket message, Supplier<NetworkEvent.Context> ctx) 
	{
		ctx.get().enqueueWork(() ->
		{
	        if(message.barType == BTABossBarType.NONE)
	        {
	            ClientEventHandlerForge.BOSS_BAR_MAP.remove(message.bossBar);
	            ClientEventHandlerForge.BOSS_MAP.remove(message.bossBar);
	        }
	        else
	        {
	        	BTAUtil.getClientLevel(level -> 
	        	{
		        	ClientEventHandlerForge.BOSS_BAR_MAP.put(message.bossBar, message.barType);
		        	Entity entity = BTAUtil.getEntityByUUID(level, message.entity);
		        	if(entity != null)
		        	{
			        	ClientEventHandlerForge.BOSS_MAP.put(message.bossBar, entity);
		        	}
	        	});
	        }
		});
		ctx.get().setPacketHandled(true);
		return true;
	}
}