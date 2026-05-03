package com.min01.beyondtheabyss.entity.projectile;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class MissileEntity extends ThrowableProjectile
{
	public MissileEntity(EntityType<? extends MissileEntity> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.setNoGravity(true);
	}

	public MissileEntity(Level pLevel, LivingEntity pShooter) 
	{
		super(BTAEntities.MISSILE.get(), pShooter, pLevel);
		this.setNoGravity(true);
	}

	public MissileEntity(Level pLevel, double pX, double pY, double pZ)
	{
		super(BTAEntities.MISSILE.get(), pX, pY, pZ, pLevel);
		this.setNoGravity(true);
	}
	
	@Override
	protected void defineSynchedData()
	{
		
	}
	
	@Override
	protected void onHitEntity(EntityHitResult pResult)
	{
		Entity entity = pResult.getEntity();
		if(entity != this.getOwner())
		{
			if(this.getOwner() != null)
			{
				if(!entity.isAlliedTo(this.getOwner()))
				{
					this.explode();
				}
			}
			else
			{
				this.explode();
			}
		}
	}
	
	@Override
	protected void onHitBlock(BlockHitResult pResult) 
	{
		super.onHitBlock(pResult);
		this.explode();
	}
	
	public void explode()
	{
		this.level.explode(this, this.getX(), this.getY(), this.getZ(), 5.0F, ExplosionInteraction.NONE);
		this.discard();
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.tickCount >= 300)
		{
			this.explode();
		}
		else if(this.getOwner() != null)
		{
			Mob mob = (Mob) this.getOwner();
			if(mob.getTarget() != null)
			{
				this.addDeltaMovement(BTAUtil.getVelocityTowards(this.position(), mob.getTarget().getEyePosition(), 0.15F));
			}
		}
	}
}
