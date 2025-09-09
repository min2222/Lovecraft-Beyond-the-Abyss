package com.min01.beyondtheabyss.entity.projectile;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTADamageSource;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EntityToothBullet extends ThrowableProjectile
{
	public static final EntityDataAccessor<Boolean> IS_GOLDEN = SynchedEntityData.defineId(EntityToothBullet.class, EntityDataSerializers.BOOLEAN);
	
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
	}
	
	@Override
	protected void onHitEntity(EntityHitResult p_37259_)
	{
		Entity entity = p_37259_.getEntity();
		if(entity != this.getOwner())
		{
			if(this.getOwner() != null)
			{
				if(!entity.isAlliedTo(this.getOwner()))
				{
					if(entity.hurt(BTADamageSource.causeToothDamage(this.level.registryAccess(), this.getOwner()), 3.0F))
					{
						this.playSound(SoundEvents.TURTLE_EGG_CRACK);
						this.discard();
					}
				}
			}
			else
			{
				if(entity.hurt(BTADamageSource.causeToothDamage(this.level.registryAccess(), this), 3.0F))
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
		this.playSound(SoundEvents.TURTLE_EGG_CRACK);
		this.discard();
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.tickCount >= 200)
		{
			this.discard();
		}
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putBoolean("isGolden", this.isGolden());
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag p_37262_)
	{
		super.readAdditionalSaveData(p_37262_);
		this.setGolden(p_37262_.getBoolean("isGolden"));
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
}
