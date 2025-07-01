package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractWormPart;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractSiamserpentPart extends AbstractWormPart<AbstractSiamserpentPart>
{
	public AbstractSiamserpentPart(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}
	
	@Override
	protected void doPush(Entity p_21294_)
	{
		if(!(p_21294_ instanceof AbstractSiamserpentPart))
		{
			super.doPush(p_21294_);
		}
	}
	
	@Override
	public int getChainLength() 
	{
		return 13;
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
