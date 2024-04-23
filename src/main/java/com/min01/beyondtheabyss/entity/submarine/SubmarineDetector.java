package com.min01.beyondtheabyss.entity.submarine;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.multipart.entity.EntityBounds;
import com.min01.beyondtheabyss.multipart.entity.MultipartAwareEntity;
import com.min01.beyondtheabyss.multipart.util.CompoundOrientedBox;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SubmarineDetector extends AbstractOwnableEntity<EntitySubmarine> implements MultipartAwareEntity
{
    public final SubmarinePartHitBoxes hitboxHelper = new SubmarinePartHitBoxes(null, this);
	
	public SubmarineDetector(EntityType<? extends SubmarineDetector> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
	}

	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.hitboxHelper.getHitbox(true).getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.hitboxHelper.getHitbox(true);
	}

	@Override
	public void onSetPos(double x, double y, double z) 
	{
        if(this.hitboxHelper != null)
        {
        	this.hitboxHelper.updatePosition(true);
        }
	}
}
