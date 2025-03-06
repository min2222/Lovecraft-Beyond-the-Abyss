package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityKormosBody extends AbstractKormosPart
{
	public static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(EntityKormosBody.class, EntityDataSerializers.INT);
	
	public EntityKormosBody(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
	{
    	EntityPartBuilder<EntityKormosBody> partBuilder = new EntityPartBuilder<EntityKormosBody>(this);
		return partBuilder;
	}
	
	@Override
	public boolean useSubRoot() 
	{
		return true;
	}
	
	@Override
	public String subRoot()
	{
		if(this.getIndex() == 0)
		{
			return "neck";
		}
		if(this.getIndex() == 1)
		{
			return "front_body";
		}
		if(this.getIndex() == 14)
		{
			return "back_body";
		}
		return "body";
	}
	
	@Override
	public float getSegmentDistance()
	{
		if(this.getIndex() == 0)
		{
			return 3.0F;
		}
		if(this.getIndex() == 1)
		{
			return 4.0F;
		}
		return super.getSegmentDistance();
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.tickCount == 2)
		{
			this.partBuilder.rebuildHitbox();
		}
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(INDEX, 0);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putInt("Index", this.getIndex());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_)
	{
		super.readAdditionalSaveData(p_37262_);
		if(p_37262_.contains("Index"))
		{
			this.setIndex(p_37262_.getInt("Index"));
		}
	}
	
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) 
    {
    	if(!this.isInvulnerableTo(p_21016_) && this.getOwner() != null)
    	{
    		this.getOwner().hurt(p_21016_, p_21017_);
    	}
    	return false;
    }
    
	public void setIndex(int value)
	{
		this.entityData.set(INDEX, value);
	}
	
	public int getIndex()
	{
		return this.entityData.get(INDEX);
	}
}
