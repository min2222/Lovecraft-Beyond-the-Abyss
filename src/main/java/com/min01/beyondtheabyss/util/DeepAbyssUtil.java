package com.min01.beyondtheabyss.util;

import java.util.List;

import com.min01.beyondtheabyss.entity.deepabyss.SubmarineEntity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class DeepAbyssUtil 
{
	public static boolean isInsideSubmarine(Entity entity)
	{
		AABB aabb = entity.getBoundingBox();
		List<SubmarineEntity> list = entity.level.getEntitiesOfClass(SubmarineEntity.class, aabb.inflate(1.0F), t -> t != entity);
		for(SubmarineEntity submarine : list)
		{
			if(submarine.partBuilder.clip(entity.position(), entity.position().subtract(0, aabb.getSize() + 1.0, 0), t -> t.contains("bottom")).isPresent())
			{
				return true;
			}
		}
		return false;
	}
	
	public static void fishFlopping(LivingEntity entity)
	{
		fishFlopping(entity, SoundEvents.COD_FLOP, 1.0F, 0.5F);
	}
	
	public static void fishFlopping(LivingEntity entity, SoundEvent flopSound, float volume, float yMotion)
	{
        if(!entity.isInWater() && entity.onGround() && entity.verticalCollision) 
        {
        	entity.setDeltaMovement(entity.getDeltaMovement().add((double)((entity.getRandom().nextFloat() * 2.0F - 1.0F) * 0.05F), yMotion, (double)((entity.getRandom().nextFloat() * 2.0F - 1.0F) * 0.05F)));
        	entity.setOnGround(false);
        	entity.hasImpulse = true;
        	entity.playSound(flopSound, volume, entity.getVoicePitch());
        }
	}
}
