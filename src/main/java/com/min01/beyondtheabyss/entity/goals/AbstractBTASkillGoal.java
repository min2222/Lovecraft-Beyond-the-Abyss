package com.min01.beyondtheabyss.entity.goals;

import com.min01.beyondtheabyss.entity.AbstractBTAEntity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public abstract class AbstractBTASkillGoal extends Goal
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
    	if (livingentity != null && livingentity.isAlive()) 
    	{
    		if (this.getMob().isUsingSkill())
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
    	LivingEntity livingentity = this.getMob().getTarget();
    	return livingentity != null && livingentity.isAlive() && this.getMob().skillUsingTickCount > 0;
    }
    
    @Override
    public void start()
    {
    	if(this.shouldStopMovingWhenStart())
    	{
        	this.getMob().setShouldMove(false);
    	}
    	
    	this.getMob().setAggressive(true);
    	this.skillWarmupDelay = this.adjustedTickDelay(this.getSkillWarmupTime());
    	this.getMob().skillUsingTickCount = this.getSkillUsingTime();
    	this.nextSkillTickCount = this.getMob().tickCount + this.getSkillUsingInterval();
    	
    	this.getMob().setIsUsingSkill(this.getSkills());
    }
    
    public boolean shouldStopMovingWhenStart()
    {
    	return true;
    }
    
	@Override
	public void stop()
	{
		if(this.shouldStopMovingWhenStart())
		{
			this.getMob().setShouldMove(true);
		}
		this.getMob().setAggressive(false);
	}
	
    @Override
    public void tick() 
    {
    	if(this.getMob().getTarget() != null)
    	{
        	this.getMob().getLookControl().setLookAt(this.getMob().getTarget(), 30.0F, 30.0F);
    	}
    	
    	--this.skillWarmupDelay;
    	if (this.skillWarmupDelay == 0) 
    	{
    		this.performSkill();
    	}
    }

    protected abstract void performSkill();

    //wait specific tick before use skill
    protected int getSkillWarmupTime()
    {
    	return 20;
    }
    
    protected abstract int getSkillUsingTime();

    protected abstract int getSkillUsingInterval();
    
    public abstract AbstractBTAEntity getMob();
    
    protected abstract AbstractBTAEntity.AbyssSkills getSkills();
}
