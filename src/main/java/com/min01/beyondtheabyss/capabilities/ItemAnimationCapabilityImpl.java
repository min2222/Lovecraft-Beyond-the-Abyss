package com.min01.beyondtheabyss.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.item.animation.ItemAnimations;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class ItemAnimationCapabilityImpl implements IItemAnimationCapability
{
	public static final Capability<IItemAnimationCapability> ITEM_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	
	private int animationTick;
	private int animationState;
	private long instanceId = ItemAnimations.UNASSIGNED;
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("AnimationTick", this.animationTick);
		nbt.putInt("AnimationState", this.animationState);
		nbt.putLong("InstanceId", this.instanceId);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.animationTick = nbt.getInt("AnimationTick");
		this.animationState = nbt.getInt("AnimationState");
		this.instanceId = nbt.getLong("InstanceId");
	}

	@Override
	public void sync(int animationState, int animationTick)
	{
		this.animationState = animationState;
		this.animationTick = animationTick;
	}

	@Override
	public void tick()
	{
	    if(this.animationTick > 0)
	    {
            this.animationTick--;
	    }
	    else if(this.animationState > 0)
	    {
            this.animationTick = 0;
	        this.animationState = 0;
	    }
	}

	@Override
	public void setInstanceId(long id) 
	{
		this.instanceId = id;
	}

	@Override
	public long getInstanceId()
	{
		return this.instanceId;
	}

	@Override
	public void setAnimationState(int state) 
	{
		this.animationState = state;
	}

	@Override
	public int getAnimationState() 
	{
		return this.animationState;
	}
	
	@Override
	public void setAnimationTick(int tick) 
	{
		this.animationTick = tick;
	}
	
	@Override
	public int getAnimationTick() 
	{
		return this.animationTick;
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) 
	{
		return ITEM_ANIMATION.orEmpty(cap, LazyOptional.of(() -> this));
	}
}
