package com.min01.beyondtheabyss.util;

import com.mojang.math.Vector3f;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BTAClientUtil
{
	public static void animateWalk(HierarchicalModel<? extends Entity> model, AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) 
	{
		long i = (long)(p_268057_ * 50.0F * p_268138_);
		float f = Math.min(p_268347_ * p_268165_, 1.0F);
		KeyframeAnimations.animate(model, p_268159_, i, f, new Vector3f());
	}
    
	public static int getCurrentFrame(Level worldIn, int frameNumber, float speed) 
	{
		if (worldIn == null)
		{
            return Math.round((System.currentTimeMillis() >> 6) % frameNumber);
		}
		else
		{
        	float time = Mth.ceil((((worldIn.getGameTime() >> 1) % frameNumber) * speed) * 1000F) / 10000F;
            return Math.round(time * 5);
        }
	}
}
