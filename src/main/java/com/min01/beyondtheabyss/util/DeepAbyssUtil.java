package com.min01.beyondtheabyss.util;

import java.util.List;

import com.min01.beyondtheabyss.capabilities.BTAAbilityImpl.BTAAbilities;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.entity.submarine.EntitySubmarine;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart;
import com.min01.beyondtheabyss.entity.submarine.SubmarinePart.SubmarinePartType;
import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;

public class DeepAbyssUtil 
{
	public static void handleSubmarineCollision(Entity entity)
	{
		if(!(entity instanceof EntitySubmarine) && !(entity instanceof SubmarinePart))
		{
			List<EntitySubmarine> list = entity.level.getEntitiesOfClass(EntitySubmarine.class, entity.getBoundingBox().inflate(0.15F), EntitySelector.NO_SPECTATORS);
			List<SubmarinePart> hatches = entity.level.getEntitiesOfClass(SubmarinePart.class, entity.getBoundingBox(), EntitySelector.NO_SPECTATORS);
			hatches.removeIf(t -> t.type != SubmarinePartType.HATCH);

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
			List<SubmarinePart> list = entity.level.getEntitiesOfClass(SubmarinePart.class, entity.getBoundingBox().inflate(0.25F));
			list.removeIf(t -> t.type != SubmarinePartType.DETECTOR);
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
	
	public static boolean isAbyssalDash(LivingEntity entity)
	{
		boolean isPlayer = entity instanceof Player player ? 
				!player.getCooldowns().isOnCooldown(entity.getItemBySlot(EquipmentSlot.HEAD).getItem())
				&& !player.getCooldowns().isOnCooldown(entity.getItemBySlot(EquipmentSlot.CHEST).getItem())
				&& !player.getCooldowns().isOnCooldown(entity.getItemBySlot(EquipmentSlot.LEGS).getItem())
				&& !player.getCooldowns().isOnCooldown(entity.getItemBySlot(EquipmentSlot.FEET).getItem()) : true;
		boolean flag = entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.GHIDRUTH_DIVING_HELMET.get() 
				&& entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == BTAItems.GHIDRUTH_DIVING_SUIT.get()
				&& entity.getItemBySlot(EquipmentSlot.LEGS).getItem() == BTAItems.GHIDRUTH_DIVING_LEGGINGS.get()
				&& entity.getItemBySlot(EquipmentSlot.FEET).getItem() == BTAItems.GHIDRUTH_DIVING_BOOTS.get() 
				&& entity.isEyeInFluidType(Fluids.WATER.getFluidType())
				&& isPlayer;
		
		return flag;
	}
	
	public static void startAbyssalDash(LivingEntity entity)
	{
		float f7 = entity.getYRot();
        float f = entity.getXRot();
        float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
        float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
        float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
        float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
        float f5 = 3.0F * ((1.0F + 2) / 4.0F);
        f1 *= f5 / f4;
        f2 *= f5 / f4;
        f3 *= f5 / f4;
        entity.push((double)f1, (double)f2, (double)f3);
        if(entity instanceof ServerPlayer player)
        {
        	player.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
        if(entity instanceof Player player)
        {
            int cooldown = 100;
            player.getCooldowns().addCooldown(entity.getItemBySlot(EquipmentSlot.HEAD).getItem(), cooldown);
            player.getCooldowns().addCooldown(entity.getItemBySlot(EquipmentSlot.CHEST).getItem(), cooldown);
            player.getCooldowns().addCooldown(entity.getItemBySlot(EquipmentSlot.LEGS).getItem(), cooldown);
            player.getCooldowns().addCooldown(entity.getItemBySlot(EquipmentSlot.FEET).getItem(), cooldown);
        }
        
		entity.getCapability(BTACapabilities.BTA_ABILITY).ifPresent((cap) -> 
		{
			cap.addAbility(BTAAbilities.ABYSSAL_DASH);
		});
	}
}
