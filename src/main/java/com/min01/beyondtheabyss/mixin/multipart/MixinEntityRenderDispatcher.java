package com.min01.beyondtheabyss.mixin.multipart;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.cerbon.CompoundOrientedBox;
import com.min01.beyondtheabyss.cerbon.OrientedBox;
import com.min01.beyondtheabyss.event.RenderEntityEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher
{
    @Inject(method = "renderHitbox", at = @At("RETURN"))
    private static void drawOrientedBoxes(PoseStack matrix, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo ci) 
    {
        AABB box = entity.getBoundingBox();
        if(box instanceof final CompoundOrientedBox compoundOrientedBox)
        {
            matrix.pushPose();
            matrix.translate(-entity.getX(), -entity.getY(), -entity.getZ());

            for(final OrientedBox orientedBox : compoundOrientedBox) 
            {
                matrix.pushPose();
                final Vec3 center = orientedBox.getCenter();
                matrix.translate(center.x, center.y, center.z);
                matrix.mulPose(orientedBox.getRotation().toFloatQuat());
                LevelRenderer.renderLineBox(matrix, vertices, orientedBox.getExtents(), 0, 0, 1, 1);
                matrix.popPose();
            }

            compoundOrientedBox.toVoxelShape().forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> LevelRenderer.renderLineBox(matrix, vertices, minX, minY, minZ, maxX, maxY, maxZ, 0, 1, 0, 1f));
            matrix.popPose();
        }
    }
    
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.AFTER), cancellable = true)
    private <E extends Entity> void render(E p_114385_, double p_114386_, double p_114387_, double p_114388_, float p_114389_, float p_114390_, PoseStack p_114391_, MultiBufferSource p_114392_, int p_114393_, CallbackInfo ci)
    {
    	if(p_114385_ instanceof LivingEntity living)
    	{
    		MinecraftForge.EVENT_BUS.post(new RenderEntityEvent<>(living));
    	}
    }
}
