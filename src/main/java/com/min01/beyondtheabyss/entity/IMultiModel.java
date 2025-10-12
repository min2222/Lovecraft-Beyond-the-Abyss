package com.min01.beyondtheabyss.entity;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.Entity;

public interface IMultiModel<T extends Entity>
{
	public HierarchicalModel<T> getModel(T entity);
}
