package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateItemTickCountPacket;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class ItemAnimationCapabilityImpl implements IItemAnimationCapability
{
	private ItemStack stack;
	private Entity entity;
	private int tickCount;
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("TickCount", this.tickCount);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.tickCount = nbt.getInt("TickCount");
	}
	
	@Override
	public void setEntity(Entity entity) 
	{
		this.entity = entity;
	}
	
	@Override
	public void setItemStack(ItemStack stack) 
	{
		this.stack = stack;
	}

	@Override
	public void update() 
	{
		this.tickCount++;
		if(this.entity != null && !this.entity.level.isClientSide)
		{
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new UpdateItemTickCountPacket(this.entity.getUUID(), this.stack, this.tickCount));
		}
	}

	@Override
	public void startItemAnimation(String name) 
	{
		AnimationState state = this.getAnimationState(name);
		state.startIfStopped(this.tickCount);
		BTAUtil.writeAnimationTime(this.stack.getOrCreateTag(), name, state);
	}

	@Override
	public void stopItemAnimation(String name) 
	{
		AnimationState state = this.getAnimationState(name);
		state.stop();
		BTAUtil.writeAnimationTime(this.stack.getOrCreateTag(), name, state);
	}
	
	@Override
	public AnimationState getAnimationState(String name)
	{
		AnimationState state = new AnimationState();
		BTAUtil.readAnimationTime(this.stack.getOrCreateTag(), name, state);
		return state;
	}
	
	@Override
	public void setTickCount(int tickCount) 
	{
		this.tickCount = tickCount;
	}
	
	@Override
	public int getTickCount() 
	{
		return this.tickCount;
	}
}
