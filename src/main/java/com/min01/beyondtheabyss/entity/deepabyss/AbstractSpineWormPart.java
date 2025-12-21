package com.min01.beyondtheabyss.entity.deepabyss;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractSpineWormPart extends AbstractDeepAbyssWormPart<AbstractSpineWormPart>
{
	public AbstractSpineWormPart(EntityType<? extends Monster> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
	}
	
	@Override
	protected void doPush(Entity pEntity)
	{
		
	}
	
	@Override
	public boolean isWormChain() 
	{
		return false;
	}
	
	@Override
	public int getChainLength() 
	{
		return 20;
	}
	
	@Override
	public float getChainSpeed() 
	{
		return 0.35F;
	}
	
	@Override
	public float getSegmentDistance(int index) 
	{
		return 1.0F;
	}
	
	@Override
	public boolean skipInvisiblePart() 
	{
		return false;
	}
}
