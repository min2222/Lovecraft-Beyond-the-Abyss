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

public class EntityMissile extends ThrowableProjectile
{
	public EntityMissile(EntityType<? extends EntityMissile> p_37391_, Level p_37392_) 
	{
		super(p_37391_, p_37392_);
		this.setNoGravity(true);
	}

	public EntityMissile(Level p_37399_, LivingEntity p_37400_) 
	{
		super(BTAEntities.MISSILE.get(), p_37400_, p_37399_);
		this.setNoGravity(true);
	}

	public EntityMissile(Level p_37394_, double p_37395_, double p_37396_, double p_37397_)
	{
		super(BTAEntities.MISSILE.get(), p_37395_, p_37396_, p_37397_, p_37394_);
		this.setNoGravity(true);
	}
	
	@Override
	protected void defineSynchedData()
	{
		
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
	protected void onHitBlock(BlockHitResult p_37258_) 
	{
		super.onHitBlock(p_37258_);
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
				this.addDeltaMovement(BTAUtil.fromToVector(this.position(), mob.getTarget().getEyePosition(), 0.15F));
			}
		}
	}
}
