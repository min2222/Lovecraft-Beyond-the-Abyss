package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class GnasherBoidGoal extends BTABoidGoal
{
	public GnasherBoidGoal(Mob mob, float separationInfluence, float separationRange, float minSpeed, float maxSpeed) 
	{
		super(mob, separationInfluence, separationRange, minSpeed, maxSpeed);
	}

	@Override
	public boolean canUse() 
	{
		return super.canUse() && !((EntityGnasher) this.mob).isDisperse();
	}
	
	@Override
    public void boid()
    {
    	if(!this.canBoid())
    		return;
    	if(this.mob.getTarget() == null)
    	{
            this.mob.addDeltaMovement(this.cohesion().scale(0.006));
            this.mob.addDeltaMovement(this.alignment().scale(0.06));
            this.lookAt();
    	}
        this.mob.addDeltaMovement(this.separation());
    }
	
	@Override
    public Vec3 cohesion() 
    {
		Vec3 center = Vec3.ZERO;
		if(this.nearbyMobs.isEmpty())
		{
			return Vec3.ZERO;
		}
		for(Mob other : this.nearbyMobs)
		{
        	if(other instanceof EntityGnasher leader && this.mob instanceof EntityGnasher gnasher)
        	{
        		if(leader.isLeader())
        		{
            		gnasher.setLeader(leader);
        		}
        	}
			center = center.add(other.position());
		}
		center = center.scale(1.0 / this.nearbyMobs.size());
		return center.subtract(this.mob.position());
    }
}
