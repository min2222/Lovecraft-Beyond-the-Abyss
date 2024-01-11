package com.min01.beyondtheabyss.entity.goals.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAEntity.BTASkills;
import com.min01.beyondtheabyss.entity.deepabyss.living.EntityGhidruth;
import com.min01.beyondtheabyss.entity.goals.BasicBTASkillGoal;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class GhidruthDashPrepareGoal extends BasicBTASkillGoal<EntityGhidruth>
{
	public GhidruthDashPrepareGoal(EntityGhidruth mob) 
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.lookAt(Anchor.FEET, this.mob.getTarget().position());
		this.mob.setCanLookOrMove(false);
		this.mob.setAnimationState(3);
	}
	
	@Override
	public boolean additionalStartCondition()
	{
		return this.mob.getAttackCount() >= 5 && !this.mob.isDash();
	}

	@Override
	protected void performSkill() 
	{
		if(this.mob.head.distanceTo(this.mob.getTarget()) <= 10)
		{
			BlockPos headRot = this.mob.getHeadRotation();
			Vec3 headLookPos = BTAUtil.getLookPos(headRot.getX() + this.mob.getXRot(), headRot.getY() + this.mob.yHeadRot, 0, 25);
			this.mob.setDashPos(this.mob.getTarget().blockPosition().offset(headLookPos.x, headLookPos.y, headLookPos.z));
		}
		else
		{
			this.mob.setDashPos(this.mob.getTarget().blockPosition());
		}
		this.mob.setDash(true);
		this.mob.setCanMove(true);
		this.mob.setAnimationState(4);
		this.mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(2.5);
	}

	@Override
	protected int getSkillUsingTime()
	{
		return 20;
	}

	@Override
	protected int getSkillUsingInterval()
	{
		return 20;
	}

	@Override
	protected BTASkills getSkills() 
	{
		return BTASkills.GHIDRUTH_DASH_PREPARE;
	}
}
