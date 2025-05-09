package com.min01.beyondtheabyss.item.animation;

import net.minecraft.world.entity.AnimationState;

public interface IAnimatableItemStack 
{
	public AnimationState getAnimationState(String name);
}
