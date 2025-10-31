package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityCorpseAngler;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.phys.Vec3;

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
		this.mob.setCanLook(true);
	}
	
	@Override
	public boolean stopMovingWhenStart() 
	{
		return false;
	}
	
	@Override
	public boolean canUse()
	{
		return super.canUse() && this.mob.canMove() && this.mob.distanceTo(this.mob.getTarget()) <= 8.0F;
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
			if(this.mob.getAnimationTick() <= this.getSkillUsingTime() - this.getSkillWarmupTime())
			{
				Vec3 lookPos = BTAUtil.getLookPos(this.mob.getRotationVector(), this.mob.position(), 0, 0, 20);
				this.mob.setLastLookPos(lookPos);
				this.mob.setDeltaMovement(BTAUtil.fromToVector(this.mob.position(), lookPos, 0.5F));
				if(BTAUtil.isWithinMeleeAttackRange(this.mob, this.mob.getTarget(), 1.5F))
				{
					this.canContinueToUse = false;
					this.mob.doHurtTarget(this.mob.getTarget());
				}
				else if(this.mob.distanceTo(this.mob.getTarget()) >= 12.0F)
				{
					this.canContinueToUse = false;
				}
			}
			else
			{
				this.mob.lookAt(Anchor.EYES, this.mob.getTarget().getEyePosition());
			}
		}
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.getNavigation().stop();
		this.mob.setDeltaMovement(Vec3.ZERO);
		this.mob.setAnimationState(2);
		this.mob.setAnimationTick(20);
		this.mob.setLastLookPos(Vec3.ZERO);
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
