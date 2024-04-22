package com.min01.beyondtheabyss.multipart.entity;

import com.min01.beyondtheabyss.multipart.util.CompoundOrientedBox;

import net.minecraft.world.phys.AABB;

public interface MultipartEntity
{
    CompoundOrientedBox getCompoundBoundingBox(AABB bounds);
}
