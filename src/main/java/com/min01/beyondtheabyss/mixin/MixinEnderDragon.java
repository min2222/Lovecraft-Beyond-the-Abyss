package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.event.EventHandlerForge;
import com.min01.beyondtheabyss.misc.BTAChatTracker;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;

@Mixin(value = EnderDragon.class, priority = -10000)
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
		this.sendChat();
	}
	
	private void sendChat()
	{
		EnderDragon dragon = EnderDragon.class.cast(this);
		if(dragon.getDragonFight() != null && !dragon.getDragonFight().hasPreviouslyKilledDragon())
		{
			EventHandlerForge.CHAT_MAP.put(dragon.level.dimension(), new BTAChatTracker(dragon));
		}
	}
}
