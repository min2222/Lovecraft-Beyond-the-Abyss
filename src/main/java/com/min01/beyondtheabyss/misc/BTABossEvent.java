package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateBossBarPacket;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;

public class BTABossEvent extends ServerBossEvent
{
	private final BTABossBarType barType;
	private final Entity entity;
	
	public BTABossEvent(Component component, BTABossBarType barType, Entity entity)
	{
		super(component, BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS);
		this.barType = barType;
		this.entity = entity;
	}
	
	public BTABossBarType getBarType()
	{
		return this.barType;
	}
	
	@Override
    public void addPlayer(ServerPlayer serverPlayer) 
    {
    	BTANetwork.sendNonLocal(new UpdateBossBarPacket(this.getId(), this.entity.getUUID(), this.barType), serverPlayer);
        super.addPlayer(serverPlayer);
    }

    @Override
    public void removePlayer(ServerPlayer serverPlayer) 
    {
    	BTANetwork.sendNonLocal(new UpdateBossBarPacket(this.getId(), this.entity.getUUID(), BTABossBarType.NONE), serverPlayer);
        super.removePlayer(serverPlayer);
    }
}