package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthBiteGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthTailSwingGoal;
import com.min01.beyondtheabyss.entity.model.ModelGhidruth;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.sound.BTASounds;

import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityGhidruth extends AbstractMultipartDeepAbyssMob<EntityGhidruth>
{
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
    	//TODO
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
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public EntityPartBuilder<EntityGhidruth> getPartBuilder() 
    {
    	ModelGhidruth model = new ModelGhidruth(Minecraft.getInstance().getEntityModels().bakeLayer(ModelGhidruth.LAYER_LOCATION));
    	EntityPartBuilder<EntityGhidruth> partBuilder = new EntityPartBuilder<EntityGhidruth>(this, model)
    	{
    		@Override
    		public Vec3 getOffset()
    		{
    			return new Vec3(0.0F, 2.5F, 0.0F);
    		}
    		
    		@Override
    		public float getRenderScale() 
    		{
    			return 1.5F;
    		}
    	};
    	return partBuilder;
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
