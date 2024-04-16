package com.min01.beyondtheabyss.entity.goal.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob.BTASkills;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGhidruth;
import com.min01.beyondtheabyss.entity.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
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
		this.mob.playSound(BTASounds.GHIDRUTH_EYEFLASH.get());
	}
	
	@Override
	public boolean additionalStartCondition()
	{
		return this.mob.getAttackCount() >= 5 && this.mob.distanceTo(this.mob.getTarget()) >= 20 && !this.mob.isDash() && !this.mob.isStun();
	}

	@Override
	protected void performSkill() 
	{
		Vec3 lookPos = BTAUtil.getLookPos(this.mob.getXRot(), this.mob.getYHeadRot(), 0, 15);
		Vec3 pos = this.mob.getTarget().position().add(lookPos);
		HitResult hitResult = this.mob.level.clip(new ClipContext(this.mob.getTarget().position(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob));
		this.mob.setDashPos(hitResult.getLocation());
		this.mob.setDash(true);
		this.mob.setCanMove(true);
		this.mob.setAnimationState(4);
		this.mob.playSound(BTASounds.GHIDRUTH_CHARGE_START.get());
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
