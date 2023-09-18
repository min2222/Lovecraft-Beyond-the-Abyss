package com.min01.beyondtheabyss.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class AbyssUtil 
{
	public static boolean isMoving(Entity entity) 
	{
		double d0 = entity.getX() - entity.xo;
		double d1 = entity.getZ() - entity.zo;
		return d0 * d0 + d1 * d1 > (double)2.5000003E-7F;
	}
	
	public static Vec3 caculateForwardVector(Entity entity, Vec3 multiplier)
	{
    	float f14 = entity.getYRot() * ((float)Math.PI / 180F);
        float x = Mth.sin(f14);
        float z = Mth.cos(f14);
        return new Vec3(entity.getX() + (x * -multiplier.x), entity.getY() + multiplier.y, entity.getZ() + (z * multiplier.z));
	}
	
	public static Vec3 caculateBackwardVector(Entity entity, Vec3 multiplier)
	{
    	float f14 = entity.getYRot() * ((float)Math.PI / 180F);
        float x = Mth.sin(f14);
        float z = Mth.cos(f14);
        return new Vec3(entity.getX() + (x * multiplier.x), entity.getY() + multiplier.y, entity.getZ() + (z * -multiplier.z));
	}
	
	public static Vec3 caculateSideVector(Entity entity, Vec3 multiplier)
	{
    	float f14 = entity.getYRot() * ((float)Math.PI / 180F);
        float x = Mth.sin(f14);
        float z = Mth.cos(f14);
        return new Vec3(entity.getX() + (x * multiplier.x), entity.getY() + multiplier.y, entity.getZ() + (z * multiplier.z));
	}
}
