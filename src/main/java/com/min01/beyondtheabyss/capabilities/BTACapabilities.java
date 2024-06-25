package com.min01.beyondtheabyss.capabilities;

import javax.annotation.Nonnull;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class BTACapabilities
{
	public static final Capability<ItemAnimationCapability> ITEM_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<BTAAbilityCapability> BTA_ABILITY = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IllusionCapability> ILLUSION = CapabilityManager.get(new CapabilityToken<>() {});
	
	public static void attachItemStackCapability(AttachCapabilitiesEvent<ItemStack> e)
	{
		e.addCapability(ItemAnimationCapability.ID, new ICapabilitySerializable<CompoundTag>() 
		{
			LazyOptional<ItemAnimationCapability> inst = LazyOptional.of(() -> 
			{
				ItemAnimationImpl i = new ItemAnimationImpl();
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
			e.addCapability(BTAAbilityCapability.ID, new ICapabilitySerializable<CompoundTag>() 
			{
				LazyOptional<BTAAbilityCapability> inst = LazyOptional.of(() -> 
				{
					BTAAbilityImpl i = new BTAAbilityImpl();
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
			
			e.addCapability(IllusionCapability.ID, new ICapabilitySerializable<CompoundTag>() 
			{
				LazyOptional<IllusionCapability> inst = LazyOptional.of(() -> 
				{
					IllusionImpl i = new IllusionImpl();
					i.setEntity(living);
					return i;
				});

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) 
				{
					return ILLUSION.orEmpty(capability, this.inst.cast());
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
