package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.control.SwimmingBoidMoveControl;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GnasherBiteGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EntityGnasher extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(EntityGnasher.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DISPERSE = SynchedEntityData.defineId(EntityGnasher.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Optional<UUID>> LEADER_UUID = SynchedEntityData.defineId(EntityGnasher.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public final SmoothAnimationState biteAnimationState = new SmoothAnimationState();
	
	public EntityGnasher(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(6);
		this.moveControl = new SwimmingBoidMoveControl(this, 0.1F, false);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 15.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
        		.add(Attributes.ATTACK_DAMAGE, 2.5F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
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
		Set<WrappedGoal> set = this.goalSelector.getAvailableGoals();
		set.removeIf(t -> t.getGoal() instanceof HurtByTargetGoal);
		this.goalSelector.addGoal(4, new HurtByTargetGoal(this).setAlertOthers());
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
        
        if(this.level.isClientSide)
        {
        	this.biteAnimationState.updateWhen(this.isUsingSkill(1), this.tickCount);
        }
        
        if(!this.level.isClientSide && this.moveControl instanceof SwimmingBoidMoveControl boid)
        {
        	if(this.getTarget() != null)
        	{
        		boid.setForceTarget(this.getTarget().position());
        	}
        	else
        	{
        		boid.setForceTarget(false);
        	}
        }
		
		if(this.getLeader() != null)
		{
			EntityGnasher leader = this.getLeader();
			if(leader.isDisperse() && !this.isDisperse() && leader.getLastHurtByMob() != null)
			{
				this.disperse(leader.getLastHurtByMob().position());
			}
			
			if(this.isDisperse() && !this.isLeader())
			{
				if(this.canMove() && this.canLook())
				{
					if(this.distanceTo(this.getLeader()) <= 3.0F)
					{
						this.getLeader().setDisperse(false);
						this.setDisperse(false);
						this.switchControl(true);
					}
					else
					{
						this.getNavigation().moveTo(this.getLeader(), 1.0F);
					}
				}
				else
				{
					if(this.distanceTo(this.getLeader()) >= 6.0F)
					{
						this.getLeader().setCanMove(true);
						this.getLeader().setCanLook(true);
						this.getLeader().switchControl(true);
						this.setCanMove(true);
						this.setCanLook(true);
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
    		this.disperse(p_21016_.getDirectEntity().position());
    	}
    	return super.hurt(p_21016_, p_21017_);
    }
    
    public void disperse(Vec3 pos)
    {
		this.setDisperse(true);
		this.setTarget(null);
		this.setCanMove(false);
		this.setCanLook(false);
		this.switchControl(false);
        Vec3 vec3 = DefaultRandomPos.getPosAway(this, 16, 7, pos);
        if(vec3 != null)
        {
        	this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0F);
        }
    }
    
    public void switchControl(boolean isBoid)
    {
    	if(isBoid)
    	{
    		this.moveControl = new SwimmingBoidMoveControl(this, 0.1F, false);
    	}
    	else
    	{
    		this.moveControl = this.getSwimmingMoveControl();
    	}
    }
    
	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_)
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putBoolean("isLeader", this.isLeader());
		if(this.entityData.get(LEADER_UUID).isPresent())
		{
			p_21484_.putUUID("Leader", this.entityData.get(LEADER_UUID).get());
		}
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) 
    {
    	super.readAdditionalSaveData(p_21450_);
		this.setLeader(p_21450_.getBoolean("isLeader"));
		if(p_21450_.hasUUID("Leader")) 
		{
			this.entityData.set(LEADER_UUID, Optional.of(p_21450_.getUUID("Leader")));
		}
    }
	
	@SuppressWarnings("deprecation")
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) 
	{
		if(Math.random() <= 0.1F)
		{
			this.setLeader(true);
			this.setHealth(30.0F);
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	@Override
	public int getMaxSpawnClusterSize() 
	{
		return 100;
	}
	
	@Override
	public boolean ignoreOperation() 
	{
		return true;
	}
	
	public static boolean checkGnasherSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
    public void setLeader(boolean value)
    {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(45);
    	this.entityData.set(IS_LEADER, value);
    }

    public boolean isLeader()
    {
    	return this.entityData.get(IS_LEADER);
    }
	
	public void setLeader(EntityGnasher leader)
	{
		if(leader == null) 
		{
			this.entityData.set(LEADER_UUID, Optional.empty());
		}
		else
		{
			this.entityData.set(LEADER_UUID, Optional.of(leader.getUUID()));
		}
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
    
    public void setDisperse(boolean value)
    {
    	this.entityData.set(IS_DISPERSE, value);
    }
    
    public boolean isDisperse()
    {
    	return this.entityData.get(IS_DISPERSE);
    }
	
	@Override
	public boolean canSwim() 
	{
		return super.canSwim() && !this.isDisperse();
	}
}
