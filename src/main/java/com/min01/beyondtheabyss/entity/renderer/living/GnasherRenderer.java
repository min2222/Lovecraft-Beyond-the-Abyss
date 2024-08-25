package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasherLeader;
import com.min01.beyondtheabyss.entity.renderer.IModel;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GnasherRenderer extends EntityRenderer<EntityGnasher> implements IModel<EntityGnasher>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher.png");
	private static final ResourceLocation TEXTURE_LEADER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_leader.png");
	private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_layer.png");
	private static final ResourceLocation LAYER_TEXTURE_LEADER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/gnasher_layer_leader.png");
	
	private final ModelGnasher model;
	private final ModelGnasherLeader leaderModel;
	
	public GnasherRenderer(Context p_174008_)
	{
		super(p_174008_);
		this.model = new ModelGnasher(p_174008_.bakeLayer(ModelGnasher.LAYER_LOCATION));
		this.leaderModel = new ModelGnasherLeader(p_174008_.bakeLayer(ModelGnasherLeader.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntityGnasher p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		p_114488_.pushPose();
		float f = Mth.rotLerp(p_114487_, p_114485_.yBodyRotO, p_114485_.yBodyRot);
		float f1 = Mth.rotLerp(p_114487_, p_114485_.yHeadRotO, p_114485_.yHeadRot);
		float f2 = f1 - f;
        float f6 = Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot());
        float f8 = p_114485_.walkAnimation.speed(p_114487_);
        float f7 = p_114485_.tickCount + p_114487_;
        float f5 = p_114485_.walkAnimation.position(p_114487_);
		BTAClientUtil.setupRotations(p_114485_, p_114488_, f7, f, p_114487_);
		if(!p_114485_.isInWater())
		{
			p_114488_.translate(0.5F, 0, 0);
			p_114488_.mulPose(Axis.ZP.rotationDegrees(90.0F));
		}
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0, -1.5F, 0);
		VertexConsumer consumer = p_114489_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_114485_)));
		if(p_114485_.isLeader())
		{
			this.leaderModel.setupAnim(p_114485_, f5, f8, f7, f2, f6);
			this.leaderModel.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			BTAClientUtil.coloredGlowingModelCopyLayerRender(this.leaderModel, this.leaderModel, LAYER_TEXTURE_LEADER, p_114488_, p_114489_, p_114490_, p_114485_, f5, f8, f7, f2, f6, p_114487_, 1.0F, 1.0F, 1.0F);
		}
		else
		{
			this.model.setupAnim(p_114485_, f5, f8, f7, f2, f6);
			this.model.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			BTAClientUtil.coloredGlowingModelCopyLayerRender(this.model, this.model, LAYER_TEXTURE, p_114488_, p_114489_, p_114490_, p_114485_, f5, f8, f7, f2, f6, p_114487_, 1.0F, 1.0F, 1.0F);
		}
		p_114488_.popPose();
	}
	
	@Override
	public HierarchicalModel<EntityGnasher> getModel(EntityGnasher entity)
	{
		return entity.isLeader() ? this.leaderModel : this.model;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGnasher p_114482_) 
	{
		return p_114482_.isLeader() ? TEXTURE_LEADER : TEXTURE;
	}
}
