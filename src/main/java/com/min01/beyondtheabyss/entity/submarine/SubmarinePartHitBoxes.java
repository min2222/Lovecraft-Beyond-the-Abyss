package com.min01.beyondtheabyss.entity.submarine;

import com.min01.beyondtheabyss.multipart.entity.EntityBounds;
import com.min01.beyondtheabyss.multipart.entity.EntityPart;
import com.min01.beyondtheabyss.multipart.entity.MutableBox;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SubmarinePartHitBoxes 
{
    private final SubmarinePart entity;
    private final AABB collisionHitbox = new AABB(Vec3.ZERO, Vec3.ZERO);
    private final String root = "root";
    private final String submarine = "submarine";
    private final String hatch = "hatch";
    private final String detector = "detector";
    
    private final EntityBounds hatchHitBoxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setParent(this.root).build()
            .add(this.hatch).setBounds(1.4125F, 0.6F, 1.4125F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    private final EntityBounds detectorHitBoxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setParent(this.root).build()
            .add(this.detector).setBounds(2.5625F, 2.4375F, 4.375F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    public SubmarinePartHitBoxes(SubmarinePart entity)
    {
    	this.entity = entity;
    }

    public EntityBounds getHitbox(SubmarinePart entity)
    {
    	if(entity.type != null)
    	{
        	switch(entity.type)
        	{
    		case DETECTOR:
    			return this.detectorHitBoxes;
    		case HATCH:
    			return this.hatchHitBoxes;
    		default:
        	}
    	}
		return this.detectorHitBoxes;
    }

    public void updatePosition(SubmarinePart entity)
    {
    	if(entity.type != null && this.entity.getOwner() != null)
    	{
        	EntitySubmarine owner = this.entity.getOwner();
        	switch(entity.type)
        	{
	    		case DETECTOR:
	    		{
	                EntityPart root = this.detectorHitBoxes.getPart(this.root);
	                EntityPart submarine = this.detectorHitBoxes.getPart(this.submarine);
	                EntityPart detector = this.detectorHitBoxes.getPart(this.detector);
	                
	                root.setRotation(0, owner.yHeadRot - owner.yBodyRot, 0, true);
	                submarine.setRotation(owner.getXRot(), 0, 0, true);
	                
	                root.setX(this.entity.getX());
	                root.setY(this.entity.getY());
	                root.setZ(this.entity.getZ());
	                
	                this.setPartPosition(detector, owner.posArray[12]);
	                
	                MutableBox overrideBox = this.detectorHitBoxes.getOverrideBox();
	                if(overrideBox != null)
	                {
	                    overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.05F, -2.75F));
	                }
	    			break;
	    		}
	    		case HATCH:
	    		{
	                EntityPart root = this.hatchHitBoxes.getPart(this.root);
	                EntityPart submarine = this.hatchHitBoxes.getPart(this.submarine);
	                EntityPart hatch = this.hatchHitBoxes.getPart(this.hatch);
	                
	                root.setRotation(0, owner.yHeadRot - owner.yBodyRot, 0, true);
	                submarine.setRotation(owner.getXRot(), 0, 0, true);
	                
	                root.setX(this.entity.getX());
	                root.setY(this.entity.getY());
	                root.setZ(this.entity.getZ());

	                this.setPartPosition(hatch, owner.posArray[8]);
	                
	                MutableBox overrideBox = this.hatchHitBoxes.getOverrideBox();
	                if(overrideBox != null)
	                {
	                    overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.05F, -2.75F));
	                }
	    			break;
	    		}
	    		default:
	    			break;
        	}
    	}
    }
    
    public void setPartPosition(EntityPart part, Vec3 vec3) 
    {
    	if(vec3 != null)
    	{
        	part.setX(vec3.x);
        	part.setY(vec3.y);
        	part.setZ(vec3.z);
    	}
    }
    
    public void setPartPosition(EntityPart part, double offsetX, double offsetY, double offsetZ) 
    {
    	part.setX(offsetX);
    	part.setY(offsetY);
    	part.setZ(offsetZ);
    }
}
