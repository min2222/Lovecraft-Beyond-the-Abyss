package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;

import net.minecraft.client.model.HierarchicalModel;

public interface IModel<T extends AbstractBTAMonster>
{
	HierarchicalModel<T> getModel(T entity);
}
