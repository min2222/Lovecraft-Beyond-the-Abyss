package com.min01.beyondtheabyss.entity.ai.control;

import com.min01.beyondtheabyss.misc.Boid;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;

public abstract class BoidMoveControl extends MoveControl
{
	public final Boid boid;
	
	public BoidMoveControl(Mob mob)
	{
		this(mob, false);
	}
	
	public BoidMoveControl(Mob mob, boolean isLeader) 
	{
		super(mob);
		this.boid = new Boid(mob, isLeader);
	}
}
