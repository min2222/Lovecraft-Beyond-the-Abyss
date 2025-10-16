package com.min01.beyondtheabyss.entity.mirroredcity;

import com.min01.beyondtheabyss.entity.AbstractBTAFlyingMonster;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityOverseer extends AbstractBTAFlyingMonster
{
	public EntityOverseer(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(30);
		this.setCanLook(false);
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 150.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
    			.add(Attributes.FLYING_SPEED, 0.8F)
        		.add(Attributes.ATTACK_DAMAGE, 15.0F)
        		.add(Attributes.FOLLOW_RANGE, 50.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAFlyingMonster> createBuilder()
	{
    	EntityPartBuilder<EntityOverseer> partBuilder = new EntityPartBuilder<EntityOverseer>(this);
    	return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
}
