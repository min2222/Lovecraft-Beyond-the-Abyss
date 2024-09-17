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
	@Inject(at = @At("RETURN"), method = "getWaterVision", cancellable = true)
	private void getWaterVision(CallbackInfoReturnable<Float> cir)
	{
		LocalPlayer player = LocalPlayer.class.cast(this);
		if(DeepAbyssUtil.isInsideSubmarine(player))
		{
			cir.setReturnValue(cir.getReturnValue() + 1.5F);
		}
	}
}
