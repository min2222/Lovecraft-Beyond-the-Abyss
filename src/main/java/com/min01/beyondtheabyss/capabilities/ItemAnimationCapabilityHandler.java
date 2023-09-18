package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.network.ItemAnimationSyncPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class ItemAnimationCapabilityHandler implements IItemAnimationCapability
{
	private int animationId;
	private Player host;
	private ItemStack stack;
	private AnimationState animationState = new AnimationState();
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt("animationId", this.animationId);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) 
	{
		this.animationId = nbt.getInt("animationId");
	}

	@Override
	public void setPlayer(Player entity)
	{
		this.host = entity;
		//FIXME
		//this.sendUpdatePacket();
	}
	
	@Override
	public void setItemStack(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public void update()
	{
		if(this.host.level.isClientSide)
		{
			if(this.animationId == 0)
			{
				this.animationState.stop();
			}
			else
			{
				this.animationState.startIfStopped(this.host.tickCount);
			}
		}
	}

	@Override
	public int getAnimationId()
	{
		return this.animationId;
	}

	@Override
	public void setAnimationId(int id)
	{
		this.animationId = id;
		this.sendUpdatePacket();
	}
	
	@Override
	public AnimationState getAnimationState() 
	{
		return this.animationState;
	}
	
	private void sendUpdatePacket() 
	{
		if(this.host instanceof ServerPlayer)
		{
			BeyondtheAbyss.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.host), new ItemAnimationSyncPacket(this.host, this.stack, this));
		}
	}
}
