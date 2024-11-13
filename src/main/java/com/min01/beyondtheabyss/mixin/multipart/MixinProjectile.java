package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.InteractMultiPartPacket;
import com.min01.beyondtheabyss.network.InteractMultiPartPacket.InteractionType;

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
	public MixinProjectile(EntityType<?> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
	}
	
    @Inject(method = "onHit", at = @At("HEAD"))
    private void onHit(HitResult hitResult, CallbackInfo ci) 
    {
        HitResult.Type type = hitResult.getType();

        if(type == HitResult.Type.ENTITY && hitResult instanceof EntityHitResult entitHit) 
        {
            Entity entity = entitHit.getEntity();
            if(entity instanceof IMultipart multipart)
            {
                String part = multipart.getBounds().raycast(this.position(), this.position().add(this.getDeltaMovement()));
                if(part != null)
                {
                    BTANetwork.sendToServer(new InteractMultiPartPacket(entity.getId(), this.getId(), part, InteractionHand.MAIN_HAND, false, InteractionType.PROJECTILE));
                	this.onHitEntity(new EntityHitResult(entity));
                }
            }
        }
    }

    @Shadow
	private void onHitEntity(EntityHitResult entityHitResult) 
	{
		
	}
}
