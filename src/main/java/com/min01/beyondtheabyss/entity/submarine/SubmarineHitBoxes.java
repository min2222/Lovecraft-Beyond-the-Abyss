package com.min01.beyondtheabyss.entity.submarine;

import com.min01.beyondtheabyss.multipart.entity.EntityBounds;
import com.min01.beyondtheabyss.multipart.entity.EntityPart;
import com.min01.beyondtheabyss.multipart.entity.MutableBox;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SubmarineHitBoxes 
{
    private final EntitySubmarine entity;
    private final AABB collisionHitbox = new AABB(Vec3.ZERO, new Vec3(5.5F, 1.0F, 5.5F));
    private final String root = "root";
    private final String submarine = "submarine";
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
    private final String hatch = "hatch";
    private final String front = "front";
    private final String back = "back";
    private final String left = "left";
    private final String right = "right";
    private final EntityBounds hitboxes = EntityBounds.builder()
            .add(this.root).setBounds(0.0, 0.0, 0.0).build()
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setParent(this.root).build()
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
            .add(this.hatch).setBounds(1.3125F, 0.5F, 1.3125F).setParent(this.submarine).build()
            .add(this.front).setBounds(2.6875F, 2.625F, 1.125F).setParent(this.submarine).build()
            .add(this.back).setBounds(2.5625F, 2.6875F, 1.125F).setParent(this.submarine).build()
            .add(this.left).setBounds(0.3125F, 2.875F, 4.375F).setParent(this.submarine).build()
            .add(this.right).setBounds(0.3125F, 2.875F, 4.375F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    public SubmarineHitBoxes(EntitySubmarine entity)
    {
        this.entity = entity;
    }

    public EntityBounds getHitbox()
    {
    	return this.hitboxes;
    }

    public void updatePosition()
    {
        EntityPart root = this.hitboxes.getPart(this.root);
        EntityPart submarine = this.hitboxes.getPart(this.submarine);
        EntityPart controllerSeat = this.hitboxes.getPart(this.controllerSeat);
        EntityPart seat1 = this.hitboxes.getPart(this.seat1);
        EntityPart seat2 = this.hitboxes.getPart(this.seat2);
        EntityPart seat3 = this.hitboxes.getPart(this.seat3);
        EntityPart seat4 = this.hitboxes.getPart(this.seat4);
        EntityPart bottom = this.hitboxes.getPart(this.bottom);
        EntityPart topLeft = this.hitboxes.getPart(this.topLeft);
        EntityPart topRight = this.hitboxes.getPart(this.topRight);
        EntityPart topFront = this.hitboxes.getPart(this.topFront);
        EntityPart topBack = this.hitboxes.getPart(this.topBack);
        EntityPart hatch = this.hitboxes.getPart(this.hatch);
        EntityPart front = this.hitboxes.getPart(this.front);
        EntityPart back = this.hitboxes.getPart(this.back);
        EntityPart left = this.hitboxes.getPart(this.left);
        EntityPart right = this.hitboxes.getPart(this.right);
        
        root.setRotation(0, this.entity.yHeadRot - this.entity.yBodyRot, 0, true);
        submarine.setRotation(this.entity.getXRot(), 0, 0, true);
        
        root.setX(this.entity.getX());
        root.setY(this.entity.getY());
        root.setZ(this.entity.getZ());
    	
    	this.setPartPosition(right, this.entity.posArray[9]);
        this.setPartPosition(left, this.entity.posArray[10]);
        this.setPartPosition(back, this.entity.posArray[6]);
        this.setPartPosition(front, this.entity.posArray[7]);
        if(this.entity.posArray[11] != null)
        {
            this.setPartPosition(topLeft, this.entity.posArray[11].add(0.85F, 0, 0));
            this.setPartPosition(topRight, this.entity.posArray[11].subtract(0.85F, 0, 0));
            this.setPartPosition(topFront, this.entity.posArray[11].add(0, 0, 1.46F));
            this.setPartPosition(topBack, this.entity.posArray[11].subtract(0, 0, 1.46F));
        }
        this.setPartPosition(bottom, this.entity.posArray[5]);
        this.setPartPosition(hatch, this.entity.hatchOpened() ? this.entity.posArray[5] : this.entity.posArray[8]);
        this.setPartPosition(seat4, this.entity.posArray[4]);
        this.setPartPosition(seat3, this.entity.posArray[3]);
        this.setPartPosition(seat2, this.entity.posArray[2]);
        this.setPartPosition(seat1, this.entity.posArray[1]);
        this.setPartPosition(controllerSeat, this.entity.posArray[0]);
        
        MutableBox overrideBox = this.hitboxes.getOverrideBox();
        if(overrideBox != null)
        {
            overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.05F, -2.75F));
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
}