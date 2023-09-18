package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.network.ArmorAbilitySyncPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

public class ArmorAbilityCapabilityHandler implements IArmorAbilityCapability
{
	private LivingEntity entity;
	private int tickCount;
	private AbyssArmorAbilities ability = AbyssArmorAbilities.NONE;
	
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
		this.ability = AbyssArmorAbilities.byId(nbt.getInt("ability"));
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
		if(this.getAbility() != AbyssArmorAbilities.NONE)
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
		default:
			break;
		}
	}
	
	public void updateAbyssalDash(LivingEntity entity)
	{
		if(this.tickCount >= 20)
		{
			this.setAbility(AbyssArmorAbilities.NONE);
		}
	}

	@Override
	public void setAbility(AbyssArmorAbilities ability)
	{
		this.ability = ability;
		this.sendUpdatePacket();
	}

	@Override
	public AbyssArmorAbilities getAbility() 
	{
		return this.ability;
	}
	
	public enum AbyssArmorAbilities
	{
		NONE(0),
		ABYSSAL_DASH(1);
		
		public int id;

		private AbyssArmorAbilities(int id) 
		{
			this.id = id;
		}
		
		public static AbyssArmorAbilities byId(int id)
		{
			for(AbyssArmorAbilities abilities : values()) 
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
			BeyondtheAbyss.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new ArmorAbilitySyncPacket(this.entity, this.ability));
		}
	}
}
