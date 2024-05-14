package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.event.ClientEventHandler;
import com.min01.beyondtheabyss.shader.BTAShaders;
import com.min01.beyondtheabyss.shader.ExtendedPostChain;
import com.mojang.math.Vector3f;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BTAClientUtil
{
	public static void testShader(float partialTick)
	{
    	ExtendedPostChain shaderChain = BTAShaders.getTest();
    	EffectInstance shader = shaderChain.getMainShader();
    	if(shader != null && shaderChain.isEnabled)
    	{
    		shaderChain.process(partialTick);
            ClientEventHandler.MC.getMainRenderTarget().bindWrite(false);
    	}
	}
	
	public static void animateWhen(AnimationState state, boolean flag, int tick) 
	{
		if(flag) 
		{
			state.startIfStopped(tick);
		}
		else
		{
			state.stop();
        }
	}
	
	public static void animateHead(ModelPart head, float netHeadYaw, float headPitch)
	{
		head.yRot += netHeadYaw * ((float)Math.PI / 180F);
		head.xRot += headPitch * ((float)Math.PI / 180F);
	}
	
	public static void animateWalk(AbstractBTAMob entity, HierarchicalModel<? extends Entity> model, AnimationDefinition animation, float limbSwing, float limbSwingAmount, float p_268138_, float p_268165_) 
	{
		if(entity.getAnimationState() == 0)
		{
			if(BTAUtil.isMoving(entity))
			{
				long i = (long)(limbSwing * 50.0F * p_268138_);
				float f = Math.min(limbSwingAmount * p_268165_, 1.0F);
				KeyframeAnimations.animate(model, animation, i, f, new Vector3f());
			}
		}
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
