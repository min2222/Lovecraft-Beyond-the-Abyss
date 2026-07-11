package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.DeepAbyssPortalEntity;
import com.min01.beyondtheabyss.entity.model.DeepAbyssPortalModel;
import com.min01.beyondtheabyss.entity.model.PipeModel;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class DeepAbyssPortalRenderer extends EntityRenderer<DeepAbyssPortalEntity>
{
	private final DeepAbyssPortalModel model;
	private final PipeModel modelPipe;
	
	public DeepAbyssPortalRenderer(Context pContext)
	{
		super(pContext);
		this.model = new DeepAbyssPortalModel(pContext.bakeLayer(DeepAbyssPortalModel.LAYER_LOCATION));
		this.modelPipe = new PipeModel(pContext.bakeLayer(PipeModel.LAYER_LOCATION));
	}
	
	@Override
	public void render(DeepAbyssPortalEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		pPoseStack.pushPose();
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.5F, 0.0F);
		this.model.setupAnim(pEntity, 0, 0, pEntity.tickCount + pPartialTick, 0, 0);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		pPoseStack.popPose();
		
		pPoseStack.pushPose();
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.5F, 0.0F);
		this.model.setupAnim(pEntity, 0, 0, pEntity.tickCount + pPartialTick, 0, 0);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(BTARenderType.eyesFix(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/deep_abyss_portal_layer.png"))), pPackedLight, OverlayTexture.NO_OVERLAY, 0.8F, 0.8F, 0.8F, 1.0F);
		pPoseStack.popPose();
		
		for(int i = 0; i < 4; i++)
		{
	        Vec3 renderingAt = pEntity.getPosition(pPartialTick);
	        Vec3 bottom = pEntity.getAnchorPos(i).subtract(renderingAt);
	        Vec3 top = pEntity.getTargetPos(i).subtract(renderingAt);
	        Vec3 moveUpTowards = top.subtract(bottom);
	        RenderType renderType = RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/pipe.png"));
	        
	        Vec3 currentPos = bottom;
	        int segmentCount = 0;

	        while(segmentCount < 20 && currentPos.distanceTo(moveUpTowards) > 0.2)
	        {
	            double remainingDistance = Math.min(currentPos.distanceTo(moveUpTowards), 1.5F);
	            Vec3 linearVec = moveUpTowards.subtract(currentPos);
	            Vec3 powVec = new Vec3(this.modifyVecAngle(linearVec.x), this.modifyVecAngle(linearVec.y), this.modifyVecAngle(linearVec.z));
	            Vec3 smoothedVec = remainingDistance < 1.0F ? linearVec : powVec;
	            Vec3 next = smoothedVec.normalize().scale(remainingDistance).add(currentPos);
                int light = this.getLightColor(pEntity, bottom.add(currentPos).add(renderingAt));
	            this.renderPipeSegments(currentPos, next, pPoseStack, pBuffer.getBuffer(renderType), light);
	            currentPos = next;
	            segmentCount++;
	        }
		}
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTick, stack -> {}, stack -> {});
	}
	
	//TODO pipe hitbox
    public void renderPipeSegments(Vec3 from, Vec3 to, PoseStack poseStack, VertexConsumer buffer, int packedLightIn) 
    {
        Vec3 sub = from.subtract(to);
        float rotY = (float) (Mth.atan2(sub.x, sub.z) * (double) Mth.RAD_TO_DEG);
        float rotX = (float) (-(Mth.atan2(sub.y, sub.horizontalDistance()) * (double) Mth.RAD_TO_DEG)) - 90.0F;
        poseStack.pushPose();
        poseStack.translate(from.x, from.y, from.z);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
        poseStack.mulPose(Axis.XP.rotationDegrees(rotX));
        this.modelPipe.renderToBuffer(poseStack, buffer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
    
    private double modifyVecAngle(double dimension) 
    {
        float abs = (float) Math.abs(dimension);
        return Math.signum(dimension) * Mth.clamp(Math.pow(abs, 0.1), 0.01 * abs, abs);
    }
    
    @SuppressWarnings("deprecation")
	private int getLightColor(DeepAbyssPortalEntity portal, Vec3 vec3)
    {
        BlockPos blockPos = BlockPos.containing(vec3);
        if(portal.level.hasChunkAt(blockPos))
        {
            int i = LevelRenderer.getLightColor(portal.level, blockPos);
            int j = LevelRenderer.getLightColor(portal.level, blockPos.above());
            int k = i & 255;
            int l = j & 255;
            int i1 = i >> 16 & 255;
            int j1 = j >> 16 & 255;
            return (Math.max(k, l)) | (Math.max(i1, j1)) << 16;
        }
        else
        {
            return 0;
        }
    }

	@Override
	public ResourceLocation getTextureLocation(DeepAbyssPortalEntity pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/deep_abyss_portal.png");
	}
}
