package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.weapon.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.weapon.ToothShotgunItem;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePlayerAnimationPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class PlayerAnimationCapabilityImpl implements IPlayerAnimationCapability
{
	private Player entity;
	private int animationTick;
	private int animationState;
	private int prevAnimationState;
	
	private final SmoothAnimationState shotgunFireAnimationState = new SmoothAnimationState(0.999F);
	private final SmoothAnimationState shotgunHoldAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState shotgunRunningAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState shotgunHoldToRunAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState gunbladeChargeAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState gunbladeShootAnimationState = new SmoothAnimationState();
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("AnimationTick", this.animationTick);
		nbt.putInt("AnimationState", this.animationState);
		nbt.putInt("PrevAnimationState", this.prevAnimationState);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.animationTick = nbt.getInt("AnimationTick");
		this.animationState = nbt.getInt("AnimationState");
		this.prevAnimationState = nbt.getInt("PrevAnimationState");
	}
	
	@Override
	public void setEntity(Player entity) 
	{
		this.entity = entity;
	}

	@Override
	public void tick() 
	{
		if(this.entity != null)
		{
			if(this.entity.level.isClientSide)
			{
				this.shotgunFireAnimationState.updateWhen(this.getAnimationState() == 1 && this.entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()), this.entity.tickCount);
				this.shotgunHoldAnimationState.updateWhen(this.getAnimationState() == 0 && this.entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && !this.entity.isSprinting(), this.entity.tickCount);
				this.shotgunHoldToRunAnimationState.updateWhen(this.getAnimationState() == 2 && this.entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && this.entity.isSprinting(), this.entity.tickCount);
				this.shotgunRunningAnimationState.updateWhen(this.getAnimationState() == 0 && this.entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && this.entity.isSprinting(), this.entity.tickCount);
				this.gunbladeChargeAnimationState.updateWhen(this.getAnimationState() == 3 && this.entity.isUsingItem() && this.entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()), this.entity.tickCount);
				this.gunbladeShootAnimationState.updateWhen(this.getAnimationState() == 4 && this.entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()), this.entity.tickCount);
			}
			if(this.entity.isSprinting() && this.getAnimationState() == 0 && this.prevAnimationState != 2 && this.entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()))
			{
				this.setAnimationState(2);
				this.setAnimationTick(10);
			}
			if(this.getAnimationTick() >= 0)
			{
				this.setAnimationTick(this.getAnimationTick() - 1);
			}
			else
			{
				if(this.getAnimationState() == 2 && this.entity.isSprinting())
				{
					this.prevAnimationState = 2;
				}
				if(!this.entity.isSprinting() && this.prevAnimationState == 2)
				{
					this.prevAnimationState = 0;
				}
				if(this.getAnimationState() == 4 && this.entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get())) 
				{
					ItemStack stack = this.entity.getItemInHand(this.entity.getUsedItemHand());
					SkeletalGunbladeItem.setLaserVisible(stack, false);
					SkeletalGunbladeItem.setLaserLength(stack, 0);
				}
				this.setAnimationState(0);
			}
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
		if(name.equals(ToothShotgunItem.SHOTGUN_FIRE))
		{
			return this.shotgunFireAnimationState;
		}
		if(name.equals(ToothShotgunItem.SHOTGUN_HOLD))
		{
			return this.shotgunHoldAnimationState;
		}
		if(name.equals(ToothShotgunItem.SHOTGUN_RUNNING))
		{
			return this.shotgunRunningAnimationState;
		}
		if(name.equals(ToothShotgunItem.SHOTGUN_HOLD_TO_RUN))
		{
			return this.shotgunHoldToRunAnimationState;
		}
		if(name.equals(SkeletalGunbladeItem.GUNBLADE_CHARGE))
		{
			return this.gunbladeChargeAnimationState;
		}
		if(name.equals(SkeletalGunbladeItem.GUNBLADE_SHOOT))
		{
			return this.gunbladeShootAnimationState;
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
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new UpdatePlayerAnimationPacket(this.entity.getUUID(), this.animationState, this.animationTick, isState));
		}
	}
}
