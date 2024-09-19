package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.DeepAbyssFollowFlockLeaderGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GnasherBiteGoal;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class EntityGnasher extends AbstractDeepAbyssMonster implements IFlocking
{
	public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(EntityGnasher.class, EntityDataSerializers.BOOLEAN);
	
	public AnimationState biteAnimationState = new AnimationState();
	
	@Nullable
	private EntityGnasher leader;
	private int schoolSize = 1;
	
	public EntityGnasher(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(6);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 15)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
        		.add(Attributes.ATTACK_DAMAGE, 2.5)
        		.add(Attributes.FOLLOW_RANGE, 10);
    }

	@Override
	public EntityPartBuilder<EntityGnasher> createBuilder() 
	{
    	EntityPartBuilder<EntityGnasher> partBuilder = new EntityPartBuilder<EntityGnasher>(this)
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
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(5, new DeepAbyssFollowFlockLeaderGoal(this));
		this.goalSelector.addGoal(4, new GnasherBiteGoal(this));
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(IS_LEADER, false);
	}
	
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) 
	{
        if(ANIMATION_STATE.equals(p_219422_) && this.level.isClientSide) 
        {
            switch(this.getAnimationState()) 
            {
        		case 0: 
        		{
        			this.stopAllAnimationStates();
        			break;
        		}
        		case 1:
        		{
        			this.stopAllAnimationStates();
        			this.biteAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.biteAnimationState.stop();
	}
	
	@Override
	public EntityDimensions getDimensions(Pose p_21047_) 
	{
		return this.isLeader() ? EntityDimensions.scalable(1.25F, 1.0F) : super.getDimensions(p_21047_);
	}
	
    @Override
    public void aiStep() 
    {
        super.aiStep();
        this.refreshDimensions();
        DeepAbyssUtil.fishFlopping(this);
    }
    
    @Override
    public void tick() 
    {
    	super.tick();
    	
        if(this.hasFollowers() && this.level.random.nextInt(200) == 1) 
        {
        	List<? extends EntityGnasher> list = this.level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
        	if(list.size() <= 1) 
        	{
        		this.schoolSize = 1;
        	}
        }
        
		List<EntityGnasher> list = this.level.getEntitiesOfClass(EntityGnasher.class, this.getBoundingBox().inflate(100));
		list.removeIf(t -> !t.isLeader());
		list.forEach(t -> 
		{
			if(!this.isLeader() && !this.isFollower())
			{
				this.startFollowing(t);
			}
		});
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_)
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putBoolean("isLeader", this.isLeader());
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_)
    {
    	super.readAdditionalSaveData(p_21450_);
    	if(p_21450_.contains("isLeader"))
    	{
    		this.setLeader(p_21450_.getBoolean("isLeader"));
    	}
    }
    
    public void setLeader(boolean value)
    {
    	this.entityData.set(IS_LEADER, value);
    }
    
    public boolean isLeader()
    {
    	return this.entityData.get(IS_LEADER);
    }

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	public int getMaxSchoolSize() 
	{
		return super.getMaxSpawnClusterSize();
	}
	
	private void addFollower()
	{
		++this.schoolSize;
	}

	private void removeFollower() 
	{
		--this.schoolSize;
	}
	
	public EntityGnasher startFollowing(EntityGnasher p_27526_)
	{
		this.leader = p_27526_;
		p_27526_.addFollower();
		return p_27526_;
	}
	
	@Override
	public boolean canRandomSwim()
	{
		return !this.isFollower();
	}

	@Override
	public boolean isFollower() 
	{
		return this.leader != null && this.leader.isAlive();
	}

	@Override
	public boolean hasFollowers() 
	{
		return this.schoolSize > 1;
	}

	@Override
	public boolean canBeFollowed() 
	{
		return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
	}

	@Override
	public boolean inRangeOfLeader() 
	{
		return this.distanceToSqr(this.leader) <= 121.0D;
	}

	@Override
	public void addFollowers(Stream<? extends AbstractDeepAbyssMonster> p_27534_)
	{
		p_27534_.limit((long)(this.getMaxSchoolSize() - this.schoolSize)).filter((p_27538_) -> 
		{
			return p_27538_ != this;
		}).forEach((p_27536_) -> 
		{
			((EntityGnasher) p_27536_).startFollowing(this);
		});
	}

	@Override
	public void pathToLeader() 
	{
		if(this.isFollower()) 
		{
			this.getNavigation().moveTo(this.leader, 1.0D);
		}
	}

	@Override
	public void stopFollowing() 
	{
		this.leader.removeFollower();
		this.leader = null;
	}
	
	@Override
	public int getMaxSpawnClusterSize() 
	{
		return 2;
	}
	
	public static boolean checkGnasherSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(350) == 0 && pPos.getY() >= -180 && pPos.getY() <= -100 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
	
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) 
	{
		if(Math.random() <= 0.1F) 
		{
			this.setAsLeader();
		}

		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
    public void setAsLeader()
    {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(15);
		this.setLeader(true);
    }
}
