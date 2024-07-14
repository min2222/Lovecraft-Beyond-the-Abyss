package com.min01.beyondtheabyss.entity.submarine;

import com.min01.beyondtheabyss.cerbon.CompoundOrientedBox;
import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.IMultipart;
import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SubmarinePart extends AbstractOwnableEntity<EntitySubmarine> implements IMultipart
{
    public SubmarinePartHitBox hitbox = new SubmarinePartHitBox(this);
	public static final EntityDataAccessor<SubmarinePartType> PART_TYPE = SynchedEntityData.defineId(SubmarinePart.class, BTAEntityDataSerializers.SUBMARINE_PART_TYPE.get());
    
    public static enum SubmarinePartType
    {
    	HATCH,
    	DETECTOR;
    }
	
	public SubmarinePart(EntityType<? extends SubmarinePart> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(PART_TYPE, SubmarinePartType.HATCH);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.getOwner() != null)
		{
			this.copyPosition(this.getOwner());
			switch(this.getPartType())
			{
			case DETECTOR:
				this.getOwner().setDetector(this);
				break;
			case HATCH:
				this.getOwner().setHatch(this);
				break;
			default:
				break;
			}
		}
		else
		{
			this.discard();
		}
	}

	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.hitbox.getHitbox(this).getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.hitbox.getHitbox(this);
	}

	@Override
	public void onSetPos(double x, double y, double z) 
	{
        if(this.hitbox != null)
        {
        	this.hitbox.updatePosition(this);
        }
	}
	
	@Override
	public boolean isPickable() 
	{
		return this.getPartType() == SubmarinePartType.HATCH;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		if(this.getPartType() != null)
		{
			p_37265_.putInt("PartType", this.getPartType().ordinal());
		}
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_) 
	{
		super.readAdditionalSaveData(p_37262_);
		if(p_37262_.contains("PartType"))
		{
			this.setPartType(SubmarinePartType.values()[p_37262_.getInt("PartType")]);
		}
	}
	
	@Override
	public InteractionResult interact(Entity entity, InteractionHand hand, String part) 
	{
		if(part == "hatch" && this.getOwner() != null && this.getPartType() == SubmarinePartType.HATCH)
		{
			this.getOwner().setHatchOpened(!this.getOwner().hatchOpened());
			return InteractionResult.SUCCESS;
		}
		return IMultipart.super.interact(entity, hand, part);
	}
	
	public SubmarinePartType getPartType()
	{
		return this.entityData.get(PART_TYPE);
	}
	
	public void setPartType(SubmarinePartType value)
	{
		this.entityData.set(PART_TYPE, value);
	}
}
