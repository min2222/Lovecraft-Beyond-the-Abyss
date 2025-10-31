package com.min01.beyondtheabyss.capabilities;

import javax.annotation.Nonnull;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class BTACapabilities
{
	public static final Capability<IItemAnimationCapability> ITEM_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IPlayerAnimationCapability> PLAYER_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IPlayerTickCountCapability> PLAYER_TICKCOUNT = CapabilityManager.get(new CapabilityToken<>() {});
	
	public static void attachItemStackCapability(AttachCapabilitiesEvent<ItemStack> e)
	{
		e.addCapability(IItemAnimationCapability.ID, new ICapabilitySerializable<CompoundTag>() 
		{
			LazyOptional<IItemAnimationCapability> inst = LazyOptional.of(() -> 
			{
				return new ItemAnimationCapabilityImpl();
			});

			@Nonnull
			@Override
			public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) 
			{
				return ITEM_ANIMATION.orEmpty(capability, this.inst.cast());
			}

			@Override
			public CompoundTag serializeNBT() 
			{
				return this.inst.orElseThrow(NullPointerException::new).serializeNBT();
			}

			@Override
			public void deserializeNBT(CompoundTag nbt)
			{
				this.inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
			}
		});
	}
	
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e)
	{
		if(e.getObject() instanceof Player) 
		{
			e.addCapability(IPlayerAnimationCapability.ID, new ICapabilitySerializable<CompoundTag>() 
			{
				LazyOptional<IPlayerAnimationCapability> inst = LazyOptional.of(() -> 
				{
					return new PlayerAnimationCapabilityImpl();
				});

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) 
				{
					return PLAYER_ANIMATION.orEmpty(capability, this.inst.cast());
				}

				@Override
				public CompoundTag serializeNBT() 
				{
					return this.inst.orElseThrow(NullPointerException::new).serializeNBT();
				}

				@Override
				public void deserializeNBT(CompoundTag nbt)
				{
					this.inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
				}
			});
			e.addCapability(IPlayerTickCountCapability.ID, new ICapabilitySerializable<CompoundTag>() 
			{
				LazyOptional<IPlayerTickCountCapability> inst = LazyOptional.of(() -> 
				{
					return new PlayerTickCountCapabilityImpl();
				});

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) 
				{
					return PLAYER_TICKCOUNT.orEmpty(capability, this.inst.cast());
				}

				@Override
				public CompoundTag serializeNBT() 
				{
					return this.inst.orElseThrow(NullPointerException::new).serializeNBT();
				}

				@Override
				public void deserializeNBT(CompoundTag nbt)
				{
					this.inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
				}
			});
		}
	}
}
