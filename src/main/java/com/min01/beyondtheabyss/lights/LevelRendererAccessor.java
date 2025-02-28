package com.min01.beyondtheabyss.lights;

public interface LevelRendererAccessor
{
	void scheduleChunkRebuild(int x, int y, int z, boolean important);
}
