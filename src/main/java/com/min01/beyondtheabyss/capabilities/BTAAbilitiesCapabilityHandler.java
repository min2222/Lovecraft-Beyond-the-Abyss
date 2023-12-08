package com.min01.beyondtheabyss.capabilities;

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
	private int tickCount;
	private BTAAbilities ability = BTAAbilities.NONE;
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag tag = new CompoundTag();
		tag.putInt("ability", this.ability.id);
		tag.putInt("tickCount", this.tickCount);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		this.ability = BTAAbilities.byId(nbt.getInt("ability"));
		this.tickCount = nbt.getInt("tickCount");
	}

	@Override
	public void setEntity(LivingEntity entity) 
	{
		this.entity = entity;
	}

	@Override
	public void update() 
	{
		if(this.getAbility() != BTAAbilities.NONE)
		{
			this.tickCount++;
		}
		else
		{
			this.tickCount = 0;
		}
		switch(this.ability)
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
	
	public void updateGhidruthsScales(LivingEntity entity)
	{
		if(!entity.hasEffect(BTAEffects.GHIDRUTHS_SCALES.get()))
		{
			this.setAbility(BTAAbilities.NONE);
		}
	}
	
	public void updateAbyssalDash(LivingEntity entity)
	{
		if(this.tickCount >= 20)
		{
			this.setAbility(BTAAbilities.NONE);
		}
	}

	@Override
	public void setAbility(BTAAbilities ability)
	{
		this.ability = ability;
		this.sendUpdatePacket();
	}

	@Override
	public BTAAbilities getAbility() 
	{
		return this.ability;
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
			BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new BTAAbilitySyncPacket(this.entity, this.ability));
		}
	}
}
