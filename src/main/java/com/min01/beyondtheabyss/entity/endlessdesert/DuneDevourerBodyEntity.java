package com.min01.beyondtheabyss.entity.endlessdesert;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.solomonlib.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class DuneDevourerBodyEntity extends AbstractDuneDevourerPart
{
	public DuneDevourerBodyEntity(EntityType<? extends AbstractDuneDevourerPart> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<DuneDevourerBodyEntity> partBuilder = new EntityPartBuilder<DuneDevourerBodyEntity>(this);
		return partBuilder;
	}
}
