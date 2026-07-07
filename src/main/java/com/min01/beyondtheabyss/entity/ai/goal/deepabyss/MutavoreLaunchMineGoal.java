package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.google.common.collect.Lists;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity.MutationType;
import com.min01.beyondtheabyss.entity.projectile.MutavoreCystEntity;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.Util;
import net.minecraft.world.phys.Vec3;

public class MutavoreLaunchMineGoal extends AbstractAnimationGoal<MutavoreEntity>
{
	public MutavoreLaunchMineGoal(MutavoreEntity mob) 
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
	public void performSkill()
	{
		List<Integer> cysts = Lists.newArrayList(0, 1, 2, 3);
		cysts.removeIf(t -> !this.mob.hasCyst(t));
		int type = Util.getRandom(cysts, this.mob.level.random);
		Vec3 pos = this.mob.modelPositions.getModelPos("mine");
		switch(type)
		{
			case 0:
				pos = this.mob.modelPositions.getModelPos("mine");
				break;
			case 1:
				pos = this.mob.modelPositions.getModelPos("mine2");
				break;
			case 2:
				pos = this.mob.modelPositions.getModelPos("mine3");
				break;
			case 3:
				pos = this.mob.modelPositions.getModelPos("mine4");
				break;
		}
		this.mob.playSound(BTASounds.MUTAVORE_CYST_SHOOT.get(), 10.0F, 1.0F);
		MutavoreCystEntity cyst = new MutavoreCystEntity(BTAEntities.MUTAVORE_CYST.get(), this.mob.level);
		cyst.setOwner(this.mob);
		cyst.setCystType(type);
		cyst.setPos(pos);
		cyst.setDeltaMovement(BTAUtil.getVelocityTowards(this.mob.position(), cyst.position(), 0.15F));
		this.mob.level.addFreshEntity(cyst);
		this.mob.removeCyst(type);
	}

	@Override
	public int getSkillUsingTime() 
	{
		return 1;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 1;
	}

	@Override
	public int getSkillUsingInterval()
	{
		return 80;
	}
}
