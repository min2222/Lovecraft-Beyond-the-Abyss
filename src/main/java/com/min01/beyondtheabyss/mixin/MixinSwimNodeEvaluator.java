package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;

@Mixin(value = SwimNodeEvaluator.class, priority = -10000)
public class MixinSwimNodeEvaluator
{
	@Inject(at = @At("TAIL"), method = "getBlockPathType(Lnet/minecraft/world/level/BlockGetter;IIILnet/minecraft/world/entity/Mob;)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", cancellable = true)
	private void getBlockPathType(BlockGetter pLevel, int pX, int pY, int pZ, Mob pMob, CallbackInfoReturnable<BlockPathTypes> cir)
	{
		if(pMob != null && pMob.hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			cir.setReturnValue(BlockPathTypes.WATER);
		}
	}
}
