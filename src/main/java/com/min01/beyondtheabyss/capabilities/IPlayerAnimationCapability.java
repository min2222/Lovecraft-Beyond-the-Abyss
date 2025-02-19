package com.min01.beyondtheabyss.capabilities;

import org.apache.commons.lang3.tuple.Pair;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IPlayerAnimationCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = new ResourceLocation(BeyondtheAbyss.MODID, "player_animation");

	void setEntity(LivingEntity entity);

	void update();
	
	int getAnimationTick();

	void setAnimationTick(int tick);
	
	AnimationState getAnimationState(String name);
	
	void setAnimationState(AnimationState state, String name);
	
	Pair<ListTag, CompoundTag> getTag();
	
	void setTag(Pair<ListTag, CompoundTag> pair);
}
