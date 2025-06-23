package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractWormPart;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractSiamserpentPart extends AbstractWormPart<AbstractSiamserpentPart>
{
	public boolean swapOwner;
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
	
	//TODO
	@Override
	public void tick()
	{
	    super.tick();
	    if(this.swapOwner)
	    {
	        AbstractSiamserpentPart current = this;
	        AbstractSiamserpentPart prev = null;
	        AbstractSiamserpentPart next;

	        while(current != null) 
	        {
	            next = current.getOwner();    // save next node
	            current.setOwner(prev);       // reverse the link
	            current.swapOwner = false;    // clear the swap flag
	            prev = current;               // move forward
	            current = next;
	        }
	        
	        if(prev != null && prev instanceof EntitySiamserpentHead)
	        {
	            prev.setOwner(null);
	            if(!(this instanceof EntitySiamserpentHead))
	            {
		            this.setHead(prev);
	            }
	        }
	    }
	}
}
