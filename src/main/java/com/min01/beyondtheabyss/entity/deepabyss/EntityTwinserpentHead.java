package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.TwinserpentBlasterBeamGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.TwinserpentBlasterShotGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.TwinserpentSlasherChargeGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.TwinserpentSlasherSlashGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
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

public class EntityTwinserpentHead extends AbstractTwinserpentPart
{
	public static final EntityDataAccessor<Integer> HEAD_TYPE = SynchedEntityData.defineId(EntityTwinserpentHead.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_DISABLED = SynchedEntityData.defineId(EntityTwinserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DORMANT = SynchedEntityData.defineId(EntityTwinserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_HEAD = SynchedEntityData.defineId(EntityTwinserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Float> BEAM_LENGTH = SynchedEntityData.defineId(EntityTwinserpentHead.class, EntityDataSerializers.FLOAT);
	
	public final AnimationState rayChargeAnimationState = new AnimationState();
	public final AnimationState rayStartAnimationState = new AnimationState();
	public final AnimationState rayLoopAnimationState = new AnimationState();
	public final AnimationState rayEndAnimationState = new AnimationState();
	public final AnimationState blasterShotAnimationState = new AnimationState();
	public final AnimationState blasterDisabledAnimationState = new AnimationState();
	public final AnimationState slashRightAnimationState = new AnimationState();
	public final AnimationState slashLeftAnimationState = new AnimationState();
	public final AnimationState slasherChargeStartAnimationState = new AnimationState();
	public final AnimationState slasherChargingAnimationState = new AnimationState();
	public final AnimationState slasherDisabledAnimationState = new AnimationState();
	
	public EntityTwinserpentHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(15);
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 60.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.7F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F)
        		.add(Attributes.ATTACK_DAMAGE, 6.0F);
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(HEAD_TYPE, 0);
    	this.entityData.define(IS_DISABLED, false);
    	this.entityData.define(IS_DORMANT, false);
    	this.entityData.define(IS_HEAD, false);
    	this.entityData.define(BEAM_LENGTH, 0.0F);
    }
    
	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntityTwinserpentHead> partBuilder = new EntityPartBuilder<EntityTwinserpentHead>(this);
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
        			this.rayChargeAnimationState.start(this.tickCount);
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			this.rayStartAnimationState.start(this.tickCount);
        			break;
        		}
        		case 3:
        		{
        			this.stopAllAnimationStates();
        			this.rayLoopAnimationState.start(this.tickCount);
        			break;
        		}
        		case 4:
        		{
        			this.stopAllAnimationStates();
        			this.rayEndAnimationState.start(this.tickCount);
        			break;
        		}
        		case 5:
        		{
        			this.stopAllAnimationStates();
        			if(this.random.nextBoolean())
        			{
            			this.slashRightAnimationState.start(this.tickCount);
        			}
        			else
        			{
            			this.slashLeftAnimationState.start(this.tickCount);
        			}
        			break;
        		}
        		case 6:
        		{
        			this.stopAllAnimationStates();
        			this.blasterShotAnimationState.start(this.tickCount);
        			break;
        		}
        		case 7:
        		{
        			this.stopAllAnimationStates();
        			this.slasherChargeStartAnimationState.start(this.tickCount);
        			break;
        		}
        		case 8:
        		{
        			this.stopAllAnimationStates();
        			this.slasherChargingAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.rayChargeAnimationState.stop();
		this.rayStartAnimationState.stop();
		this.rayLoopAnimationState.stop();
		this.rayEndAnimationState.stop();
		this.blasterShotAnimationState.stop();
		this.slashRightAnimationState.stop();
		this.slashLeftAnimationState.stop();
		this.slasherChargeStartAnimationState.stop();
		this.slasherChargingAnimationState.stop();
	}
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(4, new TwinserpentBlasterBeamGoal(this));
		this.goalSelector.addGoal(4, new TwinserpentBlasterShotGoal(this));
		this.goalSelector.addGoal(4, new TwinserpentSlasherSlashGoal(this));
		this.goalSelector.addGoal(4, new TwinserpentSlasherChargeGoal(this));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(this.level.isClientSide)
		{
			this.blasterDisabledAnimationState.animateWhen(this.getHeadType() == HeadType.BLASTER && this.isDisabled(), this.tickCount);
			this.slasherDisabledAnimationState.animateWhen(this.getHeadType() == HeadType.SLASHER && this.isDisabled(), this.tickCount);
		}
		
		if(this.getHealth() <= this.getMaxHealth() / 2.0F)
		{
			this.setDisabled(true);
		}
		
		//blaster laser
		if(this.getAnimationTick() <= 0)
		{
			if(this.getAnimationState() == 3)
			{
				this.setCanLook(true);
				this.setCanMove(true);
				this.setAnimationState(4);
				this.setAnimationTick(5);
			}
		}
		else
		{
			if(this.getAnimationState() == 3)
			{
				List<LivingEntity> arrayList = new ArrayList<>();
	        	Vec3 startPos = BTAUtil.getLookPos(new Vec2(this.getXRot(), this.getYHeadRot()), this.getEyePosition(), 0.0F, -0.25F, 0.5F);
				Vec3 lookPos = BTAUtil.getLookPos(new Vec2(this.getXRot(), this.getYHeadRot()), startPos, 0.0F, 0.0F, 100.0F);
				HitResult hitResult = level.clip(new ClipContext(startPos, lookPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
	        	Vec3 hitPos = hitResult.getLocation();
	            Vec3 targetPos = hitPos.subtract(startPos);
	            Vec3 normalizedPos = targetPos.normalize();
	            float dist = (float) startPos.distanceTo(hitPos);
				this.setBeamLength(dist);
	            for(int i = 1; i < dist; ++i)
	            {
	            	Vec3 rayPos = startPos.add(normalizedPos.scale(i));
	            	List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(rayPos, rayPos).inflate(0.375F), t -> t != this && !t.isAlliedTo(this));
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
	            	t.hurt(this.damageSources().indirectMagic(this, this), 6.0F);
	            });
			}
		}
	}
	
