package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

//https://github.com/Tomate0613/boids/blob/1.21/src/main/java/dev/doublekekse/boids/goals/LimitSpeedAndLookInVelocityDirectionGoal.java
public class LimitSpeedAndLookInVelocityDirectionGoal extends Goal
{
    private final Mob mob;
    private final float minSpeed;
    private final float maxSpeed;
    private int timeToFindNearbyEntities;
    private List<? extends Mob> nearbyMobs;

    public LimitSpeedAndLookInVelocityDirectionGoal(Mob mob, float minSpeed, float maxSpeed) 
    {
        this.mob = mob;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean canUse() 
    {
    	if(this.mob.isInWater())
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
        return false;
    }

    @Override
    public void tick() 
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
        this.mob.lookAt(EntityAnchorArgument.Anchor.EYES, this.mob.position().add(velocity.scale(10)));
    }
    
    public static List<? extends Mob> getNearbyEntitiesOfSameClass(Mob mob)
    {
        return mob.level.getEntitiesOfClass(mob.getClass(), mob.getBoundingBox().inflate(4.0F, 4.0F, 4.0F), t -> t != mob);
    }
}
