package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepVampire;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DeepVampireBiteGoal extends BasicBTASkillGoal<EntityDeepVampire>
{
	public DeepVampireBiteGoal(EntityDeepVampire mob) 
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
		return BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 3);
	}

	@Override
	protected void performSkill()
	{
		if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 3))
		{
			this.mob.getTarget().hurt(DamageSource.mobAttack(this.mob), (float) this.mob.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
		}
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
		return 30;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 40;
	}
}
