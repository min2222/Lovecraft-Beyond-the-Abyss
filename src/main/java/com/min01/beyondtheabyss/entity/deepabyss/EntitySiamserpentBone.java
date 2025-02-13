package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.WormSegmentController;

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

public class EntitySiamserpentBone extends AbstractOwnableDeepAbyssMonster<AbstractDeepAbyssMonster>
{
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntitySiamserpentBone.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(EntitySiamserpentBone.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Optional<UUID>> HEAD_UUID = SynchedEntityData.defineId(EntitySiamserpentBone.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public EntitySiamserpentBone(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.setNoAi(true);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 60.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.5F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
		this.entityData.define(INDEX, 0);
		this.entityData.define(HEAD_UUID, Optional.empty());
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
	{
		EntityPartBuilder<EntitySiamserpentBone> partBuilder = new EntityPartBuilder<EntitySiamserpentBone>(this);
		return partBuilder;
	}
    
    @Override
    public void tick() 
    {
    	super.tick();
		this.resetFallDistance();
		
		if(this.tickCount == 2)
		{
			this.partBuilder.rebuildHitbox();
		}
		
    	if(this.getOwner() != null)
    	{
			WormSegmentController.tick(this, this.getOwner(), 1.0F, 0.5F);
    	}
    	else
    	{
    		this.discard();
    	}
    	
    	if(this.getHead() != null)
    	{
    		EntitySiamserpentHead head = this.getHead();
    		this.hurtTime = head.hurtTime;
    		this.deathTime = head.deathTime;
    		if(!head.segments.contains(this) && head.segments.size() >= this.getIndex())
    		{
    			head.segments.add(this.getIndex(), this);
    		}
    		if(head.segments.isEmpty() && this.getIndex() == 0)
    		{
    			head.segments.add(this);
    		}
    	}
    }
    
    @Override
    public boolean useSubRoot() 
    {
    	return true;
    }
    
    @Override
    public String subRoot()
    {
		if(this.getVariant() == 0 || this.getVariant() == 1)
		{
			return "SiamserpentBone";
		}
		return "SiamserpentMiddlebone";
    }

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
    
	@Override
	public boolean isAlliedTo(Entity p_20355_) 
	{
		return p_20355_ == this.getOwner() || p_20355_ == this.getHead() || super.isAlliedTo(p_20355_);
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
    	if(!this.isInvulnerableTo(p_21016_) && this.getHead() != null)
    	{
    		this.getHead().hurt(p_21016_, p_21017_);
    	}
    	return false;
    }
    
    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_)
    {
    	return super.isInvulnerableTo(p_20122_) || p_20122_ == DamageSource.IN_WALL || p_20122_.isFall();
    }
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putInt("Index", this.getIndex());
		p_37265_.putInt("Variant", this.getVariant());
		if(this.entityData.get(HEAD_UUID).isPresent())
		{
			p_37265_.putUUID("Head", this.entityData.get(HEAD_UUID).get());
		}
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
		if(p_37262_.hasUUID("Head")) 
		{
			this.entityData.set(HEAD_UUID, Optional.of(p_37262_.getUUID("Head")));
		}
	}
	
	public void setHead(EntitySiamserpentHead p_37263_)
	{
		this.entityData.set(HEAD_UUID, Optional.of(p_37263_.getUUID()));
	}
	
	@Nullable
	public EntitySiamserpentHead getHead() 
	{
		if(this.entityData.get(HEAD_UUID).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(HEAD_UUID).get());
		}
		return null;
	}
	
	public boolean shouldInvertRotation()
	{
		return this.getIndex() == 11;
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
}
