package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class GnasherBoidGoal extends BTABoidGoal
{
	public GnasherBoidGoal(Mob mob, float separationInfluence, float separationRange) 
	{
		super(mob, separationInfluence, separationRange);
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
            this.mob.addDeltaMovement(this.cohesion());
            this.mob.addDeltaMovement(this.alignment());
    	}
        this.mob.addDeltaMovement(this.separation());
    }
	
	@Override
    public Vec3 cohesion() 
    {
        if(this.nearbyMobs.isEmpty()) 
        {
        	return Vec3.ZERO;
        }
        Vec3 c = Vec3.ZERO;
        for(Mob nearbyMob : this.nearbyMobs)
        {
        	if(nearbyMob instanceof EntityGnasher leader && this.mob instanceof EntityGnasher gnasher)
        	{
        		if(leader.isLeader() && !gnasher.isLeader())
        		{
            		gnasher.setLeader(leader);
        		}
        	}
            c = c.add(nearbyMob.position());
        }
        c = c.scale(1.0F / this.nearbyMobs.size());
        c = c.subtract(this.mob.position());
        return c.scale(1 / 20.0F);
    }
}
