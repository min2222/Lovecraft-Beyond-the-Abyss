package com.min01.beyondtheabyss.entity.projectile;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EntityMutavoreCyst extends AbstractOwnableEntity<EntityMutavore>
{
	public static final EntityDataAccessor<Integer> CYST_TYPE = SynchedEntityData.defineId(EntityMutavoreCyst.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_SHRAPNEL = SynchedEntityData.defineId(EntityMutavoreCyst.class, EntityDataSerializers.BOOLEAN);
	
	public EntityMutavoreCyst(EntityType<?> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(CYST_TYPE, 0);
		this.entityData.define(IS_SHRAPNEL, false);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.isShrapnel())
		{
			this.refreshDimensions();
			this.updateRotation();
			if(!this.isNoGravity()) 
			{
				Vec3 vec31 = this.getDeltaMovement();
				this.setDeltaMovement(vec31.x, vec31.y - 0.03F, vec31.z);
			}
		}
		else
		{
			if(this.tickCount >= 30) 
			{
				this.setDeltaMovement(Vec3.ZERO);
			}
			if(this.tickCount >= 100)
			{
				this.explode();
			}
		}
		this.move(MoverType.SELF, this.getDeltaMovement());
	}
	
	@Override
	public EntityDimensions getDimensions(Pose p_19975_) 
	{
		if(this.isShrapnel())
		{
			return EntityDimensions.fixed(0.1F, 0.1F);
		}
		return super.getDimensions(p_19975_);
	}
	
	protected void updateRotation() 
	{
		Vec3 vec3 = this.getDeltaMovement();
		double d0 = vec3.horizontalDistance();
		this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d0) * (double)(180.0F / (float)Math.PI))));
		this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * (double)(180.0F / (float)Math.PI))));
	}
	
	protected static float lerpRotation(float p_37274_, float p_37275_) 
	{
		while(p_37275_ - p_37274_ < -180.0F) 
		{
			p_37274_ -= 360.0F;
		}
		while(p_37275_ - p_37274_ >= 180.0F) 
		{
			p_37274_ += 360.0F;
		}
		return Mth.lerp(0.2F, p_37274_, p_37275_);
	}
	
	@Override
	public boolean isInWater() 
	{
		return this.isShrapnel();
	}
	
	@Override
	public void push(Entity p_20293_) 
	{
		if(!(p_20293_ instanceof EntityMutavore))
		{
			if(!this.isShrapnel())
			{
				this.explode();
			}
			else
			{
				p_20293_.hurt(this.damageSources().thorns(this), 0.5F);
			}
		}
	}
	
	public void explode()
	{
		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0F), t -> t != this.getOwner() && !(t instanceof EntityMutavore) && this.getOwner() != null ? !t.isAlliedTo(this.getOwner()) : true);
		list.forEach(t ->
		{
			t.hurt(this.damageSources().explosion(this, this.getOwner()), 6.0F);
		});
		for(int i = 0; i < 20; i++)
		{
			Vec3 spreadPos = BTAUtil.getSpreadPosition(this, 2);
			EntityMutavoreCyst cyst = new EntityMutavoreCyst(BTAEntities.MUTAVORE_CYST.get(), this.level);
			if(this.getOwner() != null)
			{
				cyst.setOwner(this.getOwner());
			}
			cyst.setShrapnel(true);
			cyst.setPos(this.position());
			cyst.setDeltaMovement(BTAUtil.fromToVector(cyst.position(), spreadPos, 0.3F));
			Vec2 rot = BTAUtil.lookAt(cyst.position(), spreadPos);
			cyst.setXRot(rot.x);
			cyst.setYRot(rot.y);
			this.level.addFreshEntity(cyst);
		}
		this.playSound(SoundEvents.GENERIC_EXPLODE);
		this.discard();
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_)
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putInt("CystType", this.getCystType());
		p_37265_.putBoolean("isShrapnel", this.isShrapnel());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_)
	{
		super.readAdditionalSaveData(p_37262_);
		this.setCystType(p_37262_.getInt("CystType"));
		this.setShrapnel(p_37262_.getBoolean("isShrapnel"));
	}
	
	public void setShrapnel(boolean value)
	{
		this.entityData.set(IS_SHRAPNEL, value);
	}
	
	public boolean isShrapnel()
	{
		return this.entityData.get(IS_SHRAPNEL);
	}
	
	public void setCystType(int value)
	{
		this.entityData.set(CYST_TYPE, value);
	}
	
	public int getCystType()
	{
		return this.entityData.get(CYST_TYPE);
	}
}
