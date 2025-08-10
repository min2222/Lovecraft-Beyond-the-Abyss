package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.world.BTASavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;

@Mixin(EnderDragon.class)
public class MixinEnderDragon
{
	@Inject(at = @At(value = "HEAD"), method = "kill")
	private void kill(CallbackInfo ci)
	{
		this.sendChat();
	}

	@Inject(at = @At(value = "HEAD"), method = "tickDeath")
	private void tickDeath(CallbackInfo ci)
	{
		this.sendChat2();
	}
	
	private void sendChat2()
	{
		EnderDragon dragon = EnderDragon.class.cast(this);
		MinecraftServer server = dragon.level.getServer();
		if(server == null)
			return;
		BTASavedData data = BTASavedData.get(server.getLevel(Level.OVERWORLD));
		if(dragon.getDragonFight() != null && !dragon.getDragonFight().hasPreviouslyKilledDragon())
		{
			if(!data.getHutPos().equals(BlockPos.ZERO))
			{
				if(dragon.getLastAttacker() instanceof ServerPlayer player)
				{
					BlockPos pos = data.getHutPos();
					if(dragon.dragonDeathTime == 0)
					{
						Component component = Component.translatable("message.beyondtheabyss.solomoncall1");
						player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), Component.translatable("message.beyondtheabyss.solomon")));
					}
					if(dragon.dragonDeathTime == 40)
					{
						Component component = Component.translatable("message.beyondtheabyss.solomoncall2");
						player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), Component.translatable("message.beyondtheabyss.solomon")));
					}
					if(dragon.dragonDeathTime == 80)
					{
						Component component = Component.translatable("message.beyondtheabyss.solomoncall3");
						player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), Component.translatable("message.beyondtheabyss.solomon")));
					}
					//note : lasthurtbymob become null after 100 ticks;
					if(dragon.dragonDeathTime == 90)
					{
						Component component = Component.translatable("message.beyondtheabyss.solomoncall4", "x: " + pos.getX() + ", z: " + pos.getZ());
						player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), Component.translatable("message.beyondtheabyss.solomon")));
					}
				}
			}
		}
	}
	
	private void sendChat()
	{
		EnderDragon dragon = EnderDragon.class.cast(this);
		MinecraftServer server = dragon.level.getServer();
		if(dragon.dragonDeathTime > 0 || server == null)
			return;
		BTASavedData data = BTASavedData.get(server.getLevel(Level.OVERWORLD));
		if(dragon.getDragonFight() != null && !dragon.getDragonFight().hasPreviouslyKilledDragon())
		{
			if(!data.getHutPos().equals(BlockPos.ZERO))
			{
				for(ServerPlayer player : server.getPlayerList().getPlayers())
				{
					BlockPos pos = data.getHutPos();
					Component component = Component.translatable("message.beyondtheabyss.solomoncall", pos.getX() + ", " + pos.getZ());
					player.sendChatMessage(new OutgoingChatMessage.Disguised(component), false, ChatType.bind(ChatType.CHAT, player.level.registryAccess(), Component.translatable("message.beyondtheabyss.solomon")));
				}
			}
		}
	}
}
