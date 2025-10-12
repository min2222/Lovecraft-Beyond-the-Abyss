package com.min01.beyondtheabyss.entity.ai.control;

import com.min01.beyondtheabyss.misc.Boid;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public abstract class BoidMoveControl extends MoveControl
{
	public boolean forceTarget;
	public Vec3 targetPos = Vec3.ZERO;
	public final Boid boid;
	
	public BoidMoveControl(Mob mob) 
	{
		super(mob);
		this.boid = new Boid(mob);
	}
	
	public abstract void generateNewTarget();
	
    public void setTargetPos(Vec3 pos)
    {
    	this.targetPos = pos;
    }
    
    public void setForceTarget(Vec3 pos)
    {
    	this.targetPos = pos;
    	this.forceTarget = true;
    }
    
    public void setForceTarget(boolean forceTarget)
    {
    	this.forceTarget = forceTarget;
    	if(!forceTarget)
    	{
    		this.generateNewTarget();
    	}
    }
    
    public Vec3 getTargetPos()
    {
    	return this.targetPos;
    }
}
