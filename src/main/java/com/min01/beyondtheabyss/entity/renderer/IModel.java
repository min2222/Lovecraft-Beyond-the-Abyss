package com.min01.beyondtheabyss.entity.renderer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.LivingEntity;

public interface IModel<T extends LivingEntity>
{
	HierarchicalModel<T> getModel(T entity);
}
