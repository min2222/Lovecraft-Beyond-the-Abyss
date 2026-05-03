package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.NecroshellEntity;

import net.minecraft.world.entity.player.Player;

public class NecroshellHidingGoal extends AbstractAnimationGoal<NecroshellEntity>
{
	private int noTargetTick;
	
	public NecroshellHidingGoal(NecroshellEntity mob) 
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setHiding(true);
	}
	
	@Override
	public boolean canUse()
	{
		if(this.mob.getTarget() == null && this.mob.getLastHurtByMob() == null)
		{
			this.noTargetTick++;
		}
		return !this.mob.isAnimationPlaying() && this.noTargetTick >= 100;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		Player player = this.mob.level.getNearestPlayer(this.mob.getX(), this.mob.getY(), this.mob.getZ(), 3.5F, true);
		if((this.mob.getTarget() != null && this.mob.getTarget().distanceTo(this.mob) <= 4.5F) || this.mob.getLastHurtByMob() != null || player != null)
		{
			this.noTargetTick = 0;
		}
	}
	
	@Override
	public boolean canContinueToUse() 
	{
		return this.noTargetTick >= 100;
	}
	
	@Override
	public void stop()
	{
		super.stop();
		this.mob.setHiding(false);
		this.mob.setAnimationState(2);
		this.mob.setAnimationTick(15);
	}

	@Override
	public int getSkillUsingTime() 
	{
		return 15;
	}

	@Override
	public int getSkillUsingInterval() 
	{
		return 100;
	}
}
