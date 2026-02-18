package com.min01.beyondtheabyss.entity;

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
import net.minecraft.world.level.Level;

public abstract class AbstractOwnableBTAWaterMonster<T extends AbstractBTAWaterMonster> extends AbstractBTAWaterMonster
{
	public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(AbstractOwnableBTAWaterMonster.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public AbstractOwnableBTAWaterMonster(EntityType<? extends AbstractBTAWaterMonster> pEntityType, Level pLevel)
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
			if(this.getType().is(BTATags.BTAEntity.FORCE_TICKING))
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
