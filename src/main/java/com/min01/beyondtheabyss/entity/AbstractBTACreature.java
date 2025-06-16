package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.EntityBounds;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.multipart.IMultipart;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBTACreature extends PathfinderMob implements IMultipart, IAnimatable, IPosArray
{
	public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractBTACreature.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> ANIMATION_TICK = SynchedEntityData.defineId(AbstractBTACreature.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> CAN_LOOK = SynchedEntityData.defineId(AbstractBTACreature.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> CAN_MOVE = SynchedEntityData.defineId(AbstractBTACreature.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_USING_SKILL = SynchedEntityData.defineId(AbstractBTACreature.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(AbstractBTACreature.class, EntityDataSerializers.BOOLEAN);

	public Vec3[] posArray;
	
	public final EntityPartBuilder<? extends AbstractBTACreature> partBuilder;
	
	public AbstractBTACreature(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.partBuilder = this.createBuilder();
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
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(ANIMATION_STATE, 0);
		this.entityData.define(ANIMATION_TICK, 0);
		this.entityData.define(CAN_LOOK, true);
		this.entityData.define(CAN_MOVE, true);
		this.entityData.define(IS_USING_SKILL, false);
		this.entityData.define(HAS_TARGET, false);
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
	public Vec3[] getPosArray() 
	{
		return this.posArray;
	}
	
	@Override
	public EntityPartBuilder<?> getPartBuilder() 
	{
		return this.partBuilder;
	}
	
	public abstract EntityPartBuilder<? extends AbstractBTACreature> createBuilder();
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
		
		if(!this.level.isClientSide)
		{
			this.setHasTarget(this.getTarget() != null);
		}
		
		if(this.getAnimationTick() > 0)
		{
			this.setAnimationTick(this.getAnimationTick() - 1);
		}
		
		if(this.getTarget() != null)
		{
			if(this.getBTAMobType().moveToTarget && this.canMove())
			{
				this.getNavigation().moveTo(this.getTarget(), this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
			}
			if(this.getBTAMobType().lookTarget && this.canLook())
			{
				this.lookAt(Anchor.EYES, this.getTarget().getEyePosition());
			}
		}
	}
	
	public abstract BTAMobType getBTAMobType();
    
    public void stopAllAnimationStates() 
    {
    	
    }
	
	public void setHasTarget(boolean value)
	{
		this.entityData.set(HAS_TARGET, value);
	}
	
	public boolean hasTarget()
	{
		return this.entityData.get(HAS_TARGET);
	}
	
	@Override
	public void setUsingSkill(boolean value) 
	{
		this.entityData.set(IS_USING_SKILL, value);
	}
	
	@Override
	public boolean isUsingSkill() 
	{
		return this.getAnimationTick() > 0 || this.entityData.get(IS_USING_SKILL);
	}
	
    public void setCanLook(boolean value)
    {
    	this.entityData.set(CAN_LOOK, value);
    }
    
    public boolean canLook()
    {
    	return this.entityData.get(CAN_LOOK);
    }
    
    @Override
    public void setCanMove(boolean value)
    {
    	this.entityData.set(CAN_MOVE, value);
    }
    
    @Override
    public boolean canMove()
    {
    	return this.entityData.get(CAN_MOVE);
    }
    
    @Override
    public void setAnimationTick(int value)
    {
        this.entityData.set(ANIMATION_TICK, value);
    }
    
    @Override
    public int getAnimationTick()
    {
        return this.entityData.get(ANIMATION_TICK);
    }
    
    public void setAnimationState(int value)
    {
        this.entityData.set(ANIMATION_STATE, value);
    }
    
    public int getAnimationState()
    {
        return this.entityData.get(ANIMATION_STATE);
    }
}
