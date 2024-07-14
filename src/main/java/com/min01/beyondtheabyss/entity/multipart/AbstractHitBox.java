package com.min01.beyondtheabyss.entity.multipart;

import com.min01.beyondtheabyss.cerbon.EntityBounds;
import com.min01.beyondtheabyss.cerbon.EntityPart;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractHitBox
{
    public abstract EntityBounds getEntityBounds();

    public void updatePosition()
    {
    	Vec3 rotation = this.getRotation();
        this.root().setRotation(0, rotation.y, 0, true);
        this.subRoot().setRotation(rotation.x, 0, 0, true);
        
        this.root().setX(this.getEntity().getX());
        this.root().setY(this.getEntity().getY());
        this.root().setZ(this.getEntity().getZ());
    }
    
    public abstract Entity getEntity();
    
    public abstract Vec3 getRotation();

    public abstract EntityPart root();
    
    public abstract EntityPart subRoot();
    
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
