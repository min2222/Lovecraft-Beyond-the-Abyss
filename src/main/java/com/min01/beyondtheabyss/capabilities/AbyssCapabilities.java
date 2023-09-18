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

public class AbyssCapabilities
{
	public static final Capability<IItemAnimationCapability> ITEM_ANIMATION = CapabilityManager.get(new CapabilityToken<>() {});
	public static final Capability<IArmorAbilityCapability> ARMOR_ABILITY = CapabilityManager.get(new CapabilityToken<>() {});
	
	public static void attachItemStackCapability(AttachCapabilitiesEvent<ItemStack> e)
	{
		e.addCapability(IItemAnimationCapability.ID, new ICapabilitySerializable<CompoundTag>() 
		{
			LazyOptional<IItemAnimationCapability> inst = LazyOptional.of(() -> 
			{
				ItemAnimationCapabilityHandler i = new ItemAnimationCapabilityHandler();
				i.setItemStack(e.getObject());
				return i;
			});

			@Nonnull
			@Override
			public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) 
			{
				return ITEM_ANIMATION.orEmpty(capability, inst.cast());
			}

			@Override
			public CompoundTag serializeNBT() 
			{
				return inst.orElseThrow(NullPointerException::new).serializeNBT();
			}

			@Override
			public void deserializeNBT(CompoundTag nbt)
			{
				inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
			}
		});
	}
	
	public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e)
	{
		if (e.getObject() instanceof LivingEntity living) 
		{
			e.addCapability(IArmorAbilityCapability.ID, new ICapabilitySerializable<CompoundTag>() 
			{
				LazyOptional<IArmorAbilityCapability> inst = LazyOptional.of(() -> 
				{
					ArmorAbilityCapabilityHandler i = new ArmorAbilityCapabilityHandler();
					i.setEntity(living);
					return i;
				});

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) 
				{
					return ARMOR_ABILITY.orEmpty(capability, inst.cast());
				}

				@Override
				public CompoundTag serializeNBT() 
				{
					return inst.orElseThrow(NullPointerException::new).serializeNBT();
				}

				@Override
				public void deserializeNBT(CompoundTag nbt)
				{
					inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
				}
			});
		}
	}
}
