package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBlaster;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentSlasher;
import com.min01.beyondtheabyss.entity.renderer.IModel;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SiamserpentHeadRenderer extends EntityRenderer<EntitySiamserpentHead> implements IModel<EntitySiamserpentHead>
{
	private final ModelSiamserpentSlasher slasherModel;
	private final ModelSiamserpentBlaster blasterModel;
	
	private static final ResourceLocation TEXTURE_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher.png");
	private static final ResourceLocation TEXTURE_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster.png");
	private static final ResourceLocation DISABLED_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_disabled.png");
	private static final ResourceLocation DISABLED_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_disabled.png");
	private static final ResourceLocation DORMANT_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_dormant.png");
	private static final ResourceLocation DORMANT_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_dormant.png");
	private static final ResourceLocation LAYER_SLASHER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_layer.png");
	private static final ResourceLocation LAYER_BLASTER = new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/siamserpent_blaster_layer.png");
	
	public SiamserpentHeadRenderer(Context p_174008_) 
	{
		super(p_174008_);
		this.slasherModel = new ModelSiamserpentSlasher(p_174008_.bakeLayer(ModelSiamserpentSlasher.LAYER_LOCATION));
		this.blasterModel = new ModelSiamserpentBlaster(p_174008_.bakeLayer(ModelSiamserpentBlaster.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntitySiamserpentHead p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) 
	{
		p_114488_.pushPose();
		float f = Mth.rotLerp(p_114487_, p_114485_.yBodyRotO, p_114485_.yBodyRot);
		float f1 = Mth.rotLerp(p_114487_, p_114485_.yHeadRotO, p_114485_.yHeadRot);
		float f2 = f1 - f;
        float f6 = Mth.lerp(p_114487_, p_114485_.xRotO, p_114485_.getXRot());
        float f8 = Mth.lerp(p_114487_, p_114485_.animationSpeedOld, p_114485_.animationSpeed);
        float f7 = p_114485_.tickCount + p_114487_;
        float f5 = p_114485_.animationPosition - p_114485_.animationSpeed * (1.0F - p_114487_);
		BTAClientUtil.setupRotations(p_114485_, p_114488_, f7, f, p_114487_);
		p_114488_.scale(-1.0F, -1.0F, 1.0F);
		p_114488_.translate(0, -1.5F, 0);
		VertexConsumer consumer = p_114489_.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(p_114485_)));
		switch(p_114485_.getHeadType())
		{
		case SLASHER:
			this.slasherModel.setupAnim(p_114485_, f5, f8, f7, f2, f6);
			this.slasherModel.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			if(!p_114485_.isDisabled() && !p_114485_.isDormant())
			{
				BTAClientUtil.coloredGlowingModelCopyLayerRender(this.slasherModel, this.slasherModel, LAYER_SLASHER, p_114488_, p_114489_, p_114490_, p_114485_, f5, f8, f7, f2, f6, p_114487_, 1.0F, 1.0F, 1.0F);
			}
			break;
		case BLASTER:
			this.blasterModel.setupAnim(p_114485_, f5, f8, f7, f2, f6);
			this.blasterModel.renderToBuffer(p_114488_, consumer, p_114490_, LivingEntityRenderer.getOverlayCoords(p_114485_, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
			if(!p_114485_.isDisabled() && !p_114485_.isDormant())
			{
				BTAClientUtil.coloredGlowingModelCopyLayerRender(this.blasterModel, this.blasterModel, LAYER_BLASTER, p_114488_, p_114489_, p_114490_, p_114485_, f5, f8, f7, f2, f6, p_114487_, 0.3F, 0.3F, 0.3F);
			}
			break;
		}
		p_114488_.popPose();
	}
	
	@Override
	public HierarchicalModel<EntitySiamserpentHead> getModel(EntitySiamserpentHead entity)
	{
		return entity.getHeadType() == HeadType.SLASHER ? this.slasherModel : this.blasterModel;
	}

	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentHead p_114482_) 
	{
		switch(p_114482_.getHeadType())
		{
		case SLASHER:
			return p_114482_.isDisabled() ? DISABLED_SLASHER : p_114482_.isDormant() ? DORMANT_SLASHER : TEXTURE_SLASHER;
		case BLASTER:
			return p_114482_.isDisabled() ? DISABLED_BLASTER : p_114482_.isDormant() ? DORMANT_BLASTER : TEXTURE_BLASTER;
		}
		return null;
	}
}
