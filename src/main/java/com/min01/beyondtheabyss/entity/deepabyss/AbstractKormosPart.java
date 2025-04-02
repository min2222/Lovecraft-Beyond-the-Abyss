package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.misc.BTAMobType;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractKormosPart extends AbstractWormPart<AbstractKormosPart>
{
	public AbstractKormosPart(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.BOSS;
	}
	
	@Override
	public float insideWaterSpeed() 
	{
		return 0.8F;
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
		if(index == 0)
		{
			return 3.0F;
		}
		else if(index == 1)
		{
			return 4.0F;
		}
		else if(index == this.getChainLength())
		{
			return 6.55F;
		}
		return 5.0F;
	}
	
	@Override
	protected void doPush(Entity p_21294_)
	{
		if(!(p_21294_ instanceof AbstractKormosPart))
		{
			super.doPush(p_21294_);
		}
	}
}
