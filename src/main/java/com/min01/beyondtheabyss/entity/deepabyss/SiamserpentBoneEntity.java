package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.solomonlib.multipart.EntityPartBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class SiamserpentBoneEntity extends AbstractSiamserpentPart
{
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SiamserpentBoneEntity.class, EntityDataSerializers.INT);
	
	public SiamserpentBoneEntity(EntityType<? extends AbstractSiamserpentPart> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(15);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 120.0F)
    			.add(Attributes.ARMOR, 5.0F)
        		.add(Attributes.FOLLOW_RANGE, 45.0F);
    }
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(VARIANT, 0);
	}

	@Override
	public EntityPartBuilder<? extends AbstractSiamserpentPart> createBuilder() 
	{
		EntityPartBuilder<SiamserpentBoneEntity> partBuilder = new EntityPartBuilder<SiamserpentBoneEntity>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) 
	{
		return BTASounds.SIAMSERPENT_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound()
	{
		return BTASounds.SIAMSERPENT_DEATH.get();
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("Variant", this.getVariant());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) 
	{
		super.readAdditionalSaveData(pCompound);
		if(pCompound.contains("Variant"))
		{
			this.setVariant(pCompound.getInt("Variant"));
		}
	}
	
	@Override
	public AbstractSiamserpentPart getHead() 
	{
		if(this.isSwap())
		{
			return this.getHead2();
		}
		return super.getHead();
	}
	
	public boolean isInvert()
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