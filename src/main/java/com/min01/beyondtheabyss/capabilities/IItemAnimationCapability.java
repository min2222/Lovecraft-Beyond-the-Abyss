package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IItemAnimationCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = new ResourceLocation(BeyondtheAbyss.MODID, "item_animation");

	void setEntity(Entity entity);
	
	void setItemStack(ItemStack stack);

	void update();
	
	void startItemAnimation(String name);
	
	void stopItemAnimation(String name);
	
	AnimationState getAnimationState(String name);

	void setTickCount(int tickCount);
	
	int getTickCount();
}
