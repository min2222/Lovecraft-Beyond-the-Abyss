package com.min01.beyondtheabyss.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;

public class BreachingWaterBoundPathNavigation extends WaterBoundPathNavigation
{
	public boolean allowBreaching;
	
	public BreachingWaterBoundPathNavigation(Mob pMob, Level pLevel)
	{
		super(pMob, pLevel);
	}

	@Override
	protected PathFinder createPathFinder(int pMaxVisitedNodes) 
	{
		this.allowBreaching = true;
		this.nodeEvaluator = new SwimNodeEvaluator(this.allowBreaching);
		return new BTAPathFinder(this.nodeEvaluator, pMaxVisitedNodes);
	}
	
	@Override
	protected boolean canUpdatePath() 
	{
		return this.allowBreaching || this.isInLiquid();
	}
}
