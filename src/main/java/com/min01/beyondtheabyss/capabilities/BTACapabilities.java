package com.min01.beyondtheabyss.capabilities;

import javax.annotation.Nonnull;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
	public static final Capability<IBTAAbilityCapability> BTA_ABILITY = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IPlayerAnimationCapability> PLAYER_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IItemAnimationCapability> ITEM_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	
	public static void attachItemStackCapability(AttachCapabilitiesEvent<ItemStack> e)
	{
		e.addCapability(IItemAnimationCapability.ID, new ICapabilitySerializable<CompoundTag>() 
		{
			LazyOptional<IItemAnimationCapability> inst = LazyOptional.of(() -> 
			{
				ItemAnimationCapabilityImpl i = new ItemAnimationCapabilityImpl();
				i.setItemStack(e.getObject());
				return i;
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
		if(e.getObject() instanceof LivingEntity living) 
		{
			e.addCapability(IBTAAbilityCapability.ID, new ICapabilitySerializable<CompoundTag>() 
			{
				LazyOptional<IBTAAbilityCapability> inst = LazyOptional.of(() -> 
				{
					BTAAbilityCapabilityImpl i = new BTAAbilityCapabilityImpl();
					i.setEntity(living);
					return i;
				});

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) 
				{
					return BTA_ABILITY.orEmpty(capability, this.inst.cast());
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
		if(e.getObject() instanceof Player player) 
		{
			e.addCapability(IPlayerAnimationCapability.ID, new ICapabilitySerializable<CompoundTag>() 
			{
				LazyOptional<IPlayerAnimationCapability> inst = LazyOptional.of(() -> 
				{
					PlayerAnimationCapabilityImpl i = new PlayerAnimationCapabilityImpl();
					i.setEntity(player);
					return i;
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
		}
	}
}
