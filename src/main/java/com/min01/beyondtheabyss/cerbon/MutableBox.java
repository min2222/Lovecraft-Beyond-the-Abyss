package com.min01.beyondtheabyss.cerbon;

import net.minecraft.world.phys.AABB;

public final class MutableBox
{
    private AABB box;

    public MutableBox(AABB box) 
    {
        this.box = box;
    }

    public AABB getBox() 
    {
        return box;
    }

    public void setBox(AABB box)
    {
        this.box = box;
    }
}
