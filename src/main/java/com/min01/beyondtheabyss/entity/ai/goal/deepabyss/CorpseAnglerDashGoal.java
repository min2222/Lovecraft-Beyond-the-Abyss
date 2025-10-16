package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;

public class CorpseAnglerDashGoal extends BasicBTASkillGoal<EntityCorpseAngler>
{
	public boolean canContinueToUse = true;
	
	public CorpseAnglerDashGoal(EntityCorpseAngler mob) 
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
		return super.canUse() && this.mob.getBurrowCooldown() > 0 && this.mob.distanceTo(this.mob.getTarget()) <= 8.0F;
	}
	
	@Override
	public boolean canContinueToUse()
	{
		return this.canContinueToUse;
	}

	@Override
	protected void performSkill()
	{
		
	}
	
	@Override
	public boolean requiresUpdateEveryTick() 
	{
		return true;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.mob.getTarget() != null)
		{
			this.mob.lookAt(Anchor.EYES, this.mob.getTarget().getEyePosition());
			if(this.mob.getAnimationTick() <= this.getSkillUsingTime() - this.getSkillWarmupTime())
			{
				this.mob.getNavigation().moveTo(this.mob.getTarget(), 1.5F);
				this.canContinueToUse = this.mob.distanceTo(this.mob.getTarget()) >= 4.0F;
				if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 1.5F))
				{
					this.mob.doHurtTarget(this.mob.getTarget());
				}
			}
		}
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(2);
		this.mob.setAnimationTick(20);
		this.mob.setUsingSkill(true);
		this.canContinueToUse = true;
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 120;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 60;
	}

	@Override
	protected int getSkillUsingInterval()
	{
		return 110;
	}
}
