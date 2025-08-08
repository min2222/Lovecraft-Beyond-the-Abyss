package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractWormPart;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractSpineWormPart extends AbstractWormPart<AbstractSpineWormPart>
{
	public AbstractSpineWormPart(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}
	
	@Override
	protected void doPush(Entity p_21294_)
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
}
