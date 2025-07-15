package com.min01.beyondtheabyss.entity.projectile;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EntityForneusMagic extends ThrowableProjectile
{
	public float spSize;
	public float spAlpha = 1.0F;
	public float cylLength;
	public float cylRadius = 1.5F;
	public float cylAlpha = 1.0F;
	public Vec2 rot;
	public EntityForneusMagic(EntityType<? extends EntityForneusMagic> p_37391_, Level p_37392_) 
	{
		super(p_37391_, p_37392_);
		this.noCulling = true;
		this.setNoGravity(true);
	}

	public EntityForneusMagic(Level p_37399_, LivingEntity p_37400_) 
	{
		super(BTAEntities.FORNEUS_MAGIC.get(), p_37400_, p_37399_);
		this.noCulling = true;
		this.setNoGravity(true);
	}

	public EntityForneusMagic(Level p_37394_, double p_37395_, double p_37396_, double p_37397_)
	{
		super(BTAEntities.FORNEUS_MAGIC.get(), p_37395_, p_37396_, p_37397_, p_37394_);
		this.noCulling = true;
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData()
	{
		
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.tickCount <= 100)
		{
	        this.spSize += 0.05F;
		}
		else
		{
			if(this.rot == null)
			{
			    this.rot = BTAUtil.lookAt(this.position(), BTAClientUtil.MC.player.position());
			}
			this.cylLength += 5.0F;
			this.spSize += 0.1F;
			this.spSize = Math.min(this.spSize, 10.0F);
			this.spAlpha -= 0.05F;
			this.spAlpha = Math.max(this.spAlpha, 0.0F);
			if(this.cylLength >= 100.0F)
			{
				this.cylRadius += 0.1F;
				this.cylRadius = Math.min(this.cylRadius, 10.0F);
				this.cylAlpha -= 0.05F;
				this.cylAlpha = Math.max(this.cylAlpha, 0.0F);
				if(this.cylAlpha <= 0.0F)
				{
					this.discard();
				}
			}
		}
		this.setDeltaMovement(Vec3.ZERO);
	}
}