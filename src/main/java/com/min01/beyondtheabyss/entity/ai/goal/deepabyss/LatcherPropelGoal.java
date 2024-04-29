package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.deepabyss.EntityLatcher;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class LatcherPropelGoal extends Goal
{
    private final EntityLatcher latcher;

    public LatcherPropelGoal(EntityLatcher latcher)
    {
    	this.latcher = latcher;
    }

    @Override
    public boolean canUse() 
    {
    	return !this.latcher.hasTarget() && this.latcher.isInWater();
    }

    @Override
    public void tick() 
    {
    	int i = this.latcher.getNoActionTime();
    	if (i > 100) 
    	{
    		this.latcher.setDeltaMovement(Vec3.ZERO);
    	} 
    	else if (this.latcher.getRandom().nextInt(reducedTickDelay(50)) == 0)
    	{
    		Vec3 lookPos = BTAUtil.getLookPos(this.latcher.getXRot(), this.latcher.yHeadRot, 0, 1);
    		Vec3 pos = this.latcher.position().add(lookPos);
    		this.latcher.getNavigation().moveTo(pos.x, pos.y, pos.z, 0.7);
    	}
    }
 }
