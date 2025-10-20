package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.weapon.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.weapon.ToothShotgunItem;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePlayerAnimationPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class PlayerAnimationCapabilityImpl implements IPlayerAnimationCapability
{
	private int animationTick;
	private int animationState;
	private int prevAnimationState;
	
	private final SmoothAnimationState shotgunFireAnimationState = new SmoothAnimationState(0.999F);
	private final SmoothAnimationState shotgunHoldAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState shotgunRunningAnimationState = new SmoothAnimationState(0.999F);
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
	public void tick(LivingEntity entity) 
	{
		if(entity.level.isClientSide)
		{
			this.shotgunFireAnimationState.updateWhen(this.getAnimationState() == 1 && entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()), entity.tickCount);
			this.shotgunHoldAnimationState.updateWhen(this.getAnimationState() == 0 && entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && !entity.isSprinting(), entity.tickCount);
			this.shotgunHoldToRunAnimationState.updateWhen(this.getAnimationState() == 2 && entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && entity.isSprinting(), entity.tickCount);
			this.shotgunRunningAnimationState.updateWhen(this.getAnimationState() == 0 && entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()) && entity.isSprinting(), entity.tickCount);
			this.gunbladeChargeAnimationState.updateWhen(this.getAnimationState() == 3 && entity.isUsingItem() && entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()), entity.tickCount);
			this.gunbladeShootAnimationState.updateWhen(this.getAnimationState() == 4 && entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get()), entity.tickCount);
		}
		else
		{
			if(entity.isSprinting() && this.getAnimationState() == 0 && this.getPrevAnimationState() != 2 && entity.isHolding(BTAItems.TOOTH_SHOTGUN.get()))
			{
				this.setAnimationState(2);
				this.setAnimationTick(10);
			}
			if(this.getAnimationTick() > 0)
			{
				this.setAnimationTick(this.getAnimationTick() - 1);
			}
			else
			{
				if(this.getAnimationState() == 2 && entity.isSprinting())
				{
					this.setPrevAnimationState(0);
				}
				if(!entity.isSprinting() && this.getPrevAnimationState() == 2)
				{
					this.setPrevAnimationState(0);
				}
				if(this.getAnimationState() == 4 && entity.isHolding(BTAItems.SKELETAL_GUNBLADE.get())) 
				{
					//FIXME send nbt tag to client;
					ItemStack stack = entity.getItemInHand(entity.getUsedItemHand());
					SkeletalGunbladeItem.setLaserVisible(stack, false);
					SkeletalGunbladeItem.setLaserLength(stack, 0);
				}
				this.setAnimationState(0);
				this.setAnimationTick(0);
			}
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new UpdatePlayerAnimationPacket(entity.getUUID(), this.animationState, this.prevAnimationState, this.animationTick));
		}
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
	public void setPrevAnimationState(int state) 
	{
		this.prevAnimationState = state;
	}

	@Override
	public int getPrevAnimationState() 
	{
		return this.prevAnimationState;
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
	}
	
	@Override
	public int getAnimationTick() 
	{
		return this.animationTick;
	}
}
