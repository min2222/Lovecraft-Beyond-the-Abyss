package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mixin(Aquifer.NoiseBasedAquifer.class)
public class MixinNoiseBasedAquifer 
{
	@Inject(at = @At("HEAD"), method = "computeFluidType", cancellable = true)
	private void computeFluidType(int p_223904_, int p_223905_, int p_223906_, Aquifer.FluidStatus p_223907_, int p_223908_, CallbackInfoReturnable<BlockState> ci)
	{
		for(ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
		{
			Level level = player.level;
			if(level != null && player != null)
			{
				if(level.dimension() == BTAWorlds.DEEP_ABYSS)
				{
					ci.setReturnValue(Blocks.WATER.defaultBlockState());
				}
			}
		}
	}
}
