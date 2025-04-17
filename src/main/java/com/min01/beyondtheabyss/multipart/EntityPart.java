package com.min01.beyondtheabyss.multipart;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.phys.AABB;

/**
 * Represents a hit box of an entity
 */
public final class EntityPart 
{
    private boolean changed = true;
    private double offX, offY, offZ;
    private double x, y, z;
    private final AABB box;
    private double px, py, pz;
    private QuaternionD rotation;
    private @Nullable EntityPart parent;
    private boolean collide;

    EntityPart(@Nullable EntityPart parent, AABB box, boolean center, double offX, double offY, double offZ)
    {
        this.parent = parent;
        this.offX = offX;
        this.offY = offY;
        this.offZ = offZ;
        this.rotation = QuaternionD.IDENTITY;
        if(center)
        {
        	box = box.move(-box.minX - box.getXsize() / 2, -box.minY - box.getXsize() / 2, -box.minZ - box.getXsize() / 2);
        }
        this.box = box;
        this.setX(0.0);
        this.setY(0.0);
        this.setZ(0.0);
    }

    void setParent(@Nullable EntityPart parent) 
    {
        this.parent = parent;
    }
    
    public void setCollide(boolean collide)
    {
        this.collide = collide;
        this.changed = true;
    }

    public void setOffX(double offX)
    {
        this.offX = offX;
        this.changed = true;
    }

    public void setOffY(double offY) 
    {
        this.offY = offY;
        this.changed = true;
    }

    public void setOffZ(double offZ)
    {
        this.offZ = offZ;
        this.changed = true;
    }

    /**
     * @param x X coordinate relative to parent
     */
    public void setX(double x)
    {
        this.x = x + this.offX;
        this.changed = true;
    }

    /**
     * @param y Y coordinate relative to parent
     */
    public void setY(double y)
    {
        this.y = y + this.offY;
        this.changed = true;
    }

    /**
     * @param z Z coordinate relative to parent
     */
    public void setZ(double z) 
    {
        this.z = z + this.offZ;
        this.changed = true;
    }

    /**
     * @param px X coordinate of point this part should be rotated around
     */
    public void setPivotX(double px)
    {
        this.px = px;
        this.changed = true;
    }

    /**
     * @param py X coordinate of point this part should be rotated around
     */
    public void setPivotY(double py)
    {
        this.py = py;
        this.changed = true;
    }

    /**
     * @param pz X coordinate of point this part should be rotated around
     */
    public void setPivotZ(double pz) 
    {
        this.pz = pz;
        this.changed = true;
    }

    public void setRotation(QuaternionD rotation)
    {
        this.rotation = rotation;
        this.changed = true;
    }

    public void rotate(QuaternionD quaternion)
    {
    	this.rotation = this.rotation.hamiltonProduct(quaternion);
        this.changed = true;
    }

    public void rotate(double pitch, double yaw, double roll, boolean degrees)
    {
    	this.rotation = this.rotation.hamiltonProduct(new QuaternionD(pitch, yaw, roll, degrees));
        this.changed = true;
    }

    public void setRotation(double pitch, double yaw, double roll, boolean degrees) 
    {
    	this.rotation = new QuaternionD(pitch, yaw, roll, degrees);
    	this.changed = true;
    }

    void setChanged(boolean changed) 
    {
        this.changed = changed;
    }

    boolean isChanged() 
    {
        return this.changed;
    }

    /**
     * @return Oriented box represented by this EntityPart after all transformations have been applied
     */
    public OrientedBox getBox()
    {
        OrientedBox orientedBox = new OrientedBox(this.box, this.collide);
        OrientedBox child = this.transformChild(orientedBox);
        child.collide = this.collide;
        return child;
    }

    private OrientedBox transformChild(OrientedBox orientedBox)
    {
        if(this.parent != null)
        {
            orientedBox = this.parent.transformChild(orientedBox);
        }
        return orientedBox.transform(this.x, this.y, this.z, this.px, this.py, this.pz, this.rotation);
    }
}
