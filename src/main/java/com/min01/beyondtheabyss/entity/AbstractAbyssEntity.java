package com.min01.beyondtheabyss.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class AbstractAbyssEntity extends PathfinderMob
{
	public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractAbyssEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Byte> DATA_SKILL_ID = SynchedEntityData.defineId(AbstractAbyssEntity.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Boolean> SHOULD_MOVE = SynchedEntityData.defineId(AbstractAbyssEntity.class, EntityDataSerializers.BOOLEAN);
	public boolean isBoss;
	public int skillUsingTickCount;
	
	public AbstractAbyssEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.noCulling = true;
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful()
	{
		return this.isBoss;
	}
	
	@Override
	public boolean removeWhenFarAway(double p_21542_) 
	{
		return !this.isBoss;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_)
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putBoolean("isBoss", this.isBoss);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_) 
	{
		super.readAdditionalSaveData(p_21450_);
		this.isBoss = p_21450_.getBoolean("isBoss");
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(DATA_SKILL_ID, (byte)0);
		this.entityData.define(ANIMATION_STATE, 0);
		this.entityData.define(SHOULD_MOVE, true);
	}
	
    public void setAnimationState(int value)
    {
        this.entityData.set(ANIMATION_STATE, value);
    }
    
    public int getAnimationState()
    {
        return this.entityData.get(ANIMATION_STATE);
    }
    
    public void setShouldMove(boolean value)
    {
    	this.entityData.set(SHOULD_MOVE, value);
    }
    
    public boolean shouldMove()
    {
    	return this.entityData.get(SHOULD_MOVE);
    }
    
	protected int getSkillUsingTime()
	{
		return this.skillUsingTickCount;
	}
	
	public boolean isUsingSkill() 
	{
		if (this.level.isClientSide) 
		{
			return this.entityData.get(DATA_SKILL_ID) > 0;
		} 
		else
		{
			return this.skillUsingTickCount > 0;
		}
	}
	
	@Override
	protected void customServerAiStep() 
	{
		super.customServerAiStep();
		if(this.skillUsingTickCount > 0)
		{
			--this.skillUsingTickCount;
		}
	}
    
    public void stopAllAnimationStates() 
    {
    	
    }
}
