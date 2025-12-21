package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.entity.ai.control.BTAFlyingLookControl;
import com.min01.beyondtheabyss.entity.ai.control.BTAFlyingMoveControl;
import com.min01.beyondtheabyss.entity.ai.goal.LookAtTargetGoal;
import com.min01.beyondtheabyss.entity.ai.goal.MoveToTargetGoal;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.EntityBounds;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.multipart.IMultipart;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBTAFlyingMonster extends AbstractAnimatableFlyingMonster implements IMultipart, IBTAMob
{
	public static final EntityDataAccessor<Vec3> LAST_LOOK_POS = SynchedEntityData.defineId(AbstractBTAFlyingMonster.class, BTAEntityDataSerializers.VEC3.get());
	
	public final EntityPartBuilder<? extends AbstractBTAFlyingMonster> partBuilder;
	
	public AbstractBTAFlyingMonster(EntityType<? extends Monster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.lookControl = new BTAFlyingLookControl(this, 10);
		this.moveControl = new BTAFlyingMoveControl(this);
		this.partBuilder = this.createBuilder();
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(LAST_LOOK_POS, Vec3.ZERO);
	}
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(0, new MoveToTargetGoal<>(this));
		this.goalSelector.addGoal(0, new LookAtTargetGoal<>(this));
        if(this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, false, false)
            {
            	@Override
            	protected AABB getTargetSearchArea(double pTargetDistance) 
            	{
            		return AbstractBTAFlyingMonster.this.getTargetSearchArea(pTargetDistance);
            	}
            });
        }
        if(this.getBTAMobType() == BTAMobType.NETURAL || this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        }
	}
	
	public AABB getTargetSearchArea(double pTargetDistance)
	{
		return this.getBoundingBox().inflate(pTargetDistance, 4.0D, pTargetDistance);
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful()
	{
		return this.getBTAMobType().despawnInPeaceful;
	}
	
	@Override
	public boolean removeWhenFarAway(double pDistanceToClosestPlayer) 
	{
		return this.getBTAMobType().removeWhenFarAway;
	}
	
	@Override
	public boolean isPreventingPlayerRest(Player pPlayer) 
	{
		return this.getBTAMobType().alwaysHostile;
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
	public EntityPartBuilder<?> getPartBuilder() 
	{
		return this.partBuilder;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
	}
	
	@Override
	public void lookAtTarget() 
	{
		if(this.getLastLookPos().equals(Vec3.ZERO))
		{
			super.lookAtTarget();
		}
		else if(this.canMove())
		{
			Vec3 pos = this.getLastLookPos();
			this.getLookControl().setLookAt(pos.x, pos.y, pos.z, 30.0F, 30.0F);
		}
	}
	
	public abstract EntityPartBuilder<? extends AbstractBTAFlyingMonster> createBuilder();
	
	public abstract BTAMobType getBTAMobType();
	
    public void setLastLookPos(Vec3 value)
    {
        this.entityData.set(LAST_LOOK_POS, value);
    }
    
    public Vec3 getLastLookPos()
    {
        return this.entityData.get(LAST_LOOK_POS);
    }
}
