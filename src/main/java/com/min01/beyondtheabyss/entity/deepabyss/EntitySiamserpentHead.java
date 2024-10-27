package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.SiamserpentBlasterBeamGoal;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.WormSegmentController;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntitySiamserpentHead extends AbstractOwnableDeepAbyssMonster<AbstractDeepAbyssMonster>
{
	public static final EntityDataAccessor<Integer> HEAD_TYPE = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_DISABLED = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DORMANT = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> CHAIN_CREATED = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Float> BEAM_LENGTH = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.FLOAT);

	public final List<EntitySiamserpentBone> segments = new ArrayList<>();
	
	public AnimationState beamStartAnimationState = new AnimationState();
	public AnimationState beamStopAnimationState = new AnimationState();
	
	public EntitySiamserpentHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(15);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 60.0F)
    			.add(Attributes.MOVEMENT_SPEED, 1.2F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(HEAD_TYPE, 0);
    	this.entityData.define(IS_DISABLED, false);
    	this.entityData.define(IS_DORMANT, false);
    	this.entityData.define(CHAIN_CREATED, false);
    	this.entityData.define(BEAM_LENGTH, 0.0F);
    }
    
	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntitySiamserpentHead> partBuilder = new EntityPartBuilder<EntitySiamserpentHead>(this);
		return partBuilder;
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
        			this.beamStartAnimationState.start(this.tickCount);
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			this.beamStopAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.beamStartAnimationState.stop();
		this.beamStopAnimationState.stop();
	}
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(4, new SiamserpentBlasterBeamGoal(this));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		this.resetFallDistance();
		this.setCanLook(!this.isDormant() && !this.isDisabled());
		this.setCanMove(!this.isDormant() && !this.isDisabled());
		
		if(this.getHeadType() == HeadType.BLASTER && this.tickCount == 2)
		{
			this.partBuilder.rebuildHitbox();
		}

		if(this.getOwner() == null)
		{
			if(this.tickCount == 1 && !this.chainCreated())
			{
				this.setupChain();
			}
			
			if(this.isDormant() || this.isDisabled())
			{
				this.discard();
			}
		}
		else
		{
    		this.setDormant(true);
    		this.hurtTime = this.getOwner().hurtTime;
    		this.deathTime = this.getOwner().deathTime;
    		
			WormSegmentController.tick(this.getX(), this.getY(), this.getZ(), this, this.getOwner(), 1.0F, 0.5F);
		}
		
		if(this.getHeadType() == HeadType.BLASTER)
		{
			if(this.getAnimationState() == 1)
			{
				Vec3 startPos = BTAUtil.getLookPos(this.getRotationVector(), this.position().add(0.0F, 0.5F, 0.0F), 0.0F, 0.0F, -0.2F);
				Vec3 lookPos = BTAUtil.getLookPos(this.getRotationVector(), startPos, 0.0F, 0.0F, 100.0F);
				HitResult hitResult = this.level.clip(new ClipContext(startPos, lookPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
				EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(this.level, this, startPos, lookPos, this.getBoundingBox().inflate(100.0F), Entity::isPickable);
				Vec3 pos = hitResult.getLocation();
				
	            if(entityHit != null)
	            {
	            	hitResult = entityHit;
	            }
	            
	            if(hitResult != null)
	            {
	                Vec3 vec31 = pos.subtract(startPos);
	                Vec3 vec32 = vec31.normalize();
	                List<LivingEntity> arrayList = new ArrayList<>();
	                this.setBeamLength((float) vec31.length());
					if(hitResult.getType() == HitResult.Type.ENTITY)
					{
		                for(int i = 1; i < Mth.floor(vec31.length()) + 1; ++i)
		                {
		                	Vec3 vec33 = startPos.add(vec32.scale(i));
		                	List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(vec33, vec33).inflate(0.5F));
		                	list.removeIf(t -> t == this || t.isAlliedTo(this));
		                	list.forEach(t -> 
		                	{
		                		if(!arrayList.contains(t))
		                		{
		                			arrayList.add(t);
		                		}
		                	});
		                }
					}
					
					arrayList.forEach(t -> 
                	{
                		t.hurt(this.damageSources().mobAttack(this), 2.5F);
                	});
	            }
			}
			
			if(this.getAnimationState() == 2 && this.getAnimationTick() <= 0)
			{
				this.setAnimationState(0);
			}
		}
	}
	
	public boolean shouldInvertRotation()
	{
		return this.isDormant() || this.isDisabled();
	}
	
	@Override
	public LookControl getSwimmingLookControl() 
	{
		return new LookControl(this);
	}
	
	@Override
	public boolean isAlliedTo(Entity p_20355_) 
	{
		return p_20355_ == this.getOwner() || this.segments.contains(p_20355_) || super.isAlliedTo(p_20355_);
	}
	
	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) 
	{
		if(!this.isInvulnerableTo(p_21016_) && this.getOwner() != null)
		{
    		this.getOwner().hurt(p_21016_, p_21017_);
			return false;
		}
		return super.hurt(p_21016_, p_21017_);
	}
	
    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_)
    {
    	return super.isInvulnerableTo(p_20122_) || p_20122_.is(DamageTypes.IN_WALL) || p_20122_.is(DamageTypeTags.IS_FALL);
    }
	
	@Override
	public int getMaxSpawnClusterSize()
	{
		return 1;
	}
	
	public static boolean checkSiamserpentSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		//LocateCommand
		return pRandom.nextInt(850) == 0 && pPos.getY() >= -180 && pPos.getY() <= -160 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
	
    @Override
    protected void doPush(Entity p_20971_) 
    {
    	if(!(p_20971_ instanceof EntitySiamserpentHead) && !(p_20971_ instanceof EntitySiamserpentBone))
    	{
        	super.doPush(p_20971_);
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
		p_21484_.putInt("HeadType", this.getHeadType().ordinal());
		p_21484_.putBoolean("isDormant", this.isDormant());
		p_21484_.putBoolean("isDisabled", this.isDisabled());
		p_21484_.putBoolean("ChainCreated", this.chainCreated());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_) 
	{
		super.readAdditionalSaveData(p_21450_);
		if(p_21450_.contains("HeadType"))
		{
			this.setHeadType(HeadType.values()[p_21450_.getInt("HeadType")]);
		}
		if(p_21450_.contains("isDormant"))
		{
			this.setDormant(p_21450_.getBoolean("isDormant"));
		}
		if(p_21450_.contains("isDisabled"))
		{
			this.setDisabled(p_21450_.getBoolean("isDisabled"));
		}
		if(p_21450_.contains("ChainCreated"))
		{
			this.setChainCreated(p_21450_.getBoolean("ChainCreated"));
		}
	}
	
	public void setupChain()
	{
		this.setChainCreated(true);
		
		EntitySiamserpentBone bone = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
		bone.setOwner(this);
		bone.setIndex(0);
		bone.setPos(this.position());
		bone.setHead(this);
		this.segments.add(0, bone);
		this.level.addFreshEntity(bone);
		
		for(int i = 0; i <= 11; i++)
		{
			if(i == 10)
			{
				EntitySiamserpentBone bone2 = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
				bone2.setOwner(this.segments.get(10));
				bone2.setIndex(11);
				bone2.setPos(this.position());
				bone2.setHead(this);
				this.segments.add(11, bone2);
				this.level.addFreshEntity(bone2);
			}
			else if(i == 11)
			{
				if(this.random.nextBoolean())
				{
					this.setHeadType(HeadType.BLASTER);
					EntitySiamserpentHead head = new EntitySiamserpentHead(BTAEntities.SIAMSERPENT_HEAD.get(), this.level);
					head.setOwner(this.segments.get(11));
					head.setHeadType(HeadType.SLASHER);
					head.setPos(this.position());
					this.level.addFreshEntity(head);
				}
				else
				{
					this.setHeadType(HeadType.SLASHER);
					EntitySiamserpentHead head = new EntitySiamserpentHead(BTAEntities.SIAMSERPENT_HEAD.get(), this.level);
					head.setOwner(this.segments.get(11));
					head.setHeadType(HeadType.BLASTER);
					head.setPos(this.position());
					this.level.addFreshEntity(head);
				}
			}
			else
			{
				EntitySiamserpentBone bone1 = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
				bone1.setOwner(this.segments.get(i));
				bone1.setIndex(i + 1);
				bone1.setVariant(this.level.random.nextInt(1, 3));
				bone1.setPos(this.position());
				bone1.setHead(this);
				this.segments.add(i + 1, bone1);
				this.level.addFreshEntity(bone1);
			}
		}
	}
	
	public void setBeamLength(float value)
	{
		this.entityData.set(BEAM_LENGTH, value);
	}
	
	public float getBeamLength()
	{
		return this.entityData.get(BEAM_LENGTH);
	}
	
	public void setChainCreated(boolean value)
	{
		this.entityData.set(CHAIN_CREATED, value);
	}
	
	public boolean chainCreated()
	{
		return this.entityData.get(CHAIN_CREATED);
	}
	
	public void setDormant(boolean value)
	{
		this.entityData.set(IS_DORMANT, value);
	}
	
	public boolean isDormant()
	{
		return this.entityData.get(IS_DORMANT);
	}
	
	public void setDisabled(boolean value)
	{
		this.entityData.set(IS_DISABLED, value);
	}
	
	public boolean isDisabled()
	{
		return this.entityData.get(IS_DISABLED);
	}
	
	public void setHeadType(HeadType value)
	{
		this.entityData.set(HEAD_TYPE, value.ordinal());
	}
	
	public HeadType getHeadType()
	{
		return HeadType.values()[this.entityData.get(HEAD_TYPE)];
	}
	
	@Override
	protected Component getTypeName()
	{
		switch(this.getHeadType())
		{
		case SLASHER:
			return Component.translatable("entity.beyondtheabyss.siamserpent_slasher");
		case BLASTER:
			return Component.translatable("entity.beyondtheabyss.siamserpent_blaster");
		}
		return super.getTypeName();
	}
	
	public static enum HeadType
	{
		SLASHER,
		BLASTER
	}
}
