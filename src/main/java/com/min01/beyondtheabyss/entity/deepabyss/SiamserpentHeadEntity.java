package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.SiamserpentBlasterBeamGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.SiamserpentBlasterShotGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.SiamserpentSlasherChargeGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.SiamserpentSlasherSlashGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.BTAResourceKeys;
import com.min01.beyondtheabyss.misc.Laser;
import com.min01.beyondtheabyss.misc.Laser.LaserHitResult;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class SiamserpentHeadEntity extends AbstractSiamserpentPart
{
	public static final EntityDataAccessor<Integer> HEAD_TYPE = SynchedEntityData.defineId(SiamserpentHeadEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_DISABLED = SynchedEntityData.defineId(SiamserpentHeadEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DORMANT = SynchedEntityData.defineId(SiamserpentHeadEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_HEAD = SynchedEntityData.defineId(SiamserpentHeadEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Float> BEAM_LENGTH = SynchedEntityData.defineId(SiamserpentHeadEntity.class, EntityDataSerializers.FLOAT);
	
	public final SmoothAnimationState rayChargeAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState rayStartAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState rayLoopAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState blasterShotAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState blasterDisabledAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState slashRightAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState slashLeftAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState slasherChargeStartAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState slasherChargingAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState slasherDisabledAnimationState = new SmoothAnimationState();
	
	public int tickAfterDormant;
	public Vec3 wantedPos = Vec3.ZERO;
	
	public SiamserpentHeadEntity(EntityType<? extends AbstractSiamserpentPart> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(15);
		this.partBuilder.setIgnorePredicate(t -> t.contains("Ray"));
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 120.0F)
    			.add(Attributes.ARMOR, 5.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.15F)
        		.add(Attributes.FOLLOW_RANGE, 45.0F)
        		.add(Attributes.ATTACK_DAMAGE, 12.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 10.0F);
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
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(4, new SiamserpentBlasterBeamGoal(this));
		this.goalSelector.addGoal(4, new SiamserpentBlasterShotGoal(this));
		this.goalSelector.addGoal(4, new SiamserpentSlasherSlashGoal(this));
		this.goalSelector.addGoal(4, new SiamserpentSlasherChargeGoal(this));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(this.level.isClientSide)
		{
			this.blasterDisabledAnimationState.updateWhen(this.getHeadType() == HeadType.BLASTER && (this.isDisabled() || this.isDormant()), this.tickCount);
			this.slasherDisabledAnimationState.updateWhen(this.getHeadType() == HeadType.SLASHER && (this.isDisabled() || this.isDormant()), this.tickCount);
			this.rayChargeAnimationState.updateWhen(this.isAnimationPlaying(1), this.tickCount);
			this.rayStartAnimationState.updateWhen(this.isAnimationPlaying(2), this.tickCount);
			this.rayLoopAnimationState.updateWhen(this.isAnimationPlaying(3), this.tickCount);
			this.slashRightAnimationState.updateWhen(this.isAnimationPlaying(5), this.tickCount);
			this.slashLeftAnimationState.updateWhen(this.isAnimationPlaying(9), this.tickCount);
			this.blasterShotAnimationState.updateWhen(this.isAnimationPlaying(6), this.tickCount);
			this.slasherChargeStartAnimationState.updateWhen(this.isAnimationPlaying(7), this.tickCount);
			this.slasherChargingAnimationState.updateWhen(this.isAnimationPlaying(8), this.tickCount);
		}
		
		if(this.getHealth() <= this.getMaxHealth() / 2.0F && !this.isDormant() && this.tickAfterDormant == 0)
		{
			this.setDormant(true);
			this.tickAfterDormant = this.tickCount;
		}
		
		if(this.isDormant())
		{
			if(this.tickCount - this.tickAfterDormant == 100)
			{
				this.setDormant(false);
			}
		}
		
		if(this.getTarget() != null)
		{
			if(this.getAnimationState() == 7 || this.getAnimationState() == 1)
			{
				this.lookAt(Anchor.EYES, this.getTarget().getEyePosition());
			}
		}
		
		if(this.getAnimationState() == 3)
		{
        	Vec3 startPos = BTAUtil.getLookPos(new Vec2(this.getXRot(), this.getYHeadRot()), this.getEyePosition(), 0.0F, -0.05F, -0.25F);
			Vec3 lookPos = BTAUtil.getLookPos(new Vec2(this.getXRot(), this.getYHeadRot()), startPos, 0.0F, 0.0F, 300.0F);
			LaserHitResult laserHit = Laser.raytrace(this.level, startPos, lookPos, 0.375F, t -> t != this && !t.isAlliedTo(this), this);
			laserHit.entities.forEach(t -> 
            {
            	t.hurt(this.damageSources().indirectMagic(this, this), 12.0F);
            });
			this.setBeamLength(laserHit.getLaserLength());
		}
	    BTAUtil.forceTick(this);
	}
	
	@Override
	public double getMeleeAttackRangeSqr(LivingEntity pEntity)
	{
		return (double)(this.getBbWidth() * 4.0F * this.getBbWidth() * 4.0F + pEntity.getBbWidth());
	}
	
	@Override
	public void moveToTarget() 
	{
		if(this.tickCount % 60 == 0)
		{
			Vec3 spreadPos = BTAUtil.getSpreadPosition(this.random, this.getTarget().position(), new Vec3(15, 15, 15));
			this.getNavigation().moveTo(spreadPos.x, spreadPos.y, spreadPos.z, 1.5F);
		}
	}
	
	@Override
	public boolean canMoveAround()
	{
		return !this.isTargetValid();
	}
	
	@Override
	public boolean canLookAround() 
	{
		return !this.isTargetValid();
	}
	
	@Override
	public boolean canLook() 
	{
		return super.canLook() && this.isAnimationPlaying();
	}
	
	@Override
	public boolean onAnimationEnd(int animationState) 
	{
		if(animationState == 3)
		{
			this.setStopMoveTick(0);
			this.setStopLookTick(0);
			this.setAnimationState(0);
			this.setLastLookPos(Vec3.ZERO);
		}
		return super.onAnimationEnd(animationState);
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
	public void tickWorms(AbstractSiamserpentPart head)
	{
		if(head.worms != null && head.isHead())
		{
			Worm worm = head.worms[this.getIndex()];
			if(worm != null)
			{
				Vec3 pos = worm.position();
				if(!pos.equals(Vec3.ZERO))
				{
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
	protected float getSoundVolume() 
	{
		return 1.5F;
	}
	
	@Override
	protected SoundEvent getAmbientSound() 
	{
		return BTASounds.SIAMSERPENT_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) 
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
	
	public static boolean checkSiamserpentSpawnRules(EntityType<? extends AbstractSiamserpentPart> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
        Structure structure = pServerLevel.registryAccess().registryOrThrow(Registries.STRUCTURE).get(BTAResourceKeys.BTAStructures.GIANT_FOSSIL);
		ServerLevel level = pServerLevel.getLevel();
		boolean isFossil = level.structureManager().getStructureWithPieceAt(pPos, structure).isValid();
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40 && isFossil;
    }
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("HeadType", this.getHeadType().ordinal());
		pCompound.putBoolean("isDormant", this.isDormant());
		pCompound.putBoolean("isDisabled", this.isDisabled());
		pCompound.putBoolean("isHead", this.isHead());
		pCompound.putInt("TickAfterDormant", this.tickAfterDormant);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) 
	{
		super.readAdditionalSaveData(pCompound);
		this.setHeadType(HeadType.values()[pCompound.getInt("HeadType")]);
		this.setDormant(pCompound.getBoolean("isDormant"));
		this.setDisabled(pCompound.getBoolean("isDisabled"));
		this.setHead(pCompound.getBoolean("isHead"));
		this.tickAfterDormant = pCompound.getInt("TickAfterDormant");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) 
	{
		List<AbstractSiamserpentPart> list = new ArrayList<>();
		this.setHead(true);
		AbstractSiamserpentPart prev = this;
		SiamserpentBoneEntity bone = new SiamserpentBoneEntity(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
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
				SiamserpentBoneEntity bone2 = new SiamserpentBoneEntity(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
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
					SiamserpentBoneEntity bone2 = new SiamserpentBoneEntity(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
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
					SiamserpentHeadEntity head = new SiamserpentHeadEntity(BTAEntities.SIAMSERPENT_HEAD.get(), this.level);
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
		AbstractSiamserpentPart last = list.get(list.size() - 1);
		for(int i = 0; i < list.size() - 1; i++)
		{
			AbstractSiamserpentPart part = list.get(i);
			part.setOwner2(list.get(i + 1));
			part.setHead2(last);
		}
		this.setOwner2(list.get(1));
		last.setOwner2(null);
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
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
