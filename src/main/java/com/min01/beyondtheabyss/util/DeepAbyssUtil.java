package com.min01.beyondtheabyss.util;

import java.util.List;

import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart.SubmarinePartType;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;

public class DeepAbyssUtil 
{
	public static void handleSubmarineCollision(Entity entity)
	{
		if(!(entity instanceof EntitySubmarine) && !(entity instanceof SubmarinePart))
		{
			List<EntitySubmarine> list = entity.level.getEntitiesOfClass(EntitySubmarine.class, entity.getBoundingBox().inflate(0.15F), EntitySelector.NO_SPECTATORS);
			List<SubmarinePart> hatches = entity.level.getEntitiesOfClass(SubmarinePart.class, entity.getBoundingBox(), EntitySelector.NO_SPECTATORS);
			hatches.removeIf(t -> t.getPartType() != SubmarinePartType.HATCH);

			if(!list.isEmpty())
			{
				//FIXME unable to enter submarine via hatch;
	            if(entity.getDeltaMovement().y < 0 && hatches.isEmpty()) 
	            {
	            	entity.setDeltaMovement(entity.getDeltaMovement().x, 0, entity.getDeltaMovement().z);
	            	entity.setPos(entity.position().add(0, -entity.getDeltaMovement().y, 0));
	            	entity.hasImpulse = true;
	            	if(entity instanceof ServerPlayer serverPlayer)
	            	{
	            		serverPlayer.connection.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
	            	}
	            }
	            entity.setSwimming(false);
	            entity.setOnGround(true);
	            entity.resetFallDistance();
			}
		}
	}
	
	public static boolean isInsideSubmarine(Entity entity)
	{
		if(!(entity instanceof EntitySubmarine) && !(entity instanceof SubmarinePart))
		{
			List<SubmarinePart> list = entity.level.getEntitiesOfClass(SubmarinePart.class, entity.getBoundingBox().inflate(0.25F), EntitySelector.NO_SPECTATORS);
			list.removeIf(t -> t.getPartType() != SubmarinePartType.DETECTOR);
			return !list.isEmpty();
		}
		return false;
	}
	
	public static void fishFlopping(LivingEntity entity)
	{
		fishFlopping(entity, SoundEvents.COD_FLOP, 1.0F, 0.5F);
	}
	
	public static void fishFlopping(LivingEntity entity, SoundEvent flopSound, float volume, float yMotion)
	{
        if(!entity.isInWater() && entity.isOnGround() && entity.verticalCollision) 
        {
        	entity.setDeltaMovement(entity.getDeltaMovement().add((double)((entity.getRandom().nextFloat() * 2.0F - 1.0F) * 0.05F), yMotion, (double)((entity.getRandom().nextFloat() * 2.0F - 1.0F) * 0.05F)));
        	entity.setOnGround(false);
        	entity.hasImpulse = true;
        	entity.playSound(flopSound, volume, entity.getVoicePitch());
        }
	}
}
