package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public interface IItemAnimationCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = new ResourceLocation(BeyondtheAbyss.MODID, "cap_item_animation");

	void setPlayer(Player entity);

	void update();

	int getAnimationId();
	
	void setAnimationId(int id);
	
	void setItemStack(ItemStack stack);
	
	AnimationState getAnimationState();
}
