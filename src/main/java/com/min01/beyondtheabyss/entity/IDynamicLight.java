package com.min01.beyondtheabyss.entity;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import com.min01.beyondtheabyss.misc.BTADynamicLights;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.Level;

//https://github.com/txnimc/SodiumDynamicLights/blob/main/src/main/java/toni/sodiumdynamiclights/DynamicLightSource.java
public interface IDynamicLight 
{
	double getDynamicLightX();

	double getDynamicLightY();

	double getDynamicLightZ();

	Level getDynamicLightLevel();

	default boolean isDynamicLightEnabled()
	{
		return BTADynamicLights.get().containsLightSource(this);
	}
	
	@ApiStatus.Internal
	default void setDynamicLightEnabled(boolean enabled) 
	{
		this.resetDynamicLight();
		if(enabled)
		{
			BTADynamicLights.get().addLightSource(this);
		}
		else
		{
			BTADynamicLights.get().removeLightSource(this);
		}
	}

	void resetDynamicLight();

	int getLuminance();

	void dynamicLightTick();

	boolean shouldUpdateDynamicLight();

	boolean updateDynamicLight(@NotNull LevelRenderer renderer);

	void scheduleTrackedChunksRebuild(@NotNull LevelRenderer renderer);
}
