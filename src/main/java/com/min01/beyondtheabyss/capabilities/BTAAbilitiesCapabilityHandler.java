package com.min01.beyondtheabyss.capabilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.network.BTAAbilitySyncPacket;
import com.min01.beyondtheabyss.network.BTANetwork;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

public class BTAAbilitiesCapabilityHandler implements IBTAAbilitiesCapability
{
	private LivingEntity entity;
	private Map<BTAAbilities, Integer> abilities = new HashMap<>();
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag tag = new CompoundTag();
		for(Map.Entry<BTAAbilities, Integer> entry : this.abilities.entrySet())
		{
			tag.putInt("ability", entry.getKey().id);
			tag.putInt("tickCount", entry.getValue());
		}
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		Map<BTAAbilities, Integer> abilities = new HashMap<>();
		abilities.put(BTAAbilities.byId(nbt.getInt("ability")), nbt.getInt("tickCount"));
	}

	@Override
	public void setEntity(LivingEntity entity) 
	{
		this.entity = entity;
	}

	@Override
	public void update() 
	{
		if(this.getAbilities().isEmpty() || this.getAbilities().containsKey(BTAAbilities.NONE))
		{
			this.abilities.clear();
		}
		
		for(Map.Entry<BTAAbilities, Integer> entry : this.abilities.entrySet())
		{
			BTAAbilities ability = entry.getKey();
			switch(ability)
			{
			case ABYSSAL_DASH:
				this.updateAbyssalDash(this.entity);
				break;
			case GHIDRUTHS_SCALES:
				this.updateGhidruthsScales(this.entity);
				break;
			default:
				break;
			}
		}
	}
	
	public void updateGhidruthsScales(LivingEntity entity)
	{
		if(this.getTickcount(BTAAbilities.GHIDRUTHS_SCALES) < 5 && entity.tickCount % 7F == 0)
		{
			this.setTickcount(BTAAbilities.GHIDRUTHS_SCALES, this.getTickcount(BTAAbilities.GHIDRUTHS_SCALES) + 1);
		}
		
		if(!entity.hasEffect(BTAEffects.GHIDRUTHS_SCALES.get()))
		{
			this.removeAbility(BTAAbilities.GHIDRUTHS_SCALES);
		}
	}
	
	public void updateAbyssalDash(LivingEntity entity)
	{
		this.setTickcount(BTAAbilities.ABYSSAL_DASH, this.getTickcount(BTAAbilities.ABYSSAL_DASH) + 1);
		
		if(this.getTickcount(BTAAbilities.ABYSSAL_DASH) >= 20)
		{
			this.removeAbility(BTAAbilities.ABYSSAL_DASH);
		}
	}

	@Override
	public void addAbility(BTAAbilities ability)
	{
		this.abilities.put(ability, 0);
		this.sendUpdatePacket();
	}
	
	@Override
	public void removeAbility(BTAAbilities toRemove) 
	{
		Iterator<Entry<BTAAbilities, Integer>> iterator = this.abilities.entrySet().iterator();
		while (iterator.hasNext())
		{
		    if (iterator.next().getKey().equals(toRemove))
		    {
		        iterator.remove();
		    }
		}
	}

	@Override
	public Map<BTAAbilities, Integer> getAbilities() 
	{
		return this.abilities;
	}

	@Override
	public void setTickcount(BTAAbilities ability, int tickCount) 
	{
		this.abilities.replace(ability, tickCount);
		this.sendUpdatePacket();
	}

	@Override
	public int getTickcount(BTAAbilities ability)
	{
		return this.abilities.get(ability);
	}
	
	public enum BTAAbilities
	{
		NONE(0),
		ABYSSAL_DASH(1),
		GHIDRUTHS_SCALES(2);
		
		public int id;

		private BTAAbilities(int id) 
		{
			this.id = id;
		}
		
		public static BTAAbilities byId(int id)
		{
			for(BTAAbilities abilities : values()) 
			{
				if (id == abilities.id) 
				{
					return abilities;
				}
			}
			return NONE;
		}
	}
	
	private void sendUpdatePacket() 
	{
		if(this.entity instanceof ServerPlayer)
		{
			for(Map.Entry<BTAAbilities, Integer> entry : this.abilities.entrySet())
			{
				BTAAbilities ability = entry.getKey();
				BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new BTAAbilitySyncPacket(this.entity, ability, entry.getValue()));
			}
		}
	}
}
