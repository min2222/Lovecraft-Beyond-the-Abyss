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
            .add(this.hatch).setBounds(1.4125F, 0.6F, 1.4125F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    private final EntityBounds detectorHitBoxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setPivot(0, -2.5F, 0).setParent(this.root).build()
            .add(this.detector).setBounds(2.5625F, 2.4375F, 4.375F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    private final String controllerSeat = "controllerSeat";
    private final String seat1 = "seat1";
    private final String seat2 = "seat2";
    private final String seat3 = "seat3";
    private final String seat4 = "seat4";
    private final String bottom = "bottom";
    private final String topLeft = "topLeft";
    private final String topRight = "topRight";
    private final String topFront = "topFront";
    private final String topBack = "topBack";
    private final String front = "front";
    private final String back = "back";
    private final String left = "left";
    private final String right = "right";
    private final EntityBounds colliderHitboxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setPivot(0, -2.5F, 0).setParent(this.root).build()
            .add(this.controllerSeat).setBounds(0.75F, 0.3125F, 0.625F).setParent(this.submarine).build()
            .add(this.seat1).setBounds(0.75F, 0.3125F, 0.625F).setParent(this.submarine).build()
            .add(this.seat2).setBounds(0.75F, 0.3125F, 0.625F).setParent(this.submarine).build()
            .add(this.seat3).setBounds(0.75F, 0.3125F, 0.625F).setParent(this.submarine).build()
            .add(this.seat4).setBounds(0.75F, 0.3125F, 0.625F).setParent(this.submarine).build()
            .add(this.bottom).setBounds(3.1875F, 0.625F, 4.375F).setParent(this.submarine).build()
            .add(this.topLeft).setBounds(0.85416F, 0.4375F, 4.375F).setParent(this.submarine).build()
            .add(this.topRight).setBounds(0.85416F, 0.4375F, 4.375F).setParent(this.submarine).build()
            .add(this.topFront).setBounds(0.85416F, 0.4375F, 1.4583F).setParent(this.submarine).build()
            .add(this.topBack).setBounds(0.85416F, 0.4375F, 1.4583F).setParent(this.submarine).build()
            .add(this.front).setBounds(2.6875F, 2.625F, 1.125F).setParent(this.submarine).build()
            .add(this.back).setBounds(2.5625F, 2.6875F, 1.125F).setParent(this.submarine).build()
            .add(this.left).setBounds(0.3125F, 2.875F, 4.375F).setParent(this.submarine).build()
            .add(this.right).setBounds(0.3125F, 2.875F, 4.375F).setParent(this.submarine).build()
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
	    	        EntityPart controllerSeat = this.colliderHitboxes.getPart(this.controllerSeat);
	    	        EntityPart seat1 = this.colliderHitboxes.getPart(this.seat1);
	    	        EntityPart seat2 = this.colliderHitboxes.getPart(this.seat2);
	    	        EntityPart seat3 = this.colliderHitboxes.getPart(this.seat3);
	    	        EntityPart seat4 = this.colliderHitboxes.getPart(this.seat4);
	    	        EntityPart bottom = this.colliderHitboxes.getPart(this.bottom);
	    	        EntityPart topLeft = this.colliderHitboxes.getPart(this.topLeft);
	    	        EntityPart topRight = this.colliderHitboxes.getPart(this.topRight);
	    	        EntityPart topFront = this.colliderHitboxes.getPart(this.topFront);
	    	        EntityPart topBack = this.colliderHitboxes.getPart(this.topBack);
	    	        EntityPart front = this.colliderHitboxes.getPart(this.front);
	    	        EntityPart back = this.colliderHitboxes.getPart(this.back);
	    	        EntityPart left = this.colliderHitboxes.getPart(this.left);
	    	        EntityPart right = this.colliderHitboxes.getPart(this.right);
	    	        
	    	        root.setRotation(0, -this.entity.getYRot(), 0, true);
	    	        submarine.setRotation(this.entity.getXRot(), 0, 0, true);
	    	        
	    	        root.setX(this.entity.getX());
	    	        root.setY(this.entity.getY());
	    	        root.setZ(this.entity.getZ());
	    	        
	                if(this.entity.getOwner() != null)
	                {
	                	EntitySubmarine owner = this.entity.getOwner();
	                	
		    	    	this.setPartPosition(right, owner.posArray[9]);
		    	        this.setPartPosition(left, owner.posArray[10]);
		    	        this.setPartPosition(back, owner.posArray[6]);
		    	        this.setPartPosition(front, owner.posArray[7]);
		    	        if(owner.posArray[11] != null)
		    	        {
		    	            this.setPartPosition(topLeft, owner.posArray[11].add(0.85F, 0, 0));
		    	            this.setPartPosition(topRight, owner.posArray[11].subtract(0.85F, 0, 0));
		    	            this.setPartPosition(topFront, owner.posArray[11].add(0, 0, 1.46F));
		    	            this.setPartPosition(topBack, owner.posArray[11].subtract(0, 0, 1.46F));
		    	        }
		    	        this.setPartPosition(bottom, owner.posArray[5]);
		    	        this.setPartPosition(seat4, owner.posArray[4]);
		    	        this.setPartPosition(seat3, owner.posArray[3]);
		    	        this.setPartPosition(seat2, owner.posArray[2]);
		    	        this.setPartPosition(seat1, owner.posArray[1]);
		    	        this.setPartPosition(controllerSeat, owner.posArray[0]);
	                }
	    	    	
	    	        MutableBox overrideBox = this.colliderHitboxes.getOverrideBox();
	    	        if (overrideBox != null)
	    	        {
	    	            overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.05F, -2.75F));
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
	                    overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.05F, -2.75F));
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
	                
	                if(this.entity.getOwner() != null)
	                {
	                	EntitySubmarine owner = this.entity.getOwner();
		                this.setPartPosition(hatch, owner.posArray[8]);
	                }
	                
	                MutableBox overrideBox = this.hatchHitBoxes.getOverrideBox();
	                if (overrideBox != null)
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
        	Vec3 pos = vec3.subtract(this.entity.position());
        	part.setX(pos.x);
        	part.setY(pos.y);
        	part.setZ(pos.z);
    	}
    }
    
    public void setPartPosition(EntityPart part, double offsetX, double offsetY, double offsetZ) 
    {
    	part.setX(offsetX);
    	part.setY(offsetY);
    	part.setZ(offsetZ);
    }
}
