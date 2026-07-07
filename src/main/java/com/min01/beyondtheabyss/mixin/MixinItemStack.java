package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.item.animation.ItemAnimations;

import net.minecraft.world.item.ItemStack;

@Mixin(ItemStack.class)
public class MixinItemStack 
{
	@Inject(method = "copyWithCount", at = @At(value = "RETURN"))
    public void removeInstanceIdOnCopy(int pCount, CallbackInfoReturnable<ItemStack> copy) 
	{
    	ItemAnimations.clearId(copy.getReturnValue());
    }
}