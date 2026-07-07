package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

@AutoRegisterCapability
public interface IPlayerAnimationCapability extends ICapabilitySerializable<CompoundTag>
{
	ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "player_animation");
	
	void setAnimationState(int state);
	
	int getAnimationState();
	
	void setAnimationTick(int tick);
	
	int getAnimationTick();

	void sync(int animationState, int animationTick);
	
	void tick(Player player);
}
