package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.world.damagesource.DamageSource;
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
	
	@Override
	public float getSegmentDistance() 
	{
		return 6.55F;
	}
	
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) 
    {
    	if(!this.isInvulnerableTo(p_21016_) && this.getOwner() != null)
    	{
    		this.getOwner().hurt(p_21016_, p_21017_);
    	}
    	return false;
    }
}
