package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.FulgastraEntity;
import com.min01.beyondtheabyss.entity.model.FulgastraModel;
import com.min01.beyondtheabyss.entity.renderer.layer.FulgastraLayer;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FulgastraRenderer extends MobRenderer<FulgastraEntity, FulgastraModel>
{
	public FulgastraRenderer(Context pContext)
	{
		super(pContext, new FulgastraModel(pContext.bakeLayer(FulgastraModel.LAYER_LOCATION)), 0.5F);
		this.addLayer(new FulgastraLayer(this, this.model));
	}
	
	@Override
	protected RenderType getRenderType(FulgastraEntity pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) 
	{
		return RenderType.entityTranslucent(this.getTextureLocation(pLivingEntity));
	}
	
	@Override
	public void render(FulgastraEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}

	@Override
	public ResourceLocation getTextureLocation(FulgastraEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/fulgastra.png");
	}
}
