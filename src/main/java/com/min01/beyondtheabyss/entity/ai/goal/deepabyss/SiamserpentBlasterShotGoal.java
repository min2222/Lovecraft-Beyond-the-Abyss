package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.entity.projectile.EntityEnergyBall;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class SiamserpentBlasterShotGoal extends AbstractSiamserpentSkillGoal
{
	public SiamserpentBlasterShotGoal(EntitySiamserpentHead mob) 
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setAnimationState(6);
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.getHeadType() == HeadType.BLASTER && this.isOtherHeadDormant();
	}

	@Override
	protected void performSkill() 
	{
		EntityEnergyBall ball = new EntityEnergyBall(this.mob.level, this.mob);
    	Vec3 startPos = BTAUtil.getLookPos(new Vec2(this.mob.getXRot(), this.mob.getYHeadRot()), this.mob.getEyePosition(), 0.0F, -0.25F, 0.5F);
		ball.setPos(startPos);
		ball.shootFromRotation(this.mob, this.mob.getXRot(), this.mob.getYHeadRot(), 0.0F, 1.0F, 1.0F);
		this.mob.level.addFreshEntity(ball);
		this.skillWarmupDelay = 3;
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(0);
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 35;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 10;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 100;
	}
}
