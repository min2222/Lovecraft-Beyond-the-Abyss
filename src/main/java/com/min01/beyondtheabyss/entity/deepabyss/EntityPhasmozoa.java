package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class EntityPhasmozoa extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntityPhasmozoa.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> SPECTRE_COOLDOWN = SynchedEntityData.defineId(EntityPhasmozoa.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Float> SPECTRE_ALPHA = SynchedEntityData.defineId(EntityPhasmozoa.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Boolean> IS_SPECTRE = SynchedEntityData.defineId(EntityPhasmozoa.class, EntityDataSerializers.BOOLEAN);
	
	public EntityPhasmozoa(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(5);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 15)
    			.add(Attributes.MOVEMENT_SPEED, 0.4F)
        		.add(Attributes.FOLLOW_RANGE, 15);
    }
    
    @Override
    public EntityPartBuilder<EntityPhasmozoa> createBuilder()
    {
    	EntityPartBuilder<EntityPhasmozoa> partBuilder = new EntityPartBuilder<EntityPhasmozoa>(this);
    	return partBuilder;
    }
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(VARIANT, this.random.nextInt(2) + 1);
		this.entityData.define(SPECTRE_COOLDOWN, 0);
		this.entityData.define(SPECTRE_ALPHA, 1.0F);
		this.entityData.define(IS_SPECTRE, false);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.isSpectre())
		{
			this.setSpectreAlpha(Math.max(this.getSpectreAlpha() - 0.05F, 0.1F));
			
			if(this.getTarget() != null)
			{
				if(this.distanceTo(this.getTarget()) <= 3)
				{
					this.disableSpectre();
				}
			}
		}
		else
		{
			if(this.getSpectreCooldown() > 0)
			{
				this.setSpectreCooldown(this.getSpectreCooldown() - 1);
			}

			this.setSpectreAlpha(Math.min(this.getSpectreAlpha() + 0.05F, 1.0F));
		}
	}
	
	public void enableSpectre()
	{
		this.noPhysics = true;
		this.setNoGravity(true);
		this.setSpectre(true);
	}
	
	public void disableSpectre()
	{
		this.noPhysics = false;
		this.setNoGravity(false);
		this.setSpectre(false);
		this.setSpectreCooldown(300);
	}
	
	@Override
	public boolean isPickable() 
	{
		return super.isPickable() && !this.isSpectre();
	}
	
	public void setSpectreCooldown(int value)
	{
		this.entityData.set(SPECTRE_COOLDOWN, value);
	}
	
	public int getSpectreCooldown()
	{
		return this.entityData.get(SPECTRE_COOLDOWN);
	}
	
	public void setSpectreAlpha(float value)
	{
		this.entityData.set(SPECTRE_ALPHA, value);
	}
	
	public float getSpectreAlpha()
	{
		return this.entityData.get(SPECTRE_ALPHA);
	}
	
	public void setSpectre(boolean value)
	{
		this.entityData.set(IS_SPECTRE, value);
	}
	
	public boolean isSpectre()
	{
		return this.entityData.get(IS_SPECTRE);
	}
    
	public int getVariant()
	{
		return this.entityData.get(VARIANT);
	}
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 1;
    }
    
    @Override
    public BTAMobType getBTAMobType()
    {
    	return BTAMobType.HOSTILE;
    }
}
