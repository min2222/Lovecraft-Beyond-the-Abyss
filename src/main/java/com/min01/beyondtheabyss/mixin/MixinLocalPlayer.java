package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer extends AbstractClientPlayer 
{
	public MixinLocalPlayer(ClientLevel p_250460_, GameProfile p_249912_) 
	{
		super(p_250460_, p_249912_);
	}

	@Inject(at = @At("HEAD"), method = "updateIsUnderwater", cancellable = true)
	private void updateIsUnderwater(CallbackInfoReturnable<Boolean> cir)
	{
		if(BTAUtil.canSwimInAir(LocalPlayer.class.cast(this)) || DeepAbyssUtil.isInsideSubmarine(LocalPlayer.class.cast(this)))
		{
			cir.cancel();
			super.updateIsUnderwater();
		}
	}
	
	@Inject(at = @At("RETURN"), method = "getWaterVision", cancellable = true)
	private void getWaterVision(CallbackInfoReturnable<Float> cir)
	{
		LocalPlayer player = LocalPlayer.class.cast(this);
		ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);
		if(stack.is(BTAItems.FELMETAL_DIVING_HELMET.get()))
		{
			cir.setReturnValue(cir.getReturnValue() + 1.0F);
		}
		if(DeepAbyssUtil.isInsideSubmarine(player))
		{
			cir.setReturnValue(cir.getReturnValue() + 2.5F);
		}
	}
}
