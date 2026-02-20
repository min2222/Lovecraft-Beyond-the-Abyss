package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAWaterCreature;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class EntityLithoshrimp extends AbstractBTAWaterCreature
{
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	
	public EntityLithoshrimp(EntityType<? extends AbstractBTAWaterCreature> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = 1;
	}

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 5.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.15F)
        		.add(Attributes.ARMOR, 1.0F)
        		.add(Attributes.ARMOR_TOUGHNESS, 1.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 1.0F);
    }
    
	@Override
	public EntityPartBuilder<? extends AbstractBTAWaterCreature> createBuilder()
	{
    	EntityPartBuilder<EntityLithoshrimp> partBuilder = new EntityPartBuilder<EntityLithoshrimp>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.PASSIVE;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		if(this.level.isClientSide)
		{
			this.idleAnimationState.updateWhen(this.getAnimationState() == 0 && this.isInWater(), this.tickCount);
		}
	}
	
	@Override
	protected void doPush(Entity pEntity) 
	{
		
	}
	
	@Override
	public int getMaxSpawnClusterSize() 
	{
		return 100;
	}
	
	public static boolean checkLithoshrimpSpawnRules(EntityType<? extends AbstractBTAWaterCreature> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
}
