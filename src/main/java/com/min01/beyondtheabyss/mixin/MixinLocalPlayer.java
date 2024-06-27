package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.client.player.LocalPlayer;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer 
{
	@Inject(at = @At("HEAD"), method = "getWaterVision", cancellable = true)
	private void getWaterVision(CallbackInfoReturnable<Float> cir)
	{
		if(DeepAbyssUtil.isInsideSubmarine(LocalPlayer.class.cast(this)))
		{
			cir.setReturnValue(1.5F);
		}
	}
}
