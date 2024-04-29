package com.min01.beyondtheabyss.entity;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBTAMob extends PathfinderMob
{
	public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> CAN_MOVE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> CAN_LOOK_OR_MOVE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_USING_SKILL = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	
	public int skillUsingTickCount;
	
	public AbstractBTAMob(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.noCulling = true;
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful()
	{
		return this.isBoss() || this.isHostile();
	}
	
	@Override
	public boolean removeWhenFarAway(double p_21542_) 
	{
		return !this.isBoss();
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(ANIMATION_STATE, 0);
		this.entityData.define(CAN_MOVE, true);
		this.entityData.define(CAN_LOOK_OR_MOVE, true);
		this.entityData.define(IS_USING_SKILL, false);
		this.entityData.define(HAS_TARGET, false);
	}
	
    @Override
	public void move(MoverType p_19973_, Vec3 p_19974_) 
	{
		if(this.canMove())
		{
			super.move(p_19973_, p_19974_);
		}
		else if(!this.canMove())
		{
			double yvec = this.onGround || this.isNoGravity() ? 0 : this.getDeltaMovement().y;
			super.move(p_19973_, new Vec3(0, yvec, 0));
		}
	}
	
	public void setIsUsingSkill(boolean value) 
	{
		this.entityData.set(IS_USING_SKILL, value);
	}
	
	@Override
	protected void registerGoals() 
	{
        if(this.isHostile())
        {
            this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
            if(!this.isNetural())
            {
                this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Player>(this, Player.class, false, false));
            }
        }
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.isHostile())
		{
			if(this.getTarget() != null)
			{
				this.setHasTarget(this.getTarget() != null);
				if(this.canLookOrMove())
				{
					this.getNavigation().moveTo(this.getTarget(), this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
					this.lookAt(this.getLookAnchor(), this.getLookPos());
				}
			}
		}
	}
	
	public Anchor getLookAnchor()
	{
		return Anchor.EYES;
	}
	
	public Vec3 getLookPos()
	{
		return this.getTarget().getEyePosition();
	}
	
	public void setHasTarget(boolean value)
	{
		this.entityData.set(HAS_TARGET, value);
	}
	
	public boolean hasTarget()
	{
		return this.entityData.get(HAS_TARGET);
	}
	
	public void setCanLookOrMove(boolean value)
	{
		this.entityData.set(CAN_LOOK_OR_MOVE, value);
	}
	
	public boolean canLookOrMove()
	{
		return this.entityData.get(CAN_LOOK_OR_MOVE);
	}
	
	public boolean isNetural()
	{
		return false;
	}
	
	public boolean isHostile()
	{
		return true;
	}
	
	public boolean isBoss()
	{
		return false;
	}
	
    public void setAnimationState(int value)
    {
        this.entityData.set(ANIMATION_STATE, value);
    }
    
    public int getAnimationState()
    {
        return this.entityData.get(ANIMATION_STATE);
    }
    
    public void setCanMove(boolean value)
    {
    	this.entityData.set(CAN_MOVE, value);
    }
    
    public boolean canMove()
    {
    	return this.entityData.get(CAN_MOVE);
    }
    
	protected int getSkillUsingTime()
	{
		return this.skillUsingTickCount;
	}
	
	public boolean isUsingSkill() 
	{
		return this.skillUsingTickCount > 0 || this.entityData.get(IS_USING_SKILL);
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
