package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IItemAnimationCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = new ResourceLocation(BeyondtheAbyss.MODID, "item_animation");

	void tick(Entity player, ItemStack stack);
	
	void setAnimationState(int state);
	
	int getAnimationState();
	
	SmoothAnimationState getAnimationStateByName(String name);
	
	void setAnimationTick(int tick);
	
	int getAnimationTick();
	
	int getTickCount();
}
