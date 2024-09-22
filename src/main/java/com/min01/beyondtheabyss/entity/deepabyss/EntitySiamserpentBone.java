package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.WormKinematicChain;
import com.min01.beyondtheabyss.util.WormSegmentController;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
	
	public final WormKinematicChain wormChain = new WormKinematicChain(this);
			
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
    		if(!this.level.isClientSide)
    		{
    			WormSegmentController.tick((ServerLevel) this.level, this.getX(), this.getY(), this.getZ(), this, this.getOwner(), 1.0F, 0.5F);
    		}
    		/*ChainSegment segment = this.wormChain.getSegments()[this.getIndex()];
    		this.wormChain.tick();
    		this.setYRot(segment.getRot().y);
    		this.setXRot(segment.getRot().x);
    		this.setYBodyRot(this.getYRot());
    		this.setYHeadRot(this.getYRot());
    		this.yRotO = this.getYRot();
    		this.xRotO = this.getXRot();
    		this.yBodyRotO = this.getYRot();
    		this.yHeadRotO = this.getYRot();
    		this.teleportTo(this.getOwner().getX() + segment.getPos().x, this.getOwner().getY() + segment.getPos().y, this.getOwner().getZ() + segment.getPos().z);*/
    	}
    }
    
	@Override
	public boolean isAlliedTo(Entity p_20355_) 
	{
		if(this.getOwner() != null)
		{
			return p_20355_ == this.getOwner();
		}
		return super.isAlliedTo(p_20355_);
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
    	if(p_21016_.is(DamageTypes.IN_WALL))
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
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
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

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
}
