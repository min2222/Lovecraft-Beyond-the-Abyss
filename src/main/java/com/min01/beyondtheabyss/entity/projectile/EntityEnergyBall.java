package com.min01.beyondtheabyss.entity.projectile;

import com.min01.beyondtheabyss.entity.BTAEntities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EntityEnergyBall extends ThrowableProjectile
{
	public EntityEnergyBall(EntityType<? extends EntityEnergyBall> p_37391_, Level p_37392_) 
	{
		super(p_37391_, p_37392_);
		this.setNoGravity(true);
	}

	public EntityEnergyBall(Level p_37399_, LivingEntity p_37400_) 
	{
		super(BTAEntities.ENERGY_BALL.get(), p_37400_, p_37399_);
		this.setNoGravity(true);
	}

	public EntityEnergyBall(Level p_37394_, double p_37395_, double p_37396_, double p_37397_)
	{
		super(BTAEntities.ENERGY_BALL.get(), p_37395_, p_37396_, p_37397_, p_37394_);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData() 
	{
		
	}
	
	@Override
	protected void onHitEntity(EntityHitResult p_37259_)
	{
		super.onHitEntity(p_37259_);
		Entity entity = p_37259_.getEntity();
		if(this.getOwner() != null)
		{
			if(!entity.isAlliedTo(this.getOwner()))
			{
				entity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 6.0F);
				this.discard();
			}
		}
	}
	
	@Override
	protected void onHitBlock(BlockHitResult p_37258_) 
	{
		super.onHitBlock(p_37258_);
		this.discard();
	}
}
