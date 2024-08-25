package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;

import net.minecraft.client.model.HierarchicalModel;

public interface IModel<T extends AbstractBTAMob>
{
	HierarchicalModel<T> getModel(T entity);
}
