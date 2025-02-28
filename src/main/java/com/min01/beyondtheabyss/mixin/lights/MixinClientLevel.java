package com.min01.beyondtheabyss.mixin.lights;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.lights.IDynamicLight;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.LevelEntityGetter;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel
{
	@Shadow
	protected abstract LevelEntityGetter<Entity> getEntities();

	@Inject(method = "removeEntity(ILnet/minecraft/world/entity/Entity$RemovalReason;)V", at = @At("HEAD"))
	private void removeEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo ci) 
	{
		var entity = this.getEntities().get(entityId);
		if(entity != null) 
		{
			var dls = (IDynamicLight) entity;
			dls.setDynamicLightEnabled(false);
		}
	}
}
