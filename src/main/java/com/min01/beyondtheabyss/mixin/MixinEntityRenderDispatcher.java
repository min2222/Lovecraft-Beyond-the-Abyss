package com.min01.beyondtheabyss.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.multipart.OrientedBox;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@Mixin(value = EntityRenderDispatcher.class, priority = -20000)
public class MixinEntityRenderDispatcher
{
    @Inject(method = "renderHitbox", at = @At("RETURN"))
    private static void renderHitbox(PoseStack matrix, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo ci) 
    {
        AABB box = entity.getBoundingBox();
        if(box instanceof CompoundOrientedBox compoundOrientedBox)
        {
            matrix.pushPose();
            matrix.translate(-entity.getX(), -entity.getY(), -entity.getZ());
            for(OrientedBox orientedBox : compoundOrientedBox) 
            {
                matrix.pushPose();
                Vec3 center = orientedBox.getCenter();
                matrix.translate(center.x, center.y, center.z);
                matrix.mulPose(orientedBox.getRotation().toFloatQuat());
                LevelRenderer.renderLineBox(matrix, vertices, orientedBox.getExtents(), 0, 0, 1, 1);
                matrix.popPose();
            }
            matrix.popPose();
        }
    }
    
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.AFTER), cancellable = true)
    private <E extends Entity> void renderAfter(E p_114385_, double p_114386_, double p_114387_, double p_114388_, float p_114389_, float p_114390_, PoseStack p_114391_, MultiBufferSource p_114392_, int p_114393_, CallbackInfo ci)
    {
    	if(p_114385_ instanceof LivingEntity living)
    	{
        	if(living instanceof IMultipart partBuilder)
        	{
        		EntityPartBuilder<?> builder = partBuilder.getPartBuilder();
        		builder.tick(p_114390_);
        	}
    	}
    }
}
