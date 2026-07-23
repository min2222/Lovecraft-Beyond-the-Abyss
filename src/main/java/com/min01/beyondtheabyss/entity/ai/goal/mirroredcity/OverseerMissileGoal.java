package com.min01.beyondtheabyss.entity.ai.goal.mirroredcity;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.mirroredcity.OverseerEntity;
import com.min01.beyondtheabyss.entity.projectile.MissileEntity;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.phys.Vec3;

public class OverseerMissileGoal extends AbstractAnimationGoal<OverseerEntity>
{
	public OverseerMissileGoal(OverseerEntity mob) 
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
	public boolean stopOnStart()
	{
		return false;
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && BTAUtil.distanceToXZ(this.mob, this.mob.getTarget()) <= 12.0F;
	}

	@Override
	public void run()
	{
		MissileEntity missile = new MissileEntity(this.mob.level, this.mob);
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
	public int getDuration()
	{
		return 20;
	}
	
	@Override
	public int getDelay()
	{
		return 1;
	}

	@Override
	public int getInterval() 
	{
		return 40;
	}
}
