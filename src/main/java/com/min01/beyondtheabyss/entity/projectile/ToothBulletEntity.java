package com.min01.beyondtheabyss.entity.projectile;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTADamageSource;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ToothBulletEntity extends ThrowableProjectile
{
	public static final EntityDataAccessor<Boolean> IS_GOLDEN = SynchedEntityData.defineId(ToothBulletEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_SHRAPNEL = SynchedEntityData.defineId(ToothBulletEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_FRACTURE = SynchedEntityData.defineId(ToothBulletEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> SHRAPNEL_TYPE = SynchedEntityData.defineId(ToothBulletEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> MAX_SHRAPNEL_COUNT = SynchedEntityData.defineId(ToothBulletEntity.class, EntityDataSerializers.INT);
	
	public ToothBulletEntity(EntityType<? extends ToothBulletEntity> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.setNoGravity(true);
	}

	public ToothBulletEntity(Level pLevel, LivingEntity pShooter) 
	{
		super(BTAEntities.TOOTH_BULLET.get(), pShooter, pLevel);
		this.setNoGravity(true);
	}

	public ToothBulletEntity(Level pLevel, double pX, double pY, double pZ)
	{
		super(BTAEntities.TOOTH_BULLET.get(), pX, pY, pZ, pLevel);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData() 
	{
		this.entityData.define(IS_GOLDEN, false);
		this.entityData.define(IS_SHRAPNEL, false);
		this.entityData.define(IS_FRACTURE, false);
		this.entityData.define(SHRAPNEL_TYPE, 1);
		this.entityData.define(MAX_SHRAPNEL_COUNT, 3);
	}
	
	@Override
	protected void onHitEntity(EntityHitResult pResult)
	{
		Entity entity = pResult.getEntity();
		if(entity != this.getOwner())
		{
			float damage = this.isShrapnel() ? 1.5F : 3.0F;
			if(this.isGolden())
			{
				damage = 4.5F;
				if(entity instanceof LivingEntity living && living.getArmorCoverPercentage() <= 0.0F)
				{
					damage = 6.0F;
				}
			}
			else if(this.isFracture())
			{
				damage = 0.5F;
			}
			DamageSource source = this.isGolden() ? BTADamageSource.causeGoldenToothDamage(this.level.registryAccess(), this.getOwner()) : BTADamageSource.causeToothDamage(this.level.registryAccess(), this.getOwner());
			if(this.getOwner() != null)
			{
				if(!entity.isAlliedTo(this.getOwner()))
				{
					this.breakToShrapnel();
					if(entity.hurt(source, damage))
					{
						this.playSound(SoundEvents.TURTLE_EGG_CRACK);
						this.discard();
					}
				}
			}
			else
			{
				this.breakToShrapnel();
				if(entity.hurt(source, 3.0F))
				{
					this.playSound(SoundEvents.TURTLE_EGG_CRACK);
					this.discard();
				}
			}
		}
	}
	
	@Override
	protected void onHitBlock(BlockHitResult pResult) 
	{
		super.onHitBlock(pResult);
		this.breakToShrapnel();
		this.playSound(SoundEvents.TURTLE_EGG_CRACK);
		this.discard();
	}
	
	public void breakToShrapnel()
	{
		if(!this.isShrapnel())
		{
			this.breakToShrapnel(0.5F);
		}
		else if(this.isFracture())
		{
			this.breakToShrapnel(0.5F);
		}
	}
	
	public void breakToShrapnel(float chance)
	{
		if(!this.isGolden() && Math.random() <= chance)
		{
			int count = this.random.nextInt(this.getMaxShrapnelCount() - 1, this.getMaxShrapnelCount() + 1);
			for(int i = 0; i < count; i++)
			{
				Vec3 spreadPos = BTAUtil.getSpreadPosition(this, 2);
				ToothBulletEntity bullet = new ToothBulletEntity(BTAEntities.TOOTH_BULLET.get(), this.level);
				if(this.getOwner() != null)
				{
					bullet.setOwner(this.getOwner());
				}
				if(this.isFracture() && !this.isShrapnel())
				{
					bullet.setFracture(true);
				}
				bullet.setShrapnel(true);
				bullet.setShrapnelType(this.random.nextInt(1, 3));
				bullet.setPos(this.position());
				bullet.setDeltaMovement(BTAUtil.getVelocityTowards(bullet.position(), spreadPos));
				this.level.addFreshEntity(bullet);
			}
		}
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.tickCount >= 100)
		{
			this.discard();
		}
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundTag pCompoundTag) 
	{
		super.addAdditionalSaveData(pCompoundTag);
		pCompoundTag.putBoolean("isGolden", this.isGolden());
		pCompoundTag.putBoolean("isShrapnel", this.isShrapnel());
		pCompoundTag.putBoolean("isFracture", this.isFracture());
		pCompoundTag.putInt("ShrapnelType", this.getShrapnelType());
		pCompoundTag.putInt("MaxShrapnelCount", this.getMaxShrapnelCount());
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag pCompoundTag)
	{
		super.readAdditionalSaveData(pCompoundTag);
		this.setGolden(pCompoundTag.getBoolean("isGolden"));
		this.setShrapnel(pCompoundTag.getBoolean("isShrapnel"));
		this.setFracture(pCompoundTag.getBoolean("isFracture"));
		this.setShrapnelType(pCompoundTag.getInt("ShrapnelType"));
		this.setMaxShrapnelCount(pCompoundTag.getInt("MaxShrapnelCount"));
	}
	
	@Override
	protected void updateRotation() 
	{
		
	}
	
	@Override
	public boolean isInWater() 
	{
		return false;
	}
	
	public void setGolden(boolean value)
	{
		this.entityData.set(IS_GOLDEN, value);
	}
	
	public boolean isGolden()
	{
		return this.entityData.get(IS_GOLDEN);
	}	
	
	public void setShrapnel(boolean value)
	{
		this.entityData.set(IS_SHRAPNEL, value);
	}
	
	public boolean isShrapnel()
	{
		return this.entityData.get(IS_SHRAPNEL);
	}
	
	public void setFracture(boolean value)
	{
		this.entityData.set(IS_FRACTURE, value);
	}
	
	public boolean isFracture()
	{
		return this.entityData.get(IS_FRACTURE);
	}
	
	public void setShrapnelType(int value)
	{
		this.entityData.set(SHRAPNEL_TYPE, value);
	}
	
	public int getShrapnelType()
	{
		return this.entityData.get(SHRAPNEL_TYPE);
	}
	
	public void setMaxShrapnelCount(int value)
	{
		this.entityData.set(MAX_SHRAPNEL_COUNT, value);
	}
	
	public int getMaxShrapnelCount()
	{
		return this.entityData.get(MAX_SHRAPNEL_COUNT);
	}
}
