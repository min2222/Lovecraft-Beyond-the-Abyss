package com.min01.beyondtheabyss.entity.submarine;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.multipart.entity.EntityBounds;
import com.min01.beyondtheabyss.multipart.entity.MultipartAwareEntity;
import com.min01.beyondtheabyss.multipart.util.CompoundOrientedBox;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SubmarineHatch extends AbstractOwnableEntity<EntitySubmarine> implements MultipartAwareEntity
{
    public final SubmarinePartHitBoxes hitboxHelper = new SubmarinePartHitBoxes(this, null);
	
	public SubmarineHatch(EntityType<? extends SubmarineHatch> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
	}

	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.hitboxHelper.getHitbox(false).getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.hitboxHelper.getHitbox(false);
	}

	@Override
	public void onSetPos(double x, double y, double z) 
	{
        if(this.hitboxHelper != null)
        {
        	this.hitboxHelper.updatePosition(false);
        }
	}
	
	@Override
	public boolean isPickable() 
	{
		return true;
	}
	
	@Override
	public InteractionResult interact(Entity entity, InteractionHand hand, String part) 
	{
		if(part == "hatch" && this.getOwner() != null)
		{
			this.getOwner().setHatchOpened(!this.getOwner().hatchOpened());
			return InteractionResult.SUCCESS;
		}
		return MultipartAwareEntity.super.interact(entity, hand, part);
	}
}
