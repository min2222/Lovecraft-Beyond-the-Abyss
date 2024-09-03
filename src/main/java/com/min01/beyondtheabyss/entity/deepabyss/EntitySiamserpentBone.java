package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.KinematicChain.ChainSegment;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

public class EntitySiamserpentBone extends AbstractOwnableDeepAbyssMob<EntitySiamserpentHead>
{
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntitySiamserpentBone.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(EntitySiamserpentBone.class, EntityDataSerializers.INT);
	
	public EntitySiamserpentBone(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.setNoAi(true);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 60)
    			.add(Attributes.MOVEMENT_SPEED, 0.5F)
        		.add(Attributes.FOLLOW_RANGE, 30);
    }
    
    @Override
    public void tick() 
    {
    	super.tick();

    	if(this.getOwner() != null)
    	{
    		ChainSegment segment = this.getOwner().chain.getSegments()[this.getIndex()];
    		Vec2 rot = segment.getRot();
    		this.setPos(segment.getPos());
    		this.setXRot(rot.x);
    		this.setYRot(rot.y);
    		this.setYHeadRot(rot.y);
    		this.setYBodyRot(rot.y);
    	}
    }
    
    @Override
    protected void doPush(Entity p_20971_) 
    {
    	if(!(p_20971_ instanceof EntitySiamserpentHead) && !(p_20971_ instanceof EntitySiamserpentBone))
    	{
        	super.doPush(p_20971_);
    	}
    }
    
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) 
    {
    	if(p_21016_ == DamageSource.IN_WALL)
    	{
    		return false;
    	}
    	return super.hurt(p_21016_, p_21017_);
    }
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(INDEX, 0);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMob> createBuilder() 
	{
		EntityPartBuilder<EntitySiamserpentBone> partBuilder = new EntityPartBuilder<EntitySiamserpentBone>(this);
		return partBuilder;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putInt("Index", this.getIndex());
		p_37265_.putInt("Variant", this.getVariant());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_) 
	{
		super.readAdditionalSaveData(p_37262_);
		if(p_37262_.contains("Index"))
		{
			this.setIndex(p_37262_.getInt("Index"));
		}
		if(p_37262_.contains("Variant"))
		{
			this.setVariant(p_37262_.getInt("Variant"));
		}
	}
	
	public void setIndex(int value)
	{
		this.entityData.set(INDEX, value);
	}
	
	public int getIndex()
	{
		return this.entityData.get(INDEX);
	}
	
	public void setVariant(int value)
	{
		this.entityData.set(VARIANT, value);
	}
	
	public int getVariant()
	{
		return this.entityData.get(VARIANT);
	}

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
}
