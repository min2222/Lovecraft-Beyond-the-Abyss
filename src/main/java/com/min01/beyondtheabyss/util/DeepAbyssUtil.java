package com.min01.beyondtheabyss.util;

import java.util.List;

import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.solomonlib.util.SolomonUtil;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DeepAbyssUtil 
{
	public static boolean isInsideSubmarine(Entity entity)
	{
		List<EntitySubmarine> list = entity.level.getEntitiesOfClass(EntitySubmarine.class, entity.getBoundingBox().inflate(1.0F), t -> t != entity);
		for(EntitySubmarine sub : list)
		{
			String part = SolomonUtil.getIntersectingMultiPart(sub.getBounds(), entity);
			if(part != null && !sub.hatchOpened())
			{
				return part.equals("inner");
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
