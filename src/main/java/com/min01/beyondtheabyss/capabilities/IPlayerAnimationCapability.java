package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IPlayerAnimationCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "player_animation");

	void tick(LivingEntity entity);
	
	void setAnimationState(int state);
	
	int getAnimationState();

	void setPrevAnimationState(int state);
	
	int getPrevAnimationState();
	
	SmoothAnimationState getAnimationStateByName(String name);
	
	void setAnimationTick(int tick);
	
	int getAnimationTick();
}
