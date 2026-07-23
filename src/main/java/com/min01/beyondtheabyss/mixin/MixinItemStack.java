package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.min01.beyondtheabyss.item.animation.ItemAnimations;

import net.minecraft.world.item.ItemStack;

@Mixin(ItemStack.class)
public class MixinItemStack 
{
	@ModifyReturnValue(method = "copyWithCount", at = @At(value = "RETURN"))
    public ItemStack removeInstanceIdOnCopy(ItemStack original) 
	{
		ItemAnimations.clearId(original);
		return original;
    }
}