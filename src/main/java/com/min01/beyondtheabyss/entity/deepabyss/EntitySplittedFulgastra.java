package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.FulgastraChargeGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntitySplittedFulgastra extends AbstractOwnableDeepAbyssMonster<EntityFulgastra>
{
	public static final EntityDataAccessor<Boolean> IS_CHARGED = SynchedEntityData.defineId(EntitySplittedFulgastra.class, EntityDataSerializers.BOOLEAN);
	
	public final SmoothAnimationState chargingAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState shockingAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState closedAnimationState = new SmoothAnimationState();
	
	public EntitySplittedFulgastra(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<EntitySplittedFulgastra> partBuilder = new EntityPartBuilder<EntitySplittedFulgastra>(this);
    	return partBuilder;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 5.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
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
	public void tick()
	{
		super.tick();
		this.setCanMove(false);
		this.setCanLook(false);
		this.getNavigation().stop();
		if(this.level.isClientSide)
		{
			this.chargingAnimationState.updateWhen(this.isUsingSkill(1), this.tickCount);
			this.shockingAnimationState.updateWhen(this.isUsingSkill(2), this.tickCount);
			this.closedAnimationState.updateWhen(this.isUsingSkill(3), this.tickCount);
		}
		if(this.getAnimationState() == 3 && this.getOwner() != null && this.isInWater())
		{
			this.setDeltaMovement(BTAUtil.fromToVector(this.position(), this.getOwner().position(), 0.25F));
			if(this.distanceTo(this.getOwner()) <= 2.0F)
			{
				this.discard();
				this.getOwner().setAnimationState(2);
				this.getOwner().setAnimationTick(20);
				this.getOwner().heal(this.getHealth());
			}
		}
		if(this.tickCount == 20)
		{
			this.setDeltaMovement(Vec3.ZERO);
		}
	}
	
	@Override
	public void push(Entity p_21294_) 
	{
		if(!(p_21294_ instanceof EntityFulgastra) && !(p_21294_ instanceof EntitySplittedFulgastra))
		{
			super.push(p_21294_);
		}
	}
	
	@Override
	public boolean canSwim() 
	{
		return false;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_) 
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putBoolean("isCharged", this.isCharged());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_) 
	{
		super.readAdditionalSaveData(p_21450_);
		this.setCharged(p_21450_.getBoolean("isCharged"));
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
