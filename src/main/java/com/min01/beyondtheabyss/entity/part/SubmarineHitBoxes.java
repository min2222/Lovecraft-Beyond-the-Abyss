package com.min01.beyondtheabyss.entity.part;

import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
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
            .add(this.submarine).setBounds(0.0, 0.0, 0.0).setPivot(0, -2.5F, 0).setParent(this.root).build()
            .add(this.controllerSeat).setBounds(0.8F, 0.15F, 0.8F).setParent(this.submarine).build()
            .add(this.seat1).setBounds(0.8F, 0.15F, 0.8F).setParent(this.submarine).build()
            .add(this.seat2).setBounds(0.8F, 0.15F, 0.8F).setParent(this.submarine).build()
            .add(this.seat3).setBounds(0.8F, 0.15F, 0.8F).setParent(this.submarine).build()
            .add(this.seat4).setBounds(0.8F, 0.15F, 0.8F).setParent(this.submarine).build()
            .add(this.bottom).setBounds(3.0F, 0.4F, 4.2F).setParent(this.submarine).build()
            .add(this.topLeft).setBounds(1.0F, 0.55F, 4.2F).setParent(this.submarine).build()
            .add(this.topRight).setBounds(1.0F, 0.55F, 4.2F).setParent(this.submarine).build()
            .add(this.topFront).setBounds(1.0F, 0.55F, 1.4F).setParent(this.submarine).build()
            .add(this.topBack).setBounds(1.0F, 0.55F, 1.4F).setParent(this.submarine).build()
            .add(this.hatch).setBounds(1.0F, 0.45F, 1.4F).setParent(this.submarine).build()
            .add(this.front).setBounds(3.0F, 3.25F, 1.05F).setParent(this.submarine).build()
            .add(this.back).setBounds(2.7F, 2.7F, 1.05F).setParent(this.submarine).build()
            .add(this.left).setBounds(0.1F, 3.0F, 4.2F).setParent(this.submarine).build()
            .add(this.right).setBounds(0.1F, 3.0F, 4.2F).setParent(this.submarine).build()
            .overrideCollisionBox(this.collisionHitbox)
            .getFactory().create();
    
    private EntityBounds currentHitbox = this.hitboxes;
    
    public SubmarineHitBoxes(EntitySubmarine entity)
    {
        this.entity = entity;
    }

    public EntityBounds getHitbox()
    {
    	return this.currentHitbox;
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
        
        root.setRotation(0, -this.entity.getYRot(), 0, true);
        submarine.setRotation(this.entity.getXRot(), 0, 0, true);
        
        root.setX(this.entity.getX());
        root.setY(this.entity.getY());
        root.setZ(this.entity.getZ());

    	this.setPartPosition(right, 1.55F, 2.55F, 0);
        this.setPartPosition(left, -1.55F, 2.55F, 0);
        this.setPartPosition(back, 0, 2.8F, -2.7F);
        this.setPartPosition(front, 0, 2.9F, 2.8F);
        this.setPartPosition(topLeft, 1.0F, 4.5F, 0);
        this.setPartPosition(topRight, -1.0F, 4.5F, 0);
        this.setPartPosition(topFront, 0, 4.5F, 1.5F);
        this.setPartPosition(topBack, 0, 4.5F, -1.45F);
        this.setPartPosition(bottom, 0, 1.5F, 0);
        
        this.setPartPosition(hatch, 0, this.entity.hatchOpened() ? 1.1F : 4.7F, 0);
        
        this.setPartPosition(seat4, -0.8F, 1.65F, -0.6F);
        this.setPartPosition(seat3, 0.8F, 1.65F, -0.6F);
        this.setPartPosition(seat2, -0.8F, 1.65F, 1.2F);
        this.setPartPosition(seat1, 0.8F, 1.65F, 1.2F);
        this.setPartPosition(controllerSeat, 0, 1.65F, 2.2F);

        MutableBox overrideBox = this.hitboxes.getOverrideBox();
        if (overrideBox != null)
        {
            overrideBox.setBox(this.collisionHitbox.move(this.entity.position()).move(-2.75F, 0.0, -2.75F));
        }
    }
    
    public void setPartPosition(EntityPart part, double offsetX, double offsetY, double offsetZ) 
    {
    	part.setX(offsetX);
    	part.setY(offsetY);
    	part.setZ(offsetZ);
    }
}