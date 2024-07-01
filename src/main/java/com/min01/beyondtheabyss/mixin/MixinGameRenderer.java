package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

@Mixin(GameRenderer.class)
public class MixinGameRenderer
{
	@Inject(at = @At(value = "TAIL"), method = "getNightVisionScale", cancellable = true)
	private static void getNightVisionScale(LivingEntity p_109109_, float p_109110_, CallbackInfoReturnable<Float> cir)
	{
		if(p_109109_.level.dimension() == BTAWorlds.DEEP_ABYSS)
		{
        	if(p_109109_.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.DIVING_HELMET.get())
        	{
    			cir.setReturnValue(0.1F);
        	}
        	else if(p_109109_.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.ADVANCED_DIVING_HELMET.get())
        	{
    			cir.setReturnValue(0.3F);
        	}
        	else if(p_109109_.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.GHIDRUTH_DIVING_HELMET.get())
        	{
    			cir.setReturnValue(0.5F);
        	}
        	
        	if(p_109109_.getItemBySlot(EquipmentSlot.HEAD).isEmpty() || !p_109109_.getItemBySlot(EquipmentSlot.HEAD).is(BTATags.BTAItems.DIVING_SET))
        	{
    			cir.setReturnValue(0.05F);
        	}
        	
        	if(DeepAbyssUtil.isInsideSubmarine(p_109109_))
        	{
    			cir.setReturnValue(1.5F);
        	}
		}
	}
}
