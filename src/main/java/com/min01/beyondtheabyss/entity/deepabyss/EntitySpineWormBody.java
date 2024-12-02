package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class EntitySpineWormBody extends AbstractOwnableDeepAbyssMonster<AbstractDeepAbyssMonster>
{
	public static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(EntitySpineWormBody.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Optional<UUID>> HEAD_UUID = SynchedEntityData.defineId(EntitySpineWormBody.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public EntitySpineWormBody(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.setNoGravity(true);
		this.setCanMove(false);
		this.setCanLook(false);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 30.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.0F)
        		.add(Attributes.FOLLOW_RANGE, 25.0F);
    }
    
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(INDEX, 0);
		this.entityData.define(HEAD_UUID, Optional.empty());
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntitySpineWormBody> partBuilder = new EntityPartBuilder<EntitySpineWormBody>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	protected void registerGoals() 
	{
		
	}
	
	@Override
	public void tick()
	{
		super.tick();
		this.resetFallDistance();
    	
    	if(this.getHead() != null)
    	{
    		EntitySpineWormHead head = this.getHead();
    		this.hurtTime = head.hurtTime;
    		this.deathTime = head.deathTime;
    		if(!head.bodies.contains(this) && head.bodies.size() >= this.getIndex())
    		{
    			head.bodies.add(this.getIndex(), this);
    		}
    		if(head.bodies.isEmpty() && this.getIndex() == 0)
    		{
    			head.bodies.add(this);
    		}
    		if(head.posArray[this.getIndex()] != null && this.getOwner() != null)
    		{
    			Vec3 lookPos = BTAUtil.getLookPos(this.getOwner().getRotationVector(), this.getOwner().position(), 0.0F, 0.0F, 0.6F);
    			this.lookAt(Anchor.FEET, lookPos);
    			this.setPos(head.posArray[this.getIndex()]);
    		}
    	}
    	else
    	{
    		this.discard();
    	}
	}
	
	@Override
	public boolean isSwimable() 
	{
		return false;
	}
	
	@Override
	public boolean canBreathOutsideWater()
	{
		return true;
	}
	
	@Override
	public boolean isAlliedTo(Entity p_20355_) 
	{
		return p_20355_ == this.getOwner() || p_20355_ == this.getHead() || super.isAlliedTo(p_20355_);
	}
    
    @Override
    protected void doPush(Entity p_20971_) 
    {
    	
    }
    
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) 
    {
    	if(!this.isInvulnerableTo(p_21016_) && this.getHead() != null)
    	{
    		if(this.getIndex() != 0)
    		{
        		this.getHead().hurt(p_21016_, p_21017_);
    			return false;
    		}
    		else
    		{
        		this.getHead().hurt(p_21016_, p_21017_);
    			return super.hurt(p_21016_, p_21017_);
    		}
    	}
    	return false;
    }
    
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
    {
		this.setYRot(0.0F);
		this.setYHeadRot(0.0F);
		this.setYBodyRot(0.0F);
    	return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
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
		if(p_37262_.hasUUID("Head")) 
		{
			this.entityData.set(HEAD_UUID, Optional.of(p_37262_.getUUID("Head")));
		}
	}
	
	public void setHead(EntitySpineWormHead p_37263_)
	{
		this.entityData.set(HEAD_UUID, Optional.of(p_37263_.getUUID()));
	}
	
	@Nullable
	public EntitySpineWormHead getHead() 
	{
		if(this.entityData.get(HEAD_UUID).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(HEAD_UUID).get());
		}
		return null;
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
