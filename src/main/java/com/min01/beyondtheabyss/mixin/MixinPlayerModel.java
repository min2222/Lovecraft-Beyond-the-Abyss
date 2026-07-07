package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.animation.PlayerAnimations;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@Mixin(PlayerModel.class)
public class MixinPlayerModel<T extends LivingEntity>
{
    @Inject(at = @At("HEAD"), method = "setupAnim", cancellable = true)
    private void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {	
    	PlayerModel<?> model = ((PlayerModel<?>) (Object) this);
    	PlayerAnimations.setupMap(model);
    }
    
    @Inject(at = @At("TAIL"), method = "setupAnim", cancellable = true)
    private void setupAnimTail(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci)
    {
    	PlayerModel<?> model = ((PlayerModel<?>) (Object) this);
    	if(entity instanceof Player player)
    	{
        	PlayerAnimations.animatePlayer(model, player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    	}
    }
}
