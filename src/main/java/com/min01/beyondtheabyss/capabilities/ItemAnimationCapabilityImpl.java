package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.weapon.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.weapon.ToothShotgunItem;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateItemAnimationPacket;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class ItemAnimationCapabilityImpl implements IItemAnimationCapability
{
	private ItemStack stack;
	private Entity entity;
	private int animationTick;
	private int animationState;
	
	private final SmoothAnimationState gunBladeOpenAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState gunBladeCloseAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState freakyAnimationState = new SmoothAnimationState();
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("AnimationTick", this.animationTick);
		nbt.putInt("AnimationState", this.animationState);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.animationTick = nbt.getInt("AnimationTick");
		this.animationState = nbt.getInt("AnimationState");
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
	public void tick() 
	{
		BTAUtil.setTickCount(this.stack, BTAUtil.getTickCount(this.stack) + 1);
		if(this.entity.level.isClientSide)
		{
			this.gunBladeOpenAnimationState.updateWhen(this.getAnimationState() == 1 && this.stack.is(BTAItems.SKELETAL_GUNBLADE.get()), BTAUtil.getTickCount(this.stack));
			this.gunBladeCloseAnimationState.updateWhen(this.getAnimationState() == 2 && this.stack.is(BTAItems.SKELETAL_GUNBLADE.get()), BTAUtil.getTickCount(this.stack));
			this.freakyAnimationState.updateWhen(this.getAnimationState() == 3 && this.stack.is(BTAItems.TOOTH_SHOTGUN.get()), BTAUtil.getTickCount(this.stack));
		}
		if(this.getAnimationTick() >= 0)
		{
			this.setAnimationTick(this.getAnimationTick() - 1);
		}
		else
		{
			this.setAnimationState(0);
		}
	}

	@Override
	public void setAnimationState(int state) 
	{
		this.animationState = state;
		this.sendUpdatePacket(true);
	}

	@Override
	public int getAnimationState() 
	{
		return this.animationState;
	}
	
	@Override
	public SmoothAnimationState getAnimationStateByName(String name) 
	{
		if(name.equals(SkeletalGunbladeItem.GUNBLADE_OPEN))
		{
			return this.gunBladeOpenAnimationState;
		}
		if(name.equals(SkeletalGunbladeItem.GUNBLADE_CLOSE))
		{
			return this.gunBladeCloseAnimationState;
		}
		if(name.equals(ToothShotgunItem.FREAKY))
		{
			return this.freakyAnimationState;
		}
		return new SmoothAnimationState();
	}
	
	@Override
	public void setAnimationTick(int tick) 
	{
		this.animationTick = tick;
		this.sendUpdatePacket(false);
	}
	
	@Override
	public int getAnimationTick() 
	{
		return this.animationTick;
	}
	
	public void sendUpdatePacket(boolean isState)
	{
		if(this.entity != null && !this.entity.level.isClientSide)
		{
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new UpdateItemAnimationPacket(this.entity.getUUID(), this.stack, this.animationState, this.animationTick, isState));
		}
	}
}
