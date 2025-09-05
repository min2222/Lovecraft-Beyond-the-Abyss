package com.min01.beyondtheabyss.entity;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.LivingEntity;

public interface IMultiModel<T extends LivingEntity>
{
	public HierarchicalModel<T> getModel(T entity);
}
