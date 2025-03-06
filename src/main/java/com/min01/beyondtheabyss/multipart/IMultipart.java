package com.min01.beyondtheabyss.multipart;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;

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
    
	default boolean rotateHead()
	{
		return false;
	}
	
	default Vec2 headRotation(LivingEntity living, Vec2 original)
	{
		return original;
	}
	
	EntityPartBuilder<?> getPartBuilder();
}
