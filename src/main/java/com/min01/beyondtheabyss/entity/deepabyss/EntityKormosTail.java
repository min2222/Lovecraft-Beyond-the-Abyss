package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityKormosTail extends AbstractKormosPart
{
	public EntityKormosTail(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
	{
    	EntityPartBuilder<EntityKormosTail> partBuilder = new EntityPartBuilder<EntityKormosTail>(this);
		return partBuilder;
	}
}
