package com.min01.beyondtheabyss.util;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.renderer.IModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BTAClientUtil
{
	public static final Minecraft MC = Minecraft.getInstance();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends AbstractBTAMob> HierarchicalModel<T> getModelFromEntity(T entity)
	{
		EntityRenderer renderer = MC.getEntityRenderDispatcher().getRenderer(entity);
		if(renderer instanceof LivingEntityRenderer livingRenderer)
		{
			return (HierarchicalModel<T>) livingRenderer.getModel();
		}
		else if(renderer instanceof IModel model)
		{
			return model.getModel(entity);
		}
		return null;
	}
	
    public static String getModelPartName(ModelPart root, ModelPart target) 
    {
        AtomicReference<String> name = new AtomicReference<>("root");
        root.getAllParts().filter(part -> 
        {
            return part.children.containsValue(target);
        }).findFirst().ifPresent(part -> 
        {
            for(Map.Entry<String, ModelPart> entry : part.children.entrySet()) 
            {
                if(entry.getValue() == target) 
                {
                    name.set(entry.getKey());
                    return;
                }
            }
        });
        return name.get();
    }
	
	public static void setupRotations(AbstractBTAMob p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) 
	{
		if(p_115317_.isFullyFrozen())
		{
			p_115320_ += (float)(Math.cos((double)p_115317_.tickCount * 3.25D) * Math.PI * (double)0.4F);
		}

		if(!p_115317_.hasPose(Pose.SLEEPING)) 
		{
			p_115318_.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_115320_));
		}

		if(p_115317_.deathTime > 0)
		{
			float f = ((float)p_115317_.deathTime + p_115321_ - 1.0F) / 20.0F * 1.6F;
			f = Mth.sqrt(f);
			if(f > 1.0F) 
			{
				f = 1.0F;
			}

			p_115318_.mulPose(Vector3f.ZP.rotationDegrees(f * 90.0F));
		} 
		else if(p_115317_.isAutoSpinAttack()) 
		{
			p_115318_.mulPose(Vector3f.XP.rotationDegrees(-90.0F - p_115317_.getXRot()));
			p_115318_.mulPose(Vector3f.YP.rotationDegrees(((float)p_115317_.tickCount + p_115321_) * -75.0F));
		} 
		else if(p_115317_.hasPose(Pose.SLEEPING))
		{
			Direction direction = p_115317_.getBedOrientation();
			float f1 = direction != null ? p_115317_.partBuilder.sleepDirectionToRotation(direction) : p_115320_;
			p_115318_.mulPose(Vector3f.YP.rotationDegrees(f1));
			p_115318_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
			p_115318_.mulPose(Vector3f.YP.rotationDegrees(270.0F));
		}
		else if(LivingEntityRenderer.isEntityUpsideDown(p_115317_)) 
		{
			p_115318_.translate(0.0D, (double)(p_115317_.getBbHeight() + 0.1F), 0.0D);
			p_115318_.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
		}
	}
	
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
		head.yRot += Math.toRadians(netHeadYaw);
		head.xRot += Math.toRadians(headPitch);
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
}
