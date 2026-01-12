package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

@AutoRegisterCapability
public interface IPlayerAnimationCapability extends ICapabilitySerializable<CompoundTag>
{
	ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "player_animation");

	void tick(LivingEntity entity);
	
	void setAnimationState(int state);
	
	int getAnimationState();

	void setPrevAnimationState(int state);
	
	int getPrevAnimationState();
	
	void setAnimationTick(int tick);
	
	int getAnimationTick();
}
