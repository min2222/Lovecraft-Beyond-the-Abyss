package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;

@Mixin(value = SwimNodeEvaluator.class, priority = -10000)
public abstract class MixinSwimNodeEvaluator extends NodeEvaluator
{
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"), method = "getBlockPathType(Lnet/minecraft/world/level/BlockGetter;IIILnet/minecraft/world/entity/Mob;)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;")
	private boolean getBlockPathType(FluidState instance, TagKey<Fluid> pTag)
	{
		if(this.mob.hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			return true;
		}
		else
		{
			return instance.is(pTag);
		}
	}
	
	@Inject(at = @At("TAIL"), method = "getBlockPathType(Lnet/minecraft/world/level/BlockGetter;IIILnet/minecraft/world/entity/Mob;)Lnet/minecraft/world/level/pathfinder/BlockPathTypes;", cancellable = true)
	private void getBlockPathType(BlockGetter pLevel, int pX, int pY, int pZ, Mob pMob, CallbackInfoReturnable<BlockPathTypes> cir)
	{
		if(pMob.hasEffect(BTAEffects.AIR_SWIM.get()))
		{
			cir.setReturnValue(BlockPathTypes.WATER);
		}
	}
}
