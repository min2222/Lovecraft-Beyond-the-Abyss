package com.min01.beyondtheabyss.multipart;

import java.util.List;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;

public interface IMultipart
{
    EntityBounds getBounds();

    CompoundOrientedBox getCompoundBoundingBox(AABB bounds);
    
	default boolean rotateHead()
	{
		return false;
	}
	
	default Vec2 headRotation(LivingEntity living, Vec2 original)
	{
		return original;
	}
	
	default List<String> getCollidePart()
	{
		return List.of();
	}
	
	default boolean skipInvisiblePart()
	{
		return true;
	}

	EntityPartBuilder<?> getPartBuilder();
}
