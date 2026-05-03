package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Comparator;
import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreConsumingGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreLaunchMineGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavorePutridBubbleGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreTongueGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.misc.MobClassification;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.solomonlib.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MutavoreEntity extends AbstractBTAMonster
{
	public static final EntityDataAccessor<CompoundTag> MUTATION = SynchedEntityData.defineId(MutavoreEntity.class, EntityDataSerializers.COMPOUND_TAG);
	public static final EntityDataAccessor<CompoundTag> CYST = SynchedEntityData.defineId(MutavoreEntity.class, EntityDataSerializers.COMPOUND_TAG);
	public static final EntityDataAccessor<Boolean> IS_CONSUME = SynchedEntityData.defineId(MutavoreEntity.class, EntityDataSerializers.BOOLEAN);
	
	public final Worm worm1 = new Worm();
	public final Worm worm6 = new Worm();
	
	public final Worm worm2 = new Worm();
	public final Worm worm7 = new Worm();
	
	public final Worm worm3 = new Worm();
	public final Worm worm8 = new Worm();
	
	public final Worm worm4 = new Worm();
	public final Worm worm9 = new Worm();
	
	public final Worm worm5 = new Worm();
	public final Worm worm10 = new Worm();
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState bubbleStartAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState bubbleStopAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState tongueStartAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState tongueLoopAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState tongueStopAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState mutateLArmAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState mutateRArmAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState mutate1AnimationState = new SmoothAnimationState();
	public final SmoothAnimationState mutate2AnimationState = new SmoothAnimationState();
	public final SmoothAnimationState mutate3AnimationState = new SmoothAnimationState();
	public final SmoothAnimationState mutate4AnimationState = new SmoothAnimationState();
	public final SmoothAnimationState mutateHeadAnimationState = new SmoothAnimationState();
	
	public MutavoreEntity(EntityType<? extends AbstractBTAMonster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(25);
		this.posArray = new Vec3[4];
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 150.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.15F)
        		.add(Attributes.FOLLOW_RANGE, 80.0F)
        		.add(Attributes.ATTACK_DAMAGE, 8.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 4.0F)
        		.add(Attributes.ARMOR, 8.0F);
    }
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
    	this.goalSelector.addGoal(0, new MutavorePutridBubbleGoal(this));
    	this.goalSelector.addGoal(0, new MutavoreTongueGoal(this));
    	this.goalSelector.addGoal(0, new MutavoreConsumingGoal(this));
    	this.goalSelector.addGoal(0, new MutavoreLaunchMineGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, GnasherEntity.class, false, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, GloomfishEntity.class, false, false));
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<MutavoreEntity> partBuilder = new EntityPartBuilder<MutavoreEntity>(this);
		return partBuilder;
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(MUTATION, new CompoundTag());
		this.entityData.define(CYST, new CompoundTag());
		this.entityData.define(IS_CONSUME, false);
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
	protected SoundEvent getAmbientSound()
	{
		return BTASounds.MUTAVORE_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() 
	{
		return BTASounds.MUTAVORE_DEATH.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource)
	{
		return BTASounds.MUTAVORE_HURT.get();
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		this.worm1.setOldPosAndRot();
		this.worm2.setOldPosAndRot();
		this.worm3.setOldPosAndRot();
		this.worm4.setOldPosAndRot();
		this.worm5.setOldPosAndRot();
		this.worm6.setOldPosAndRot();
		this.worm7.setOldPosAndRot();
		this.worm8.setOldPosAndRot();
		this.worm9.setOldPosAndRot();
		this.worm10.setOldPosAndRot();
		
		float speed = 0.15F;
    	
    	WormChain.tick(this.worm1, this, 0.0F, speed);
    	WormChain.tick(this.worm2, this.worm1, 0.0F, speed);
    	
    	WormChain.tick(this.worm3, this, 0.0F, speed);
    	WormChain.tick(this.worm4, this.worm3, 0.0F, speed);
    	
    	WormChain.tick(this.worm5, this, 0.0F, speed);
    	WormChain.tick(this.worm6, this.worm5, 0.0F, speed);
    	
    	WormChain.tick(this.worm7, this, 0.0F, speed);
    	WormChain.tick(this.worm8, this.worm7, 0.0F, speed);
    	
    	WormChain.tick(this.worm9, this, 0.0F, speed);
    	WormChain.tick(this.worm10, this.worm9, 0.0F, speed);
    	
    	if(this.level.isClientSide)
    	{
    		this.idleAnimationState.updateWhen(this.getAnimationState() == 0 && this.isInWater(), this.tickCount);
    		this.bubbleStartAnimationState.updateWhen(this.isAnimationPlaying(1), this.tickCount);
    		this.bubbleStopAnimationState.updateWhen(this.isAnimationPlaying(2), this.tickCount);
    		this.tongueStartAnimationState.updateWhen(this.isAnimationPlaying(3), this.tickCount);
    		this.tongueLoopAnimationState.updateWhen(this.isAnimationPlaying(4), this.tickCount);
    		this.tongueStopAnimationState.updateWhen(this.isAnimationPlaying(5), this.tickCount);
    		this.mutateLArmAnimationState.updateWhen(this.isMutated(MutationType.MUTATE_L_ARM), this.tickCount);
    		this.mutateRArmAnimationState.updateWhen(this.isMutated(MutationType.MUTATE_R_ARM), this.tickCount);
    		this.mutate1AnimationState.updateWhen(this.isMutated(MutationType.MUTATE1), this.tickCount);
    		this.mutate2AnimationState.updateWhen(this.isMutated(MutationType.MUTATE2), this.tickCount);
    		this.mutate3AnimationState.updateWhen(this.isMutated(MutationType.MUTATE3), this.tickCount);
    		this.mutate4AnimationState.updateWhen(this.isMutated(MutationType.MUTATE4), this.tickCount);
    		this.mutateHeadAnimationState.updateWhen(this.isMutated(MutationType.MUTATE_HEAD), this.tickCount);
    	}
	}
	
	@Override
	protected void updateWalkAnimation(float pPartialTick) 
	{
		float f = Math.min(pPartialTick * 10.0F, 1.0F);
		this.walkAnimation.update(f, 0.4F);
	}
	
	@Override
	public boolean canBeAffected(MobEffectInstance pEffectInstance)
	{
		if(pEffectInstance.getEffect() == MobEffects.POISON)
		{
			return false;
		}
		return super.canBeAffected(pEffectInstance);
	}
	
	@Override
	public float maxSwimTurnX() 
	{
		if(this.isMutated(MutationType.MUTATE_HEAD))
		{
			return 45;
		}
		return 20;
	}
	
	@Override
	public float maxSwimTurnY() 
	{
		if(this.isMutated(MutationType.MUTATE_HEAD))
		{
			return 13;
		}
		return 8;
	}
	
	@Override
	public boolean canMove()
	{
		return super.canMove() && !this.isConsume();
	}
	
	@Override
	protected void doPush(Entity pEntity)
	{
		
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.put("Mutation", this.getMutation());
		pCompound.put("Cyst", this.getCyst());
		pCompound.putBoolean("isConsume", this.isConsume());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) 
	{
		super.readAdditionalSaveData(pCompound);
		this.setMutation(pCompound.getCompound("Mutation"));
		this.setCyst(pCompound.getCompound("Cyst"));
		this.setConsume(pCompound.getBoolean("isConsume"));
	}
	
	public static boolean checkMutavoreSpawnRules(EntityType<? extends AbstractBTAMonster> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	public List<ItemEntity> getConsumableItems(AABB aabb)
	{
		List<ItemEntity> list = this.level.getEntitiesOfClass(ItemEntity.class, aabb, item -> 
		{
			boolean flag = item.getItem().isEdible() && item.getItem().getFoodProperties(this).isMeat();
			return item.isInWater() && (item.getItem().is(BTATags.BTAItems.MUTAVORE_CONSUMABLE) || flag);
		});
    	list.sort(Comparator.comparing(Entity::getUUID));
		return list;
	}
	
	public boolean hasCyst(int type)
	{
		return !this.getCyst().contains("Cyst" + type);
	}
	
	public void removeCyst(int type)
	{
		if(this.hasCyst(type))
		{
			this.getCyst().putBoolean("Cyst" + type, true);
		}
	}
	
	public boolean isMutated(MutationType type)
	{
		return this.getMutation().contains(type.name);
	}
	
	public void doMutation(MutationType type, boolean mutate)
	{
		if(!this.isMutated(type))
		{
			this.getMutation().putBoolean(type.name, mutate);
		}
	}
	
	public void setConsume(boolean value)
	{
		this.entityData.set(IS_CONSUME, value);
	}
	
	public boolean isConsume()
	{
		return this.entityData.get(IS_CONSUME);
	}
	
	public void setCyst(CompoundTag tag)
	{
		this.entityData.set(CYST, tag);
	}
	
	public CompoundTag getCyst()
	{
		return this.entityData.get(CYST);
	}
	
	public void setMutation(CompoundTag tag)
	{
		this.entityData.set(MUTATION, tag);
	}
	
	public CompoundTag getMutation()
	{
		return this.entityData.get(MUTATION);
	}
	
	public static enum MutationType implements StringRepresentable
	{
		MUTATE_L_ARM("MutateLArm"),
		MUTATE_R_ARM("MutateRArm"),
		MUTATE1("Mutate1"),
		MUTATE2("Mutate2"),
		MUTATE3("Mutate3"),
		MUTATE4("Mutate4"),
		MUTATE_HEAD("MutateHead");
		
		private String name;
		
		private MutationType(String name) 
		{
			this.name = name;
		}
		
		@Override
		public String getSerializedName()
		{
			return this.name;
		}
	}
}
