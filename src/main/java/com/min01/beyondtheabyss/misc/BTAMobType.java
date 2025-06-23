package com.min01.beyondtheabyss.misc;

public enum BTAMobType
{
	BOSS(true, false, true, true, true),
	HOSTILE(true, true, true, true, true),
	NETURAL(false, true, true, true, false),
	PASSIVE(false, true, false, false, false),
	MISC(false, false, false, false, false);
	
	public boolean despawnInPeaceful;
	public boolean removeWhenFarAway;
	public boolean lookTarget;
	public boolean moveToTarget;
	public boolean alwaysHostile;
	
	BTAMobType(boolean despawnInPeaceful, boolean removeWhenFarAway, boolean lookTarget, boolean moveToTarget, boolean alwaysHostile)
	{
		this.despawnInPeaceful = despawnInPeaceful;
		this.removeWhenFarAway = removeWhenFarAway;
		this.lookTarget = lookTarget;
		this.moveToTarget = moveToTarget;
		this.alwaysHostile = alwaysHostile;
	}
}
