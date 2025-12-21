package com.min01.beyondtheabyss.entity;

import net.minecraft.world.entity.Entity;

public interface ILeader<T extends Entity>
{
	public void setLeader(T leader);
	
	public T getLeader();
	
	public boolean isLeader();
}
