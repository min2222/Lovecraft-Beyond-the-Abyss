package com.min01.beyondtheabyss.entity.goals.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractAbyssEntity.AbyssSkills;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.goals.BasicAbyssSkillGoal;
import com.min01.beyondtheabyss.util.AbyssUtil;

import net.minecraft.world.damagesource.DamageSource;

public class GhidruthBiteGoal extends BasicAbyssSkillGoal<EntityGhidruth>
{
	public GhidruthBiteGoal(EntityGhidruth mob) 
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
	public boolean additionalStartCondition()
	{
		return AbyssUtil.isWithinMeleeAttackRangeOfPart(this.mob.head, this.mob.getTarget(), 3);
	}

	@Override
	protected void performSkill() 
	{
		this.mob.getTarget().hurt(DamageSource.mobAttack(this.mob), 5);
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 25;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 40;
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(0);
	}

	@Override
	protected AbyssSkills getSkills() 
	{
		return AbyssSkills.GHIDRUTH_BITE;
	}
}
