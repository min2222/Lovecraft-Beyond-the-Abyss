package com.min01.beyondtheabyss.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractAbyssEntity extends PathfinderMob
{
	public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractAbyssEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Byte> DATA_SKILL_ID = SynchedEntityData.defineId(AbstractAbyssEntity.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Boolean> SHOULD_MOVE = SynchedEntityData.defineId(AbstractAbyssEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_BOSS = SynchedEntityData.defineId(AbstractAbyssEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_HOSTILE = SynchedEntityData.defineId(AbstractAbyssEntity.class, EntityDataSerializers.BOOLEAN);
	public int skillUsingTickCount;
	private AbstractAbyssEntity.AbyssSkills currentSkill = AbstractAbyssEntity.AbyssSkills.NONE;
	
	public AbstractAbyssEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.noCulling = true;
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful()
	{
		return this.isBoss();
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
		this.entityData.define(DATA_SKILL_ID, (byte)0);
		this.entityData.define(ANIMATION_STATE, 0);
		this.entityData.define(SHOULD_MOVE, true);
		this.entityData.define(IS_BOSS, false);
		this.entityData.define(IS_HOSTILE, false);
	}
	
    @Override
	public void move(MoverType p_19973_, Vec3 p_19974_) 
	{
		if(this.shouldMove())
		{
			super.move(p_19973_, p_19974_);
		}
		else if(!this.shouldMove())
		{
			double yvec = this.onGround || this.isNoGravity() ? 0 : this.getDeltaMovement().y;
			super.move(p_19973_, new Vec3(0, yvec, 0));
		}
	}
    
	protected AbstractAbyssEntity.AbyssSkills getCurrentSkill() 
	{
		return !this.level.isClientSide ? this.currentSkill : AbstractAbyssEntity.AbyssSkills.byId(this.entityData.get(DATA_SKILL_ID));
	}
	
	public void setIsUsingSkill(AbstractAbyssEntity.AbyssSkills p_33728_) 
	{
		this.currentSkill = p_33728_;
		this.entityData.set(DATA_SKILL_ID, (byte)p_33728_.id);
	}
	
	@Override
	public void aiStep() 
	{
		super.aiStep();
		if(this.isHostile())
		{
			if(this.getTarget() != null)
			{
				this.getNavigation().moveTo(this.getTarget(), this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
				this.getLookControl().setLookAt(this.getTarget(), 30, 30);
			}
		}
	}
	
	public void setAsBoss()
	{
		this.setBoss(true);
		this.setHostile(true);
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
    
	public static enum AbyssSkills
	{
		NONE(0),
		GHIDRUTH_DASH(1),
		GHIDRUTH_BITE(2),
		GHIDRUTH_TAIL_SLAP(3);
		
		int id;

		private AbyssSkills(int p_33754_) 
		{
			this.id = p_33754_;
		}
		
		public static AbstractAbyssEntity.AbyssSkills byId(int p_33759_)
		{
			for(AbstractAbyssEntity.AbyssSkills skils : values()) 
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
