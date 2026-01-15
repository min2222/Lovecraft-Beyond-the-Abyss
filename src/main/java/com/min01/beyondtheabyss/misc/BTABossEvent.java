package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateBossBarPacket;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.registries.ForgeRegistries;

public class BTABossEvent extends ServerBossEvent
{
	private final Entity entity;
	
	public BTABossEvent(Component component, Entity entity)
	{
		super(component, BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS);
		this.entity = entity;
	}
	
	@Override
    public void addPlayer(ServerPlayer serverPlayer) 
    {
    	BTANetwork.sendNonLocal(new UpdateBossBarPacket(this.getId(), ForgeRegistries.ENTITY_TYPES.getKey(this.entity.getType()), false), serverPlayer);
        super.addPlayer(serverPlayer);
    }

    @Override
    public void removePlayer(ServerPlayer serverPlayer) 
    {
    	BTANetwork.sendNonLocal(new UpdateBossBarPacket(this.getId(), ForgeRegistries.ENTITY_TYPES.getKey(this.entity.getType()), true), serverPlayer);
        super.removePlayer(serverPlayer);
    }
}