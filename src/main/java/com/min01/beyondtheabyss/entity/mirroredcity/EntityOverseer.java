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
	private float rollAngle = 0.0F;
	
	public final SmoothAnimationState openAnimationState = new SmoothAnimationState();
	
	public EntityOverseer(EntityType<? extends Monster> pEntityType, Level pLevel)
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
			this.openAnimationState.updateWhen(this.isUsingSkill(1), this.tickCount);
		}
		
	    Vec3 movement = this.getDeltaMovement();
	    float speed = (float) movement.length();
	    if(speed > 0.01F) 
	    {
	        float targetRoll = (float) Math.toDegrees(Math.atan2(movement.x, movement.z)) * 0.1F;
	        this.rollAngle += (targetRoll - this.rollAngle) * 0.025F;
	    }
	    else
	    {
	        this.rollAngle *= 0.9F;
	    }
	    
	    BlockPos groundPos = BTAUtil.getGroundPos(this.level, this.getX(), this.getY(), this.getZ()).above();
	    if(this.onGround() || this.blockPosition().distSqr(groundPos) <= 150.0F)
	    {
	    	this.addDeltaMovement(new Vec3(0.0F, 0.005F, 0.0F));
	    }
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
		if(this.canMove())
		{
			Vec3 targetPos = this.getTarget().getEyePosition();
			Vec3 pos = new Vec3(targetPos.x, this.getY(), targetPos.z);
			this.getMoveControl().setWantedPosition(pos.x, pos.y, pos.z, 1.0F);
		}
	}
	
	@Override
	public void lookAtTarget() 
	{
		if(this.canLook())
		{
			this.getLookControl().setLookAt(this.getTarget(), 30.0F, 0.0F);
		}
	}
	
	@Override
	public int maxTurnX()
	{
		return 0;
	}
	
	@Override
	public int maxTurnY()
	{
		return 4;
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
	
	public float getRollAngle()
	{
		return this.rollAngle;
	}
}
