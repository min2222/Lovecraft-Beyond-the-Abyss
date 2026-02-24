package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAWaterCreature;
import com.min01.beyondtheabyss.entity.ai.control.BoidMoveControl;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

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

public class EntityGloomfish extends AbstractBTAWaterCreature
{	
	public EntityGloomfish(EntityType<? extends AbstractBTAWaterCreature> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = 1;
		this.moveControl = new BoidMoveControl<>(this);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 2.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAWaterCreature> createBuilder()
	{
    	EntityPartBuilder<EntityGloomfish> partBuilder = new EntityPartBuilder<EntityGloomfish>(this)
    	{
    		@Override
    		public boolean isInWater() 
    		{
    			return true;
    		}
    		
    		@Override
    		public float getWaterOffset() 
    		{
    			return 0.25F;
    		}
    	};
    	return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.PASSIVE;
	}
	
	@Override
	protected void doPush(Entity pEntity) 
	{
		
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		DeepAbyssUtil.fishFlopping(this);
	}
	
	@Override
	public void switchControl(boolean isWater) 
	{
		
	}
	
	@Override
	public int getMaxSpawnClusterSize() 
	{
		return 100;
	}
	
	public static boolean checkGloomfishSpawnRules(EntityType<? extends AbstractBTAWaterCreature> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
}