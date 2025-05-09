package com.min01.beyondtheabyss.mixin.lights;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.lights.DynamicLights;
import com.min01.beyondtheabyss.lights.IDynamicLight;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer<T extends Entity>
{
	@Inject(method = "getBlockLightLevel", at = @At("RETURN"), cancellable = true)
	private void getBlockLightLevel(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) 
	{
		int vanilla = cir.getReturnValueI();
		int entityLuminance = ((IDynamicLight) entity).getLuminance();
		int posLuminance = (int) DynamicLights.get().getDynamicLightLevel(pos);

		cir.setReturnValue(Math.max(Math.max(vanilla, entityLuminance), posLuminance));
	}
}
