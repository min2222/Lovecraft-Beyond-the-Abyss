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
	
	public EntityPutridBubble(EntityType<? extends EntityPutridBubble> p_37391_, Level p_37392_) 
	{
		super(p_37391_, p_37392_);
		this.setNoGravity(true);
	}

	public EntityPutridBubble(Level p_37399_, LivingEntity p_37400_) 
	{
		super(BTAEntities.PUTRID_BUBBLE.get(), p_37400_, p_37399_);
		this.setNoGravity(true);
	}

	public EntityPutridBubble(Level p_37394_, double p_37395_, double p_37396_, double p_37397_)
	{
		super(BTAEntities.PUTRID_BUBBLE.get(), p_37395_, p_37396_, p_37397_, p_37394_);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData() 
	{
		this.entityData.define(IS_EXPLODE, false);
	}
	
	@Override
	protected void onHitEntity(EntityHitResult p_37259_)
	{
		Entity entity = p_37259_.getEntity();
		if(entity != this.getOwner())
		{
			this.setExplode(true);
		}
	}
	
	@Override
	protected void onHitBlock(BlockHitResult p_37258_) 
	{
		super.onHitBlock(p_37258_);
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
			if(1.0F - tick <= 0.0F)
			{
				this.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
				List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5F), t -> !(t instanceof EntityMutavore) && this.getOwner() != null ? !t.isAlliedTo(this.getOwner()) && t != this.getOwner() : true);
				list.forEach(t -> 
				{
					if(t.hurt(BTADamageSource.causePutridDamage(this.level.registryAccess(), this), 1.5F))
					{
						t.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0));
					}
				});
				this.discard();
			}
		}
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putBoolean("isExplode", this.isExplode());
		p_37265_.putInt("ExplosionTick", this.explosionTick);
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag p_37262_)
	{
		super.readAdditionalSaveData(p_37262_);
		this.setExplode(p_37262_.getBoolean("isExplode"));
		this.explosionTick = p_37262_.getInt("ExplosionTick");
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
