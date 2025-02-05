package com.min01.beyondtheabyss.entity.ai.goal;

import com.min01.beyondtheabyss.entity.IAnimatable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public abstract class AbstractBTASkillGoal<T extends Mob & IAnimatable> extends Goal
{
	protected int skillWarmupDelay;
	protected int nextSkillTickCount;
	
	public AbstractBTASkillGoal() 
	{

	}
	
    @Override
    public boolean canUse() 
    {
    	LivingEntity livingentity = this.getMob().getTarget();
    	if(livingentity != null && livingentity.isAlive()) 
    	{
    		if(this.getMob().isUsingSkill())
    		{
    			return false;
    		}
    		else 
    		{
    			return this.getMob().tickCount >= this.nextSkillTickCount && this.additionalStartCondition();
    		}
    	}
    	else 
    	{
    		return false;
    	}
    }
    
    public boolean additionalStartCondition()
    {
    	return true;
    }
    
    @Override
    public boolean canContinueToUse() 
    {
    	return this.getMob().getAnimationTick() > 0;
    }
    
    @Override
    public void start()
    {
    	if(this.stopMovingWhenStart())
    	{
        	this.getMob().setCanMove(false);
        	this.getMob().getNavigation().stop();
    	}
    	
    	this.getMob().setAggressive(true);
    	this.skillWarmupDelay = this.adjustedTickDelay(this.getSkillWarmupTime());
    	this.getMob().setAnimationTick(this.getSkillUsingTime());
    	this.nextSkillTickCount = this.getMob().tickCount + this.getSkillUsingInterval();
    	
    	this.getMob().setUsingSkill(true);
    }
    
    public boolean stopMovingWhenStart()
    {
    	return true;
    }
    
	@Override
	public void stop()
	{
		if(this.stopMovingWhenStart())
		{
			this.getMob().setCanMove(true);
		}
		this.getMob().setAggressive(false);
    	this.getMob().setUsingSkill(false);
	}
	
    @Override
    public void tick() 
    {
    	--this.skillWarmupDelay;
    	if(this.skillWarmupDelay == 0) 
    	{
    		this.performSkill();
    	}
    	
		if(this.getMob().canMove() && this.getMob().getAnimationTick() == this.getMob().getPrevAnimationTick() - this.getMob().getMoveStopDelay())
		{
			this.perfomSkillAfterMove();
		}
    }

    public void perfomSkillAfterMove()
    {
    	
    }

    protected abstract void performSkill();

    //wait specific tick before use skill
    protected int getSkillWarmupTime()
    {
    	return 20;
    }
    
    protected abstract int getSkillUsingTime();

    protected abstract int getSkillUsingInterval();
    
    public abstract T getMob();
}
