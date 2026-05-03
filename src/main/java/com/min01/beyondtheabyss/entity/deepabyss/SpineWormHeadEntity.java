package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.solomonlib.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;

public class SpineWormHeadEntity extends AbstractSpineWormPart
{
	public static final EntityDataAccessor<Direction> ATTACHED_DIRECTION = SynchedEntityData.defineId(SpineWormHeadEntity.class, EntityDataSerializers.DIRECTION);
	public static final EntityDataAccessor<BlockPos> ATTACHED_POS = SynchedEntityData.defineId(SpineWormHeadEntity.class, EntityDataSerializers.BLOCK_POS);
	public static final EntityDataAccessor<Integer> COOLDOWN = SynchedEntityData.defineId(SpineWormHeadEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Optional<UUID>> TARGET_UUID = SynchedEntityData.defineId(SpineWormHeadEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public KinematicChain chain;
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState holdingAnimationState = new SmoothAnimationState();
	
	public SpineWormHeadEntity(EntityType<? extends AbstractSpineWormPart> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.setStopMoveTick(Integer.MAX_VALUE);
		this.setStopLookTick(Integer.MAX_VALUE);
		this.xpReward = this.random.nextInt(15);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 50.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.0F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F)
    			.add(Attributes.ATTACK_DAMAGE, 1.5F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 100.0F);
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(ATTACHED_DIRECTION, Direction.DOWN);
    	this.entityData.define(ATTACHED_POS, this.blockPosition());
    	this.entityData.define(COOLDOWN, 100);
    	this.entityData.define(TARGET_UUID, Optional.empty());
    }

	@Override
	public EntityPartBuilder<? extends AbstractSpineWormPart> createBuilder() 
	{
		EntityPartBuilder<SpineWormHeadEntity> partBuilder = new EntityPartBuilder<SpineWormHeadEntity>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}

	@Override
	protected boolean isAffectedByFluids()
	{
		return false;
	}
	
	@Override
	public boolean isHead() 
	{
		return true;
	}
	
	@Override
	public boolean canMove() 
	{
		return false;
	}
	
	@Override
	public boolean canLook()
	{
		return false;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.level.isClientSide)
		{
			this.idleAnimationState.updateWhen(this.getAnimationState() == 0 && !this.isVehicle(), this.tickCount);
			this.holdingAnimationState.updateWhen(this.isVehicle(), this.tickCount);
		}
		if(this.chain == null)
		{
			this.chain = new KinematicChain(this, this.getChainLength() + 1, this.getSegmentDistance(0));
			this.chain.setAnchorPos(Vec3.atBottomCenterOf(this.getAttachedPos()));
			this.chain.speed = 1.5F;
			Direction direction = this.getAttachedDirection();
			switch(direction)
			{
			case DOWN:
				this.chain.setInitialRot(new Vec2(direction.toYRot() + 180.0F, 0.0F));
				break;
			case EAST:
				this.chain.setInitialRot(new Vec2(direction.toYRot() + 90.0F, -90.0F));
				break;
			case NORTH:
				this.chain.setInitialRot(new Vec2(direction.toYRot() + 180.0F, 180.0F));
				break;
			case SOUTH:
				this.chain.setInitialRot(new Vec2(direction.toYRot(), 0.0F));
				break;
			case UP:
				this.chain.setInitialRot(new Vec2(direction.toYRot(), 0.0F));
				break;
			case WEST:
				this.chain.setInitialRot(new Vec2(direction.toYRot() - 90.0F, 90.0F));
				break;
			default:
				this.chain.setInitialRot(new Vec2(direction.toYRot(), 0.0F));
				break;
			}
		}
		else
		{
			this.chain.setOldPosAndRot();
			this.chain.tickBobbit();

			if(this.canExtend())
			{
				if(this.getTarget() != null)
				{
					if(this.chain.getTarget().equals(Vec3.ZERO))
					{
						if(this.distanceTo(this.getTarget()) <= 15.0F && !this.getTarget().isPassenger() && !this.isVehicle())
						{
							this.chain.setTarget(this.getTarget().position());
						}
					}
					else if(this.chain.getTarget().distanceTo(this.position()) <= 2.5F)
					{
						if(this.getTarget().distanceTo(this) <= 2.5F)
						{
							this.getTarget().startRiding(this);
							this.setCooldown(100);
						}
						else
						{
							this.setCooldown(40);
						}
						this.chain.setTarget(Vec3.ZERO);
					}
				}
				else
				{
					this.chain.setTarget(Vec3.ZERO);
				}
			}
			else
			{
				this.setCooldown(this.getCooldown() - 1);
			}
			
			if(this.isVehicle())
			{
				Entity entity = this.getFirstPassenger();
				this.doHurtTarget(entity);
			}

			ChainSegment segment = this.chain.getTipSegment();
			Vec3 pos = segment.getPos();
			Vec2 rot = segment.getRot();
			
			this.setPos(pos);
			this.setXRot(rot.x);
			this.setYRot(rot.y);
			this.setYBodyRot(rot.y);
			this.setYHeadRot(rot.y);

			this.xRotO = rot.x;
			this.yRotO = rot.y;
			this.yHeadRotO = rot.y;
			this.yBodyRotO = rot.y;
		}
	}
	
	@Override
	public void setTarget(LivingEntity pTarget) 
	{
		if(pTarget == null)
		{
			this.entityData.set(TARGET_UUID, Optional.empty());
		}
		else
		{
			this.entityData.set(TARGET_UUID, Optional.of(pTarget.getUUID()));
		}
		super.setTarget(pTarget);
	}
	
