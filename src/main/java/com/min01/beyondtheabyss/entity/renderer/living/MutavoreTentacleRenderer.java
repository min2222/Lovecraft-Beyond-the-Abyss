package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavoreTentacle;
import com.min01.beyondtheabyss.entity.model.ModelMutavoreTentacle;
import com.min01.beyondtheabyss.entity.model.ModelMutavoreTentacleEdge;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MutavoreTentacleRenderer extends EntityRenderer<EntityMutavoreTentacle>
{
	public static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/mutavore_tentacle.png");
	public static final ResourceLocation EDGE_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/mutavore_tentacle_edge.png");
	public final ModelMutavoreTentacle model;
	public final ModelMutavoreTentacleEdge edgeModel;
	public MutavoreTentacleRenderer(Context p_174008_)
	{
		super(p_174008_);
		this.model = new ModelMutavoreTentacle(p_174008_.bakeLayer(ModelMutavoreTentacle.LAYER_LOCATION));
		this.edgeModel = new ModelMutavoreTentacleEdge(p_174008_.bakeLayer(ModelMutavoreTentacleEdge.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntityMutavoreTentacle p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		p_114488_.pushPose();
		float f = Mth.rotLerp(p_114487_, p_114485_.yRotO, p_114485_.getYRot());
        float f6 = Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot());
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0, -1.5F, 0);
		VertexConsumer consumer = p_114489_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_114485_)));
		if(p_114485_.getBody() != null)
		{
			if(p_114485_.isEdge())
			{
				this.edgeModel.setupAnim(p_114485_, 0, 0, 0, f, f6);
				this.edgeModel.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_.getBody(), 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			}
			else
			{
				this.model.setupAnim(p_114485_, 0, 0, 0, f, f6);
				this.model.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_.getBody(), 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
		p_114488_.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMutavoreTentacle p_114482_) 
	{
		return p_114482_.isEdge() ? EDGE_TEXTURE : TEXTURE;
	}
}
