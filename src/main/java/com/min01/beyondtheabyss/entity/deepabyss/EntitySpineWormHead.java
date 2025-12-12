package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateVehiclePacket;
import com.min01.beyondtheabyss.util.BTAUtil;

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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;

public class EntitySpineWormHead extends AbstractSpineWormPart
{
	public static final EntityDataAccessor<Direction> ATTACHED_DIRECTION = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.DIRECTION);
	public static final EntityDataAccessor<BlockPos> ATTACHED_POS = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.BLOCK_POS);
	public static final EntityDataAccessor<Integer> COOLDOWN = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Optional<UUID>> TARGET_UUID = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public KinematicChain chain;
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState holdingAnimationState = new SmoothAnimationState();
	
	public EntitySpineWormHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.setCanMove(false);
		this.setCanLook(false);
		this.xpReward = this.random.nextInt(15);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 30.0F)
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
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
	{
		EntityPartBuilder<EntitySpineWormHead> partBuilder = new EntityPartBuilder<EntitySpineWormHead>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}

	@Override
	public boolean isSwimable() 
	{
		return false;
	}
	
	@Override
	public boolean isHead() 
	{
		return true;
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
				if(this.getTarget() != null && !this.getTarget().isPassenger() && !this.isVehicle())
				{
					Vec3 pos = this.getTarget().position();
					if(pos.subtract(this.position()).length() <= 0.5F)
					{
						if(this.level.isClientSide)
						{
							this.getTarget().startRiding(this);
		    				BTANetwork.sendToServer(new UpdateVehiclePacket(this.getTarget(), this));
						}
						this.chain.setTarget(Vec3.ZERO);
						this.setCooldown(100);
					}
					else
					{
						this.chain.setTarget(pos);
					}
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
	public void setTarget(LivingEntity p_21544_) 
	{
		if(p_21544_ == null)
		{
			this.entityData.set(TARGET_UUID, Optional.empty());
		}
		else
		{
			this.entityData.set(TARGET_UUID, Optional.of(p_21544_.getUUID()));
		}
		super.setTarget(p_21544_);
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
	public boolean hurt(DamageSource p_21016_, float p_21017_)
	{
		if(this.isVehicle() && p_21017_ >= 3.0F)
		{
			this.getFirstPassenger().stopRiding();
		}
		return super.hurt(p_21016_, p_21017_);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
	{
		BlockPos attachPos = this.blockPosition();
		if(p_21436_ == MobSpawnType.NATURAL)
		{
			BlockPos blockPos = BTAUtil.getGroundPos(this.level, this.getX(), this.getY(), this.getZ(), 0).above();
			if(!p_21434_.getBlockState(blockPos).is(Blocks.WATER))
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
			EntitySpineWormBody body = new EntitySpineWormBody(BTAEntities.SPINE_WORM_BODY.get(), this.level);
			body.setPos(this.position());
			body.setHead(this);
			body.setOwner(prev);
			body.setIndex(this.getChainLength() - i);
			prev = body;
			this.level.addFreshEntity(body);
		}
		
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	public static boolean checkSpineWormSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
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
    public void addAdditionalSaveData(CompoundTag p_21484_) 
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putInt("AttachedDirection", this.getAttachedDirection().ordinal());
    	p_21484_.putInt("AttachedPosX", this.getAttachedPos().getX());
    	p_21484_.putInt("AttachedPosY", this.getAttachedPos().getY());
    	p_21484_.putInt("AttachedPosZ", this.getAttachedPos().getZ());
    	p_21484_.putInt("Cooldown", this.getCooldown());
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_)
    {
    	super.readAdditionalSaveData(p_21450_);
    	if(p_21450_.contains("AttachedDirection"))
    	{
    		this.setAttachedDirection(Direction.values()[p_21450_.getInt("AttachedDirection")]);
    	}
    	if(p_21450_.contains("AttachedPosX") && p_21450_.contains("AttachedPosY") && p_21450_.contains("AttachedPosZ"))
    	{
    		this.setAttachedPos(new BlockPos(p_21450_.getInt("AttachedPosX"), p_21450_.getInt("AttachedPosY"), p_21450_.getInt("AttachedPosZ")));
    	}
    	if(p_21450_.contains("Cooldown"))
    	{
    		this.setCooldown(p_21450_.getInt("Cooldown"));
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
