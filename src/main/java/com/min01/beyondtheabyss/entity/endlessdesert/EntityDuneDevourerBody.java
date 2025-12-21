package com.min01.beyondtheabyss.entity.endlessdesert;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityDuneDevourerBody extends AbstractDuneDevourerPart
{
	public EntityDuneDevourerBody(EntityType<? extends Monster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<EntityDuneDevourerBody> partBuilder = new EntityPartBuilder<EntityDuneDevourerBody>(this);
		return partBuilder;
	}
}
