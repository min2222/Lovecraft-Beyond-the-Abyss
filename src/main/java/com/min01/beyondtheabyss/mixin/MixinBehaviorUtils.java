package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

@Mixin(BehaviorUtils.class)
public class MixinBehaviorUtils 
{
	@Inject(at = @At("HEAD"), method = "getRandomSwimmablePos", cancellable = true)
	private static void getRandomSwimmablePos(PathfinderMob p_147445_, int p_147446_, int p_147447_, CallbackInfoReturnable<Vec3> ci)
	{
		ci.cancel();
		
		Vec3 vec3 = DefaultRandomPos.getPos(p_147445_, p_147446_, p_147447_);
		PathComputationType type = p_147445_.hasEffect(BTAEffects.AIR_SWIM.get()) ? PathComputationType.AIR : PathComputationType.WATER;
		for(int i = 0; vec3 != null && !p_147445_.level.getBlockState(new BlockPos(vec3)).isPathfindable(p_147445_.level, new BlockPos(vec3), type) && i++ < 10; vec3 = DefaultRandomPos.getPos(p_147445_, p_147446_, p_147447_))
		{
			
		}

		ci.setReturnValue(vec3);
	}
}
