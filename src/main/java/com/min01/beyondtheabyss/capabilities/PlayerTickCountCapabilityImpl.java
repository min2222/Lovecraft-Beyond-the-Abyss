package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.effect.BTAEffects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PlayerTickCountCapabilityImpl implements IPlayerTickCountCapability
{
	private int tickCount;
	
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
		this.tickCount = nbt.getInt("TickCount");
	}
	
	@Override
	public void tick(Entity entity) 
	{
		if(entity instanceof Player player)
		{
			if(player.hasEffect(BTAEffects.STONE_SKIN.get()))
			{
				if(this.tickCount < 25)
				{
					this.tickCount++;
				}
			}
			else
			{
				this.tickCount = 0;
			}
		}
	}

	@Override
	public int getPlayerTickCount() 
	{
		return this.tickCount;
	}
}
