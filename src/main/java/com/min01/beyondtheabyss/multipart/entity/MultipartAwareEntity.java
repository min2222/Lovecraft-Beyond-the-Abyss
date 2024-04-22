package com.min01.beyondtheabyss.multipart.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;

/**
 * Should be implemented by entities who want to know what part of them was attacked or interacted
 */
public interface MultipartAwareEntity extends MultipartEntity 
{
    /**
     * @return Entity bounds as shown in the example in {@link MultipartEntity}
     */
    EntityBounds getBounds();

    void onSetPos(final double x, final double y, final double z);
    
    /**
     * @param entity The entity interacting with this
     * @param hand   The hand the entity is using
     * @param part   The part it is interacting with, should not be null
     * @return Same as vanilla interact
     */
    default InteractionResult interact(final Entity entity, final InteractionHand hand, final String part) 
    {
        return InteractionResult.PASS;
    }
}
