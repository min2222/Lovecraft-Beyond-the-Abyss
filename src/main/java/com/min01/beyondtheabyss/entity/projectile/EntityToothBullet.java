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

public class EntityToothBullet extends ThrowableProjectile
{
	public static final EntityDataAccessor<Boolean> IS_GOLDEN = SynchedEntityData.defineId(EntityToothBullet.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_SHRAPNEL = SynchedEntityData.defineId(EntityToothBullet.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_FRACTURE = SynchedEntityData.defineId(EntityToothBullet.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> SHRAPNEL_TYPE = SynchedEntityData.defineId(EntityToothBullet.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> MAX_SHRAPNEL_COUNT = SynchedEntityData.defineId(EntityToothBullet.class, EntityDataSerializers.INT);
	
	public EntityToothBullet(EntityType<? extends EntityToothBullet> p_37391_, Level p_37392_) 
	{
		super(p_37391_, p_37392_);
		this.setNoGravity(true);
	}

	public EntityToothBullet(Level p_37399_, LivingEntity p_37400_) 
	{
		super(BTAEntities.TOOTH_BULLET.get(), p_37400_, p_37399_);
		this.setNoGravity(true);
	}

	public EntityToothBullet(Level p_37394_, double p_37395_, double p_37396_, double p_37397_)
	{
		super(BTAEntities.TOOTH_BULLET.get(), p_37395_, p_37396_, p_37397_, p_37394_);
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
	protected void onHitEntity(EntityHitResult p_37259_)
	{
		Entity entity = p_37259_.getEntity();
		if(entity != this.getOwner())
		{
			float damage = this.isShrapnel() ? 1.5F : 3.0F;
			if(this.isGolden())
			{
				damage = 4.5F;
				if(((LivingEntity)entity).getArmorCoverPercentage() <= 0.0F)
				{
					damage = 6.0F;
				}
			}
			else if(this.isFracture())
			{
				damage = 0.5F;
			}
			DamageSource source = this.isGolden() ? BTADamageSource.causeGoldenToothDamage(this.level.registryAccess(), this) : BTADamageSource.causeToothDamage(this.level.registryAccess(), this);
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
	protected void onHitBlock(BlockHitResult p_37258_) 
	{
		super.onHitBlock(p_37258_);
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
				EntityToothBullet bullet = new EntityToothBullet(BTAEntities.TOOTH_BULLET.get(), this.level);
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
				bullet.setDeltaMovement(BTAUtil.fromToVector(bullet.position(), spreadPos));
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
	protected void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putBoolean("isGolden", this.isGolden());
		p_37265_.putBoolean("isShrapnel", this.isShrapnel());
		p_37265_.putBoolean("isFracture", this.isFracture());
		p_37265_.putInt("ShrapnelType", this.getShrapnelType());
		p_37265_.putInt("MaxShrapnelCount", this.getMaxShrapnelCount());
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag p_37262_)
	{
		super.readAdditionalSaveData(p_37262_);
		this.setGolden(p_37262_.getBoolean("isGolden"));
		this.setShrapnel(p_37262_.getBoolean("isShrapnel"));
		this.setFracture(p_37262_.getBoolean("isFracture"));
		this.setShrapnelType(p_37262_.getInt("ShrapnelType"));
		this.setMaxShrapnelCount(p_37262_.getInt("MaxShrapnelCount"));
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
