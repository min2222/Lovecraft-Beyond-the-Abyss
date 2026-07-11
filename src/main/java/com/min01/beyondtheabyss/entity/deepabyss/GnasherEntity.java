package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ILeader;
import com.min01.beyondtheabyss.entity.ai.control.AnimationSwimmingMoveControl;
import com.min01.beyondtheabyss.entity.ai.control.BoidMoveControl;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GnasherBiteGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.MobClassification;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class GnasherEntity extends AbstractBTAMonster implements ILeader<GnasherEntity>
{
	public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(GnasherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DISPERSE = SynchedEntityData.defineId(GnasherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Optional<UUID>> LEADER_UUID = SynchedEntityData.defineId(GnasherEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public final SmoothAnimationState biteAnimationState = new SmoothAnimationState();
	
	public GnasherEntity(EntityType<? extends AbstractBTAMonster> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(6);
		this.moveControl = new BoidMoveControl<>(this);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 15.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.2F)
        		.add(Attributes.ATTACK_DAMAGE, 3.5F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(0, new GnasherBiteGoal(this));
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
	public EntityDimensions getDimensions(Pose pPose)
	{
		return this.isLeader() ? EntityDimensions.scalable(1.25F, 1.0F) : super.getDimensions(pPose);
	}
	
	@Override
	public boolean alertOthers() 
	{
		return true;
	}
	
	@Override
	protected void doPush(Entity pEntity) 
	{
		if(!(pEntity instanceof GnasherEntity))
		{
			super.doPush(pEntity);
		}
	}
    
    @Override
    public void tick() 
    {
    	super.tick();
        this.refreshDimensions();
        DeepAbyssUtil.fishFlopping(this);
        
        if(this.level.isClientSide)
        {
        	this.biteAnimationState.updateWhen(this.isAnimationPlaying(1), this.tickCount);
        }
		
		if(this.getLeader() != null)
		{
			GnasherEntity leader = this.getLeader();
			if(leader.isDisperse() && !this.isDisperse())
			{
				this.disperse(leader.position());
			}
			
			if(this.isDisperse() && !this.isLeader())
			{
				if(this.canMove() && this.canLook())
				{
					if(this.distanceTo(leader) <= 3.0F)
					{
						leader.setDisperse(false);
						this.setDisperse(false);
						this.switchControl(this.isInWater(), true);
					}
					else
					{
						this.getNavigation().moveTo(leader, 1.0F);
					}
				}
				else
				{
					if(this.distanceTo(leader) >= 12.0F)
					{
						leader.setStopMoveTick(0);
						leader.setStopLookTick(0);
						leader.switchControl(true);
						this.setStopMoveTick(0);
						this.setStopLookTick(0);
					}
				}
			}
		}
    }
    
	@Override
	public double getMeleeAttackRangeSqr(LivingEntity pEntity)
	{
		return (double)(this.getBbWidth() * 2.5F * this.getBbWidth() * 2.5F + pEntity.getBbWidth());
	}
    
	@Override
	protected SoundEvent getAmbientSound()
	{
		return BTASounds.GNASHER_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() 
	{
		return BTASounds.GNASHER_DEATH.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource)
	{
		return BTASounds.GNASHER_HURT.get();
	}
    
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) 
    {
    	if(this.isLeader() && !this.isDisperse() && pSource.getDirectEntity() != null)
    	{
    		this.disperse(pSource.getDirectEntity().position());
    	}
    	return super.hurt(pSource, pAmount);
    }
    
    public void disperse(Vec3 pos)
    {
    	this.getNavigation().stop();
		this.setDisperse(true);
		this.setTarget(null);
		this.setStopMoveTick(Integer.MAX_VALUE);
		this.setStopLookTick(Integer.MAX_VALUE);
		this.switchControl(this.isInWater(), false);
        Vec3 vec3 = DefaultRandomPos.getPosAway(this, 16, 7, pos);
        if(vec3 != null)
        {
        	this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.0F);
        }
    }
    
    public void switchControl(boolean isWater, boolean isBoid)
    {
    	if(isWater && !isBoid && !(this.moveControl instanceof AnimationSwimmingMoveControl))
    	{
    		this.moveControl = new AnimationSwimmingMoveControl<>(this);
    		this.lookControl = new SmoothSwimmingLookControl(this, 10);
    	}
    	
    	if(isBoid && !(this.moveControl instanceof BoidMoveControl))
    	{
    		this.moveControl = new BoidMoveControl<>(this);
    		this.lookControl = new SmoothSwimmingLookControl(this, 10);
    	}
    }
    
	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public MobClassification getMobClassification() 
	{
		return MobClassification.WATER;
	}
	
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound)
    {
    	super.addAdditionalSaveData(pCompound);
    	pCompound.putBoolean("isLeader", this.isLeader());
		if(this.entityData.get(LEADER_UUID).isPresent())
		{
			pCompound.putUUID("LeaderUUID", this.entityData.get(LEADER_UUID).get());
		}
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) 
    {
    	super.readAdditionalSaveData(pCompound);
		this.setLeader(pCompound.getBoolean("isLeader"));
		if(pCompound.hasUUID("LeaderUUID")) 
		{
			this.entityData.set(LEADER_UUID, Optional.of(pCompound.getUUID("LeaderUUID")));
		}
    }
	
	@SuppressWarnings("deprecation")
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) 
	{
		if(Math.random() <= 0.1F)
		{
			this.setLeader(true);
			this.setHealth(30.0F);
		}
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}
	
	@Override
	public int getMaxSpawnClusterSize() 
	{
		return 100;
	}
	
	@Override
	public void moveToTarget() 
	{
		super.moveToTarget();
		if(this.getMoveControl() instanceof BoidMoveControl control)
		{
			control.boid.target = this.getTarget().position();
		}
	}
	
	@Override
	public boolean canMoveAround() 
	{
		return super.canMoveAround() && !this.isDisperse();
	}
	
	public static boolean checkGnasherSpawnRules(EntityType<? extends AbstractBTAMonster> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
    public void setLeader(boolean value)
    {
    	if(value)
    	{
    		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
    		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
    		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(45);
    	}
    	this.entityData.set(IS_LEADER, value);
    }

    @Override
    public boolean isLeader()
    {
    	return this.entityData.get(IS_LEADER);
    }
	
    @Override
	public void setLeader(GnasherEntity leader)
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
	@Override
	public GnasherEntity getLeader() 
	{
		if(this.entityData.get(LEADER_UUID).isPresent()) 
		{
			return (GnasherEntity) BTAUtil.getEntityByUUID(this.level, this.entityData.get(LEADER_UUID).get());
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
}
