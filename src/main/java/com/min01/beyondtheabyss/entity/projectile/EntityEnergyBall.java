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
	public EntityEnergyBall(EntityType<? extends EntityEnergyBall> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.setNoGravity(true);
	}

	public EntityEnergyBall(Level pLevel, LivingEntity pShooter) 
	{
		super(BTAEntities.ENERGY_BALL.get(), pShooter, pLevel);
		this.setNoGravity(true);
	}

	public EntityEnergyBall(Level pLevel, double pX, double pY, double pZ)
	{
		super(BTAEntities.ENERGY_BALL.get(), pX, pY, pZ, pLevel);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData() 
	{
		
	}
	
	@Override
	protected void onHitEntity(EntityHitResult pResult)
	{
		super.onHitEntity(pResult);
		Entity entity = pResult.getEntity();
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
	protected void onHitBlock(BlockHitResult pResult) 
	{
		super.onHitBlock(pResult);
		this.discard();
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
	public boolean isInWater() 
	{
		return false;
	}
}
