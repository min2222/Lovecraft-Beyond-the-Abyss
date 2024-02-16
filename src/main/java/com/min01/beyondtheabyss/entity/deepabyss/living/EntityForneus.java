package com.min01.beyondtheabyss.entity.deepabyss.living;

import com.min01.beyondtheabyss.entity.deepabyss.AbstractMultipartDeepAbyssEntity;
import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityForneus extends AbstractMultipartDeepAbyssEntity
{
	public EntityForneus(EntityType<? extends Monster> p_33002_, Level p_33003_)
	{
		super(p_33002_, p_33003_);
		this.setAsBoss();
	}
	
	@Override
	public int getBodyRotationSpeed() 
	{
		return 0;
	}

	@Override
	public BasicBTAEntityPart[] getDeepAbyssEntityParts()
	{
		return null;
	}
}
