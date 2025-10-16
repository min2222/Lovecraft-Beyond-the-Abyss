package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.phys.Vec3;

//FIXME
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
		this.mob.lookAt(Anchor.EYES, this.mob.getTarget().getEyePosition());
	}
	
	@Override
	public boolean canUse()
	{
		return super.canUse() && this.mob.getBurrowCooldown() > 0;
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
	public void tick() 
	{
		super.tick();
		if(this.mob.getTarget() != null)
		{
			if(this.mob.getAnimationTick() <= this.getSkillUsingTime() - this.getSkillWarmupTime())
			{
				this.mob.getNavigation().moveTo(this.mob.getTarget(), 1.5F);
			}
			this.canContinueToUse = this.mob.distanceTo(this.mob.getTarget()) >= 4.0F;
		}
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(2);
		this.mob.setAnimationTick(20);
		this.mob.setUsingSkill(true);
		this.mob.setLastLookPos(Vec3.ZERO);
		this.canContinueToUse = true;
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 60;
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
