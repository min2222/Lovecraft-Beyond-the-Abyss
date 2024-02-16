package com.min01.beyondtheabyss.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BTAClientUtil
{
    public static void matrixStackFromModel(PoseStack matrixStack, ModelPart modelPart)
    {
        modelPart.getAllParts().forEach(t -> t.translateAndRotate(matrixStack));
    }

    public static Vec3 getWorldPosFromModel(Entity entity, ModelPart modelPart, Vec3 rotation, float yRot) 
    {
        PoseStack matrixStack = new PoseStack();
        matrixStack.translate(0, 0, 0);
        float pi = (float) (Math.PI / 180);
        matrixStack.mulPose(new Quaternion(0, yRot, 0, true));
        matrixStack.mulPose(new Quaternion((float)rotation.x / pi, (float)rotation.y / pi, (float)rotation.z / pi, true));
        matrixStack.scale(-1, -1, 1);
        matrixStack.translate(0, -1.5F, 0);
        matrixStackFromModel(matrixStack, modelPart);
        PoseStack.Pose matrixEntry = matrixStack.last();
        Matrix4f matrix4f = matrixEntry.pose();

        Vector4f vec = new Vector4f(0, 0, 0, 1);
        vec.transform(matrix4f);
        return new Vec3(vec.x(), vec.y(), vec.z());
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
