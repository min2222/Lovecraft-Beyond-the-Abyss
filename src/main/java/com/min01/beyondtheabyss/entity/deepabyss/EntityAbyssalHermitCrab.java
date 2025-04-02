package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class EntityAbyssalHermitCrab extends AbstractDeepAbyssCreature
{
	public EntityAbyssalHermitCrab(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(3);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 30.0F)
    			.add(Attributes.ATTACK_DAMAGE, 5.0F)
    			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.35F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTACreature> createBuilder() 
	{
    	EntityPartBuilder<EntityAbyssalHermitCrab> partBuilder = new EntityPartBuilder<EntityAbyssalHermitCrab>(this);
		return partBuilder;
	}
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 1;
    }
    
	public static boolean checkHermitCrabSpawnRules(EntityType<? extends AbstractDeepAbyssCreature> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(40) == 0 && pPos.getY() >= -400 && pServerLevel.getBlockState(pPos.below()).is(BTABlocks.ABYSSALITH.get()) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }

    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.35F));
    }
	
	@Override
	public float insideWaterSpeed() 
	{
		return 0.25F;
	}
	
	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.NETURAL;
	}
	
	@Override
	public boolean isSwimable() 
	{
		return false;
	}
	
	@Override
	public boolean canBreathOutsideWater()
	{
		return true;
	}
	
	@Override
	public boolean rotateHead()
	{
		return false;
	}
}