	@Override
	public LivingEntity getTarget()
	{
		if(this.entityData.get(TARGET_UUID).isPresent())
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(TARGET_UUID).get());
		}
		return super.getTarget();
	}
	
	@Override
	public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) 
	{
		return true;
	}
	
	@Override
	public void positionRider(Entity entity, MoveFunction function) 
	{
		Vec3 pos = BTAUtil.getLookPos(this.getRotationVector(), this.position(), 0.0F, 0.0F, 0.2F);
		function.accept(entity, pos.x, pos.y, pos.z);
	}
	
	@Override
	public boolean hurt(DamageSource pSource, float pAmount)
	{
		if(this.isVehicle() && pAmount >= 3.0F)
		{
			this.getFirstPassenger().stopRiding();
		}
		return super.hurt(pSource, pAmount);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) 
	{
		BlockPos attachPos = this.blockPosition();
		
		if(pReason == MobSpawnType.NATURAL)
		{
			BlockPos blockPos = BTAUtil.getGroundPos(this.level, this.getX(), this.getY(), this.getZ());
			if(!pLevel.getBlockState(blockPos).is(Blocks.WATER))
			{
				blockPos = blockPos.above();
			}
			Vec3 pos = Vec3.atBottomCenterOf(blockPos);
			attachPos = blockPos;
			this.moveTo(pos);
		}
		
		AbstractSpineWormPart prev = this;
		Direction direction = this.tryAttach();
		this.setAttachedPos(attachPos);
		this.setAttachedDirection(direction);
		
		switch(direction)
		{
		case DOWN:
			this.setYRot(0.0F);
			this.setYHeadRot(0.0F);
			this.setYBodyRot(0.0F);
			this.setXRot(direction.toYRot() + 180.0F);
			break;
		case EAST:
			this.setYRot(-90.0F);
			this.setYHeadRot(-90.0F);
			this.setYBodyRot(-90.0F);
			this.setXRot(direction.toYRot() + 90.0F);
			break;
		case NORTH:
			this.setYRot(180.0F);
			this.setYHeadRot(180.0F);
			this.setYBodyRot(180.0F);
			this.setXRot(direction.toYRot() + 180.0F);
			break;
		case SOUTH:
			this.setYRot(0.0F);
			this.setYHeadRot(0.0F);
			this.setYBodyRot(0.0F);
			this.setXRot(direction.toYRot());
			break;
		case UP:
			this.setYRot(0.0F);
			this.setYHeadRot(0.0F);
			this.setYBodyRot(0.0F);
			this.setXRot(direction.toYRot());
			break;
		case WEST:
			this.setYRot(90.0F);
			this.setYHeadRot(90.0F);
			this.setYBodyRot(90.0F);
			this.setXRot(direction.toYRot() - 90.0F);
			break;
		default:
			this.setYRot(0.0F);
			this.setYHeadRot(0.0F);
			this.setYBodyRot(0.0F);
			this.setXRot(direction.toYRot());
			break;
		}
		
		for(int i = 0; i < this.getChainLength(); i++)
		{
			SpineWormBodyEntity body = new SpineWormBodyEntity(BTAEntities.SPINE_WORM_BODY.get(), this.level);
			body.setPos(this.position());
			body.setHead(this);
			body.setOwner(prev);
			body.setIndex(this.getChainLength() - i);
			prev = body;
			this.level.addFreshEntity(body);
		}
		
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}
	
	public static boolean checkSpineWormSpawnRules(EntityType<? extends AbstractSpineWormPart> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	public Direction tryAttach()
	{
		for(Direction direction : Direction.values())
		{
			BlockPos pos = this.blockPosition().relative(direction);
			if(BTAUtil.isCollisionShapeFullBlock(this.level, pos))
			{
				return direction.getOpposite();
			}
		}
		return Direction.DOWN;
	}
    
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) 
    {
    	super.addAdditionalSaveData(pCompound);
    	pCompound.putInt("AttachedDirection", this.getAttachedDirection().ordinal());
    	pCompound.putInt("AttachedPosX", this.getAttachedPos().getX());
    	pCompound.putInt("AttachedPosY", this.getAttachedPos().getY());
    	pCompound.putInt("AttachedPosZ", this.getAttachedPos().getZ());
    	pCompound.putInt("Cooldown", this.getCooldown());
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
    	super.readAdditionalSaveData(pCompound);
    	if(pCompound.contains("AttachedDirection"))
    	{
    		this.setAttachedDirection(Direction.values()[pCompound.getInt("AttachedDirection")]);
    	}
    	if(pCompound.contains("AttachedPosX") && pCompound.contains("AttachedPosY") && pCompound.contains("AttachedPosZ"))
    	{
    		this.setAttachedPos(new BlockPos(pCompound.getInt("AttachedPosX"), pCompound.getInt("AttachedPosY"), pCompound.getInt("AttachedPosZ")));
    	}
    	if(pCompound.contains("Cooldown"))
    	{
    		this.setCooldown(pCompound.getInt("Cooldown"));
    	}
    }
    
    public boolean canExtend()
    {
    	return this.getCooldown() <= 0;
    }
    
    public void setCooldown(int value)
    {
    	this.entityData.set(COOLDOWN, value);
    }
    
    public int getCooldown()
    {
    	return this.entityData.get(COOLDOWN);
    }
	
	public void setAttachedDirection(Direction value)
	{
		this.entityData.set(ATTACHED_DIRECTION, value);
	}
	
	public Direction getAttachedDirection()
	{
		return this.entityData.get(ATTACHED_DIRECTION);
	}
	
	public void setAttachedPos(BlockPos value)
	{
		this.entityData.set(ATTACHED_POS, value);
	}
	
	public BlockPos getAttachedPos()
	{
		return this.entityData.get(ATTACHED_POS);
	}
}
