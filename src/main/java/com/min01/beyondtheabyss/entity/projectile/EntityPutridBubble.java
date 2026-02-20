package com.min01.beyondtheabyss.entity.projectile;

import java.util.List;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.misc.BTADamageSource;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityPutridBubble extends ThrowableProjectile
{
	public static final EntityDataAccessor<Boolean> IS_EXPLODE = SynchedEntityData.defineId(EntityPutridBubble.class, EntityDataSerializers.BOOLEAN);
	
	public int explosionTick;
	
	public EntityPutridBubble(EntityType<? extends EntityPutridBubble> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.setNoGravity(true);
	}

	public EntityPutridBubble(Level pLevel, LivingEntity pShooter) 
	{
		super(BTAEntities.PUTRID_BUBBLE.get(), pShooter, pLevel);
		this.setNoGravity(true);
	}

	public EntityPutridBubble(Level pLevel, double pX, double pY, double pZ)
	{
		super(BTAEntities.PUTRID_BUBBLE.get(), pX, pY, pZ, pLevel);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData() 
	{
		this.entityData.define(IS_EXPLODE, false);
	}
	
	@Override
	protected void onHitEntity(EntityHitResult pResult)
	{
		Entity entity = pResult.getEntity();
		if(this.getOwner() != null && entity != this.getOwner())
		{
			this.setExplode(true);
		}
	}
	
	@Override
	protected void onHitBlock(BlockHitResult pResult) 
	{
		super.onHitBlock(pResult);
		this.setExplode(true);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.isExplode() || this.tickCount >= 100)
		{
			this.explosionTick++;
			this.setDeltaMovement(Vec3.ZERO);
			float tick = this.explosionTick * 0.08F;
			List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5F), t -> 
			{
				boolean flag = this.getOwner() != null ? !t.isAlliedTo(this.getOwner()) : true;
				return !(t instanceof EntityMutavore) && flag;
			});
			list.forEach(t -> 
			{
				if(t.hurt(BTADamageSource.causePutridDamage(this.level.registryAccess(), this), 1.5F))
				{
					t.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0));
				}
			});
			if(1.0F - tick <= 0.0F)
			{
				this.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
				this.discard();
			}
		}
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundTag pCompound)
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putBoolean("isExplode", this.isExplode());
		pCompound.putInt("ExplosionTick", this.explosionTick);
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag pCompound)
	{
		super.readAdditionalSaveData(pCompound);
		this.setExplode(pCompound.getBoolean("isExplode"));
		this.explosionTick = pCompound.getInt("ExplosionTick");
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
	
	public void setExplode(boolean value)
	{
		this.entityData.set(IS_EXPLODE, value);
	}
	
	public boolean isExplode()
	{
		return this.entityData.get(IS_EXPLODE);
	}
}
