package com.min01.beyondtheabyss.entity.mirroredcity;

import com.min01.beyondtheabyss.entity.AbstractBTAFlyingMonster;
import com.min01.beyondtheabyss.entity.ai.goal.mirroredcity.OverseerMissileGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EntityOverseer extends AbstractBTAFlyingMonster
{
	public final SmoothAnimationState openAnimationState = new SmoothAnimationState();
	
	public EntityOverseer(EntityType<? extends AbstractBTAFlyingMonster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(30);
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 150.0F)
    			.add(Attributes.ARMOR, 10.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
    			.add(Attributes.FLYING_SPEED, 0.8F)
        		.add(Attributes.ATTACK_DAMAGE, 15.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 100.0F)
        		.add(Attributes.FOLLOW_RANGE, 150.0F);
    }
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
    	this.goalSelector.addGoal(0, new OverseerMissileGoal(this));
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAFlyingMonster> createBuilder()
	{
    	EntityPartBuilder<EntityOverseer> partBuilder = new EntityPartBuilder<EntityOverseer>(this);
    	return partBuilder;
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
		if(this.level.isClientSide)
		{
			this.openAnimationState.updateWhen(this.isAnimationPlaying(1), this.tickCount);
		}
	    
	    BlockPos groundPos = BTAUtil.getGroundPos(this.level, this.getX(), this.getY(), this.getZ()).above();
	    if(this.onGround() || this.blockPosition().distSqr(groundPos) <= 150.0F)
	    {
	    	this.addDeltaMovement(new Vec3(0.0F, 0.005F, 0.0F));
	    }
	}
	
	@Override
	public float getTargetRoll(Vec3 movement)
	{
		return (float) Math.toDegrees(Math.atan2(movement.x, movement.z)) * 0.1F;
	}
	
	@Override
	public float getRollAmount()
	{
		return 0.025F;
	}
	
	@Override
	public AABB getTargetSearchArea(double radius) 
	{
		return this.getBoundingBox().inflate(radius, 100, radius);
	}
	
	@Override
	public float getRelativeSpeed()
	{
		return 0.05F;
	}
	
	@Override
	public void moveToTarget()
	{
		Vec3 targetPos = this.getTarget().getEyePosition();
		Vec3 pos = new Vec3(targetPos.x, this.getY(), targetPos.z);
		this.getNavigation().moveTo(pos.x, pos.y, pos.z, 1.5F);
	}
	
	@Override
	public float maxFlyTurnX()
	{
		return 0;
	}
	
	@Override
	public float maxFlyTurnY()
	{
		return 2;
	}
	
	@Override
	public boolean ignoreExplosion() 
	{
		return true;
	}
	
	@Override
	public boolean removeWhenFarAway(double pDistanceToClosestPlayer)
	{
		return false;
	}
}
