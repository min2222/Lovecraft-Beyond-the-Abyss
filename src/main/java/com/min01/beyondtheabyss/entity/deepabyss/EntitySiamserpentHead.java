package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.WormSegmentController;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;

public class EntitySiamserpentHead extends AbstractOwnableDeepAbyssMonster<EntitySiamserpentBone>
{
	public static final EntityDataAccessor<Integer> HEAD_TYPE = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_DISABLED = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DORMANT = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Float> BEAM_LENGTH = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Optional<UUID>> OTHER_UUID = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public final List<EntitySiamserpentBone> segments = new ArrayList<>();
	
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
    	this.entityData.define(BEAM_LENGTH, 0.0F);
    	this.entityData.define(OTHER_UUID, Optional.empty());
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
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		
	}
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		//this.goalSelector.addGoal(4, new SiamserpentBlasterBeamGoal(this));
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		this.resetFallDistance();
		this.setCanLook(!this.isDormant() && !this.isDisabled());
		this.setCanMove(!this.isDormant() && !this.isDisabled());
		
		if(this.tickCount == 2)
		{
			this.partBuilder.rebuildHitbox();
		}

		if(this.getOwner() == null)
		{
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

			WormSegmentController.tick(this, this.getOwner(), 1.0F, 0.35F);
		}
	}
	
	public boolean shouldInvertRotation()
	{
		return this.isDormant() || this.isDisabled();
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
    	return super.isInvulnerableTo(p_20122_) || p_20122_ == DamageSource.IN_WALL || p_20122_.isFall();
    }
	
	@Override
	public int getMaxSpawnClusterSize()
	{
		return 1;
	}
	
	@Override
	public boolean useSubRoot() 
	{
		return true;
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
		return this.shouldInvertRotation() ? new Vec2(-original.x, original.y + 180.0F) : original;
	}
	
	public static boolean checkSiamserpentSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		//LocateCommand
		return pPos.getY() >= 30 && pPos.getY() <= 80 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
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
	public void addAdditionalSaveData(CompoundTag p_21484_) 
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putInt("HeadType", this.getHeadType().ordinal());
		p_21484_.putBoolean("isDormant", this.isDormant());
		p_21484_.putBoolean("isDisabled", this.isDisabled());
		if(this.entityData.get(OTHER_UUID).isPresent())
		{
			p_21484_.putUUID("OtherHead", this.entityData.get(OTHER_UUID).get());
		}
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
		if(p_21450_.hasUUID("OtherHead")) 
		{
			this.entityData.set(OTHER_UUID, Optional.of(p_21450_.getUUID("OtherHead")));
		}
	}
	
	@Override
	public void die(DamageSource p_21192_)
	{
		super.die(p_21192_);
		this.segments.forEach(t -> 
		{
			t.die(p_21192_);
		});
		if(this.getOtherHead() != null)
		{
			this.getOtherHead().die(p_21192_);
		}
	}
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
	{
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
		this.segments.add(0, bone);
		this.level.addFreshEntity(bone);
		
		for(int i = 0; i < 12; i++)
		{
			if(i < 10)
			{
				EntitySiamserpentBone bone2 = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
				bone2.setOwner(this.segments.get(i));
				bone2.setIndex(i + 1);
				bone2.setVariant(2);
				bone2.setPos(this.position());
				bone2.setHead(this);
				this.segments.add(i + 1, bone2);
				this.level.addFreshEntity(bone2);
			}
			else
			{
				if(i == 10)
				{
					int variant = this.segments.get(0).getVariant() == 0 ? 1 : 0;
					EntitySiamserpentBone bone2 = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
					bone2.setOwner(this.segments.get(i));
					bone2.setIndex(i + 1);
					bone2.setVariant(variant);
					bone2.setPos(this.position());
					bone2.setHead(this);
					this.segments.add(i + 1, bone2);
					this.level.addFreshEntity(bone2);
				}
				if(i == 11)
				{
					HeadType type = this.getHeadType() == HeadType.SLASHER ? HeadType.BLASTER : HeadType.SLASHER;
					EntitySiamserpentHead head = new EntitySiamserpentHead(BTAEntities.SIAMSERPENT_HEAD.get(), this.level);
					head.setOwner(this.segments.get(i));
					head.setHeadType(type);
					head.setPos(this.position());
					this.setOtherHead(head);
					this.level.addFreshEntity(head);
				}
			}
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}

	public void setOtherHead(EntitySiamserpentHead head)
	{
		this.entityData.set(OTHER_UUID, Optional.of(head.getUUID()));
	}
	
	@Nullable
	public EntitySiamserpentHead getOtherHead() 
	{
		if(this.entityData.get(OTHER_UUID).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(OTHER_UUID).get());
		}
		return null;
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
