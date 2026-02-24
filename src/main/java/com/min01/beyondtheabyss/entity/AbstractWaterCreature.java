package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.entity.ai.navigation.NoSpinWaterBoundPathNavigation;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public abstract class AbstractWaterCreature extends PathfinderMob
{
	public static final EntityDataAccessor<Boolean> IS_SWIM = SynchedEntityData.defineId(AbstractWaterCreature.class, EntityDataSerializers.BOOLEAN);
	
	public float rollAngleO = 0.0F;
	public float rollAngle = 0.0F;
	
	public AbstractWaterCreature(EntityType<? extends PathfinderMob> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(IS_SWIM, true);
	}

	@Override
	public boolean canBreatheUnderwater() 
	{
		return true;
	}

	@Override
	public MobType getMobType()
	{
		return MobType.WATER;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader pLevel)
	{
		return pLevel.isUnobstructed(this);
	}

	@Override
	public int getAmbientSoundInterval() 
	{
		return 120;
	}
	
	@Override
	protected void playStepSound(BlockPos pPos, BlockState pState) 
	{
		if(!this.isSwim())
		{
			super.playStepSound(pPos, pState);
		}
	}

	@Override
	public int getExperienceReward() 
	{
		return 1 + this.level.random.nextInt(3);
	}

	protected void handleAirSupply(int pAirSupply) 
	{
		if(this.isAlive() && !this.isInWaterOrBubble())
		{
			this.setAirSupply(pAirSupply - 1);
			if(this.getAirSupply() == -20)
			{
				this.setAirSupply(0);
				this.hurt(this.damageSources().drown(), 2.0F);
			}
		} 
		else 
		{
			this.setAirSupply(300);
		}
	}
	
    @Override
    public void travel(Vec3 pTravelVector) 
    {
    	if(this.isEffectiveAi() && this.isInWater() && this.isSwim())
    	{
    		this.moveRelative((float) (this.getSpeed() * this.getAttributeValue(ForgeMod.SWIM_SPEED.get())), pTravelVector);
    		this.move(MoverType.SELF, this.getDeltaMovement());
    		this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
    	}
    	else
    	{
    		super.travel(pTravelVector);
    	}
    }
	
	@Override
	public void tick() 
	{
		super.tick();
		this.switchControl(this.isSwim());
	    Vec3 movement = this.getDeltaMovement();
	    float speed = (float) movement.length();
	    this.rollAngleO = this.rollAngle;
	    if(speed > this.getRollThreshold() && this.isInWater()) 
	    {
	        this.rollAngle += (this.getTargetRoll(movement) - this.rollAngle) * this.getRollAmount();
	    }
	    else
	    {
	        this.rollAngle *= 0.9F;
	    }
	}
	
    @Override
    protected boolean isAffectedByFluids()
    {
    	return this.isSwim();
    }

	@Override
	public void baseTick() 
	{
		int i = this.getAirSupply();
		super.baseTick();
		this.handleAirSupply(i);
	}

	@Override
	public boolean isPushedByFluid() 
	{
		return false;
	}
	
	@Override
	protected PathNavigation createNavigation(Level pLevel) 
	{
		return new NoSpinWaterBoundPathNavigation(this, pLevel);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putBoolean("isSwim", this.isSwim());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound)
	{
		super.readAdditionalSaveData(pCompound);
		this.setSwim(pCompound.getBoolean("isSwim"));
	}
	
	public void switchControl(boolean isWater)
	{
		
	}
	
	public float getRollAngle(float partialTicks)
	{
		return Mth.lerp(partialTicks, this.rollAngleO, this.rollAngle);
	}
	
	public float getTargetRoll(Vec3 movement)
	{
		return (float) (Math.toDegrees(Math.atan2(movement.x, movement.z)) * 0.01F);
	}
	
	public float getRollThreshold()
	{
		return 0.05F;
	}
	
	public float getRollAmount()
	{
		return 0.01F;
	}
	
	public void setSwim(boolean isSwim)
	{
		this.entityData.set(IS_SWIM, isSwim);
	}
	
	public boolean isSwim()
	{
		return this.entityData.get(IS_SWIM);
	}
	
	public static boolean checkFishSpawnRules(EntityType<? extends PathfinderMob> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above(2)).is(Blocks.WATER);
    }
}
