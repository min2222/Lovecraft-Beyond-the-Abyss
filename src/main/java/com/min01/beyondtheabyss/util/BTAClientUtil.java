package com.min01.beyondtheabyss.util;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.renderer.IModel;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BTAClientUtil
{
	public static final Minecraft MC = Minecraft.getInstance();
	
	public static <T extends AbstractBTAMonster> void coloredGlowingModelCopyLayerRender(EntityModel<T> p_117360_, EntityModel<T> p_117361_, ResourceLocation p_117362_, PoseStack p_117363_, MultiBufferSource p_117364_, int p_117365_, T p_117366_, float p_117367_, float p_117368_, float p_117369_, float p_117370_, float p_117371_, float p_117372_, float p_117373_, float p_117374_, float p_117375_)
	{
		if(!p_117366_.isInvisible())
		{
			p_117360_.copyPropertiesTo(p_117361_);
			p_117361_.prepareMobModel(p_117366_, p_117367_, p_117368_, p_117372_);
			renderColoredGlowingModel(p_117361_, p_117362_, p_117363_, p_117364_, p_117365_, p_117366_, p_117373_, p_117374_, p_117375_);
		}
	}

	public static <T extends AbstractBTAMonster> void renderColoredGlowingModel(EntityModel<T> p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, T p_117382_, float p_117383_, float p_117384_, float p_117385_)
	{
		VertexConsumer vertexconsumer = p_117380_.getBuffer(BTARenderType.eyesFix(p_117378_));
		p_117377_.renderToBuffer(p_117379_, vertexconsumer, p_117381_, LivingEntityRenderer.getOverlayCoords(p_117382_, 0.0F), p_117383_, p_117384_, p_117385_, 1.0F);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends LivingEntity> HierarchicalModel<T> getModelFromEntity(T entity)
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
	
	public static void setupRotations(AbstractBTAMonster p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) 
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
	
	public static void animateWalk(LivingEntity entity, HierarchicalModel<? extends Entity> model, AnimationDefinition animation, float limbSwing, float limbSwingAmount, float p_268138_, float p_268165_) 
	{
		long i = (long)(limbSwing * 50.0F * p_268138_);
		float f = Math.min(limbSwingAmount * p_268165_, 1.0F);
		KeyframeAnimations.animate(model, animation, i, f, new Vector3f());
	}
}
