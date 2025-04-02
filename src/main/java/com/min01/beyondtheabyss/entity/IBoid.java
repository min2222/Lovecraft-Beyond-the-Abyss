package com.min01.beyondtheabyss.entity;

import java.util.Collection;
import java.util.Map;

import com.min01.beyondtheabyss.misc.Boid;
import com.min01.beyondtheabyss.misc.Boid.Bounds;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public interface IBoid<T extends LivingEntity>
{
	public void setLeader(T leader);
	
	public T getLeader();
	
	public void setLeader(boolean value);
	
	public boolean isLeader();
	
	default boolean rotLerp()
	{
		return false;
	}
	
	public Vec3 getBoundSize();
	
	public Map<T, Boid> getBoid();
	
	public Collection<Boid.Obstacle> getObstacle();
	
	public Bounds getBoidBounds();
	
	public void setBound(Bounds bound);
}
