package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class EntityRunicFish extends AbstractDeepAbyssCreature
{
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntityRunicFish.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> PANIC_TICK = SynchedEntityData.defineId(EntityRunicFish.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_PANIC = SynchedEntityData.defineId(EntityRunicFish.class, EntityDataSerializers.BOOLEAN);
	
	public EntityRunicFish(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(2);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 5.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F);
    }
    
    @Override
    public EntityPartBuilder<? extends AbstractBTACreature> createBuilder()
    {
    	EntityPartBuilder<EntityRunicFish> partBuilder = new EntityPartBuilder<EntityRunicFish>(this)
    	{
    		@Override
    		public boolean isInWater() 
    		{
    			return true;
    		}
    	};
    	return partBuilder;
    }
    
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(VARIANT, this.random.nextInt(5) + 1);
		this.entityData.define(IS_PANIC, false);
		this.entityData.define(PANIC_TICK, 0);
	}
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 4;
    }
    
    //TODO probably only spawn in poi type of structure for temple guardian
	public static boolean checkRunicFishSpawnRules(EntityType<? extends AbstractDeepAbyssCreature> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(20) == 0 && pPos.getY() >= -400 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
    
    @Override
    public void tick() 
    {
        super.tick();
        DeepAbyssUtil.fishFlopping(this);
        
        if(this.isPanic())
        {
        	this.setPanicTick(this.getPanicTick() + 1);
        	if(this.getPanicTick() >= 60)
        	{
        		this.setPanic(false);
        		this.setPanicTick(0);
        	}
        }
    }
	
	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) 
	{
		if(!this.isPanic() && p_21016_.getDirectEntity() != null)
		{
			this.setPanic(true);
		}
		return super.hurt(p_21016_, p_21017_);
	}
	
	public void setPanicTick(int value)
	{
		this.entityData.set(PANIC_TICK, value);
	}
	
	public int getPanicTick() 
	{
		return this.entityData.get(PANIC_TICK);
	}
	
	public void setPanic(boolean value)
	{
		this.entityData.set(IS_PANIC, value);
	}
	
	public boolean isPanic() 
	{
		return this.entityData.get(IS_PANIC);
	}
	
	public int getVariant() 
	{
		return this.entityData.get(VARIANT);
	}
	
	@Override
	public float insideWaterSpeed() 
	{
		return this.isPanic() ? 0.1F : super.insideWaterSpeed();
	}
    
    @Override
    public BTAMobType getBTAMobType()
    {
    	return BTAMobType.PASSIVE;
    }
}
