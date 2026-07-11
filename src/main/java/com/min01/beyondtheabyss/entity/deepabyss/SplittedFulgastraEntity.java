package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractOwnableBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.FulgastraChargeGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.MobClassification;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SplittedFulgastraEntity extends AbstractOwnableBTAMonster<FulgastraEntity>
{
	public static final EntityDataAccessor<Boolean> IS_CHARGED = SynchedEntityData.defineId(SplittedFulgastraEntity.class, EntityDataSerializers.BOOLEAN);

	public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState chargingAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState shockingAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState closedAnimationState = new SmoothAnimationState();
	
	public SplittedFulgastraEntity(EntityType<? extends AbstractOwnableBTAMonster<FulgastraEntity>> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(2);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 5.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.25F)
    			.add(Attributes.FOLLOW_RANGE, 30.0F);
    }
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(0, new FulgastraChargeGoal(this));
	}
	
    @Override
    protected void defineSynchedData()
    {
    	super.defineSynchedData();
    	this.entityData.define(IS_CHARGED, false);
    }

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public MobClassification getMobClassification() 
	{
		return MobClassification.WATER;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.level.isClientSide)
		{
			this.swimAnimationState.updateWhen(true, this.tickCount);
			this.chargingAnimationState.updateWhen(this.isAnimationPlaying(1), this.tickCount);
			this.shockingAnimationState.updateWhen(this.isAnimationPlaying(2), this.tickCount);
			this.closedAnimationState.updateWhen(this.isAnimationPlaying(3), this.tickCount);
		}
		FulgastraEntity owner = this.getOwner();
		if(this.getAnimationState() == 3 && owner != null && this.isInWater())
		{
			this.getNavigation().moveTo(owner, 1.25F);
			if(this.distanceTo(owner) <= 2.0F)
			{
				owner.setSplit(false);
				owner.setAnimationState(1);
				owner.setAnimationTick(20);
				owner.heal(this.getHealth());
				this.discard();
			}
		}
		if(this.tickCount == 20)
		{
			this.setDeltaMovement(Vec3.ZERO);
		}
	}
	
	@Override
	public void push(Entity pEntity) 
	{
		if(!(pEntity instanceof FulgastraEntity) && !(pEntity instanceof SplittedFulgastraEntity))
		{
			super.push(pEntity);
		}
	}
	
	@Override
	public boolean canLook()
	{
		return false;
	}
	
	@Override
	public boolean canMove() 
	{
		return false;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putBoolean("isCharged", this.isCharged());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) 
	{
		super.readAdditionalSaveData(pCompound);
		this.setCharged(pCompound.getBoolean("isCharged"));
	}
	
	public void setCharged(boolean value)
	{
		this.entityData.set(IS_CHARGED, value);
	}
	
	public boolean isCharged()
	{
		return this.entityData.get(IS_CHARGED);
	}
}
