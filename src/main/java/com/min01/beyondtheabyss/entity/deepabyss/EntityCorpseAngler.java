package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityCorpseAngler extends AbstractDeepAbyssMonster
{
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState swimAnimationState = new AnimationState();
	
	public EntityCorpseAngler(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 80.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<EntityCorpseAngler> partBuilder = new EntityPartBuilder<EntityCorpseAngler>(this)
    	{
    		@Override
    		public boolean isInWater() 
    		{
    			return true;
    		}

    		@Override
    		public float getWaterOffset() 
    		{
    			return 1.5F;
    		}
    	};
    	return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.level.isClientSide)
		{
			BTAClientUtil.animateWhen(this.swimAnimationState, BTAUtil.isMoving(this), this.tickCount);
			BTAClientUtil.animateWhen(this.idleAnimationState, this.isInWater() && !BTAUtil.isMoving(this), this.tickCount);
		}
		DeepAbyssUtil.fishFlopping(this);
	}
	
	@Override
	public int maxTurnX() 
	{
		return 5;
	}
	
	@Override
	public int maxTurnY() 
	{
		return 5;
	}
}
