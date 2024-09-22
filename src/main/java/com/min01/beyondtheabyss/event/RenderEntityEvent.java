package com.min01.beyondtheabyss.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;

public class RenderEntityEvent<T extends Entity> extends Event
{
    private final LivingEntity entity;

    public RenderEntityEvent(LivingEntity entity)
    {
    	this.entity = entity;
    }
    
    public LivingEntity getEntity()
    {
        return entity;
    }
}
