package com.min01.beyondtheabyss.entity.submarine;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.multipart.entity.EntityBounds;
import com.min01.beyondtheabyss.multipart.entity.MultipartAwareEntity;
import com.min01.beyondtheabyss.multipart.util.CompoundOrientedBox;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SubmarinePart extends AbstractOwnableEntity<EntitySubmarine> implements MultipartAwareEntity
{
    public final SubmarinePartHitBoxes hitboxHelper = new SubmarinePartHitBoxes(this);
    public SubmarinePartType type;
    
    public static enum SubmarinePartType
    {
    	HATCH,
    	DETECTOR,
    	COLLIDER;
    }
	
	public SubmarinePart(EntityType<? extends SubmarinePart> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
	}

	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.hitboxHelper.getHitbox(this).getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.hitboxHelper.getHitbox(this);
	}

	@Override
	public void onSetPos(double x, double y, double z) 
	{
        if(this.hitboxHelper != null)
        {
        	this.hitboxHelper.updatePosition(this);
        }
	}
	
	@Override
	public boolean isPickable() 
	{
		return this.type == SubmarinePartType.HATCH;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		if(this.type != null)
		{
			p_37265_.putInt("PartType", this.type.ordinal());
		}
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_) 
	{
		super.readAdditionalSaveData(p_37262_);
		if(p_37262_.contains("PartType"))
		{
			this.type = SubmarinePartType.values()[p_37262_.getInt("PartType")];
		}
	}
	
	@Override
	public InteractionResult interact(Entity entity, InteractionHand hand, String part) 
	{
		if(part == "hatch" && this.getOwner() != null && this.type == SubmarinePartType.HATCH)
		{
			this.getOwner().setHatchOpened(!this.getOwner().hatchOpened());
			return InteractionResult.SUCCESS;
		}
		return MultipartAwareEntity.super.interact(entity, hand, part);
	}
}
