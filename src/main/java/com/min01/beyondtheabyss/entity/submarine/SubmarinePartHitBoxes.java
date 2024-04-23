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
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setPivot(0, -2.5F, 0).setParent(this.root).build()
            .add(this.hatch).setBounds(1.0F, 0.75F, 1.4F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    private final EntityBounds detectorHitBoxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setPivot(0, -2.5F, 0).setParent(this.root).build()
            .add(this.detector).setBounds(2.8F, 2.65F, 3.9F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    private final String collider = "collider";
    private final String frontCollider = "frontCollider";
    private final EntityBounds colliderHitboxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setPivot(0, -2.5F, 0).setParent(this.root).build()
            .add(this.collider).setBounds(3.25F, 3.45F, 4.4F).setParent(this.submarine).build()
            .add(this.frontCollider).setBounds(3.05F, 3.3F, 1.1F).setParent(this.submarine).build()
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
    		case COLLIDER:
    			return this.colliderHitboxes;
    		case DETECTOR:
    			return this.detectorHitBoxes;
    		case HATCH:
    			return this.hatchHitBoxes;
    		default:
        	}
    	}
		return this.colliderHitboxes;
    }

    public void updatePosition(SubmarinePart entity)
    {
    	if(entity.type != null)
    	{
        	switch(entity.type)
        	{
    		case COLLIDER:
    		{
    	        EntityPart root = this.colliderHitboxes.getPart(this.root);
    	        EntityPart submarine = this.colliderHitboxes.getPart(this.submarine);
    	        EntityPart collider = this.colliderHitboxes.getPart(this.collider);
    	        EntityPart frontCollider = this.colliderHitboxes.getPart(this.frontCollider);
    	        
    	        root.setRotation(0, -this.entity.getYRot(), 0, true);
    	        submarine.setRotation(this.entity.getXRot(), 0, 0, true);
    	        
    	        root.setX(this.entity.getX());
    	        root.setY(this.entity.getY());
    	        root.setZ(this.entity.getZ());

    	    	this.setPartPosition(frontCollider, 0, 2.9F, 2.8F);
    	    	this.setPartPosition(collider, 0, 2.85F, 0);
    	    	
    	        MutableBox overrideBox = this.colliderHitboxes.getOverrideBox();
    	        if (overrideBox != null)
    	        {
    	            overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.0, -2.75F));
    	        }
    			break;
    		}
    		case DETECTOR:
    		{
                EntityPart root = this.detectorHitBoxes.getPart(this.root);
                EntityPart submarine = this.detectorHitBoxes.getPart(this.submarine);
                EntityPart detector = this.detectorHitBoxes.getPart(this.detector);
                
                root.setRotation(0, -this.entity.getYRot(), 0, true);
                submarine.setRotation(this.entity.getXRot(), 0, 0, true);
                
                root.setX(this.entity.getX());
                root.setY(this.entity.getY());
                root.setZ(this.entity.getZ());
                
                this.setPartPosition(detector, 0, 3.0F, 0);
                
                MutableBox overrideBox = this.detectorHitBoxes.getOverrideBox();
                if (overrideBox != null)
                {
                    overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.0, -2.75F));
                }
    			break;
    		}
    		case HATCH:
    		{
                EntityPart root = this.hatchHitBoxes.getPart(this.root);
                EntityPart submarine = this.hatchHitBoxes.getPart(this.submarine);
                EntityPart hatch = this.hatchHitBoxes.getPart(this.hatch);
                
                root.setRotation(0, -this.entity.getYRot(), 0, true);
                submarine.setRotation(this.entity.getXRot(), 0, 0, true);
                
                root.setX(this.entity.getX());
                root.setY(this.entity.getY());
                root.setZ(this.entity.getZ());
                
                this.setPartPosition(hatch, 0, 4.6F, 0);
                
                MutableBox overrideBox = this.hatchHitBoxes.getOverrideBox();
                if (overrideBox != null)
                {
                    overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.0, -2.75F));
                }
    			break;
    		}
    		default:
    			break;
        	}
    	}
    }
    
    public void setPartPosition(EntityPart part, double offsetX, double offsetY, double offsetZ) 
    {
    	part.setX(offsetX);
    	part.setY(offsetY);
    	part.setZ(offsetZ);
    }
}
