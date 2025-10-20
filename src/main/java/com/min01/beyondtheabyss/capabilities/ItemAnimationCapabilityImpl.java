package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.weapon.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.weapon.ToothShotgunItem;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateItemAnimationPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class ItemAnimationCapabilityImpl implements IItemAnimationCapability
{
	private int animationTick;
	private int animationState;
	private int tickCount;
	
	private final SmoothAnimationState gunBladeOpenAnimationState = new SmoothAnimationState(0.999F);
	private final SmoothAnimationState gunBladeCloseAnimationState = new SmoothAnimationState(0.999F);
	private final SmoothAnimationState freakyAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState reloadAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState shootAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState emptyAnimationState = new SmoothAnimationState();
	private final SmoothAnimationState empty2AnimationState = new SmoothAnimationState();
	
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
	public void tick(Entity player, ItemStack stack) 
	{
		this.tickCount++;
		
		if(player.level.isClientSide)
		{
			this.gunBladeOpenAnimationState.updateWhen(this.getAnimationState() == 1 && stack.is(BTAItems.SKELETAL_GUNBLADE.get()), this.tickCount);
			this.gunBladeCloseAnimationState.updateWhen(this.getAnimationState() == 2 && stack.is(BTAItems.SKELETAL_GUNBLADE.get()), this.tickCount);

			this.freakyAnimationState.updateWhen(this.getAnimationState() == 1 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			this.reloadAnimationState.updateWhen(this.getAnimationState() == 2 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			this.shootAnimationState.updateWhen(this.getAnimationState() == 3 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			this.emptyAnimationState.updateWhen(this.getAnimationState() == 4 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			this.empty2AnimationState.updateWhen(this.getAnimationState() == 5 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
		}
		else
		{
			if(this.getAnimationTick() > 0)
			{
				this.setAnimationTick(this.getAnimationTick() - 1);
			}
			else
			{
				if(stack.is(BTAItems.SKELETAL_GUNBLADE.get())) 
				{
					if(this.getAnimationState() != 1 && this.getAnimationState() != 2)
					{
						this.setAnimationState(0);
					}
				}
				else
				{
					this.setAnimationState(0);
				}
			}
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new UpdateItemAnimationPacket(stack, player.getUUID(), this.animationState, this.animationTick));
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
		if(name.equals(ToothShotgunItem.RELOAD))
		{
			return this.reloadAnimationState;
		}
		if(name.equals(ToothShotgunItem.SHOOT))
		{
			return this.shootAnimationState;
		}
		if(name.equals(ToothShotgunItem.EMPTY))
		{
			return this.emptyAnimationState;
		}
		if(name.equals(ToothShotgunItem.EMPTY2))
		{
			return this.empty2AnimationState;
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
	
	@Override
	public int getTickCount() 
	{
		return this.tickCount;
	}
}
