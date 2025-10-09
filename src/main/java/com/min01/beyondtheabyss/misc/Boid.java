package com.min01.beyondtheabyss.misc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.min01.beyondtheabyss.entity.ai.control.BoidMoveControl;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

//https://github.com/SebLague/Boids/blob/master/Assets/Scripts/Boid.cs
public class Boid
{
	public final BoidSettings settings = new BoidSettings();

	public final List<Boid> boids = new ArrayList<>();
	public List<? extends Mob> nearbyMobs = new ArrayList<>();

    public Vec3 position = Vec3.ZERO;
    public Vec3 forward = new Vec3(0, 0, 1);
    public Vec3 velocity;

    public Vec3 acceleration = Vec3.ZERO;
    public Vec3 avgFlockHeading = Vec3.ZERO;
    public Vec3 avgAvoidanceHeading = Vec3.ZERO;
    public Vec3 centreOfFlockmates = Vec3.ZERO;
    public int numPerceivedFlockmates;
    
    public Vec3 target;
    
	public final Mob mob;
	
	public Boid(Mob mob)
	{
		this.mob = mob;
        float startSpeed = (this.settings.minSpeed + this.settings.maxSpeed) / 2;
        this.velocity = this.forward.scale(startSpeed);
	}
	
    public void tick() 
    {
    	this.tickNearbyMobs();
    	
    	this.compute(new ArrayList<>(this.boids), this.settings.perceptionRadius, this.settings.avoidanceRadius);
    	
        Vec3 acceleration = Vec3.ZERO;
        
        if(this.target != null) 
        {
            Vec3 offsetToTarget = this.target.subtract(this.position);
            acceleration = this.steerTowards(offsetToTarget).scale(this.settings.targetWeight);
        }

        if(this.numPerceivedFlockmates != 0)
        {
            this.centreOfFlockmates = this.centreOfFlockmates.scale(1.0F / this.numPerceivedFlockmates);

            Vec3 offsetToFlockmatesCentre = this.centreOfFlockmates.subtract(this.position);

            Vec3 alignmentForce = this.steerTowards(this.avgFlockHeading).scale(this.settings.alignWeight);
            Vec3 cohesionForce = this.steerTowards(offsetToFlockmatesCentre).scale(this.settings.cohesionWeight);
            Vec3 seperationForce = this.steerTowards(this.avgAvoidanceHeading).scale(this.settings.seperateWeight);

            acceleration = acceleration.add(alignmentForce);
            acceleration = acceleration.add(cohesionForce);
            acceleration = acceleration.add(seperationForce);
        }

        if(this.isHeadingForCollision()) 
        {
            Vec3 collisionAvoidDir = this.obstacleRays();
            Vec3 collisionAvoidForce = this.steerTowards(collisionAvoidDir).scale(this.settings.avoidCollisionWeight);
            acceleration = acceleration.add(collisionAvoidForce);
        }

        this.velocity = this.velocity.add(acceleration);
        double speed = this.velocity.length();
        Vec3 dir = this.velocity.scale(1.0F / speed);
        speed = Mth.clamp(speed, this.settings.minSpeed, this.settings.maxSpeed);
        this.velocity = dir.scale(speed);

        this.position = this.mob.position().add(this.velocity.scale(0.005F));
        this.forward = dir;
        
        this.mob.addDeltaMovement(this.velocity.scale(0.005F));
    }
    
