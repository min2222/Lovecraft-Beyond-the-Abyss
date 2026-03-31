package com.min01.beyondtheabyss.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerTickCountCapabilityImpl implements IPlayerTickCountCapability
{
	public static final Capability<IPlayerTickCountCapability> PLAYER_TICK_COUNT = CapabilityManager.get(new CapabilityToken<>() {});
	
	private int tickCount;
	private Entity entity;
	
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("TickCount", this.tickCount);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.setTickCount(nbt.getInt("TickCount"));
	}
	
	@Override
	public void tick(Entity entity) 
	{
		if(entity instanceof Player player)
		{
			if(player.hasEffect(BTAEffects.STONE_SKIN.get()))
			{
				if(this.getTickCount() < 25)
				{
					this.setTickCount(this.getTickCount() + 1);
				}
			}
			else
			{
				this.setTickCount(0);
			}
		}
	}
	
	@Override
	public void setTickCount(int tickCount)
	{
		this.tickCount = tickCount;
		this.sendUpdatePacket();
	}

	@Override
	public int getTickCount() 
	{
		return this.tickCount;
	}
	
	private void sendUpdatePacket() 
	{
		if(!this.entity.level.isClientSide)
		{
			
		}
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) 
	{
		return PLAYER_TICK_COUNT.orEmpty(cap, LazyOptional.of(() -> this));
	}
}
