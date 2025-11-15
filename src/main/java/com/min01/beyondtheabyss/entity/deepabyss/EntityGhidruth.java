package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.EntityFallingStone;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthBiteGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthChargePrepareGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthTailSwingGoal;
import com.min01.beyondtheabyss.misc.BTABossBarType;
import com.min01.beyondtheabyss.misc.BTABossEvent;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.particle.DustCloudParticle;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityGhidruth extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Boolean> IS_CHARGE = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_STUN = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.BOOLEAN);
	
	public final SmoothAnimationState biteRightAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState biteLeftAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState tailSwingRightAnimationState = new SmoothAnimationState(0.999F);
	public final SmoothAnimationState tailSwingLeftAnimationState = new SmoothAnimationState(0.999F);
	public final SmoothAnimationState chargePrepareAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState stunnedAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState stunLoopAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState stunEndAnimationState = new SmoothAnimationState();
	
	public int stunTick;
	public int chargeTick;
	
	public final BTABossEvent bossEvent = (BTABossEvent) new BTABossEvent(this.getDisplayName(), BTABossBarType.GHIDRUTH, this).setDarkenScreen(true);
	
	public EntityGhidruth(EntityType<? extends Monster> p_33002_, Level p_33003_) 
	{
		super(p_33002_, p_33003_);
		this.xpReward = 1000 + this.random.nextInt(100);
		this.posArray = new Vec3[1];
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 300.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.95F)
        		.add(Attributes.ATTACK_DAMAGE, 20.0F)
        		.add(Attributes.FOLLOW_RANGE, 100.0F)
        		.add(Attributes.ARMOR, 20.0F)
        		.add(Attributes.ARMOR_TOUGHNESS, 20.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 10.0F);
    }
	
    @Override
    public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
    {
    	EntityPartBuilder<EntityGhidruth> partBuilder = new EntityPartBuilder<EntityGhidruth>(this)
    	{
    		@Override
    		public Vec3 getOffset()
    		{
    			return new Vec3(0.0F, 2.25F, 0.0F);
    		}
    		
    		@Override
    		public float getRenderScale() 
    		{
    			return 1.5F;
    		}
    	};
    	return partBuilder;
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(IS_CHARGE, false);
    	this.entityData.define(IS_STUN, false);
    }
    
    @Override
    protected void registerGoals()
    {
    	super.registerGoals();
    	this.goalSelector.addGoal(0, new GhidruthBiteGoal(this));
    	this.goalSelector.addGoal(0, new GhidruthTailSwingGoal(this));
    	this.goalSelector.addGoal(0, new GhidruthChargePrepareGoal(this));
    }
    
    @Override
    public void tick()
    {
    	super.tick();
    	
    	if(!this.level.isClientSide)
    	{
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    	}
    	
    	if(this.level.isClientSide)
    	{
    		this.biteRightAnimationState.updateWhen(this.isUsingSkill(1), this.tickCount);
    		this.biteLeftAnimationState.updateWhen(this.isUsingSkill(2), this.tickCount);
    		this.tailSwingRightAnimationState.updateWhen(this.isUsingSkill(3), this.tickCount);
    		this.tailSwingLeftAnimationState.updateWhen(this.isUsingSkill(4), this.tickCount);
    		this.chargePrepareAnimationState.updateWhen(this.isUsingSkill(5), this.tickCount);
    		this.stunnedAnimationState.updateWhen(this.isUsingSkill(6), this.tickCount);
    		this.stunLoopAnimationState.updateWhen(this.getAnimationState() == 0 && this.isStun(), this.tickCount);
    		this.stunEndAnimationState.updateWhen(this.isUsingSkill(7), this.tickCount);
    	}
        
    	if(this.isStun())
    	{
    		if(this.getAnimationTick() <= 0)
    		{
        		this.stunTick++;
        		if(this.getAnimationState() == 6)
        		{
    				this.setAnimationState(0);
        		}
        		else if(this.getAnimationState() == 0)
        		{
        			if(this.stunTick >= 150)
        			{
            			this.setAnimationState(7);
            			this.setAnimationTick(25);
        				this.playSound(BTASounds.GHIDRUTH_AWAKEN.get());
        			}
        		}
        		else if(this.getAnimationState() == 7)
        		{
    				this.setCanLook(true);
    				this.setCanMove(true);
    				this.setStun(false);
    				this.stunTick = 0;
        		}
    		}
    	}

		if(this.getTarget() != null && !this.isCharge() && !this.isStun())
		{
			this.lookTarget();
			this.moveToTarget();
		}
    	
    	if(this.isCharge())
    	{
    		if(this.horizontalCollision || this.verticalCollision)
    		{
				this.setCharge(false);
				this.setCanLook(false);
				this.setCanMove(false);
				this.setStun(true);
				this.setAnimationState(6);
				this.setAnimationTick(35);
				this.setLastLookPos(Vec3.ZERO);
				this.setDeltaMovement(Vec3.ZERO);
				EntityBTACameraShake.cameraShake(this.level, this.position(), 100.0F, 0.35F, 0, 25);
				this.playSound(BTASounds.GHIDRUTH_STUN.get());
				this.getNavigation().stop();
				this.chargeTick = 0;
				this.fallStones();
    		}
    		else if(!this.getLastLookPos().equals(Vec3.ZERO))
    		{
    			this.chargeTick++;
    			if(this.position().distanceTo(this.getLastLookPos()) <= 4.0F || this.chargeTick >= 200)
    			{
    				this.setCharge(false);
    				this.setCanLook(true);
    				this.setCanMove(true);
    				this.setLastLookPos(Vec3.ZERO);
    				this.setDeltaMovement(Vec3.ZERO);
    				this.getNavigation().stop();
    				this.chargeTick = 0;
    			}
    			else
    			{
    				Vec3 pos = this.getLastLookPos();
    				this.getNavigation().moveTo(pos.x, pos.y, pos.z, 2.5F);
            		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.5F), t -> t != this && !t.isAlliedTo(this));
            		list.forEach(t ->
            		{
            			this.doHurtTarget(t);
            		});
    			}
    		}
    	}
    }
    
    public void fallStones()
    {
    	this.level.broadcastEntityEvent(this, (byte) 99);
    	for(int i = 0; i < this.random.nextInt(20, 35); i++)
    	{
    		Vec3 spreadPos = BTAUtil.getSpreadPosition(this, new Vec3(15, 2, 15));
    		HitResult result = this.level.clip(new ClipContext(this.position(), spreadPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
    		Vec3 pos = result.getLocation();
    		BlockPos ceilingPos = BTAUtil.getCeilingPos(this.level, pos.x, this.getY(), pos.z, -1);
    		EntityFallingStone stone = new EntityFallingStone(BTAEntities.FALLING_STONE.get(), this.level);
    		stone.setOwner(this);
    		stone.setPos(Vec3.atCenterOf(ceilingPos.below()));
    		stone.setBlockState(this.level.getBlockState(ceilingPos));
    		stone.setRotation(this.random.nextFloat() * 270.0F);
    		stone.setDelay(this.random.nextInt(15, 60));
    		this.level.addFreshEntity(stone);
    	}
    }
    
    @Override
    public void handleEntityEvent(byte p_21375_) 
    {
    	super.handleEntityEvent(p_21375_);
    	if(p_21375_ == 99)
    	{
        	for(int i = 0; i < this.random.nextInt(20, 35); i++)
        	{
        		Vec3 spreadPos = BTAUtil.getSpreadPosition(this, new Vec3(15, 2, 15));
        		HitResult result = this.level.clip(new ClipContext(this.position(), spreadPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        		Vec3 pos = result.getLocation();
        		BlockPos ceilingPos = BTAUtil.getCeilingPos(this.level, pos.x, this.getY(), pos.z, -1);
        		BlockPos below = ceilingPos.below(2);
        		for(int j = 0; j < 150; j++)
        		{
    				this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.level.getBlockState(ceilingPos)), below.getX(), below.getY(), below.getZ(), this.random.nextGaussian() * 0.5F, 0.0F, this.random.nextGaussian() * 0.5F);
        		}
        	}
        	
        	for(int i = 0; i < this.random.nextInt(10, 20); i++)
        	{
        		Vec3 spreadPos = BTAUtil.getSpreadPosition(this, new Vec3(15, 4, 15));
        		for(int j = 0; j < 30; j++)
        		{
        			this.level.addParticle(new DustCloudParticle.DustCloudParticleOption(BTABlocks.ABYSSALITH.get().defaultBlockState(), 1.5F), spreadPos.x, spreadPos.y, spreadPos.z, this.random.nextGaussian() * 0.5F, this.random.nextGaussian() * 0.5F, this.random.nextGaussian() * 0.5F);
        		}
        	}
    	}
    }
    
    @Override
    public boolean isEffectiveAi()
    {
    	return super.isEffectiveAi() && !this.isStun();
    }
	
	@Override
	public void push(double p_20286_, double p_20287_, double p_20288_) 
	{
		
	}
    
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) 
    {
    	if(this.isStun())
    	{
        	this.walkAnimation.setSpeed(0.0F);
    		p_21017_ *= 2.0F;
    	}
    	else if(!p_21016_.is(DamageTypeTags.BYPASSES_ARMOR) && !p_21016_.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
    	{
    		p_21017_ *= 0.1F;
    	}
    	return super.hurt(p_21016_, p_21017_);
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) 
    {
    	return BTASounds.GHIDRUTH_HURT.get();
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
    	return BTASounds.GHIDRUTH_AMBIENT.get();
    }
	
	@Override
	protected float getSoundVolume() 
	{
		return 0.45F;
	}
	
    @Override
    public int maxTurnX() 
    {
    	return !this.hasTarget() ? 55 : 65;
    }

    @Override
    public int maxTurnY() 
    {
    	return !this.hasTarget() ? 6 : 8;
    }
    
    @Override
    public BTAMobType getBTAMobType()
    {
    	return BTAMobType.BOSS;
    }
    
    @Override
    public int targetSettingInterval()
    {
    	return 100;
    }
    
    @Override
    public Vec3 getMoveRadius()
    {
    	return new Vec3(40, 10, 40);
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) 
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putBoolean("isCharge", this.isCharge());
    	p_21484_.putBoolean("isStun", this.isStun());
    	p_21484_.putInt("StunTick", this.stunTick);
    	p_21484_.putInt("ChargeTick", this.chargeTick);
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_)
    {
    	super.readAdditionalSaveData(p_21450_);
    	this.setCharge(p_21450_.getBoolean("isCharge"));
    	this.setStun(p_21450_.getBoolean("isStun"));
    	this.stunTick = p_21450_.getInt("StunTick");
    	this.chargeTick = p_21450_.getInt("ChargeTick");
        if(this.hasCustomName()) 
        {
        	this.bossEvent.setName(this.getDisplayName());
        }
    }
    
    @Override
    public void setCustomName(@Nullable Component p_31476_) 
    {
    	super.setCustomName(p_31476_);
    	this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer p_31483_)
    {
        super.startSeenByPlayer(p_31483_);
        this.bossEvent.addPlayer(p_31483_);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer p_31488_)
    {
    	super.stopSeenByPlayer(p_31488_);
    	this.bossEvent.removePlayer(p_31488_);
    }
    
    public void setCharge(boolean value)
    {
    	this.entityData.set(IS_CHARGE, value);
    }
    
    public boolean isCharge()
    {
    	return this.entityData.get(IS_CHARGE);
    }
    
    public void setStun(boolean value)
    {
    	this.entityData.set(IS_STUN, value);
    }
    
    public boolean isStun()
    {
    	return this.entityData.get(IS_STUN);
    }
}
