package com.min01.beyondtheabyss.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class SemiWaterboundPathNavigation extends SemiGroundPathNavigation 
{
    public SemiWaterboundPathNavigation(Mob entity, Level world) 
    {
        super(entity, world);
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes)
    {
        this.nodeEvaluator = new AmphibiousNodeEvaluator(false);
        this.nodeEvaluator.setCanPassDoors(true);
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected Vec3 getTempMobPos() 
    {
    	if(this.isInLiquid())
    	{
        	return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
    	}
    	return super.getTempMobPos();
    }

    @Override
    protected double getGroundY(Vec3 pVec)
    {
    	if(this.isInLiquid())
    	{
        	return pVec.y;
    	}
    	return super.getGroundY(pVec);
    }
    
    @Override
    protected boolean canMoveDirectly(Vec3 pPosVec31, Vec3 pPosVec32)
    {
    	if(this.isInLiquid())
    	{
        	return isClearForMovementBetween(this.mob, pPosVec31, pPosVec32, false);
    	}
    	return super.canMoveDirectly(pPosVec31, pPosVec32);
    }
    
    @Override
    public boolean isStableDestination(BlockPos pPos)
    {
    	if(this.isInLiquid())
    	{
    		return !this.level.getBlockState(pPos.below()).isAir();
    	}
    	return super.isStableDestination(pPos);
    }
}
