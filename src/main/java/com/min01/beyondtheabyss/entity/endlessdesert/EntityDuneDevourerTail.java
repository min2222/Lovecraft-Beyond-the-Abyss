package com.min01.beyondtheabyss.entity.endlessdesert;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityDuneDevourerTail extends AbstractDuneDevourerPart
{
	public EntityDuneDevourerTail(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<EntityDuneDevourerTail> partBuilder = new EntityPartBuilder<EntityDuneDevourerTail>(this);
		return partBuilder;
	}
}
