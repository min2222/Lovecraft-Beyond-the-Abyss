package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;

public class EntityCorpseAngler extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Integer> BURROW_COOLDOWN = SynchedEntityData.defineId(AbstractBTAMonster.class, EntityDataSerializers.INT);
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState openMouthAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState closeMouthAnimationState = new SmoothAnimationState();
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
	
	public EntityCorpseAngler(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(10);
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 80.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F)
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
		
		float speed = 0.35F;

    	WormChain.tick(this.worm, this, 0.0F, speed);
    	WormChain.tick(this.worm1, this.worm, 0.0F, speed);
    	WormChain.tick(this.worm2, this.worm1, 0.0F, speed);
    	WormChain.tick(this.worm3, this.worm2, 0.0F, speed);
    	
    	WormChain.tick(this.worm4, this.worm3, 0.0F, speed);
    	WormChain.tick(this.worm5, this.worm4, 0.0F, speed);
    	WormChain.tick(this.worm6, this.worm5, 0.0F, speed);
    	
		DeepAbyssUtil.fishFlopping(this);
		boolean canBurrow = BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below()) && BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below(2)) && BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below(3));
		if(this.level.isClientSide)
		{
			this.idleAnimationState.updateWhen(this.getAnimationState() == 0 && this.isInWater(), this.tickCount);
			this.openMouthAnimationState.updateWhen(this.getAnimationState() == 1, this.tickCount);
			this.closeMouthAnimationState.updateWhen(this.isUsingSkill(2), this.tickCount);
			this.burrowAnimationState.updateWhen(this.getAnimationState() == 3, this.tickCount);
			this.unburrowAnimationState.updateWhen(this.getAnimationState() == 4, this.tickCount);
			this.ambushAnimationState.updateWhen(this.getAnimationState() == 5, this.tickCount);
		}
		if(this.getAnimationState() == 0 && this.isInWater())
		{
			if(this.getBurrowCooldown() <= 0)
			{
				if(canBurrow)
				{
					if(!this.level.isClientSide && this.getTarget() == null)
					{
						this.setAnimationState(3);
						this.setAnimationTick(40);
						this.setCanMove(false);
						this.setCanLook(false);
					}
				}
				else
				{
					Vec3 pos = Vec3.atBottomCenterOf(this.level.getHeightmapPos(Types.OCEAN_FLOOR_WG, this.blockPosition()).above());
					if(this.getNavigation().isDone())
					{
						this.getNavigation().moveTo(pos.x, pos.y, pos.z, 0.25F);
					}
				}
			}
		}
		if(this.getAnimationState() == 3)
		{
			this.getNavigation().stop();
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
	
	public static boolean checkCorpseAnglerSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		if(p_21436_ == MobSpawnType.NATURAL)
		{
			BlockPos floorPos = BTAUtil.getGroundPos(this.level, this.getX(), this.getY(), this.getZ(), 0).above();
			Vec3 pos = Vec3.atBottomCenterOf(floorPos);
			this.moveTo(pos);
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
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
			this.level.addAlwaysVisibleParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), x, this.getY(), z, motionX, motionY, motionZ);
		}
	}
	
	@Override
	public boolean canSwim() 
	{
		return super.canSwim() && this.canMove();
	}
	
    @Override
    protected void updateWalkAnimation(float p_268283_)
    {
        float f = Math.min(p_268283_ * 20.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }
	
	@Override
	protected void doPush(Entity p_21294_)
	{
		if(this.getAnimationState() != 3)
		{
			super.doPush(p_21294_);
		}
	}
	
	@Override
	public boolean isPushable() 
	{
		return super.isPushable() && this.getAnimationState() != 3;
	}
	
	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) 
	{
		if(p_21016_.getDirectEntity() instanceof Player player && this.getAnimationState() == 3)
		{
	        String part = BTAUtil.getMultiPart(this.getBounds(), player);
	        if(part != null && LIST.contains(part))
	        {
				this.setAnimationState(4);
				this.setAnimationTick(20);
	        }
		}
		return super.hurt(p_21016_, p_21017_);
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
		if(this.getAnimationState() == 1)
		{
			return 7;
		}
		return 5;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_)
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putInt("BurrowCooldown", this.getBurrowCooldown());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_)
	{
		super.readAdditionalSaveData(p_21450_);
		this.setBurrowCooldown(p_21450_.getInt("BurrowCooldown"));
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
