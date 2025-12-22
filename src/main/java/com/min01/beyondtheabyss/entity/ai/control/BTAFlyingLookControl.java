package com.min01.beyondtheabyss.entity.ai.control;

import com.min01.beyondtheabyss.entity.IBTAMob;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class BTAFlyingLookControl extends LookControl 
{
	public BTAFlyingLookControl(Mob mob) 
	{
		super(mob);
	}

	@Override
	public void tick() 
	{
		IBTAMob mob = (IBTAMob) this.mob;
		if(this.lookAtCooldown > 0) 
		{
			--this.lookAtCooldown;
			this.getYRotD().ifPresent(t ->
			{
				this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, t + 20.0F, this.yMaxRotSpeed);
			});
			this.getXRotD().ifPresent(t ->
			{
				this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), t + 1.0F, this.xMaxRotAngle));
			});
		} 
		else 
		{
			if(this.mob.getNavigation().isDone()) 
			{
				this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0F, 5.0F));
			}
			this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
		}
		float f = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
		if(f < -mob.maxTurnY()) 
		{
			this.mob.yBodyRot -= 4.0F;
		} 
		else if(f > mob.maxTurnY())
		{
			this.mob.yBodyRot += 4.0F;
		}
	}
}