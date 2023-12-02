package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mixin(Aquifer.FluidStatus.class)
public class MixinFluidStatus
{
	@Shadow
    @Final int fluidLevel;

	@Shadow
    @Final BlockState fluidType;
    
	@Inject(at = @At("HEAD"), method = "at", cancellable = true)
	private void at(int p_188406_, CallbackInfoReturnable<BlockState> ci)
	{
		for(ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
		{
			Level level = player.level;
			if(level != null && player != null)
			{
				BlockState original = p_188406_ < this.fluidLevel ? this.fluidType : Blocks.AIR.defaultBlockState();
				BlockState water = p_188406_ < this.fluidLevel ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
				BlockState blockstate = level.dimension().location().getPath().equals("deep_abyss") ? water : original;
				ci.setReturnValue(blockstate);
			}
		}
	}
}
