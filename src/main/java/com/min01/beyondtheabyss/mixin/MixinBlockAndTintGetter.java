package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.min01.beyondtheabyss.misc.BTADynamicLights;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;

@Mixin(BlockAndTintGetter.class)
public interface MixinBlockAndTintGetter extends BlockAndTintGetter
{
	@Override
	default int getBrightness(LightLayer p_45518_, BlockPos p_45519_) 
	{
		int vanilla = this.getLightEngine().getLayerListener(p_45518_).getLightValue(p_45519_);
		int posLuminance = (int) BTADynamicLights.get().getDynamicLightLevel(p_45519_);
		return Math.max(vanilla, posLuminance);
	}
}
