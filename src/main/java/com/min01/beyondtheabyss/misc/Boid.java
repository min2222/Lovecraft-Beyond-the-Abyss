package com.min01.beyondtheabyss.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.min01.beyondtheabyss.entity.ILeader;
import com.min01.beyondtheabyss.entity.ai.control.BoidMoveControl;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

//https://github.com/TheCymaera/minecraft-boids/tree/master
public class Boid
{
	public final Mob mob;
	public final boolean isLeader;
	public final List<Boid> boids = new ArrayList<>();
	public List<? extends Mob> nearbyMobs = new ArrayList<>();
	
	public Vec3 velocity = Vec3.ZERO;
    public Vec3 target = Vec3.ZERO;

	public Boid(Mob mob, boolean isLeader)
	{
		this.mob = mob;
		this.isLeader = isLeader;
		this.velocity = new Vec3(Math.random(), Math.random(), Math.random());
	}

	public void update(Collection<Boid.Obstacle> obstacles, boolean avoidance, boolean alignment, boolean cohesion, float flockRadius, float maxVelocity) 
	{
		this.tickNearbyMobs();
		
		Collection<Boid> boids = this.boids;
		
		Collection<Boid> flock = this.getInRange(boids, this.mob.position(), flockRadius);
		
		Vec3 acceleration = Vec3.ZERO;
		
		if(!this.target.equals(Vec3.ZERO))
		{
			Vec3 toTarget = this.target.subtract(this.mob.position());
			acceleration = acceleration.add(toTarget.scale(0.05F));
		}

		if(avoidance)
		{
			Collection<Obstacle> flockObstacles = new ArrayList<Obstacle>();
			for(Boid boid : flock)
			{
				flockObstacles.add(new Boid.Obstacle(boid.mob.position(), 0.5F, 0.05F));
			}

			acceleration = acceleration.add(this.awayFrom(obstacles));
			acceleration = acceleration.add(this.awayFrom(flockObstacles));
		}
		
		if(alignment) 
		{
			acceleration = acceleration.add(this.averageVelocity(flock).scale(0.06F));
		}
		
		if(cohesion) 
		{
			acceleration = acceleration.add(this.centerDisplacement(flock).scale(0.006F));
		}
		
		this.velocity = this.velocity.add(acceleration);

		if(!this.velocity.equals(Vec3.ZERO))
		{
			if(this.velocity.length() > maxVelocity)
			{
				this.velocity = this.velocity.normalize().scale(maxVelocity);
			}
		}
		
		this.mob.addDeltaMovement(this.velocity.scale(0.05F));
	}
	
    @SuppressWarnings("unchecked")
	public void tickNearbyMobs()
    {
    	if(this.mob.tickCount % 60 == 0 || this.nearbyMobs.isEmpty())
    	{
        	this.nearbyMobs = this.mob.level.getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(10.0F), t -> !t.isDeadOrDying());
        	this.nearbyMobs.sort(Comparator.comparing(Entity::getUUID));
    	}
    	
        this.nearbyMobs.removeIf(t -> t.isDeadOrDying());
        
        for(Mob mob : this.nearbyMobs)
        {
        	if(mob.getMoveControl() instanceof BoidMoveControl control)
        	{
            	Boid boid = control.boid;
            	if(!this.boids.contains(boid))
            	{
    	        	this.boids.add(boid);
            	}
        	}
        	if(this.isLeader)
        	{
        		if(!(mob instanceof ILeader<?> leader) || !leader.isLeader())
        		{
        			continue;
        		}
        		ILeader<Mob> mob1 = (ILeader<Mob>) this.mob;
        		if(mob1.getLeader() == null && !mob1.isLeader())
        		{
            		mob1.setLeader(mob);
        		}
        	}
        }
        
        if(!this.nearbyMobs.isEmpty())
        {
        	Mob mob = this.nearbyMobs.get(0);
        	if(this.isLeader)
        	{
        		Optional<? extends Mob> optional = this.nearbyMobs.stream().filter(t -> t instanceof ILeader<?> leader && leader.isLeader()).findFirst();
        		if(optional.isPresent())
        		{
        			mob = optional.get();
        		}
        	}
        	MoveControl moveControl = mob.getMoveControl();
        	Vec3 wantedPos = new Vec3(moveControl.getWantedX(), moveControl.getWantedY(), moveControl.getWantedZ());
        	if(!wantedPos.equals(Vec3.ZERO))
        	{
            	this.target = wantedPos;
        	}
        }
    }

	private Vec3 averageVelocity(Collection<Boid> flock)
	{
		Vec3 avg = Vec3.ZERO;
		if(flock.size() == 0)
		{
			return Vec3.ZERO;
		}
		for(Boid other : flock)
		{
			avg = avg.add(other.velocity);
		}
		avg = avg.scale(1.0 / flock.size());
		return avg;
	}

	private Vec3 centerDisplacement(Collection<Boid> flock)
	{
		Vec3 center = Vec3.ZERO;
		if(flock.size() == 0)
		{
			return Vec3.ZERO;
		}
		for(Boid other : flock)
		{
			center = center.add(other.mob.position());
		}
		center = center.scale(1.0 / flock.size());
		return center.subtract(this.mob.position());
	}

	private Vec3 awayFrom(Collection<Obstacle> obstacles) 
	{
		Vec3 acc = Vec3.ZERO;
		for(Obstacle obstacle : obstacles) 
		{
			double distance = this.mob.position().distanceTo(obstacle.position);
			if(distance > obstacle.avoidRadius)
			{
				continue;
			}

			Vec3 diff = this.mob.position().subtract(obstacle.position);
			acc = acc.add(diff.scale(obstacle.avoidFactor));
		}
		return acc;
	}

	private Collection<Boid> getInRange(Collection<? extends Boid> boids, Vec3 location, double radius)
	{
		Collection<Boid> out = new ArrayList<Boid>();
		for(Boid other : boids) 
		{
			double distance = this.mob.position().distanceTo(other.mob.position());
			if(distance < radius)
			{
				out.add(other);
			}
			else
			{
				continue;
			}
		}
		return out;
	}

	public static class Obstacle 
	{
		public final Vec3 position;
		public final double avoidRadius;
		public final double avoidFactor;
		public Obstacle(Vec3 position, double avoidRadius, double avoidFactor)
		{
			this.position = position;
			this.avoidRadius = avoidRadius;
			this.avoidFactor = avoidFactor;
		}
	}
}