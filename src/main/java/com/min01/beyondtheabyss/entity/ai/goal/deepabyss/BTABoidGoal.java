package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

//https://github.com/Tomate0613/boids/tree/1.21
public class BTABoidGoal extends Goal 
{
    protected final Mob mob;
    private int timeToFindNearbyEntities;
    protected List<? extends Mob> nearbyMobs;
    public final float separationInfluence;
    public final float separationRange;
    private final float minSpeed;
    private final float maxSpeed;

    public BTABoidGoal(Mob mob, float separationInfluence, float separationRange, float minSpeed, float maxSpeed)
    {
        this.timeToFindNearbyEntities = 0;
        this.mob = mob;
        this.separationInfluence = separationInfluence;
        this.separationRange = separationRange;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean canUse() 
    {
        return this.mob.isInWater();
    }

    @Override
    public void tick()
    {
    	this.boid();
        this.stayInWater();
    }
    
    public boolean canBoid()
    {
        if(--this.timeToFindNearbyEntities <= 0)
        {
            this.timeToFindNearbyEntities = this.adjustedTickDelay(40);
            this.nearbyMobs = getNearbyEntitiesOfSameClass(this.mob);
        } 
        else
        {
        	this.nearbyMobs.removeIf(LivingEntity::isDeadOrDying);
        }
        return !this.nearbyMobs.isEmpty();
    }
    
    public void boid()
    {
    	if(!this.canBoid())
    		return;
        this.mob.addDeltaMovement(this.cohesion());
        this.mob.addDeltaMovement(this.alignment());
        this.mob.addDeltaMovement(this.separation());
        this.lookAt();
    }
    
    public void lookAt()
    {
        Vec3 velocity = this.mob.getDeltaMovement();
        double speed = velocity.length();
        if(speed < this.minSpeed)
        {
        	velocity = velocity.normalize().scale(this.minSpeed);
        }
        if(speed > this.maxSpeed)
        {
            velocity = velocity.normalize().scale(this.maxSpeed);
        }
        this.mob.setDeltaMovement(velocity);
        this.mob.lookAt(Anchor.EYES, this.mob.position().add(velocity.scale(10)));
    }
    
    public void stayInWater()
    {
		if(this.mob.isInWater())
		{
	        BlockPos blockPos = this.mob.blockPosition();
	        BlockState blockAbove = this.mob.level.getBlockState(blockPos.above(2));
	        BlockState blockBelow = this.mob.level.getBlockState(blockPos.below(1));
	        float amount = this.amount();
	        if(blockBelow.getFluidState().isEmpty()) 
	        {
	        	this.mob.addDeltaMovement(new Vec3(0, amount, 0));
	        }
	        if(blockAbove.getFluidState().isEmpty())
	        {
	        	this.mob.addDeltaMovement(new Vec3(0, -amount, 0));
	        }
		}
    }
    
    public float amount() 
    {
        float amount = 0.1F;
        float dY = Mth.abs((float) this.mob.getDeltaMovement().y);
        if(dY > amount) 
        {
            amount = dY;
        }
        return amount;
    }
    
    public Vec3 separation() 
    {
        Vec3 c = Vec3.ZERO;
        for(Mob nearbyMob : this.nearbyMobs)
        {
            if((nearbyMob.position().subtract(this.mob.position()).length()) < this.separationRange)
            {
            	c = c.subtract(nearbyMob.position().subtract(this.mob.position()));
            }
        }
        return c.scale(this.separationInfluence);
    }
    
    public Vec3 cohesion()
	{
        if(this.nearbyMobs.isEmpty()) 
        {
        	return Vec3.ZERO;
        }
        Vec3 c = Vec3.ZERO;
        for(Mob nearbyMob : this.nearbyMobs)
        {
            c = c.add(nearbyMob.position());
        }
        c = c.scale(1.0F / this.nearbyMobs.size());
        c = c.subtract(this.mob.position());
        return c.scale(1 / 20.0F);
	}
    
	public Vec3 alignment()
	{
        if(this.nearbyMobs.isEmpty()) 
        {
        	return Vec3.ZERO;
        }
        Vec3 c = Vec3.ZERO;
        for(Mob nearbyMob : this.nearbyMobs)
        {
            c = c.add(nearbyMob.getDeltaMovement());
        }
        c = c.scale(1.0F / this.nearbyMobs.size());
        c = c.subtract(this.mob.getDeltaMovement());
        return c.scale(8 / 20.0F);
	}

    public static List<? extends Mob> getNearbyEntitiesOfSameClass(Mob mob)
    {
        return mob.level.getEntitiesOfClass(mob.getClass(), mob.getBoundingBox().inflate(4.0F), t -> t != mob && !t.isDeadOrDying());
    }
}
