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

public class BTAAbilityImpl implements BTAAbilityCapability
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
			tag.putInt("TickCount", entry.getValue());
		}
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.abilities.put(BTAAbilities.byId(nbt.getInt("ability")), nbt.getInt("TickCount"));
	}

	@Override
	public void setEntity(LivingEntity entity) 
	{
		this.entity = entity;
	}

	@Override
	public void update() 
	{	
		for(Map.Entry<BTAAbilities, Integer> entry : this.abilities.entrySet())
		{
			BTAAbilities ability = entry.getKey();
			switch(ability)
			{
			case ABYSSAL_DASH:
				this.updateAbyssalDash(this.entity);
				break;
			case ABYSSAL_SCALES:
				this.updateAbyssalScale(this.entity);
				break;
			default:
				break;
			}
		}
	}
	
	public void updateAbyssalScale(LivingEntity entity)
	{
		if(this.getTickCount(BTAAbilities.ABYSSAL_SCALES) < 5 && entity.tickCount % 7.0F == 0)
		{
			this.setTickCount(BTAAbilities.ABYSSAL_SCALES, this.getTickCount(BTAAbilities.ABYSSAL_SCALES) + 1);
		}
		
		if(!entity.hasEffect(BTAEffects.ABYSSAL_SCALES.get()))
		{
			this.removeAbility(BTAAbilities.ABYSSAL_SCALES);
		}
	}
	
	public void updateAbyssalDash(LivingEntity entity)
	{
		this.setTickCount(BTAAbilities.ABYSSAL_DASH, this.getTickCount(BTAAbilities.ABYSSAL_DASH) + 1);
		
		if(this.getTickCount(BTAAbilities.ABYSSAL_DASH) >= 20)
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
		    if(iterator.next().getKey().equals(toRemove))
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
	public void setTickCount(BTAAbilities ability, int TickCount) 
	{
		this.abilities.replace(ability, TickCount);
		this.sendUpdatePacket();
	}

	@Override
	public int getTickCount(BTAAbilities ability)
	{
		return this.abilities.get(ability);
	}
	
	public enum BTAAbilities
	{
		NONE(0),
		ABYSSAL_DASH(1),
		ABYSSAL_SCALES(2);
		
		public int id;

		private BTAAbilities(int id) 
		{
			this.id = id;
		}
		
		public static BTAAbilities byId(int id)
		{
			for(BTAAbilities abilities : values()) 
			{
				if(id == abilities.id) 
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
