package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.world.BTASavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ChatTicker 
{
	public final LivingEntity entity;
	public int tickCount;
	
	public ChatTicker(LivingEntity entity)
	{
		this.entity = entity;
	}
	
	public void tick()
	{
		this.tickCount++;

		MinecraftServer server = this.entity.level.getServer();
		if(server != null)
		{
			BTASavedData data = BTASavedData.get(server.getLevel(Level.OVERWORLD));
			if(!data.getHutPos().equals(BlockPos.ZERO))
			{
				for(ServerPlayer player : server.getPlayerList().getPlayers())
				{
					BlockPos pos = data.getHutPos();
					Component mysteriousGuy = Component.translatable("message.beyondtheabyss.mysterious_guy");
					if(this.tickCount == 0)
					{
						Component component = Component.translatable("message.beyondtheabyss.mysterious_guycall1");
						player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), mysteriousGuy));
					}
					if(this.tickCount == 40)
					{
						Component component = Component.translatable("message.beyondtheabyss.mysterious_guycall2");
						player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), mysteriousGuy));
					}
					if(this.tickCount == 80)
					{
						Component component = Component.translatable("message.beyondtheabyss.mysterious_guycall3");
						player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), mysteriousGuy));
					}
					if(this.tickCount == 120)
					{
						Component component = Component.translatable("message.beyondtheabyss.mysterious_guycall4", "x: " + pos.getX() + ", z: " + pos.getZ());
						player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), mysteriousGuy));
						data.setDragonKilled(true);
					}
				}
			}
		}
	}
}
