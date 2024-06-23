package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthBiteGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthDashPrepareGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthTailSwingGoal;
import com.min01.beyondtheabyss.entity.part.AbstractBTAEntityPart;
import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityGhidruth extends AbstractMultipartDeepAbyssMob
{
	public BasicBTAEntityPart head = new BasicBTAEntityPart(this, 4.5F, 4.5F);
	public BasicBTAEntityPart body = new BasicBTAEntityPart(this, 5.5F, 4.5F);
	public BasicBTAEntityPart tail = new BasicBTAEntityPart(this, 5.5F, 4.3F);
	public BasicBTAEntityPart[] parts = { this.head, this.body, this.tail };
	public AnimationState biteRightAnimationState = new AnimationState();
	public AnimationState biteLeftAnimationState = new AnimationState();
	public AnimationState tailSwingRightAnimationState = new AnimationState();
	public AnimationState tailSwingLeftAnimationState = new AnimationState();
	public AnimationState dashAnimationState = new AnimationState();
	public AnimationState dashPrepareAnimationState = new AnimationState();
	public AnimationState stunAnimationState = new AnimationState();
	public AnimationState stunLoopAnimationState = new AnimationState();
	public AnimationState stunEndAnimationState = new AnimationState();
	
	public static final EntityDataAccessor<Vec3> DASH_POS = SynchedEntityData.defineId(EntityGhidruth.class, BTAEntityDataSerializers.VEC3.get());
	public static final EntityDataAccessor<Boolean> IS_DASH = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_STUN = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> ATTACK_COUNT = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> STUN_TICK = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.INT);
	
	public static final double DEFAULT_MOVEMENT_SPEED = 1.0D;
	
	public EntityGhidruth(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_) 
	{
		super(p_33002_, p_33003_);
		this.posArray = new Vec3[3];
		this.xpReward = 1000 + this.random.nextInt(100);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 300)
    			.add(Attributes.MOVEMENT_SPEED, DEFAULT_MOVEMENT_SPEED)
        		.add(Attributes.ATTACK_DAMAGE, 5)
        		.add(Attributes.FOLLOW_RANGE, 100)
        		.add(Attributes.ARMOR, 150)
        		.add(Attributes.ARMOR_TOUGHNESS, 150)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 10);
    }
    
    @Override
    protected void defineSynchedData() 
    {
        super.defineSynchedData();
        this.entityData.define(DASH_POS, Vec3.ZERO);
        this.entityData.define(IS_DASH, false);
        this.entityData.define(IS_STUN, false);
        this.entityData.define(ATTACK_COUNT, 0);
        this.entityData.define(STUN_TICK, 0);
    }
    
    @Override
    protected void registerGoals()
    {
    	super.registerGoals();
        this.goalSelector.addGoal(4, new GhidruthBiteGoal(this));
        this.goalSelector.addGoal(4, new GhidruthTailSwingGoal(this));
        this.goalSelector.addGoal(4, new GhidruthDashPrepareGoal(this));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
    }
    
    @Override
    protected ResourceLocation getDefaultLootTable() 
    {
    	return new ResourceLocation(BeyondtheAbyss.MODID, "entity/ghidruth");
    }
    
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) 
	{
        if(ANIMATION_STATE.equals(p_219422_) && this.level.isClientSide) 
        {
            switch (this.getAnimationState()) 
            {
        		case 0: 
        		{
        			this.stopAllAnimationStates();
        			break;
        		}
        		case 1:
        		{
        			this.stopAllAnimationStates();
        			if(this.random.nextBoolean())
        			{
            			this.biteRightAnimationState.start(this.tickCount);
        			}
        			else
        			{
            			this.biteLeftAnimationState.start(this.tickCount);
        			}
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			if(this.random.nextBoolean())
        			{
            			this.tailSwingRightAnimationState.start(this.tickCount);
        			}
        			else
        			{
            			this.tailSwingLeftAnimationState.start(this.tickCount);
        			}
        			break;
        		}
        		case 3:
        		{
        			this.stopAllAnimationStates();
        			this.dashPrepareAnimationState.start(this.tickCount);
        			break;
        		}
        		case 4:
        		{
        			this.stopAllAnimationStates();
        			this.dashAnimationState.start(this.tickCount);
        			break;
        		}
        		case 5:
        		{
        			this.stopAllAnimationStates();
        			this.stunAnimationState.start(this.tickCount);
        			break;
        		}
        		case 6:
        		{
        			this.stopAllAnimationStates();
        			this.stunLoopAnimationState.start(this.tickCount);
        			break;
        		}
        		case 7:
        		{
        			this.stopAllAnimationStates();
        			this.stunEndAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
        super.onSyncedDataUpdated(p_219422_);
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.biteRightAnimationState.stop();
		this.biteLeftAnimationState.stop();
		this.tailSwingRightAnimationState.stop();
		this.tailSwingLeftAnimationState.stop();
		this.dashPrepareAnimationState.stop();
		this.dashAnimationState.stop();
		this.stunAnimationState.stop();
		this.stunLoopAnimationState.stop();
		this.stunEndAnimationState.stop();
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
    public void tick()
    {
    	super.tick();
    	
        this.setPartPosition(this.tail, this.posArray[2]);
        this.setPartPosition(this.body, this.posArray[1]);
        this.setPartPosition(this.head, this.posArray[0]);

        if(this.isDash())
        {
        	if(this.tickCount % 10 == 0)
        	{
            	this.playSound(BTASounds.GHIDRUTH_CHARGE_LOOP.get());
        	}
        	Vec3 vec3 = this.getDashPos();
			this.setDeltaMovement(BTAUtil.fromToVector(this.position(), vec3, (float) this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED)));
			this.lookAt(this.getLookAnchor(), vec3);
			
			List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.head.getBoundingBox().inflate(3));
			list.removeIf((living) -> living == this);
			list.forEach((living) -> living.hurt(DamageSource.mobAttack(this), 4));
	        
			boolean flag = false;
	        HitResult hitResult = this.level.clip(new ClipContext(this.head.position(), this.head.position().add(BTAUtil.getLookPos(this.getXRot(), this.getYHeadRot(), 0, 6)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
	        if(hitResult instanceof BlockHitResult blockHit)
	        {
	        	BlockPos blockPos = blockHit.getBlockPos();
				int i2 = Mth.floor(blockPos.getX());
				int j1 = Mth.floor(blockPos.getY());
                int j2 = Mth.floor(blockPos.getZ());
                for(int j = -6; j <= 6; ++j)
                {
                	for(int k2 = -6; k2 <= 6; ++k2)
                	{
                		for(int k = 0; k <= 6; ++k) 
                		{
                			int l2 = i2 + j;
                			int l = j1 + k;
                			int i1 = j2 + k2;
                			BlockPos blockpos = new BlockPos(l2, l, i1);
                			BlockState blockstate = this.level.getBlockState(blockpos);
                			flag = !blockstate.isAir() && !blockstate.getMaterial().isLiquid();
                		}
                	}
                }
	        }
	        
	        if(flag)
	        {
	        	this.stopDashAndStun();
	        }
	        
	        if(this.distanceToSqr(vec3) <= 2 || !this.hasTarget())
	        {
	        	this.stopDash();
	        }
        }
        
        if(this.isStun())
        {
        	this.setXRot(this.xRotO);
        	this.setYBodyRot(this.yBodyRotO);
        	this.setYHeadRot(this.yHeadRotO);
        	if(this.getStunTick() == 2)
        	{
            	this.playSound(BTASounds.GHIDRUTH_STUN.get());
        	}
        	
        	if(this.getStunTick() == 35)
        	{
        		this.setAnimationState(6);
        	}
        	
        	if(this.getStunTick() == 100)
        	{
        		this.setAnimationState(7);
        		this.playSound(BTASounds.GHIDRUTH_AWAKEN.get());
        	}
        	
        	if(this.getStunTick() == 125)
        	{
        		this.setAnimationState(0);
        		this.setStunTick(0);
        		this.setStun(false);
        		this.setNoAi(false);
        		this.setCanLookOrMove(true);
        		this.getAttribute(Attributes.ARMOR).setBaseValue(150);
        		this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(150);
        	}
        	
        	if(this.getStunTick() < 125)
        	{
            	this.setStunTick(this.getStunTick() + 1);
        	}
        }
    }
    
	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) 
	{
		return super.hurt(p_21016_, this.isStun() ? p_21017_ * 2 : p_21017_ * 0.3F);
	}
	
	@Override
	protected float getSoundVolume() 
	{
		return 0.45F;
	}
    
    public void stopDashAndStun()
    {
    	this.setAnimationState(5);
		this.setDash(false);
		this.setStun(true);
    	this.setNoAi(true);
		this.setCanLookOrMove(false);
		this.setAttackCount(0);
		this.setDashPos(Vec3.ZERO);
		this.setDeltaMovement(Vec3.ZERO);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(DEFAULT_MOVEMENT_SPEED);
		this.getAttribute(Attributes.ARMOR).setBaseValue(10);
		this.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(10);
    }
    
    public void stopDash()
    {
		this.setAnimationState(0);
		this.setDash(false);
		this.setCanLookOrMove(true);
		this.setAttackCount(0);
		this.setDashPos(Vec3.ZERO);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(DEFAULT_MOVEMENT_SPEED);
    }

    @Override
    public int getBodyRotationSpeed() 
    {
    	return !this.hasTarget() ? 2 : 6;
    }
    
    @Override
    public BTAMobType getBTAMobType()
    {
    	return BTAMobType.BOSS;
    }

	@Override
	public AbstractBTAEntityPart<AbstractBTAMob>[] getDeepAbyssEntityParts() 
	{
		return this.parts;
	}
    
    public void setStunTick(int count)
    {
    	this.entityData.set(STUN_TICK, count);
    }
      
    public int getStunTick() 
    {
    	return this.entityData.get(STUN_TICK);
    }
    
    public void setStun(boolean value)
    {
    	this.entityData.set(IS_STUN, value);
    }
      
    public boolean isStun() 
    {
    	return this.entityData.get(IS_STUN);
    }
    
    public void setDashPos(Vec3 value)
    {
    	this.entityData.set(DASH_POS, value);
    }
      
    public Vec3 getDashPos() 
    {
    	return this.entityData.get(DASH_POS);
    }
    
    public void setDash(boolean value)
    {
    	this.entityData.set(IS_DASH, value);
    }
      
    public boolean isDash() 
    {
    	return this.entityData.get(IS_DASH);
    }
    
    public void setAttackCount(int count)
    {
    	this.entityData.set(ATTACK_COUNT, count);
    }
      
    public int getAttackCount() 
    {
    	return this.entityData.get(ATTACK_COUNT);
    }
}
