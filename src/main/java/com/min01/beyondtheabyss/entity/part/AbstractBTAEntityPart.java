package com.min01.beyondtheabyss.entity.part;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.entity.PartEntity;

public abstract class AbstractBTAEntityPart<T extends AbstractBTAMob> extends PartEntity<T>
{
	public final T parentMob;
	private final EntityDimensions size;

	public AbstractBTAEntityPart(T entity, float width, float height)
	{
		super(entity);
		this.size = EntityDimensions.scalable(width, height);
		this.refreshDimensions();
		this.parentMob = entity;
	}

	@Override
	protected void defineSynchedData() 
	{
		
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag p_31025_) 
	{
		
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_31028_) 
	{
		
	}

	@Override
	public boolean isPickable() 
	{
		return true;
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
		return this.parentMob.canBeCollidedWith();
	}

	@Override
	public boolean hurt(DamageSource p_31020_, float p_31021_) 
	{
		return this.isInvulnerableTo(p_31020_) ? false : this.parentMob.hurt(p_31020_, p_31021_);
	}

	@Override	
	public boolean is(Entity p_31031_) 
	{
		return this == p_31031_ || this.parentMob == p_31031_;
	}

	@Override
	public Packet<?> getAddEntityPacket() 
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public EntityDimensions getDimensions(Pose p_31023_) 
	{
		return this.size;
	}

	@Override
	public boolean shouldBeSaved() 
	{
		return false;
	}
}