    public void compute(List<Boid> boids, float viewRadius, float avoidRadius) 
    {
        int numBoids = boids.size();
        double viewSqr = viewRadius * viewRadius;
        double avoidSqr = avoidRadius * avoidRadius;
        for(int i = 0; i < numBoids; i++)
        {
            Boid boidA = boids.get(i);
            boidA.numPerceivedFlockmates = 0;
            boidA.avgFlockHeading = Vec3.ZERO;
            boidA.centreOfFlockmates = Vec3.ZERO;
            boidA.avgAvoidanceHeading = Vec3.ZERO;
            for(int j = 0; j < numBoids; j++)
            {
                if(i == j) 
                {
                	continue;
                }
                Boid boidB = boids.get(j);
                Vec3 offset = boidB.position.subtract(boidA.position);
                double sqrDst = offset.lengthSqr();
                if(sqrDst < viewSqr) 
                {
                    boidA.numPerceivedFlockmates += 1;
                    boidA.avgFlockHeading = boidA.avgFlockHeading.add(boidB.forward);
                    boidA.centreOfFlockmates = boidA.centreOfFlockmates.add(boidB.position);
                    if(sqrDst < avoidSqr && sqrDst > 0.0001) 
                    {
                        Vec3 separation = offset.scale(1.0 / sqrDst);
                        boidA.avgAvoidanceHeading = boidA.avgAvoidanceHeading.subtract(separation);
                    }
                }
            }
        }
    }
    
    public Vec3 steerTowards(Vec3 vector) 
    {
    	Vec3 v = vector.normalize().scale(this.settings.maxSpeed).subtract(this.velocity);
        if(v.length() > this.settings.maxSteerForce) 
        {
            v = v.normalize().scale(this.settings.maxSteerForce);
        }
        return v;
    }
    
    public boolean isHeadingForCollision()
    {
        Vec3 start = this.position;
        Vec3 end = start.add(this.forward.scale(this.settings.collisionAvoidDst));
        HitResult hit = this.mob.level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob));
        return hit.getType() != HitResult.Type.MISS;
    }
    
    public Vec3 obstacleRays()
    {
    	for(int i = 0; i < 360; i += 45)
    	{
    		for(int j = 0; j < 360; j += 45)
    		{
                Vec3 start = this.position;
                Vec3 dir = this.calculateViewVector(this.mob.getXRot() + j, this.mob.getYRot() + i).normalize();
                Vec3 end = this.position.add(dir.scale(this.settings.collisionAvoidDst));
                HitResult hit = this.mob.level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob));
                if(hit.getType() == HitResult.Type.MISS)
                {
                	return dir;
                }
    		}
    	}
        return this.forward;
    }
    
    public Vec3 calculateViewVector(float xRot, float yRot)
    {
    	float f = xRot * ((float)Math.PI / 180.0F);
    	float f1 = -yRot * ((float)Math.PI / 180.0F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }
    
    public void tickNearbyMobs()
    {
    	if(this.mob.tickCount % 60 == 0 || this.nearbyMobs.isEmpty())
    	{
        	this.nearbyMobs = this.mob.level.getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(6.0F), t -> !t.isDeadOrDying());
        	this.nearbyMobs.sort(Comparator.comparing(Entity::getUUID));
    	}
    	
        this.nearbyMobs.removeIf(t -> t.isDeadOrDying());
        
        for(Mob mob : this.nearbyMobs)
        {
        	Boid boid = ((BoidMoveControl)mob.getMoveControl()).boid;
        	if(!this.boids.contains(boid))
        	{
	        	this.boids.add(boid);
        	}
        }
        
        if(!this.nearbyMobs.isEmpty())
        {
        	Vec3 targetPos = ((BoidMoveControl)this.nearbyMobs.get(0).getMoveControl()).targetPos;
        	if(!targetPos.equals(Vec3.ZERO))
        	{
        		this.target = targetPos;
        	}
        }
    }
    
    public static class BoidSettings
    {
        public float minSpeed = 2;
        public float maxSpeed = 5;
        public float perceptionRadius = 2.5F;
        public float avoidanceRadius = 1;
        public float maxSteerForce = 3;

        public float alignWeight = 1;
        public float cohesionWeight = 1;
        public float seperateWeight = 1;

        public float targetWeight = 1;

        public float boundsRadius = 0.27F;
        public float avoidCollisionWeight = 1;
        public float collisionAvoidDst = 5;
    }
}