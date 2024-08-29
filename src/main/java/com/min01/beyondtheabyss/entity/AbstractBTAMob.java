package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.cerbon.CompoundOrientedBox;
import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.entity.ai.navigation.NoSpinGroundPathNavigation;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBTAMob extends Monster implements IMultipart
{
	public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> CAN_MOVE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> CAN_LOOK_OR_MOVE = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_USING_SKILL = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(AbstractBTAMob.class, EntityDataSerializers.BOOLEAN);
	
	public int skillUsingTickCount;
	
	public Vec3[] posArray;
	
	public final EntityPartBuilder<? extends AbstractBTAMob> partBuilder;
	
	public AbstractBTAMob(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.partBuilder = this.createBuilder();
		this.noCulling = true;
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful()
	{
		return this.getBTAMobType().despawnInPeaceful;
	}
	
	@Override
	public boolean removeWhenFarAway(double p_21542_) 
	{
		return this.getBTAMobType().removeWhenFarAway;
	}
	
	@Override
	public boolean isPreventingPlayerRest(Player p_33036_) 
	{
		return this.getBTAMobType() == BTAMobType.HOSTILE;
	}
	
	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.partBuilder.hitbox.getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.partBuilder.hitbox;
	}
	
	@Override
	public void onSetPos(double x, double y, double z) 
	{
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
	}
	
	public abstract EntityPartBuilder<? extends AbstractBTAMob> createBuilder();
	
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
	protected PathNavigation createNavigation(Level p_21480_)
	{
		return new NoSpinGroundPathNavigation(this, p_21480_);
	}
	
	@Override
	protected void registerGoals() 
	{
        if(this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Player>(this, Player.class, false, false));
        }
        
        if(this.getBTAMobType() == BTAMobType.NETURAL || this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        }
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(!this.level.isClientSide)
		{
			this.setHasTarget(this.getTarget() != null);
		}
		
		if(this.getTarget() != null)
		{
			if(this.canLookOrMove())
			{
				if(this.getBTAMobType().moveToTarget && this.canMove())
				{
					this.getNavigation().moveTo(this.getTarget(), this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
				}
				if(this.getBTAMobType().lookTarget)
				{
					this.getLookControl().setLookAt(this.getTarget(), 30.0F, 30.0F);
				}
			}
		}
	}
	
	public abstract BTAMobType getBTAMobType();
	
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
	
	public void setIsUsingSkill(boolean value) 
	{
		this.entityData.set(IS_USING_SKILL, value);
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
