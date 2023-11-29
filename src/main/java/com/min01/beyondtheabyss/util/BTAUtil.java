package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.entity.parts.BasicBTAEntityPart;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class BTAUtil 
{
	public static Vec3 moveToEntity(Vec3 from, Vec3 to, Entity mover, Entity target, float multiplier)
	{
		double d0 = from.x - to.x;
		double d1 = from.y - to.y;
		double d2 = from.z - to.z;
		double d3 = -d0 / (multiplier * mover.distanceTo(target));
		double d4 = -d1 / (multiplier * mover.distanceTo(target));
		double d5 = -d2 / (multiplier * mover.distanceTo(target));
		return new Vec3(d3, d4, d5);
	}
	
    public static Entity teleportEntity(Entity entity, ServerLevel endpointWorld, BlockPos endpoint)
    {
        if (entity.getLevel().dimension().location().getPath().equals("deep_abyss"))
        {
        	
        } 
        else
        {
            if (entity instanceof Player && ((Player) entity).getSleepingPos().isPresent()) 
            {
                BlockPos bedPos = ((Player) entity).getSleepingPos().get();
                endpoint = bedPos;
                entity.moveTo(bedPos.getX() + 0.5D, bedPos.getY() + 1.5D, bedPos.getZ() + 0.5D, 0.0F, 0.0F);
            } 
            else 
            {
                BlockPos height = entity.level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, new BlockPos(entity.position()));
                endpoint = height;
                entity.moveTo(height.getX() + 0.5D, height.getY() + 0.5D, height.getZ() + 0.5D, entity.getYRot(), 0.0F);
            }
        }
        
        if (entity instanceof ServerPlayer) 
        {
        	ServerPlayer player = (ServerPlayer) entity;
            player.teleportTo(endpointWorld, endpoint.getX() + 0.5D, endpoint.getY() + 0.5D, endpoint.getZ() + 0.5D, entity.getYRot(), entity.getXRot());
            return player;
        }

        entity.unRide();
        entity.changeDimension(endpointWorld);
        Entity teleportedEntity = entity.getType().create(endpointWorld);
        if (teleportedEntity == null) 
        {
            return entity;
        }
        teleportedEntity.restoreFrom(entity);
        teleportedEntity.moveTo(endpoint.getX() + 0.5D, endpoint.getY() + 0.5D, endpoint.getZ() + 0.5D, entity.getYRot(), entity.getXRot());
        teleportedEntity.setYHeadRot(entity.getYRot());
        endpointWorld.addDuringTeleport(teleportedEntity);
        return teleportedEntity;
    }
	
    public static double getMeleeAttackRangeSqrOfPart(BasicBTAEntityPart owner, LivingEntity target, float multiplier)
    {
    	return (double)(owner.getBbWidth() * multiplier * owner.getBbWidth() * multiplier + target.getBbWidth());
    }

    public static boolean isWithinMeleeAttackRangeOfPart(BasicBTAEntityPart owner, LivingEntity target, float multiplier)
    {
    	double d0 = owner.distanceToSqr(target.getX(), target.getY(), target.getZ());
    	return d0 <= getMeleeAttackRangeSqrOfPart(owner, target, multiplier);
    }
    
    public static double getMeleeAttackRangeSqr(LivingEntity owner, LivingEntity target, float multiplier)
    {
    	return (double)(owner.getBbWidth() * multiplier * owner.getBbWidth() * multiplier + target.getBbWidth());
    }

    public static boolean isWithinMeleeAttackRange(LivingEntity owner, LivingEntity target, float multiplier)
    {
    	double d0 = owner.distanceToSqr(target.getX(), target.getY(), target.getZ());
    	return d0 <= getMeleeAttackRangeSqr(owner, target, multiplier);
    }
    
	public static boolean isMoving(Entity entity) 
	{
		double d0 = entity.getX() - entity.xo;
		double d1 = entity.getZ() - entity.zo;
		return d0 * d0 + d1 * d1 > (double)2.5000003E-7F;
	}
	
	public static Vec3 caculateBackwardVector(Entity entity, float yRot, Vec3 multiplier)
	{
    	float f14 = yRot * ((float)Math.PI / 180F);
        float x = Mth.sin(f14);
        float y = Mth.sin(f14);
        float z = Mth.cos(f14);
        return new Vec3(entity.getX() + (x * multiplier.x), entity.getY() + (-y * multiplier.y), entity.getZ() + (z * -multiplier.z));
	}
	
	public static Vec3 caculateForwardVector(Entity entity, float yRot, Vec3 multiplier)
	{
    	float f14 = yRot * ((float)Math.PI / 180F);
        float x = Mth.sin(f14);
        float y = Mth.sin(f14);
        float z = Mth.cos(f14);
        return new Vec3(entity.getX() + (x * -multiplier.x), entity.getY() + (-y * multiplier.y), entity.getZ() + (z * multiplier.z));
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
