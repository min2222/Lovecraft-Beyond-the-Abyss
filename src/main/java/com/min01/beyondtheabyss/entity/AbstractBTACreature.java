package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.entity.ai.goal.LookAtTargetGoal;
import com.min01.beyondtheabyss.entity.ai.goal.MoveToTargetGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.min01.solomonlib.multipart.IMultipart;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public abstract class AbstractBTACreature extends AbstractAnimatableCreature implements IMultipart
{
	public final EntityPartBuilder partBuilder = new EntityPartBuilder();
	
	public AbstractBTACreature(EntityType<? extends AbstractAnimatableCreature> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.noCulling = this.getBTAMobType() == BTAMobType.BOSS;
	}
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(0, new MoveToTargetGoal<>(this));
		this.goalSelector.addGoal(0, new LookAtTargetGoal<>(this));
        if(this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false, false)
            {
            	@Override
            	protected AABB getTargetSearchArea(double pTargetDistance) 
            	{
            		return AbstractBTACreature.this.getTargetSearchArea(pTargetDistance);
            	}
            });
        }
        if(this.getBTAMobType() == BTAMobType.NETURAL || this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(0, this.alertOthers() ? new HurtByTargetGoal(this).setAlertOthers() : new HurtByTargetGoal(this));
        }
	}
	
	public AABB getTargetSearchArea(double pTargetDistance)
	{
		return this.getBoundingBox().inflate(pTargetDistance, 4.0D, pTargetDistance);
	}
	
	public boolean alertOthers()
	{
		return false;
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
	public boolean shouldRenderAtSqrDistance(double pDistance)
	{
		return super.shouldRenderAtSqrDistance(pDistance) || this.noCulling;
	}
	
	@Override
	public EntityPartBuilder getPartBuilder() 
	{
		return this.partBuilder;
	}
	
	public abstract BTAMobType getBTAMobType();
}
