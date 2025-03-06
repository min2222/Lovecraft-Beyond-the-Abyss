package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class EntityAbyssalBulbray extends AbstractDeepAbyssCreature
{
	public EntityAbyssalBulbray(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(4);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 80.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
        		.add(Attributes.ARMOR, 2.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTACreature> createBuilder() 
	{
		EntityPartBuilder<EntityAbyssalBulbray> partBuilder = new EntityPartBuilder<EntityAbyssalBulbray>(this);
		return partBuilder;
	}
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 1;
    }
    
	public static boolean checkBulbraySpawnRules(EntityType<? extends AbstractDeepAbyssCreature> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(70) == 0 && pPos.getY() >= -400 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
	
	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.PASSIVE;
	}
}
