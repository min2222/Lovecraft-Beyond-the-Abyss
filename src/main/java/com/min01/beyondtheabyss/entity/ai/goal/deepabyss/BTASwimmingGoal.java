package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.IDeepAbyssMob;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BTASwimmingGoal extends Goal 
{
    private final Mob mob;
    private final double speed;
    private double targetX, targetY, targetZ;
    private Vec3 prevTarget = Vec3.ZERO;

    public BTASwimmingGoal(Mob mob, double speed) 
    {
        this.mob = mob;
        this.speed = speed;
    }

    @Override
    public boolean canUse() 
    {
    	this.generateNewTarget();
        return ((IDeepAbyssMob) this.mob).canSwim();
    }
    
    @Override
    public boolean canContinueToUse() 
    {
    	return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start()
    {
        this.mob.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, this.speed);
    }
    
    @Override
    public void stop() 
    {
        this.mob.getNavigation().stop();
    }

    private void generateNewTarget() 
    {
        Level world = this.mob.level;
        BlockPos currentPos = this.mob.blockPosition();
        int radius = ((IDeepAbyssMob) this.mob).getSwimRadius();
        
        for(int i = 0; i < 10; i++)
        {
            double x = currentPos.getX() + Mth.nextInt(this.mob.getRandom(), -radius, radius);
            double y = currentPos.getY() + Mth.nextInt(this.mob.getRandom(), -radius, radius);
            double z = currentPos.getZ() + Mth.nextInt(this.mob.getRandom(), -radius, radius);
            BlockPos targetPos = BlockPos.containing(x, y, z);
            BlockState blockState = world.getBlockState(targetPos);
            
            if(blockState.is(Blocks.WATER) && this.prevTarget.distanceTo(new Vec3(x, y, z)) >= radius)
            {
            	this.targetX = x;
            	this.targetY = y;
            	this.targetZ = z;
            	this.prevTarget = new Vec3(this.targetX, this.targetY, this.targetZ);
            	break;
            }
        }
    }
}
