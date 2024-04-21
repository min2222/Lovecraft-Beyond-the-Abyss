package com.min01.beyondtheabyss.entity.part;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;

public class BasicBTAEntityPart extends AbstractBTAEntityPart<AbstractBTAMob>
{
	public BasicBTAEntityPart(AbstractBTAMob entity, float width, float height)
	{
		this(entity, width, height, false);
	}
	
	public BasicBTAEntityPart(AbstractBTAMob entity, float width, float height, boolean canBeCollideWith)
	{
		super(entity, width, height, canBeCollideWith);
	}
}
