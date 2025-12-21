package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractOwnableDeepAbyssMonster<T extends AbstractDeepAbyssMonster> extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(AbstractOwnableDeepAbyssMonster.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public AbstractOwnableDeepAbyssMonster(EntityType<? extends Monster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(OWNER_UUID, Optional.empty());
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		if(this.entityData.get(OWNER_UUID).isPresent())
		{
			pCompound.putUUID("Owner", this.entityData.get(OWNER_UUID).get());
		}
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) 
	{
		super.readAdditionalSaveData(pCompound);
		if(pCompound.hasUUID("Owner")) 
		{
			this.entityData.set(OWNER_UUID, Optional.of(pCompound.getUUID("Owner")));
		}
	}
	
	public void setOwner(T owner)
	{
		if(owner == null)
		{
			this.entityData.set(OWNER_UUID, Optional.empty());
		}
		else
		{
			this.entityData.set(OWNER_UUID, Optional.of(owner.getUUID()));
		}
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	public T getOwner() 
	{
		if(this.entityData.get(OWNER_UUID).isPresent()) 
		{
			if(this.getType().is(BTATags.BTAEntity.FAR_RANGE_TICKING))
			{
				for(Entity entity : BTAUtil.getAllEntities(this.level))
				{
					if(!entity.getUUID().equals(this.entityData.get(OWNER_UUID).get()))
					{
						continue;
					}
					return (T) entity;
				}
			}
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(OWNER_UUID).get());
		}
		return null;
	}
}
