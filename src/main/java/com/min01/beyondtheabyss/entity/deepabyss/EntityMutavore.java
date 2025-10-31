package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;
import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreConsumingGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreLaunchMineGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavorePutridBubbleGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreTongueGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EntityMutavore extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<CompoundTag> MUTATION = SynchedEntityData.defineId(EntityMutavore.class, EntityDataSerializers.COMPOUND_TAG);
	public static final EntityDataAccessor<CompoundTag> CYST = SynchedEntityData.defineId(EntityMutavore.class, EntityDataSerializers.COMPOUND_TAG);
	public static final EntityDataAccessor<Boolean> IS_CONSUME = SynchedEntityData.defineId(EntityMutavore.class, EntityDataSerializers.BOOLEAN);
	
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
	
	public EntityMutavore(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(25);
		this.posArray = new Vec3[4];
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 150.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
        		.add(Attributes.FOLLOW_RANGE, 50.0F)
        		.add(Attributes.ATTACK_DAMAGE, 8.0F)
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
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<EntityGnasher>(this, EntityGnasher.class, false, false));
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntityMutavore> partBuilder = new EntityPartBuilder<EntityMutavore>(this);
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
    		this.bubbleStartAnimationState.updateWhen(this.isUsingSkill(1), this.tickCount);
    		this.bubbleStopAnimationState.updateWhen(this.isUsingSkill(2), this.tickCount);
    		this.tongueStartAnimationState.updateWhen(this.isUsingSkill(3), this.tickCount);
    		this.tongueLoopAnimationState.updateWhen(this.isUsingSkill(4), this.tickCount);
    		this.tongueStopAnimationState.updateWhen(this.isUsingSkill(5), this.tickCount);
    		this.mutateLArmAnimationState.updateWhen(this.isMutated(MutationType.MUTATE_L_ARM), this.tickCount);
    		this.mutateRArmAnimationState.updateWhen(this.isMutated(MutationType.MUTATE_R_ARM), this.tickCount);
    		this.mutate1AnimationState.updateWhen(this.isMutated(MutationType.MUTATE1), this.tickCount);
    		this.mutate2AnimationState.updateWhen(this.isMutated(MutationType.MUTATE2), this.tickCount);
    		this.mutate3AnimationState.updateWhen(this.isMutated(MutationType.MUTATE3), this.tickCount);
    		this.mutate4AnimationState.updateWhen(this.isMutated(MutationType.MUTATE4), this.tickCount);
    		this.mutateHeadAnimationState.updateWhen(this.isMutated(MutationType.MUTATE_HEAD), this.tickCount);
    	}

		List<MutationType> types = Lists.newArrayList(MutationType.values());
		types.removeIf(t -> this.isMutated(t));
		
		if(!this.isUsingSkill() && !types.isEmpty())
		{
			float size = 1.25F;
			Vec3 lookPos = BTAUtil.getLookPos(new Vec2(this.getXRot(), this.getYHeadRot()), this.position(), 0.0F, 0.5F, 3.0F);
			AABB aabb = new AABB(-size, -size, -size, size, 2.0F, size).move(lookPos);
			List<ItemEntity> list = this.getConsumableItems(aabb);
			if(!list.isEmpty())
			{
				MutationType type = Util.getRandom(types, this.random);
				ItemEntity item = list.get(0);
				this.playSound(SoundEvents.PLAYER_BURP);
				item.discard();
				this.doMutation(type, true);
			}
		}
	}
	
	@Override
	protected void updateWalkAnimation(float p_268283_) 
	{
		float f = Math.min(p_268283_ * 10.0F, 1.0F);
		this.walkAnimation.update(f, 0.4F);
	}
	
	@Override
	public boolean canBeAffected(MobEffectInstance p_21197_)
	{
		if(p_21197_.getEffect() == MobEffects.POISON)
		{
			return false;
		}
		return super.canBeAffected(p_21197_);
	}
	
	@Override
	public void moveToTarget()
	{
		double speed = !this.hasTarget() ? this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) : 0.75F;
		if(this.isMutated(MutationType.MUTATE_L_ARM) || this.isMutated(MutationType.MUTATE_R_ARM))
		{
			speed = 0.95F;
		}
		this.getNavigation().moveTo(this.getTarget(), speed);
	}
	
	@Override
	public int maxTurnX() 
	{
		if(this.isMutated(MutationType.MUTATE_HEAD))
		{
			return 10;
		}
		return !this.hasTarget() ? 3 : 5;
	}
	
	@Override
	public int maxTurnY() 
	{
		if(this.isMutated(MutationType.MUTATE_HEAD))
		{
			return 7;
		}
		else if(this.isConsume())
		{
			return 6;
		}
		return !this.hasTarget() ? 3 : 4;
	}
	
	@Override
	public boolean canSwim() 
	{
		return super.canSwim() && !this.isConsume();
	}
	
	@Override
	public LookControl getSwimmingLookControl() 
	{
		return new MutavoreLookControl(this);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_) 
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.put("Mutation", this.getMutation());
		p_21484_.put("Cyst", this.getCyst());
		p_21484_.putBoolean("isConsume", this.isConsume());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_) 
	{
		super.readAdditionalSaveData(p_21450_);
		this.setMutation(p_21450_.getCompound("Mutation"));
		this.setCyst(p_21450_.getCompound("Cyst"));
		this.setConsume(p_21450_.getBoolean("isConsume"));
	}
	
	public static boolean checkMutavoreSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	public List<ItemEntity> getConsumableItems(AABB aabb)
	{
		List<ItemEntity> list = this.level.getEntitiesOfClass(ItemEntity.class, aabb, item -> 
		{
			boolean flag = item.getItem().getFoodProperties(this) != null && item.getItem().getFoodProperties(this).isMeat();
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
	
	public static class MutavoreLookControl extends SmoothSwimmingLookControl
	{
		public MutavoreLookControl(Mob p_148061_) 
		{
			super(p_148061_, 10);
		}
		
		@Override
		public void tick() 
		{
			if(!((EntityMutavore) this.mob).isConsume())
			{
				super.tick();
			}
		}
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
