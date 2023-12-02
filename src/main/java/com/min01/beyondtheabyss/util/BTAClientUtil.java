package com.min01.beyondtheabyss.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BTAClientUtil
{
    public static void matrixStackFromModel(PoseStack matrixStack, ModelPart modelPart)
    {
        /*ModelPart parent = modelPart;
        if(parent != null) matrixStackFromModel(matrixStack, parent);*/
        modelPart.translateAndRotate(matrixStack);
    }

    public static Vec3 getWorldPosFromModel(Entity entity, float entityYaw, ModelPart modelPart) 
    {
        PoseStack matrixStack = new PoseStack();
        matrixStack.translate(entity.getX(), entity.getY(), entity.getZ());
        matrixStack.mulPose(new Quaternion(0, -entityYaw + 180, 0, true));
        matrixStack.scale(-1, -1, 1);
        matrixStack.translate(0, -1.5f, 0);
        matrixStackFromModel(matrixStack, modelPart);
        PoseStack.Pose matrixEntry = matrixStack.last();
        Matrix4f matrix4f = matrixEntry.pose();

        Vector4f vec = new Vector4f(0, 0, 0, 1);
        vec.transform(matrix4f);
        return new Vec3(vec.x(), vec.y(), vec.z());
    }
}
