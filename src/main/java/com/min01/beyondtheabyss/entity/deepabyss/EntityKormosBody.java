package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityKormosBody extends AbstractKormosPart
{
	public EntityKormosBody(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
	{
    	EntityPartBuilder<EntityKormosBody> partBuilder = new EntityPartBuilder<EntityKormosBody>(this);
		return partBuilder;
	}
	
	@Override
	public boolean useSubRoot() 
	{
		return true;
	}
	
	@Override
	public String subRoot()
	{
		if(this.getIndex() == 0)
		{
			return "neck";
		}
		if(this.getIndex() == 1)
		{
			return "front_body";
		}
		if(this.getIndex() == this.getChainLength() - 4)
		{
			return "back_body";
		}
		return "body";
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.tickCount == 2)
		{
			this.partBuilder.rebuildHitbox();
		}
	}
}
