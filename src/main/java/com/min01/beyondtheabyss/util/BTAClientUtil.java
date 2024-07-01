package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BTAClientUtil
{	
	//https://github.com/EEEAB/EEEABsMobs/blob/master/src/main/java/com/eeeab/animate/client/util/ModelPartUtils.java#L57
    
    public static Vec3 getWorldPositionOfMultiPart(Entity entity, ModelPart root, Vec3 rotation, String... modelPartName)
    {
    	return getWorldPosition(entity, root, false, rotation, modelPartName);
    }
    
    public static Vec3 getWorldPosition(Entity entity, ModelPart root, Vec3 rotation, String... modelPartName)
    {
    	return getWorldPosition(entity, root, true, rotation, modelPartName);
    }
    
    public static Vec3 getWorldPosition(Entity entity, ModelPart root, boolean translateToEntity, Vec3 rotation, String... modelPartName)
    {
        PoseStack poseStack = new PoseStack();
        if(translateToEntity)
        {
        	poseStack.translate(entity.getX(), entity.getY(), entity.getZ());
        }
        poseStack.mulPose(new Quaternion((float)rotation.x, (float)-rotation.y + 180.0F, (float)rotation.z, true));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        ModelPart nextPart = null;
        for(int i = 0; i < modelPartName.length; i++)
        {
            if(i == 0)
            {
                nextPart = root.getChild(modelPartName[0]);
                nextPart.translateAndRotate(poseStack);
            }
            else 
            {
                ModelPart child = nextPart.getChild(modelPartName[i]);
                child.translateAndRotate(poseStack);
                nextPart = child;
            }
        }
        PoseStack.Pose last = poseStack.last();
        Matrix4f matrix4f = last.pose();
        Vector4f vector4f = new Vector4f(0, 0, 0, 1);
        vector4f.transform(matrix4f);
        return new Vec3(vector4f.x(), vector4f.y(), vector4f.z());
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
		if(worldIn == null)
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
