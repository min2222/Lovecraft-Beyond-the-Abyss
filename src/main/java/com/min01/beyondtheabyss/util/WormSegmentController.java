package com.min01.beyondtheabyss.util;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class WormSegmentController
{
	public static void tick(ServerLevel world, double x, double y, double z, LivingEntity living, LivingEntity owner, float distance, float speed)
	{
		float xz = 0.0F;
		float yRot = 0.0F;
		float xRot = 0.0F;
		
		living.lookAt(Anchor.FEET, owner.position());
		living.teleportTo(owner.getX() - living.getLookAngle().x * distance, owner.getY() - living.getLookAngle().y * distance, owner.getZ() - living.getLookAngle().z * distance);
		yRot = living.getYRot();
		xRot = living.getXRot();
		living.setYRot(owner.getYRot());
		living.setXRot(owner.getXRot());
		living.setYBodyRot(owner.getYRot());
		living.setYHeadRot(owner.getYRot());
		living.yRotO = owner.getYRot();
		living.xRotO = owner.getXRot();
		living.yBodyRotO = owner.getYRot();
		living.yHeadRotO = owner.getYRot();
		
		if(180.0F + yRot + 180.0F - living.getYRot() < Math.abs(living.getYRot() - yRot)) 
		{
			xz = (180.0F + yRot + 180.0F - living.getYRot()) * -1.0F * speed;
		}
		else if((180.0F + living.getYRot() + 180.0F) - yRot < Math.abs(living.getYRot() - yRot)) 
		{
			xz = ((180.0F + living.getYRot() + 180.0F) - yRot) * 1.0F * speed;
		}
		else
		{
			xz = (living.getYRot() - yRot) * speed;
		}
		
		living.setYRot(yRot + xz);
		living.setXRot(xRot + (living.getXRot() - xRot) * speed);
		living.setYBodyRot(living.getYRot());
		living.setYHeadRot(living.getYRot());
		living.yRotO = living.getYRot();
		living.xRotO = living.getXRot();
		living.yBodyRotO = living.getYRot();
		living.yHeadRotO = living.getYRot();
		living.teleportTo(owner.getX() - living.getLookAngle().x * distance, owner.getY() - living.getLookAngle().y * distance, owner.getZ() - living.getLookAngle().z * distance);
	}
}