package com.min01.beyondtheabyss.multipart.entity;

import com.min01.beyondtheabyss.multipart.util.CompoundOrientedBox;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public interface IMultipart
{
    EntityBounds getBounds();

    CompoundOrientedBox getCompoundBoundingBox(AABB bounds);

    void onSetPos(final double x, final double y, final double z);
    
    default InteractionResult interact(final Entity entity, final InteractionHand hand, final String part) 
    {
        return InteractionResult.PASS;
    }
}
