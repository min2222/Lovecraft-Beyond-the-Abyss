package com.min01.beyondtheabyss.entity.endlessdesert;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.solomonlib.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class DuneDevourerTailEntity extends AbstractDuneDevourerPart
{
	public DuneDevourerTailEntity(EntityType<? extends AbstractDuneDevourerPart> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<DuneDevourerTailEntity> partBuilder = new EntityPartBuilder<DuneDevourerTailEntity>(this);
		return partBuilder;
	}
}
