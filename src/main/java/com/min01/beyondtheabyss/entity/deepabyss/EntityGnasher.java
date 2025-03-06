package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GnasherBiteGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class EntityGnasher extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(EntityGnasher.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DISPERSE = SynchedEntityData.defineId(EntityGnasher.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Optional<UUID>> LEADER_UUID = SynchedEntityData.defineId(EntityGnasher.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public final AnimationState biteAnimationState = new AnimationState();
	
	public final List<EntityGnasher> list = new ArrayList<>();
	
	public EntityGnasher(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(6);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 15.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
        		.add(Attributes.ATTACK_DAMAGE, 2.5F)
        		.add(Attributes.FOLLOW_RANGE, 10.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
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
		this.goalSelector.addGoal(4, new GnasherBiteGoal(this));
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(IS_LEADER, false);
		this.entityData.define(IS_DISPERSE, false);
		this.entityData.define(LEADER_UUID, Optional.empty());
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
    public void tick() 
    {
    	super.tick();
        this.refreshDimensions();
        DeepAbyssUtil.fishFlopping(this);
		if(this.tickCount == 2 && this.isLeader())
		{
        	this.partBuilder.rebuildHitbox();
		}
		
		if(this.isDisperse() && this.getNavigation().isDone())
		{
			this.setDisperse(false);
		}

		if(!this.isLeader())
		{
			EntityGnasher leader = this.getLeader();
			if(leader != null)
			{
				if(!leader.list.contains(this))
				{
					leader.list.add(this);
				}
				if(!this.isDisperse())
				{			
					if(this.distanceTo(leader) > 2.5F)
					{
						this.getNavigation().moveTo(leader, 0.8F);
					}
					else
					{
						if(leader.getNavigation().getPath() != null)
						{
							BlockPos pos = leader.getNavigation().getPath().getTarget();
							Path path = this.getNavigation().createPath(pos, 1);
							this.getNavigation().moveTo(path, 0.8F);
						}
						if(leader.getTarget() != null)
						{
							this.setTarget(leader.getTarget());
						}
					}
				}
			}
		}
    }
    
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) 
    {
    	if(this.isLeader() && !this.isDisperse() && p_21016_.getDirectEntity() != null)
    	{
	        this.list.forEach(t -> 
	        {
	    		t.setDisperse(true);
				t.setTarget(null);
		        Vec3 vec3 = DefaultRandomPos.getPosAway(t, 16, 7, p_21016_.getDirectEntity().position());
		        if(vec3 != null)
		        {
		        	t.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0F);
		        }
	        });
    		this.setDisperse(true);
			this.setTarget(null);
	        Vec3 vec3 = DefaultRandomPos.getPosAway(this, 16, 7, p_21016_.getDirectEntity().position());
	        if(vec3 != null)
	        {
	        	this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0F);
	        }
    	}
    	return super.hurt(p_21016_, p_21017_);
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_)
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putBoolean("isLeader", this.isLeader());
    	p_21484_.putBoolean("isDisperse", this.isDisperse());
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_)
    {
    	super.readAdditionalSaveData(p_21450_);
    	if(p_21450_.contains("isLeader"))
    	{
    		this.setAsLeader(p_21450_.getBoolean("isLeader"));
    	}
    	if(p_21450_.contains("isDisperse"))
    	{
    		this.setDisperse(p_21450_.getBoolean("isDisperse"));
    	}
    }
    
	public void setLeader(EntityGnasher leader)
	{
		this.entityData.set(LEADER_UUID, Optional.of(leader.getUUID()));
	}
	
	@Nullable
	public EntityGnasher getLeader() 
	{
		if(this.entityData.get(LEADER_UUID).isPresent()) 
		{
			return (EntityGnasher) BTAUtil.getEntityByUUID(this.level, this.entityData.get(LEADER_UUID).get());
		}
		return null;
	}
    
    public void setAsLeader(boolean value)
    {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(15);
    	this.entityData.set(IS_LEADER, value);
    }
    
    public boolean isLeader()
    {
    	return this.entityData.get(IS_LEADER);
    }
    
    public void setDisperse(boolean value)
    {
    	this.entityData.set(IS_DISPERSE, value);
    }
    
    public boolean isDisperse()
    {
    	return this.entityData.get(IS_DISPERSE);
    }

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public boolean useSubRoot()
	{
		return true;
	}
	
	@Override
	public String subRoot()
	{
		if(this.isLeader())
		{
			return "LeadGnasher";
		}
		return "Gnasher";
	}
	
	@Override
	public boolean rotateHead() 
	{
		return true;
	}
	
	@Override
	public int getMaxSpawnClusterSize() 
	{
		return 1;
	}
	
	@Override
	public boolean canSwim() 
	{
		return super.canSwim() && !this.isDisperse() && this.getLeader() == null;
	}
	
	public static boolean checkGnasherSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pPos.getY() >= 30 && pPos.getY() <= 80 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
	
	@SuppressWarnings("deprecation")
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) 
	{
		if(p_21436_ == MobSpawnType.NATURAL)
		{
			this.spawnAsSwarm();
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	public void spawnAsSwarm()
	{
    	this.setHealth(30);
		this.setAsLeader(true);
		for(int i = 0; i < 5; i++) 
		{
			EntityGnasher entity = new EntityGnasher(BTAEntities.GNASHER.get(), this.level);
			entity.setPos(this.position());
			entity.setLeader(this);
			this.list.add(entity);
			this.level.addFreshEntity(entity);
		}
	}
}
