package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.stream.Stream;

public interface IFlocking 
{
	boolean isFollower();
	
	boolean hasFollowers();
	
	boolean canBeFollowed();
	
	boolean inRangeOfLeader();
	
	void addFollowers(Stream<? extends AbstractDeepAbyssMob> p_27534_);
	
	void pathToLeader();
	
	void stopFollowing();
}
