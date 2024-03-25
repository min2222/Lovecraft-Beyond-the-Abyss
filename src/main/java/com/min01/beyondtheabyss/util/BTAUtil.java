package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.capabilities.BTAAbilitiesCapabilityHandler;
import com.min01.beyondtheabyss.capabilities.BTAAbilitiesCapabilityHandler.BTAAbilities;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IBTAAbilitiesCapability;
import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;

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
	public static float rotlerp(float p_24992_, float p_24993_, float p_24994_)
	{
		float f = Mth.wrapDegrees(p_24993_ - p_24992_);
		if (f > p_24994_) 
		{
			f = p_24994_;
		}

		if (f < -p_24994_) 
		{
			f = -p_24994_;
		}

		float f1 = p_24992_ + f;
		if (f1 < 0.0F)
		{
			f1 += 360.0F;
		}
		else if (f1 > 360.0F)
		{
			f1 -= 360.0F;
		}

		return f1;
	}
	   
	public static int getAbilityTickcount(BTAAbilities ability, LivingEntity entity)
	{
		IBTAAbilitiesCapability handler = entity.getCapability(BTACapabilities.BTA_ABILITY).orElse(new BTAAbilitiesCapabilityHandler());
		return handler.getTickcount(ability);
	}
	
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
	
	public static Vec3 fromToVector(Vec3 from, Vec3 to)
	{
		Vec3 motion = new Vec3(to.x - from.x, to.y - from.y, to.z - from.z).normalize();
		return motion;
	}
	
	public static Vec3 fromToPos(Vec3 from, Vec3 to)
	{
		Vec3 motion = new Vec3(to.x - from.x, to.y - from.y, to.z - from.z);
		return motion;
	}
	
    public static Entity teleportEntityToDim(Entity entity, ServerLevel endpointWorld, BlockPos endpoint)
    {
        if (!entity.getLevel().dimension().location().getPath().equals(endpointWorld.dimension().location().getPath()))
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
		return entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
	}

	public static Vec3 getLookPos(float xRot, float yRot, float yPos, double distance)
	{
		float f = -Mth.sin(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
		float f1 = -Mth.sin((xRot + yPos) * ((float)Math.PI / 180F));
		float f2 = Mth.cos(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
		return new Vec3(f, f1, f2).scale(distance);
	}
}
