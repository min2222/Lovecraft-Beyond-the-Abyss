package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.min01.beyondtheabyss.item.animation.IAnimatableItemStack;
import com.min01.beyondtheabyss.item.weapon.SkeletalGunbladeItem;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItemStack;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements IForgeItemStack, IAnimatableItemStack
{
	@Unique
	private final AnimationState openAnimationState = new AnimationState();
	
	@Unique
	private final AnimationState closeAnimationState = new AnimationState();
	
	@Unique
	private final AnimationState openedAnimationState = new AnimationState();
	
	@Unique
	private final AnimationState closedAnimationState = new AnimationState();
	
	@Override
	public AnimationState getAnimationState(String name) 
	{
		if(name.equals(SkeletalGunbladeItem.GUNBLADE_OPEN))
		{
			return this.openAnimationState;
		}
		if(name.equals(SkeletalGunbladeItem.GUNBLADE_CLOSE))
		{
			return this.closeAnimationState;
		}
		if(name.equals(SkeletalGunbladeItem.GUNBLADE_OPENED))
		{
			return this.openedAnimationState;
		}
		if(name.equals(SkeletalGunbladeItem.GUNBLADE_CLOSED))
		{
			return this.closedAnimationState;
		}
		return new AnimationState();
	}
}
