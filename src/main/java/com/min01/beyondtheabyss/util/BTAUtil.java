package com.min01.beyondtheabyss.util;

import java.util.List;
import java.util.UUID;

import com.min01.beyondtheabyss.capabilities.BTAAbilitiesCapabilityHandler;
import com.min01.beyondtheabyss.capabilities.BTAAbilitiesCapabilityHandler.BTAAbilities;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.capabilities.IBTAAbilitiesCapability;
import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart.SubmarinePartType;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class BTAUtil 
{
	public static boolean isInsideSubmarine(Entity entity)
	{
		if(!(entity instanceof EntitySubmarine) && !(entity instanceof SubmarinePart))
		{
			List<SubmarinePart> list = entity.level.getEntitiesOfClass(SubmarinePart.class, entity.getBoundingBox().inflate(0.25F));
			list.removeIf(t -> t.type != SubmarinePartType.DETECTOR);
			return !list.isEmpty();
		}
		return false;
	}
	
	public static boolean isModLoaded(String modid)
	{
		return ModList.get().isLoaded(modid);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getEntityByUUID(Level level, UUID uuid)
	{
		if(level instanceof ServerLevel serverLevel)
		{
			return (T) serverLevel.getEntity(uuid);
		}
		else if(level instanceof ClientLevel clientLevel)
		{
			TransientEntitySectionManager<Entity> entityStorage = ObfuscationReflectionHelper.getPrivateValue(ClientLevel.class, clientLevel, "f_171631_");
			return (T) entityStorage.getEntityGetter().get(uuid);
		}
		return null;
	}
	
	public static void fishFlopping(LivingEntity entity)
	{
		fishFlopping(entity, SoundEvents.COD_FLOP, 1.0F, 0.5F);
	}
	
	public static void fishFlopping(LivingEntity entity, SoundEvent flopSound, float volume, float yMotion)
	{
        if (!entity.isInWater() && entity.isOnGround() && entity.verticalCollision) 
        {
        	entity.setDeltaMovement(entity.getDeltaMovement().add((double)((entity.getRandom().nextFloat() * 2.0F - 1.0F) * 0.05F), yMotion, (double)((entity.getRandom().nextFloat() * 2.0F - 1.0F) * 0.05F)));
        	entity.setOnGround(false);
        	entity.hasImpulse = true;
        	entity.playSound(flopSound, volume, entity.getVoicePitch());
        }
	}
	
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
	
	public static boolean hasAbility(LivingEntity entity, BTAAbilities ability)
	{
		IBTAAbilitiesCapability handler = entity.getCapability(BTACapabilities.BTA_ABILITY).orElse(new BTAAbilitiesCapabilityHandler());
		return handler.getAbilities().containsKey(ability);
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
	
	public static Vec3 fromToVector(Vec3 from, Vec3 to, float scale)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion.scale(scale);
	}
	
	public static Vec3 fromToVector(Vec3 from, Vec3 to)
	{
		Vec3 motion = to.subtract(from).normalize();
		return motion;
	}
	
	public static Vec3 fromToPos(Vec3 from, Vec3 to)
	{
		Vec3 pos = to.subtract(from);
		return pos;
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
    
    public static double getMeleeAttackRangeSqr(Entity owner, LivingEntity target, float multiplier)
    {
    	return (double)(owner.getBbWidth() * multiplier * owner.getBbWidth() * multiplier + target.getBbWidth());
    }

    public static boolean isWithinMeleeAttackRange(Entity owner, LivingEntity target, float multiplier)
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
