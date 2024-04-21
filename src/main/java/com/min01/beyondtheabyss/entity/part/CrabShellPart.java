package com.min01.beyondtheabyss.entity.part;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.deepabyss.EntityAbyssalHermitCrab;

import net.minecraft.world.damagesource.DamageSource;

public class CrabShellPart extends AbstractBTAEntityPart<AbstractBTAMob>
{
	public CrabShellPart(EntityAbyssalHermitCrab entity, float width, float height)
	{
		super(entity, width, height, true);
	}
	
	@Override
	public boolean hurt(DamageSource p_31020_, float p_31021_) 
	{
		return super.hurt(p_31020_, p_31021_ / 2);
	}
}
