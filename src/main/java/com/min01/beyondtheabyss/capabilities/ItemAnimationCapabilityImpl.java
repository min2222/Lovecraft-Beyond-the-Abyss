package com.min01.beyondtheabyss.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.deepabyss.ClamOfGuidanceItem;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateItemAnimationPacket;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

public class ItemAnimationCapabilityImpl implements IItemAnimationCapability
{
	public static final Capability<IItemAnimationCapability> ITEM_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	
	private int animationTick;
	private int animationState;
	private int tickCount;
	
	public final SmoothAnimationState gunBladeOpenAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState gunBladeCloseAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState freakyAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState reloadAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState shootAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState emptyAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState empty2AnimationState = new SmoothAnimationState();
	public final SmoothAnimationState clamOpenAnimationState = new SmoothAnimationState();
	
	private ItemStack stack;
	
	public void setItemStack(ItemStack stack)
	{
		this.stack = stack;
	}
	
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
		this.setAnimationTick(nbt.getInt("AnimationTick"));
		this.setAnimationState(nbt.getInt("AnimationState"));
	}
	
	@Override
	public void tick(Entity player, ItemStack stack) 
	{
		this.tickCount++;
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
		
		if(player.level.isClientSide)
		{
			this.gunBladeOpenAnimationState.updateWhen(this.getAnimationState() == 1 && stack.is(BTAItems.SKELETAL_GUNBLADE.get()), this.tickCount);
			this.gunBladeCloseAnimationState.updateWhen(this.getAnimationState() == 2 && stack.is(BTAItems.SKELETAL_GUNBLADE.get()), this.tickCount);

			this.freakyAnimationState.updateWhen(this.getAnimationState() == 1 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			this.reloadAnimationState.updateWhen(this.getAnimationState() == 2 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			this.shootAnimationState.updateWhen(this.getAnimationState() == 3 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			this.emptyAnimationState.updateWhen(this.getAnimationState() == 4 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			this.empty2AnimationState.updateWhen(this.getAnimationState() == 5 && stack.is(BTAItems.TOOTH_SHOTGUN.get()), this.tickCount);
			
			this.clamOpenAnimationState.updateWhen(ClamOfGuidanceItem.isOpen(stack) && stack.is(BTAItems.CLAM_OF_GUIDANCE.get()), this.tickCount);
		}
		else
		{
			this.sendUpdatePacket(player);
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
	
	//TODO
	private void sendUpdatePacket(Entity entity) 
	{
		if(!entity.level.isClientSide)
		{
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new UpdateItemAnimationPacket(this.stack, entity.getUUID(), this.animationState, this.animationTick));
		}
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) 
	{
		return ITEM_ANIMATION.orEmpty(cap, LazyOptional.of(() -> this));
	}
}
