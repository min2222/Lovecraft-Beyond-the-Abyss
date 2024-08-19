package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasherLeader;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;

public class GnasherRenderer extends EntityRenderer<EntityGnasher>
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
        float f8 = Mth.lerp(p_114487_, p_114485_.animationSpeedOld, p_114485_.animationSpeed);
        float f7 = p_114485_.tickCount + p_114487_;
        float f5 = p_114485_.animationPosition - p_114485_.animationSpeed * (1.0F - p_114487_);
		this.setupRotations(p_114485_, p_114488_, f7, f, p_114487_);
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0, -1.5F, 0);
		VertexConsumer consumer = p_114489_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_114485_)));
		if(p_114485_.isLeader())
		{
			this.leaderModel.setupAnim(p_114485_, f5, f8, f7, f2, f6);
			this.leaderModel.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			this.coloredGlowingModelCopyLayerRender(this.leaderModel, this.leaderModel, LAYER_TEXTURE_LEADER, p_114488_, p_114489_, p_114490_, p_114485_, f5, f8, f7, f2, f6, p_114487_, 1.0F, 1.0F, 1.0F);
		}
		else
		{
			this.model.setupAnim(p_114485_, f5, f8, f7, f2, f6);
			this.model.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			this.coloredGlowingModelCopyLayerRender(this.model, this.model, LAYER_TEXTURE, p_114488_, p_114489_, p_114490_, p_114485_, f5, f8, f7, f2, f6, p_114487_, 1.0F, 1.0F, 1.0F);
		}
		p_114488_.popPose();
	}
	
	public void setupRotations(EntityGnasher p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) 
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
			float f1 = direction != null ? p_115317_.getClientPartBuilder().sleepDirectionToRotation(direction) : p_115320_;
			p_115318_.mulPose(Vector3f.YP.rotationDegrees(f1));
			p_115318_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
			p_115318_.mulPose(Vector3f.YP.rotationDegrees(270.0F));
		}
		else if(LivingEntityRenderer.isEntityUpsideDown(p_115317_)) 
		{
			p_115318_.translate(0.0D, (double)(p_115317_.getBbHeight() + 0.1F), 0.0D);
			p_115318_.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
		}
		
		if(!p_115317_.isInWater())
		{
			p_115318_.translate(0.5F, 0, 0);
			p_115318_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
		}
	}
	
	public void coloredGlowingModelCopyLayerRender(EntityModel<EntityGnasher> p_117360_, EntityModel<EntityGnasher> p_117361_, ResourceLocation p_117362_, PoseStack p_117363_, MultiBufferSource p_117364_, int p_117365_, EntityGnasher p_117366_, float p_117367_, float p_117368_, float p_117369_, float p_117370_, float p_117371_, float p_117372_, float p_117373_, float p_117374_, float p_117375_)
	{
		if(!p_117366_.isInvisible())
		{
			p_117360_.copyPropertiesTo(p_117361_);
			p_117361_.prepareMobModel(p_117366_, p_117367_, p_117368_, p_117372_);
			p_117361_.setupAnim(p_117366_, p_117367_, p_117368_, p_117369_, p_117370_, p_117371_);
			this.renderColoredGlowingModel(p_117361_, p_117362_, p_117363_, p_117364_, p_117365_, p_117366_, p_117373_, p_117374_, p_117375_);
		}
	}

	public void renderColoredGlowingModel(EntityModel<EntityGnasher> p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, EntityGnasher p_117382_, float p_117383_, float p_117384_, float p_117385_)
	{
		VertexConsumer vertexconsumer = p_117380_.getBuffer(BTARenderType.eyesFix(p_117378_));
		p_117377_.renderToBuffer(p_117379_, vertexconsumer, p_117381_, LivingEntityRenderer.getOverlayCoords(p_117382_, 0.0F), p_117383_, p_117384_, p_117385_, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGnasher p_114482_) 
	{
		return p_114482_.isLeader() ? TEXTURE_LEADER : TEXTURE;
	}
}
