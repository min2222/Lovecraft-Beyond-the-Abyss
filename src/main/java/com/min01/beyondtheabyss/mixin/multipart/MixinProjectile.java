package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.MultiPartInteractionPacket;
import com.min01.beyondtheabyss.network.MultiPartInteractionPacket.InteractionType;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

@Mixin(Projectile.class)
public abstract class MixinProjectile extends Entity
{
    public MixinProjectile(EntityType<?> type, Level level)
    {
        super(type, level);
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void onHit(HitResult hitResult, CallbackInfo ci)
    {
        if(hitResult.getType() == HitResult.Type.ENTITY && hitResult instanceof EntityHitResult entityHit) 
        {
            Entity entity = entityHit.getEntity();
            if(entity instanceof IMultipart multipart)
            {
                String part = multipart.getBounds().raycast(this.position(), this.position().add(this.getDeltaMovement()));
                if(part != null)
                {
                    multipart.setNextDamagedPart(part);
                    BTANetwork.sendToServer(new MultiPartInteractionPacket(entity.getId(), this.getId(), part, InteractionHand.MAIN_HAND, false, InteractionType.PROJECTILE));
                }
            }
        }
    }
}
