package com.min01.beyondtheabyss.misc;

public interface LevelRendererAccessor
{
	void scheduleChunkRebuild(int x, int y, int z, boolean important);
}
