package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;

@Mixin(Projectile.class)
public interface ProjectileInvoker 
{
	@Invoker("onHitEntity")
	public void invokeOnHitEntity(EntityHitResult entityHit);
}
