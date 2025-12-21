package com.min01.beyondtheabyss.world.effects;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

public class DeepAbyssDimensionSpecialEffects extends DimensionSpecialEffects
{
	public DeepAbyssDimensionSpecialEffects()
	{
		super(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, false);
	}

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 pFogColor, float pBrightness)
	{
		return pFogColor.multiply((double)(pBrightness * 0.94F + 0.06F), (double)(pBrightness * 0.94F + 0.06F), (double)(pBrightness * 0.91F + 0.09F));
	}

	@Override
	public boolean isFoggyAt(int pX, int pY)
	{
		return false;
	}
	
	@Override
	public float[] getSunriseColor(float pTimeOfDay, float pPartialTicks) 
	{
		return null;
	}
}
