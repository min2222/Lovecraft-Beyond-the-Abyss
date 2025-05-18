package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

//https://github.com/Tomate0613/boids/blob/1.21/src/main/java/dev/doublekekse/boids/goals/StayInWaterGoal.java
public class StayInWaterGoal extends Goal 
{
    private final Mob mob;

    public StayInWaterGoal(Mob mob)
    {
        this.mob = mob;
    }

    @Override
    public boolean canUse() 
    {
        return this.mob.isInWater();
    }

    @Override
    public void tick() 
    {
        BlockPos blockPos = this.mob.blockPosition();
        BlockState blockAbove = this.mob.level.getBlockState(blockPos.above(2));
        BlockState blockBelow = this.mob.level.getBlockState(blockPos.below(1));
        float amount = this.amount();
        if(blockBelow.getFluidState().isEmpty()) 
        {
        	this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(new Vec3(0, amount, 0)));
        }
        if(blockAbove.getFluidState().isEmpty())
        {
        	this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(new Vec3(0, -amount, 0)));
        }
    }

    private float amount() 
    {
        float amount = 0.1F;
        float dY = Mth.abs((float) this.mob.getDeltaMovement().y);
        if(dY > amount) 
        {
            amount = dY;
        }
        return amount;
    }
}
