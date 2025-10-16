package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.google.common.collect.Lists;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore.MutationType;
import com.min01.beyondtheabyss.entity.projectile.EntityMutavoreCyst;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.Util;

public class MutavoreLaunchMineGoal extends BasicBTASkillGoal<EntityMutavore>
{
	public MutavoreLaunchMineGoal(EntityMutavore mob) 
	{
		super(mob);
	}
	
	@Override
	public boolean stopMovingWhenStart()
	{
		return false;
	}
	
	@Override
	public boolean canUse()
	{
		List<Integer> cysts = Lists.newArrayList(0, 1, 2, 3);
		cysts.removeIf(t -> !this.mob.hasCyst(t));
		if(cysts.isEmpty())
		{
			this.mob.doMutation(MutationType.MUTATE3, false);
			this.mob.doMutation(MutationType.MUTATE4, false);
			return false;
		}
		return super.canUse() && (this.mob.isMutated(MutationType.MUTATE3) && this.mob.isMutated(MutationType.MUTATE4));
	}

	@Override
	protected void performSkill()
	{
		List<Integer> cysts = Lists.newArrayList(0, 1, 2, 3);
		cysts.removeIf(t -> !this.mob.hasCyst(t));
		int type = Util.getRandom(cysts, this.mob.level.random);
		if(this.mob.posArray[type] != null)
		{
			EntityMutavoreCyst cyst = new EntityMutavoreCyst(BTAEntities.MUTAVORE_CYST.get(), this.mob.level);
			cyst.setOwner(this.mob);
			cyst.setCystType(type);
			cyst.setPos(this.mob.posArray[type]);
			cyst.setDeltaMovement(BTAUtil.fromToVector(this.mob.position(), cyst.position(), 0.15F));
			this.mob.level.addFreshEntity(cyst);
			this.mob.removeCyst(type);
		}
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 1;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 1;
	}

	@Override
	protected int getSkillUsingInterval()
	{
		return 80;
	}
}
