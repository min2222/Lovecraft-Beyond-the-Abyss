package com.min01.beyondtheabyss.entity.projectile;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity;
import com.min01.beyondtheabyss.misc.BTADamageSource;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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

public class MutavoreCystEntity extends AbstractOwnableEntity<MutavoreEntity>
{
	public static final EntityDataAccessor<Integer> CYST_TYPE = SynchedEntityData.defineId(MutavoreCystEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_SHRAPNEL = SynchedEntityData.defineId(MutavoreCystEntity.class, EntityDataSerializers.BOOLEAN);
	
	public MutavoreCystEntity(EntityType<?> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
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
	public EntityDimensions getDimensions(Pose pPose) 
	{
		if(this.isShrapnel())
		{
			return EntityDimensions.fixed(0.1F, 0.1F);
		}
		return super.getDimensions(pPose);
	}
	
	protected void updateRotation() 
	{
		Vec3 vec3 = this.getDeltaMovement();
		double d0 = vec3.horizontalDistance();
		this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d0) * (double)(180.0F / (float)Math.PI))));
		this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * (double)(180.0F / (float)Math.PI))));
	}
	
	protected static float lerpRotation(float pCurrentRotation, float pTargetRotation) 
	{
		while(pTargetRotation - pCurrentRotation < -180.0F) 
		{
			pCurrentRotation -= 360.0F;
		}
		while(pTargetRotation - pCurrentRotation >= 180.0F)
		{
			pCurrentRotation += 360.0F;
		}
		return Mth.lerp(0.2F, pCurrentRotation, pTargetRotation);
	}
	
	@Override
	public boolean isInWater() 
	{
		return this.isShrapnel();
	}
	
	@Override
	public void push(Entity pEntity) 
	{
		if(!(pEntity instanceof MutavoreEntity))
		{
			if(!this.isShrapnel())
			{
				this.explode();
			}
			else
			{
				pEntity.hurt(BTADamageSource.causeShrapnelDamage(this.level.registryAccess(), this), 0.5F);
			}
		}
	}
	
	public void explode()
	{
		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.0F), t ->  !(t instanceof MutavoreEntity) && this.getOwner() != null ? !t.isAlliedTo(this.getOwner()) && t != this.getOwner() : true);
		list.forEach(t ->
		{
			t.hurt(this.damageSources().explosion(this, this.getOwner()), 6.0F);
		});
		for(int i = 0; i < 20; i++)
		{
			Vec3 spreadPos = BTAUtil.getSpreadPosition(this, 2);
			MutavoreCystEntity cyst = new MutavoreCystEntity(BTAEntities.MUTAVORE_CYST.get(), this.level);
			if(this.getOwner() != null)
			{
				cyst.setOwner(this.getOwner());
			}
			cyst.setShrapnel(true);
			cyst.setPos(this.position());
			cyst.setDeltaMovement(BTAUtil.getVelocityTowards(cyst.position(), spreadPos, 0.3F));
			Vec2 rot = BTAUtil.lookAt(cyst.position(), spreadPos);
			cyst.setXRot(rot.x);
			cyst.setYRot(rot.y);
			this.level.addFreshEntity(cyst);
		}
		this.playSound(BTASounds.MUTAVORE_CYST_EXPLODE.get(), 10.0F, 1.0F);
		this.discard();
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound)
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("CystType", this.getCystType());
		pCompound.putBoolean("isShrapnel", this.isShrapnel());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound)
	{
		super.readAdditionalSaveData(pCompound);
		this.setCystType(pCompound.getInt("CystType"));
		this.setShrapnel(pCompound.getBoolean("isShrapnel"));
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
