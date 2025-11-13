package com.min01.beyondtheabyss.entity;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;

public class EntityFallingStone extends ThrowableProjectile
{
	public static final EntityDataAccessor<BlockState> BLOCK_STATE = SynchedEntityData.defineId(EntityFallingStone.class, EntityDataSerializers.BLOCK_STATE);
	public static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityFallingStone.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Integer> DELAY = SynchedEntityData.defineId(EntityFallingStone.class, EntityDataSerializers.INT);
	
	public EntityFallingStone(EntityType<? extends ThrowableProjectile> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
	}
	
	@Override
	protected void defineSynchedData() 
	{
		this.entityData.define(BLOCK_STATE, Blocks.STONE.defaultBlockState());
		this.entityData.define(ROTATION, 0.0F);
		this.entityData.define(DELAY, 0);
	}
	
	@Override
	protected void onHit(HitResult p_37260_) 
	{
		super.onHit(p_37260_);
		BlockState state = this.getBlockState();
		if(!state.isAir() && this.tickCount >= 10 + this.getDelay())
		{
			this.level.broadcastEntityEvent(this, (byte) 99);
			this.playSound(state.getSoundType().getBreakSound());
			this.discard();
		}
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.isInWater() && this.tickCount >= 10 + this.getDelay()) 
		{
			Vec3 vec3 = this.getDeltaMovement();
			double x = this.getX() + vec3.x;
			double y = this.getY() + vec3.y;
			double z = this.getZ() + vec3.z;
			for(int i = 0; i < 6; ++i) 
			{
				this.level.addParticle(ParticleTypes.BUBBLE, (x - vec3.x) + this.random.nextGaussian() * 0.1F, (y - vec3.y) + this.random.nextGaussian() * 0.1F, (z - vec3.z) + this.random.nextGaussian() * 0.1F, this.random.nextGaussian() * 0.5F, this.random.nextGaussian() * 0.5F, this.random.nextGaussian() * 0.5F);
			}
		}
	}
	
	@Override
	protected void updateRotation() 
	{
		
	}
	
	@Override
	public boolean isPushedByFluid(FluidType type)
	{
		return false;
	}
	
	@Override
	protected float getGravity()
	{
		if(this.tickCount <= this.getDelay())
		{
			return 0.0F;
		}
		if(this.isInWater())
		{
			return 0.15F;
		}
		return super.getGravity();
	}
	
	@Override
	public void handleEntityEvent(byte p_19882_) 
	{
		super.handleEntityEvent(p_19882_);
		if(p_19882_ == 99)
		{
			for(int i = 0; i < 60; ++i) 
			{
				this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockState()), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
			}
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_)
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.put("BlockState", NbtUtils.writeBlockState(this.getBlockState()));
		p_37265_.putFloat("Rotation", this.getRotation());
		p_37265_.putInt("Delay", this.getDelay());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_)
	{
		super.readAdditionalSaveData(p_37262_);
		this.setBlockState(NbtUtils.readBlockState(this.level.holderLookup(Registries.BLOCK), p_37262_.getCompound("BlockState")));
		this.setRotation(p_37262_.getFloat("Rotation"));
		this.setDelay(p_37262_.getInt("Delay"));
	}
	
	public void setDelay(int index)
	{
		this.entityData.set(DELAY, index);
	}
	
	public int getDelay()
	{
		return this.entityData.get(DELAY);
	}
	
	public void setRotation(float rot)
	{
		this.entityData.set(ROTATION, rot);
	}
	
	public float getRotation()
	{
		return this.entityData.get(ROTATION);
	}
	
	public void setBlockState(BlockState state)
	{
		this.entityData.set(BLOCK_STATE, state);
	}
	
	public BlockState getBlockState()
	{
		return this.entityData.get(BLOCK_STATE);
	}
}
