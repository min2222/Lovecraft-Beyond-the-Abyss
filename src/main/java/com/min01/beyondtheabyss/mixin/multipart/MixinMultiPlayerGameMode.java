package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.InteractMultiPartPacket;
import com.min01.beyondtheabyss.network.InteractMultiPartPacket.InteractionType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;

@Mixin(MultiPlayerGameMode.class)
public class MixinMultiPlayerGameMode
{
    @Shadow 
    private GameType localPlayerMode;

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void attack(Player player, Entity target, CallbackInfo ci)
    {
        if(target instanceof IMultipart multipart)
        {
        	this.ensureHasSentCarriedItem();

            Minecraft client = Minecraft.getInstance();
            Vec3 pos = client.cameraEntity.getEyePosition(client.getFrameTime());
            Vec3 dir = client.cameraEntity.getViewVector(client.getFrameTime());
            double reach = client.gameMode.getPickRange();
            String part = multipart.getBounds().raycast(pos, pos.add(dir.scale(reach)));
            if(part != null)
            {
                BTANetwork.sendToServer(new InteractMultiPartPacket(target.getId(), part, InteractionHand.MAIN_HAND, client.cameraEntity.isShiftKeyDown(), InteractionType.ATTACK));
                if(this.localPlayerMode != GameType.SPECTATOR)
                {
                    player.attack(target);
                    player.resetAttackStrengthTicker();
                }
                ci.cancel();
            }
        }
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void interact(Player player, Entity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir)
    {
        if(entity instanceof IMultipart multipart) 
        {
            this.ensureHasSentCarriedItem();

            Minecraft client = Minecraft.getInstance();
            Vec3 pos = client.cameraEntity.getEyePosition(client.getFrameTime());
            Vec3 dir = client.cameraEntity.getViewVector(client.getFrameTime());
            double reach = client.gameMode.getPickRange();
            String part = multipart.getBounds().raycast(pos, pos.add(dir.scale(reach)));
            if(part != null)
            {
                BTANetwork.sendToServer(new InteractMultiPartPacket(entity.getId(), part, hand, client.cameraEntity.isShiftKeyDown(), InteractionType.INTERACT));
                if(this.localPlayerMode != GameType.SPECTATOR)
                {
                    cir.setReturnValue(multipart.interact(player, hand, part));
                }
                cir.setReturnValue(InteractionResult.PASS);	
            }
        }
    }
    
    @Shadow 
    private void ensureHasSentCarriedItem()
    {
    	
    }
}
