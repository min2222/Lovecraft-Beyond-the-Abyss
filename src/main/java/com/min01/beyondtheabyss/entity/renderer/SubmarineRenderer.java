package com.min01.beyondtheabyss.entity.renderer;

import org.joml.Matrix3f;
import org.joml.Vector3f;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SubmarineEntity;
import com.min01.beyondtheabyss.entity.model.SubmarineModel;
import com.min01.beyondtheabyss.event.ClientEventHandlerForge;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class SubmarineRenderer extends EntityRenderer<SubmarineEntity>
{
	public final SubmarineModel model;
	
	public SubmarineRenderer(Context pContext)
	{
		super(pContext);
		this.model = new SubmarineModel(pContext.bakeLayer(SubmarineModel.LAYER_LOCATION));
	}
	
	@Override
	public void render(SubmarineEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		pPoseStack.pushPose();
		float yRot = Mth.rotLerp(pPartialTick, pEntity.yRotO, pEntity.getYRot());
		float xRot = Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot());
		pPoseStack.scale(-1.0F, -1.0F, 1.0F);
		pPoseStack.translate(0.0F, -1.5F, 0.0F);
		if(pEntity.getFirstPassenger() != null)
		{
			boolean flag = pEntity.getFirstPassenger() instanceof Player ? !BTAClientUtil.MC.options.getCameraType().isFirstPerson() : true;
			if(flag && pEntity.isAlive())
			{
				pPoseStack.pushPose();
				EntityRenderer<? super Entity> entityRenderer = BTAClientUtil.MC.getEntityRenderDispatcher().getRenderer(pEntity.getFirstPassenger());
				ClientEventHandlerForge.RENDERER_LIST.remove(pEntity.getFirstPassenger().getUUID());
				this.transform(pPoseStack);
				pPoseStack.mulPose(Axis.XN.rotationDegrees(180.0F));
	            pPoseStack.mulPose(Axis.YN.rotationDegrees(360.0F - Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot())));
				entityRenderer.render(pEntity.getFirstPassenger(), 0, pPartialTick, pPoseStack, pBuffer, pPackedLight);
				ClientEventHandlerForge.RENDERER_LIST.add(pEntity.getFirstPassenger().getUUID());
				pPoseStack.popPose();
			}
		}
		this.model.setupAnim(pEntity, 0, 0, pEntity.tickCount + pPartialTick, yRot + 180.0F, xRot);
		this.model.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		pPoseStack.popPose();

        //TODO
        /*PoseStack stack = new PoseStack();
        stack.scale(-1.0F, -1.0F, 1.0F);
        stack.translate(0.0F, -1.5F, 0.0F);

		this.transform(stack);

		stack.mulPose(Axis.XN.rotationDegrees(180.0F));
		stack.mulPose(Axis.YN.rotationDegrees(360.0F - Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot())));
		stack.mulPose(Axis.XN.rotationDegrees(xRot));
		
        Vector4f vector4f = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
        vector4f.mul(stack.last().pose());
        Vec3 offset = new Vec3(vector4f.x(), vector4f.y(), vector4f.z());
        stack.popPose();
		
		pEntity.sitPos[0] = offset.add(pEntity.position());
		BTANetwork.sendToServer(new UpdatePosArrayPacket(pEntity.getUUID(), offset.add(pEntity.position()), 0));*/
		
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTick, stack -> {}, stack -> {});
	}
	
	public void transform(PoseStack stack)
	{
		this.model.root.translateAndRotate(stack);
		this.model.submarine.translateAndRotate(stack);
		this.model.controller.translateAndRotate(stack);
		stack.translate(0.0F, 0.25F, 0.0F);
	}
	
	public SubmarineTransform buildTransform(SubmarineEntity entity, float yRot, float xRot) 
	{
		PoseStack stack = new PoseStack();
		stack.scale(-1.0F, -1.0F, 1.0F);
		stack.translate(0.0F, -1.5F, 0.0F);
		this.transform(stack);
		stack.mulPose(Axis.XN.rotationDegrees(180.0F));
		stack.mulPose(Axis.YN.rotationDegrees(360.0F - yRot));
		stack.mulPose(Axis.XN.rotationDegrees(xRot));
		
		Matrix3f normal = new Matrix3f(stack.last().normal());
		Vector3f forward = normal.transform(new Vector3f(0.0F, 0.0F, 1.0F));
		forward.normalize();
		Vector3f up = normal.transform(new Vector3f(0.0F, 1.0F, 0.0F));
		up.normalize();
		
		float pitch = (float) Math.toDegrees(Math.asin(Mth.clamp(-forward.y(), -1.0, 1.0)));
		float yaw = (float) Math.toDegrees(-Math.atan2(forward.x(), forward.z()));
		pitch = Mth.wrapDegrees(pitch);
		yaw = Mth.wrapDegrees(yaw);
		return new SubmarineTransform(pitch, yaw);
	}
	
	public static record SubmarineTransform(float pitch, float yaw)
	{
		
	}

	@Override
	public ResourceLocation getTextureLocation(SubmarineEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/submarine.png");
	}
}
