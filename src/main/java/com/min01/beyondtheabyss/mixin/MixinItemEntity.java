package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.item.ItemEntity;

@Mixin(ItemEntity.class)
public class MixinItemEntity
{
	@Inject(at = @At("HEAD"), method = "tick", cancellable = true)
	private void tick(CallbackInfo ci)
	{
		if(ItemEntity.class.cast(this).level.dimension().location().getPath().equals("deep_abyss"))
		{
			ItemEntity.class.cast(this).setDeltaMovement(ItemEntity.class.cast(this).getDeltaMovement().subtract(0, 0.01F, 0));
		}
	}
}