	@Override
	public void setupWorms()
	{
		if(this.worms == null)
		{
			Worm[] worms = new Worm[this.getChainLength()];
			for(int i = 0; i < worms.length; i++) 
			{
			    worms[i] = new Worm();
			}
			this.worms = worms;
		}
		else
		{
			for(int i = 0; i < this.worms.length; i++)
			{
				float speed = this.getChainSpeed();
				float distance = this.getSegmentDistance(i);
				Worm worm = this.worms[i];
				if(worm != null)
				{
					worm.setOldPosAndRot();
					if(i == 0)
					{
						WormChain.tick(worm, this, distance, speed);
					}
					else
					{
						Worm parent = this.worms[i - 1];
						if(parent != null)
						{
							WormChain.tick(worm, parent, distance, speed);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void tickWorms(AbstractTwinserpentPart head)
	{
		if(head.worms != null && head.isHead())
		{
			Worm worm = head.worms[this.getIndex()];
			if(worm != null)
			{
				Vec3 pos = head.position().add(worm.position());
				Vec2 rot = worm.getRot(1.0F);
				this.setPos(pos);
				this.setXRot(-rot.x);
				this.setYRot(rot.y + 180.0F);
				this.setYHeadRot(rot.y + 180.0F);
				this.setYBodyRot(rot.y + 180.0F);
				
				this.xRotO = -rot.x;
				this.yRotO = rot.y + 180.0F;
				this.yHeadRotO = rot.y + 180.0F;
				this.yBodyRotO = rot.y + 180.0F;
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
		return BTASounds.TWINSERPENT_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource p_33034_) 
	{
		return BTASounds.TWINSERPENT_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return BTASounds.TWINSERPENT_DEATH.get();
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
			return "TwinserpentSlasher";
		}
		return "TwinserpentBlaster";
	}
	
	@Override
	public boolean rotateHead() 
	{
		return true;
	}
	
	public static boolean checkTwinserpentSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		//TODO spawn in only nearby of giant fossil structure;
		//LocateCommand
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_) 
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putInt("HeadType", this.getHeadType().ordinal());
		p_21484_.putBoolean("isDormant", this.isDormant());
		p_21484_.putBoolean("isDisabled", this.isDisabled());
		p_21484_.putBoolean("isHead", this.isHead());
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
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
	{
		List<AbstractTwinserpentPart> list = new ArrayList<>();
		this.setHead(true);
		AbstractTwinserpentPart prev = this;
		EntityTwinserpentBone bone = new EntityTwinserpentBone(BTAEntities.TWINSERPENT_BONE.get(), this.level);
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
		list.add(this);
		list.add(bone);
		for(int i = 0; i < 12; i++)
		{
			if(i < 10)
			{
				EntityTwinserpentBone bone2 = new EntityTwinserpentBone(BTAEntities.TWINSERPENT_BONE.get(), this.level);
				bone2.setOwner(prev);
				bone2.setIndex(i + 1);
				bone2.setVariant(2);
				bone2.setPos(this.position());
				bone2.setHead(this);
				prev = bone2;
				this.level.addFreshEntity(bone2);
				list.add(bone2);
			}
			else
			{
				if(i == 10)
				{
					int variant = this.getHeadType() == HeadType.SLASHER ? 1 : 0;
					EntityTwinserpentBone bone2 = new EntityTwinserpentBone(BTAEntities.TWINSERPENT_BONE.get(), this.level);
					bone2.setOwner(prev);
					bone2.setIndex(i + 1);
					bone2.setVariant(variant);
					bone2.setPos(this.position());
					bone2.setHead(this);
					prev = bone2;
					this.level.addFreshEntity(bone2);
					list.add(bone2);
				}
				if(i == 11)
				{
					HeadType type = this.getHeadType() == HeadType.SLASHER ? HeadType.BLASTER : HeadType.SLASHER;
					EntityTwinserpentHead head = new EntityTwinserpentHead(BTAEntities.TWINSERPENT_HEAD.get(), this.level);
					head.setOwner(prev);
					head.setHeadType(type);
					head.setIndex(i + 1);
					head.setPos(this.position());
					head.setHead(this);
					this.setHead(head);
					this.level.addFreshEntity(head);
					list.add(head);
				}
			}
		}
		AbstractTwinserpentPart last = list.get(list.size() - 1);
		for(int i = 0; i < list.size() - 1; i++)
		{
			AbstractTwinserpentPart part = list.get(i);
			part.setOwner2(list.get(i + 1));
			part.setHead2(last);
		}
		this.setOwner2(list.get(1));
		last.setOwner2(null);
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
			return Component.translatable("entity.beyondtheabyss.twinserpent_slasher");
		case BLASTER:
			return Component.translatable("entity.beyondtheabyss.twinserpent_blaster");
		}
		return super.getTypeName();
	}
	
	public static enum HeadType
	{
		SLASHER,
		BLASTER
	}
}
