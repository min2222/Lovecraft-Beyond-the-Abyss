package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.sound.BTASounds;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

public class EntitySiamserpentBone extends AbstractSiamserpentPart
{
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntitySiamserpentBone.class, EntityDataSerializers.INT);
	
	public EntitySiamserpentBone(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 60.0F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
	{
		EntityPartBuilder<EntitySiamserpentBone> partBuilder = new EntityPartBuilder<EntitySiamserpentBone>(this);
		return partBuilder;
	}
    
    @Override
    public void tick() 
    {
    	super.tick();
		if(this.tickCount == 2)
		{
			this.partBuilder.rebuildHitbox();
		}
    }
    
    @Override
    public boolean useSubRoot() 
    {
    	return true;
    }
    
    @Override
    public String subRoot()
    {
		if(this.getVariant() == 0 || this.getVariant() == 1)
		{
			return "SiamserpentBone";
		}
		return "SiamserpentMiddlebone";
    }
    
	@Override
	public boolean rotateHead() 
	{
		return true;
	}

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public Vec2 headRotation(LivingEntity living, Vec2 original)
	{
		return this.shouldInvertRotation() ? new Vec2(-original.x, original.y + 180.0F) : original;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource p_33034_) 
	{
		return BTASounds.SIAMSERPENT_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return BTASounds.SIAMSERPENT_DEATH.get();
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putInt("Variant", this.getVariant());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_) 
	{
		super.readAdditionalSaveData(p_37262_);
		if(p_37262_.contains("Variant"))
		{
			this.setVariant(p_37262_.getInt("Variant"));
		}
	}
	
	public boolean shouldInvertRotation()
	{
		return this.getIndex() == 11;
	}
	
	public void setVariant(int value)
	{
		this.entityData.set(VARIANT, value);
	}
	
	public int getVariant()
	{
		return this.entityData.get(VARIANT);
	}
}
