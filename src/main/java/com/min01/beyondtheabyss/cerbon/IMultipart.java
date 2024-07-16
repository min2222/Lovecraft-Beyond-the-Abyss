package com.min01.beyondtheabyss.cerbon;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public interface IMultipart
{
    EntityBounds getBounds();

    CompoundOrientedBox getCompoundBoundingBox(AABB bounds);

    void onSetPos(double x, double y, double z);
    
    default InteractionResult interact(Entity entity, InteractionHand hand, String part) 
    {
        return InteractionResult.PASS;
    }
}
