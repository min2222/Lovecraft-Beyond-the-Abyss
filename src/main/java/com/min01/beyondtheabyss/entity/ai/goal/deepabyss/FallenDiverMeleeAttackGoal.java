package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityFallenDiver;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class FallenDiverMeleeAttackGoal extends MeleeAttackGoal 
{
	private final EntityFallenDiver diver;
	private int raiseArmTicks;

	public FallenDiverMeleeAttackGoal(EntityFallenDiver diver, double speedModifier, boolean followingTargetEvenIfNotSeen)
	{
		super(diver, speedModifier, followingTargetEvenIfNotSeen);
		this.diver = diver;
	}

	@Override
	public void start() 
	{
		super.start();
		this.raiseArmTicks = 0;
	}

	@Override
	public void stop() 
	{
		super.stop();
		this.diver.setAggressive(false);
	}

	@Override
	public void tick()
	{
		super.tick();
		++this.raiseArmTicks;
		if(this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2)
		{
			this.diver.setAggressive(true);
		} 
		else
		{
			this.diver.setAggressive(false);
		}
	}
}
