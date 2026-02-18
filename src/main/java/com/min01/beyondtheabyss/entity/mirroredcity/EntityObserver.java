package com.min01.beyondtheabyss.entity.mirroredcity;

import com.min01.beyondtheabyss.entity.AbstractBTAFlyingMonster;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class EntityObserver extends AbstractBTAFlyingMonster
{
	public EntityObserver(EntityType<? extends AbstractBTAFlyingMonster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(3);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 5.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.6F)
    			.add(Attributes.FLYING_SPEED, 0.6F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAFlyingMonster> createBuilder() 
	{
    	EntityPartBuilder<EntityObserver> partBuilder = new EntityPartBuilder<EntityObserver>(this);
    	return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public boolean ignoreExplosion() 
	{
		return true;
	}
	
	public static boolean checkObserverSpawnRules(EntityType<? extends AbstractBTAFlyingMonster> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		BlockPos ceilingPos = BTAUtil.getCeilingPos(pServerLevel, pPos.getX(), pPos.getY(), pPos.getZ());
		return pServerLevel.getBlockState(ceilingPos).isAir() && pServerLevel.getBlockState(pPos.below()).isAir() && pServerLevel.getBlockState(pPos.above()).isAir() && pPos.getY() <= 10 && pPos.getY() >= -10;
    }
}
