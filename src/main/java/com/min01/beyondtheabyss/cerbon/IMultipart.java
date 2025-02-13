package com.min01.beyondtheabyss.cerbon;

import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public interface IMultipart
{
    EntityBounds getBounds();

    CompoundOrientedBox getCompoundBoundingBox(AABB bounds);

    void onSetPos(double x, double y, double z);
    
    default boolean useSubRoot() 
    {
    	return false;
    }
    
    default String subRoot()
    {
    	return "";
    }

	EntityPartBuilder<?> getPartBuilder();
    
    default InteractionResult interact(Entity entity, InteractionHand hand, String part) 
    {
        return InteractionResult.PASS;
    }
}
