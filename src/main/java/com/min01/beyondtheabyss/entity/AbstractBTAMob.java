package com.min01.beyondtheabyss.entity;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBTAMob extends PathfinderMob
{
	public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Byte> SKILL_ID = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Boolean> CAN_MOVE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_BOSS = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_HOSTILE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> CAN_LOOK_OR_MOVE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	
	public int skillUsingTickCount;
	private AbstractBTAMob.BTASkills currentSkill = AbstractBTAMob.BTASkills.NONE;
	
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
		this.entityData.define(SKILL_ID, (byte)0);
		this.entityData.define(ANIMATION_STATE, 0);
		this.entityData.define(CAN_MOVE, true);
		this.entityData.define(IS_BOSS, false);
		this.entityData.define(IS_HOSTILE, false);
		this.entityData.define(CAN_LOOK_OR_MOVE, true);
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
    
	protected AbstractBTAMob.BTASkills getCurrentSkill() 
	{
		return !this.level.isClientSide ? this.currentSkill : AbstractBTAMob.BTASkills.byId(this.entityData.get(SKILL_ID));
	}
	
	public void setIsUsingSkill(AbstractBTAMob.BTASkills p_33728_) 
	{
		this.currentSkill = p_33728_;
		this.entityData.set(SKILL_ID, (byte)p_33728_.id);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.isHostile())
		{
			if(this.getTarget() != null && this.canLookOrMove())
			{
				this.getNavigation().moveTo(this.getTarget(), this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
				this.lookAt(this.getLookAnchor(), this.getLookPos());
			}
		}
	}
	
	public abstract Anchor getLookAnchor();
	public abstract Vec3 getLookPos();
	
	public void setAsBoss()
	{
		this.setBoss(true);
		this.setHostile(true);
	}
	
	public void setCanLookOrMove(boolean value)
	{
		this.entityData.set(CAN_LOOK_OR_MOVE, value);
	}
	
	public boolean canLookOrMove()
	{
		return this.entityData.get(CAN_LOOK_OR_MOVE);
	}
	
	public void setHostile(boolean value)
	{
		this.entityData.set(IS_HOSTILE, value);
	}
	
	public boolean isHostile()
	{
		return this.entityData.get(IS_HOSTILE);
	}
	
	public void setBoss(boolean value)
	{
		this.entityData.set(IS_BOSS, value);
	}
	
	public boolean isBoss()
	{
		return this.entityData.get(IS_BOSS);
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
		if (this.level.isClientSide) 
		{
			return this.entityData.get(SKILL_ID) > 0;
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
    
	public static enum BTASkills
	{
		NONE(0),
		GHIDRUTH_BITE(1),
		GHIDRUTH_TAIL_SWING(2),
		GHIDRUTH_DASH_PREPARE(3);
		
		int id;

		private BTASkills(int p_33754_) 
		{
			this.id = p_33754_;
		}
		
		public static AbstractBTAMob.BTASkills byId(int p_33759_)
		{
			for(AbstractBTAMob.BTASkills skils : values()) 
			{
				if (p_33759_ == skils.id) 
				{
					return skils;
				}
			}
			return NONE;
		}
	}
}
