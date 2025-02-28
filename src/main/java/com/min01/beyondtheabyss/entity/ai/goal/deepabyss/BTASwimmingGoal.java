package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.IDeepAbyssMob;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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
    	if(((IDeepAbyssMob) this.mob).canSwim())
    	{
        	this.generateNewTarget();
        	return true;
    	}
        return false;
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
        int radius = ((IDeepAbyssMob) this.mob).getSwimRadius();
        
        for(int i = 0; i < 10; i++)
        {
        	Vec3 pos = BTAUtil.getRandomPosition(this.mob, radius);
        	HitResult hitResult = this.mob.level.clip(new ClipContext(this.mob.position(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob));
        	if(hitResult instanceof BlockHitResult blockHit)
        	{
                BlockPos targetPos = blockHit.getBlockPos();
                BlockState blockState = world.getBlockState(targetPos);
                
                if(blockState.is(Blocks.WATER) && this.prevTarget.distanceTo(pos) >= radius)
                {
                	this.targetX = pos.x;
                	this.targetY = pos.y;
                	this.targetZ = pos.z;
                	this.prevTarget = new Vec3(this.targetX, this.targetY, this.targetZ);
                	break;
                }
        	}
        }
    }
}
