package com.min01.beyondtheabyss.capabilities;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.misc.BTAAbilities;
import com.min01.beyondtheabyss.network.BTAAbilitySyncPacket;
import com.min01.beyondtheabyss.network.BTAAbilitySyncPacket.PacketType;
import com.min01.beyondtheabyss.network.BTANetwork;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;

public class BTAAbilityImpl implements BTAAbilityCapability
{
	private LivingEntity entity;
	private List<BTAAbility> abilities = new ArrayList<>();
	
	@Override
	public CompoundTag serializeNBT() 
	{
		CompoundTag nbt = new CompoundTag();
		ListTag list = new ListTag();
		this.abilities.forEach(t -> 
		{
			CompoundTag tag = new CompoundTag();
			t.save(tag);
			list.add(tag);
		});
		nbt.put("Abilities", list);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt)
	{
		ListTag list = nbt.getList("Abilities", 11);
		for(int i = 0; i < list.size(); ++i)
		{
			BTAAbility ability = BTAAbility.load(list.getCompound(i));
			this.abilities.add(ability);
		}
	}

	@Override
	public void setEntity(LivingEntity entity) 
	{
		this.entity = entity;
	}

	@Override
	public void update() 
	{
		if(!this.abilities.isEmpty())
		{
			this.abilities.forEach(t -> 
			{
				if(t == BTAAbilities.ABYSSAL_SCALES)
				{
					this.updateAbyssalScale(this.entity);
				}
			});
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

	@Override
	public void addAbility(BTAAbility ability)
	{
		this.abilities.add(ability);
		this.sendUpdatePacket(PacketType.ADD);
	}
	
	@Override
	public void removeAbility(BTAAbility toRemove) 
	{
		this.abilities.removeIf(t -> t == toRemove);
		this.sendUpdatePacket(PacketType.REMOVE);
	}

	@Override
	public List<BTAAbility> getAbilities() 
	{
		return this.abilities;
	}

	@Override
	public void setTickCount(BTAAbility ability, int tickCount) 
	{
		this.abilities.forEach(t -> 
		{
			if(t == ability)
			{
				t.setTickcount(tickCount);
				this.sendUpdatePacket(PacketType.TICK);
			}
		});
	}

	@Override
	public int getTickCount(BTAAbility ability)
	{
		for(BTAAbility ab : this.abilities)
		{
			if(ab == ability)
			{
				return ab.getTickcount();
			}
		}
		return 0;
	}
	
	public static class BTAAbility
	{
		private String name;
		private int tickCount;
		
		public BTAAbility(String name)
		{
			this.name = name;
		}
		
		public void tick()
		{
			
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public void setTickcount(int tickCount)
		{
			this.tickCount = tickCount;
		}
		
		public int getTickcount()
		{
			return this.tickCount;
		}
		
		public void write(FriendlyByteBuf buf)
		{
			buf.writeUtf(this.name);
			buf.writeInt(this.tickCount);
		}
		
		public static BTAAbility read(FriendlyByteBuf buf)
		{
			BTAAbility ability = new BTAAbility(buf.readUtf());
			ability.setTickcount(buf.readInt());
			return ability;
		}
		
		public void save(CompoundTag tag)
		{
			tag.putString("Ability", this.name);
			tag.putInt("TickCount", this.tickCount);
		}
		
		public static BTAAbility load(CompoundTag tag)
		{
			BTAAbility ability = new BTAAbility(tag.getString("Ability"));
			ability.setTickcount(tag.getInt("TickCount"));
			return ability;
		}
	}
	
	private void sendUpdatePacket(PacketType type) 
	{
		if(this.entity instanceof ServerPlayer)
		{
			this.abilities.forEach(t -> 
			{
				BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.entity), new BTAAbilitySyncPacket(this.entity, t, t.getTickcount(), type));
			});
		}
	}
}
