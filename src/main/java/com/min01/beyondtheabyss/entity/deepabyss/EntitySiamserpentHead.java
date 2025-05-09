package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.SiamserpentBlasterBeamGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EntitySiamserpentHead extends AbstractSiamserpentPart
{
	public static final EntityDataAccessor<Integer> HEAD_TYPE = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_DISABLED = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DORMANT = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_HEAD = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_INVERT = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Float> BEAM_LENGTH = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.FLOAT);
	
	public final AnimationState chargeAnimationState = new AnimationState();
	public final AnimationState shootStartAnimationState = new AnimationState();
	public final AnimationState shootLoopAnimationState = new AnimationState();
	public final AnimationState shootEndAnimationState = new AnimationState();
	
	public EntitySiamserpentHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(15);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 60.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.7F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(HEAD_TYPE, 0);
    	this.entityData.define(IS_DISABLED, false);
    	this.entityData.define(IS_DORMANT, false);
    	this.entityData.define(IS_HEAD, false);
    	this.entityData.define(IS_INVERT, false);
    	this.entityData.define(BEAM_LENGTH, 0.0F);
    }
    
	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntitySiamserpentHead> partBuilder = new EntityPartBuilder<EntitySiamserpentHead>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
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
        			this.chargeAnimationState.start(this.tickCount);
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			this.shootStartAnimationState.start(this.tickCount);
        			break;
        		}
        		case 3:
        		{
        			this.stopAllAnimationStates();
        			this.shootLoopAnimationState.start(this.tickCount);
        			break;
        		}
        		case 4:
        		{
        			this.stopAllAnimationStates();
        			this.shootEndAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.chargeAnimationState.stop();
		this.shootStartAnimationState.stop();
		this.shootLoopAnimationState.stop();
		this.shootEndAnimationState.stop();
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
		if(this.isHead())
		{
			if(this.getHealth() <= this.getMaxHealth() / 2)
			{
				//TODO swap owner;
			}
		}
		
		//blaster laser
		if(this.getAnimationTick() <= 0)
		{
			if(this.getAnimationState() == 3)
			{
				this.setAnimationState(4);
				this.setAnimationTick(5);
			}
		}
		else
		{
			if(this.getAnimationState() == 3)
			{
				List<LivingEntity> arrayList = new ArrayList<>();
	        	Vec3 startPos = BTAUtil.getLookPos(this.getRotationVector(), this.getEyePosition(), 0.0F, -0.25F, 0.5F);
				Vec3 lookPos = BTAUtil.getLookPos(this.getRotationVector(), startPos, 0.0F, 0.0F, 100.0F);
				HitResult hitResult = level.clip(new ClipContext(startPos, lookPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
	        	Vec3 hitPos = hitResult.getLocation();
	            Vec3 targetPos = hitPos.subtract(startPos);
	            Vec3 normalizedPos = targetPos.normalize();
	            float dist = (float) startPos.distanceTo(hitPos);
				this.setBeamLength(dist);
	            for(int i = 1; i < dist; ++i)
	            {
	            	Vec3 rayPos = startPos.add(normalizedPos.scale(i));
	            	List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(rayPos, rayPos).inflate(1.5F));
	            	list.removeIf(t -> t == this || t.isAlliedTo(this));
	            	list.forEach(t -> 
	            	{
	            		if(!arrayList.contains(t))
	            		{
	            			arrayList.add(t);
	            		}
	            	});
	            }
	            arrayList.forEach(t -> 
	            {
	            	t.hurt(DamageSource.mobAttack(this), 0.5F);
	            });
			}
		}
	}
	
	@Override
	public boolean isHead() 
	{
		return this.entityData.get(IS_HEAD);
	}
	
	public void setHead(boolean value)
	{
		this.entityData.set(IS_HEAD, value);
	}
	
	@Override
	protected SoundEvent getAmbientSound() 
	{
		return BTASounds.SIAMSERPENT_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource p_33034_) 
	{
		return BTASounds.SIAMSERPENT_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return BTASounds.SIAMSERPENT_DEATH.get();
	}
	
	@Override
	public int getMaxSpawnClusterSize()
	{
		return 1;
	}
	
	@Override
	public String subRoot() 
	{
		if(this.getHeadType() == HeadType.SLASHER)
		{
			return "SiamserpentSlasher";
		}
		return "SiamserpentBlaster";
	}
	
	@Override
	public boolean rotateHead() 
	{
		return true;
	}
	
	@Override
	public Vec2 headRotation(LivingEntity living, Vec2 original)
	{
		return this.isInvert() ? new Vec2(-original.x, original.y + 180.0F) : original;
	}
	
	public static boolean checkSiamserpentSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		//TODO spawn in only nearby of giant fossil structure;
		//LocateCommand
		return pPos.getY() >= 10 && pPos.getY() <= 40 && pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_) 
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putInt("HeadType", this.getHeadType().ordinal());
		p_21484_.putBoolean("isDormant", this.isDormant());
		p_21484_.putBoolean("isDisabled", this.isDisabled());
		p_21484_.putBoolean("isHead", this.isHead());
		p_21484_.putBoolean("isInvert", this.isInvert());
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
		if(p_21450_.contains("isHead"))
		{
			this.setHead(p_21450_.getBoolean("isHead"));
		}
		if(p_21450_.contains("isInvert"))
		{
			this.setInvert(p_21450_.getBoolean("isInvert"));
		}
	}
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
	{
		this.setHead(true);
		AbstractSiamserpentPart prev = this;
		EntitySiamserpentBone bone = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
		bone.setOwner(this);
		bone.setIndex(0);
		if(this.random.nextBoolean())
		{
			this.setHeadType(HeadType.SLASHER);
			bone.setVariant(0);
		}
		else
		{
			this.setHeadType(HeadType.BLASTER);
			bone.setVariant(1);
		}
		bone.setPos(this.position());
		bone.setHead(this);
		prev = bone;
		this.level.addFreshEntity(bone);
		for(int i = 0; i < 12; i++)
		{
			if(i < 10)
			{
				EntitySiamserpentBone bone2 = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
				bone2.setOwner(prev);
				bone2.setIndex(i + 1);
				bone2.setVariant(2);
				bone2.setPos(this.position());
				bone2.setHead(this);
				prev = bone2;
				this.level.addFreshEntity(bone2);
			}
			else
			{
				if(i == 10)
				{
					int variant = this.getHeadType() == HeadType.SLASHER ? 1 : 0;
					EntitySiamserpentBone bone2 = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
					bone2.setOwner(prev);
					bone2.setIndex(i + 1);
					bone2.setVariant(variant);
					bone2.setPos(this.position());
					bone2.setHead(this);
					prev = bone2;
					this.level.addFreshEntity(bone2);
				}
				if(i == 11)
				{
					HeadType type = this.getHeadType() == HeadType.SLASHER ? HeadType.BLASTER : HeadType.SLASHER;
					EntitySiamserpentHead head = new EntitySiamserpentHead(BTAEntities.SIAMSERPENT_HEAD.get(), this.level);
					head.setOwner(prev);
					head.setHeadType(type);
					head.setIndex(i + 1);
					head.setPos(this.position());
					head.setDormant(true);
					head.setHead(this);
					head.setInvert(true);
					this.setHead(head);
					this.level.addFreshEntity(head);
				}
			}
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}

	public void setBeamLength(float value)
	{
		this.entityData.set(BEAM_LENGTH, value);
	}
	
	public float getBeamLength()
	{
		return this.entityData.get(BEAM_LENGTH);
	}
	
	public void setInvert(boolean value)
	{
		this.entityData.set(IS_INVERT, value);
	}
	
	public boolean isInvert()
	{
		return this.entityData.get(IS_INVERT);
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
