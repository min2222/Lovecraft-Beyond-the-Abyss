package com.min01.beyondtheabyss.misc;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.world.phys.Vec3;

//https://github.com/TheCymaera/minecraft-boids/tree/master
public class Boid
{
	public Bounds bounds;
	public Vec3 position;
	public Vec3 velocity = Vec3.ZERO;

	// this should be equal to velocity unless velocity is zero.
	// this will give us a non-zero vector for rendering.
	public Vec3 direction = new Vec3(1, 0, 0);

	public Boid(Vec3 position, Bounds bounds)
	{
		this.position = position;
		this.bounds = bounds;
		this.velocity = new Vec3(Math.random(), Math.random(), Math.random());
	}

	public void update(Collection<Boid> boids, Collection<Boid.Obstacle> obstacles, boolean avoidance, boolean alignment, boolean cohesion, float flockRadius, float maxVelocity) 
	{
		Collection<Boid> flock = this.getInRange(boids, this.position, flockRadius);
		
		Vec3 acceleration = Vec3.ZERO;
		
		acceleration = acceleration.add(this.stayInBounds());

		if(avoidance)
		{
			Collection<Obstacle> flockObstacles = new ArrayList<Obstacle>();
			for(Boid boid : flock)
			{
				flockObstacles.add(new Boid.Obstacle(boid.position, 0.5, 0.05));
			}

			acceleration = acceleration.add(this.awayFrom(obstacles));
			acceleration = acceleration.add(this.awayFrom(flockObstacles));
		}
		
		if(alignment) 
		{
			acceleration = acceleration.add(this.averageVelocity(flock).scale(0.06));
		}
		
		if(cohesion) 
		{
			acceleration = acceleration.add(this.centerDisplacement(flock).scale(0.006));
		}
		
		this.velocity = this.velocity.add(acceleration);

		if(!this.velocity.equals(Vec3.ZERO))
		{
			if(this.velocity.length() > maxVelocity)
			{
				this.velocity = this.velocity.normalize().scale(maxVelocity);
			}
			this.direction = this.velocity;
		}
		this.position = this.position.add(this.velocity);
	}

	private Vec3 stayInBounds() 
	{
		Vec3 acceleration = Vec3.ZERO;
		double magnitude = 0.025;
	    if(this.position.x < this.bounds.minX())
	    {
	        acceleration = new Vec3(magnitude, acceleration.y, acceleration.z);
	    }
	    if(this.position.x > this.bounds.maxX())
	    {
	        acceleration = new Vec3(-magnitude, acceleration.y, acceleration.z);
	    }
	    if(this.position.y < this.bounds.minY())
	    {
	        acceleration = new Vec3(acceleration.x, magnitude, acceleration.z);
	    }
	    if(this.position.y > this.bounds.maxY())
	    {
	        acceleration = new Vec3(acceleration.x, -magnitude, acceleration.z);
	    }
	    if(this.position.z < this.bounds.minZ()) 
	    {
	        acceleration = new Vec3(acceleration.x, acceleration.y, magnitude);
	    }
	    if(this.position.z > this.bounds.maxZ()) 
	    {
	        acceleration = new Vec3(acceleration.x, acceleration.y, -magnitude);
	    }
		return acceleration;
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
			center = center.add(other.position);
		}
		center = center.scale(1.0 / flock.size());
		return center.subtract(this.position);
	}

	private Vec3 awayFrom(Collection<Obstacle> obstacles) 
	{
		Vec3 acc = Vec3.ZERO;
		for(Obstacle obstacle : obstacles) 
		{
			double distance = this.position.distanceTo(obstacle.position);
			if(distance > obstacle.avoidRadius)
			{
				continue;
			}

			Vec3 diff = this.position.subtract(obstacle.position);
			acc = acc.add(diff.scale(obstacle.avoidFactor));
		}
		return acc;
	}

	private Collection<Boid> getInRange(Collection<? extends Boid> boids, Vec3 location, double radius)
	{
		Collection<Boid> out = new ArrayList<Boid>();
		for(Boid other : boids) 
		{
			double distance = this.position.distanceTo(other.position);
			if(distance < radius)
			{
				out.add(other);
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
	
	public static class Bounds 
	{
		public Vec3 origin;
		public Vec3 size;
		
		public Bounds(Vec3 pos, Vec3 size)
		{
			this.origin = pos;
			this.size = size;
		}
		
		public static Bounds fromCenter(Vec3 pos, Vec3 size)
		{
			return new Bounds(pos.add(size.scale(-0.5)), size);
		}

		public double minX()
		{
			return this.origin.x;
		}

		public double minY() 
		{
			return this.origin.y;
		}

		public double minZ()
		{
			return this.origin.z;
		}

		public double maxX()
		{
			return this.origin.x + this.size.x;
		}

		public double maxY() 
		{
			return this.origin.y + this.size.y;
		}

		public double maxZ()
		{
			return this.origin.z + this.size.z;
		}

		public boolean contains(Vec3 loc)
		{
			if(loc.x <  this.minX() || this.maxX() < loc.x)
			{
				 return false;
			}
			if(loc.y <  this.minY() || this.maxY() < loc.y)
			{
				 return false;
			}
			if(loc.z <  this.minZ() || this.maxZ() < loc.z)
			{
				 return false;
			}
			return true;
		}
	}
}
