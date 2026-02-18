package com.min01.beyondtheabyss.entity.ai.goal.mirroredcity;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.mirroredcity.EntityOverseer;
import com.min01.beyondtheabyss.entity.projectile.EntityMissile;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.phys.Vec3;

public class OverseerMissileGoal extends AbstractAnimationGoal<EntityOverseer>
{
	public OverseerMissileGoal(EntityOverseer mob) 
	{
		super(mob);
	}
	
	@Override
	public void start()
	{
		super.start();
		this.mob.setAnimationState(1);
	}
	
	@Override
	public boolean stopMovingWhenStart()
	{
		return false;
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && BTAUtil.distanceToXZ(this.mob, this.mob.getTarget()) <= 12.0F;
	}

	@Override
	public void performSkill()
	{
		EntityMissile missile = new EntityMissile(this.mob.level, this.mob);
		Vec3 lookPos = BTAUtil.getLookPos(this.mob.getRotationVector(), this.mob.position(), 0, 0, -2.5F);
		missile.setPos(lookPos);
		this.mob.level.addFreshEntity(missile);
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setAnimationState(0);
	}

	@Override
	public int getSkillUsingTime()
	{
		return 20;
	}
	
	@Override
	public int getSkillWarmupTime()
	{
		return 1;
	}

	@Override
	public int getSkillUsingInterval() 
	{
		return 40;
	}
}
