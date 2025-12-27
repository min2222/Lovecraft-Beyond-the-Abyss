package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.CorpseAnglerAmbushGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.CorpseAnglerDashGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EntityCorpseAngler extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Integer> BURROW_COOLDOWN = SynchedEntityData.defineId(EntityCorpseAngler.class, EntityDataSerializers.INT);
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState openMouthAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState burrowAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState unburrowAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState ambushAnimationState = new SmoothAnimationState();

	public final Worm worm = new Worm();
	public final Worm worm1 = new Worm();
	public final Worm worm2 = new Worm();
	public final Worm worm3 = new Worm();
	public final Worm worm4 = new Worm();
	public final Worm worm5 = new Worm();
	public final Worm worm6 = new Worm();
	
	public static final List<String> LIST = List.of("Up", "Jaw2", "Tails", "TailEdge", "Body2", "Left", "Right");
	
	public EntityCorpseAngler(EntityType<? extends Monster> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.posArray = new Vec3[1];
		this.xpReward = this.random.nextInt(20);
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 80.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.45F)
        		.add(Attributes.FOLLOW_RANGE, 60.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 10.0F)
        		.add(Attributes.ATTACK_DAMAGE, 8.0F);
    }
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
    	this.goalSelector.addGoal(0, new CorpseAnglerAmbushGoal(this));
    	this.goalSelector.addGoal(0, new CorpseAnglerDashGoal(this));
    }

    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(BURROW_COOLDOWN, 0);
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

		this.worm.setOldPosAndRot();
		this.worm1.setOldPosAndRot();
		this.worm2.setOldPosAndRot();
		this.worm3.setOldPosAndRot();
		this.worm4.setOldPosAndRot();
		this.worm5.setOldPosAndRot();
		this.worm6.setOldPosAndRot();
		

		if(this.tickCount > 2)
		{
			float speed = 0.35F;
	    	WormChain.tick(this.worm, this, 0.0F, speed);
	    	WormChain.tick(this.worm1, this.worm, 0.0F, speed);
	    	WormChain.tick(this.worm2, this.worm1, 0.0F, speed);
	    	WormChain.tick(this.worm3, this.worm2, 0.0F, speed);
	    	
	    	WormChain.tick(this.worm4, this.worm3, 0.0F, speed);
	    	WormChain.tick(this.worm5, this.worm4, 0.0F, speed);
	    	WormChain.tick(this.worm6, this.worm5, 0.0F, speed);
		}
    	
		DeepAbyssUtil.fishFlopping(this);
		
		if(this.level.isClientSide)
		{
			this.idleAnimationState.updateWhen(this.getAnimationState() == 0 && this.isInWater(), this.tickCount);
			this.openMouthAnimationState.updateWhen(this.getAnimationState() == 1, this.tickCount);
			this.burrowAnimationState.updateWhen(this.getAnimationState() == 3, this.tickCount);
			this.unburrowAnimationState.updateWhen(this.getAnimationState() == 4, this.tickCount);
			this.ambushAnimationState.updateWhen(this.getAnimationState() == 5, this.tickCount);
		}
		
		boolean canBurrow = BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below()) && BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below(2)) && BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below(3));
		
		if(this.getAnimationState() == 0 && this.isInWater() && !this.hasTarget())
		{
			if(this.getBurrowCooldown() <= 0)
			{
				if(canBurrow)
				{
					this.setAnimationState(3);
					this.setAnimationTick(40);
					this.setCanMove(false);
					this.setCanLook(false);
				}
				else
				{
					BlockPos floorPos = BTAUtil.getGroundPos(this.level, this.getX(), this.getY(), this.getZ());
					Vec3 pos = Vec3.atBottomCenterOf(floorPos);
					boolean flag = this.position().distanceTo(pos) <= 12.0F;
					this.setCanMove(!flag);
					this.setCanLook(!flag);
					if(flag)
					{
						this.getNavigation().moveTo(floorPos.getX(), floorPos.getY(), floorPos.getZ(), 1.25F);
					}
				}
			}
		}

		if(this.getAnimationState() == 3)
		{
			if(!this.level.isClientSide)
			{
				this.getNavigation().stop();
				this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0F);
			}
			if(this.getAnimationTick() > 0)
			{
				this.spawnParticle();
			}
			else if(!canBurrow)
			{
				this.setAnimationState(4);
				this.setAnimationTick(20);
			}
		}
		if(this.getAnimationState() == 4)
		{
			if(this.getAnimationTick() > 0)
			{
				this.spawnParticle();
			}
			else
			{
				this.setAnimationState(0);
				this.setBurrowCooldown(100);
				this.setCanMove(true);
				this.setCanLook(true);
			}
		}
	}
	
	@Override
	public boolean canMove() 
	{
		return super.canMove() && !this.isBurrow();
	}
	
	@Override
	public boolean canLook() 
	{
		return super.canLook() && !this.isBurrow();
	}
	
	public boolean isBurrow()
	{
		return this.getAnimationState() == 3 || this.getAnimationState() == 4;
	}
	
	public static boolean checkCorpseAnglerSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag)
	{
		if(pReason == MobSpawnType.NATURAL)
		{
			BlockPos floorPos = BTAUtil.getGroundPos(this.level, this.getX(), this.getY(), this.getZ());
			Vec3 pos = Vec3.atBottomCenterOf(floorPos);
			this.moveTo(pos);
		}
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}
	
	public void spawnParticle()
	{
		double motionX = this.random.nextGaussian() * 0.2D;
		double motionY = 0.03D;
		double motionZ = this.random.nextGaussian() * 0.2D;
		for(int i = 0; i < 50; i++)
		{
			double range = this.getBbWidth() * 2;
            double x = this.getX() + (this.random.nextDouble() - this.random.nextDouble()) * range + 0.5D;
            double z = this.getZ() + (this.random.nextDouble() - this.random.nextDouble()) * range + 0.5D;
			this.level.addAlwaysVisibleParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.level.getBlockState(this.blockPosition().below())), x, this.getY(), z, motionX, motionY, motionZ);
		}
	}
	
    @Override
    protected void updateWalkAnimation(float pPartialTick)
    {
        float f = Math.min(pPartialTick * 20.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }
	
	@Override
	protected void doPush(Entity pEntity)
	{
		if(this.getAnimationState() != 3)
		{
			super.doPush(pEntity);
		}
	}
	
	@Override
	public boolean isPushable() 
	{
		return super.isPushable() && this.getAnimationState() != 3;
	}
	
	@Override
	public boolean hurt(DamageSource pSource, float pAmount) 
	{
		if(pSource.getDirectEntity() instanceof Player player && this.getAnimationState() == 3)
		{
	        String part = BTAUtil.getMultiPart(this.getBounds(), player);
	        if(part != null && LIST.contains(part))
	        {
				this.setAnimationState(4);
				this.setAnimationTick(20);
	        }
		}
		return super.hurt(pSource, pAmount);
	}
    
	@Override
	public int maxTurnX() 
	{
		if(this.getAnimationState() == 1)
		{
			return 75;
		}
		return 55;
	}
	
	@Override
	public int maxTurnY() 
	{
		return 8;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound)
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("BurrowCooldown", this.getBurrowCooldown());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound)
	{
		super.readAdditionalSaveData(pCompound);
		this.setBurrowCooldown(pCompound.getInt("BurrowCooldown"));
	}
	
	public void setBurrowCooldown(int cooldown)
	{
		this.entityData.set(BURROW_COOLDOWN, cooldown);
	}
	
	public int getBurrowCooldown()
	{
		return this.entityData.get(BURROW_COOLDOWN);
	}
}
