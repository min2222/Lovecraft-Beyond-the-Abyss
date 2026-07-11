package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SplittedFulgastraEntity;
import com.min01.beyondtheabyss.entity.model.SplittedFulgastraModel;
import com.min01.beyondtheabyss.entity.renderer.layer.SplittedFulgastraLayer;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SplittedFulgastraRenderer extends MobRenderer<SplittedFulgastraEntity, SplittedFulgastraModel>
{
	public SplittedFulgastraRenderer(Context pContext)
	{
		super(pContext, new SplittedFulgastraModel(pContext.bakeLayer(SplittedFulgastraModel.LAYER_LOCATION)), 0.5F);
		this.addLayer(new SplittedFulgastraLayer(this, this.model));
	}
	
	@Override
	public void render(SplittedFulgastraEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		EntityPartBuilder builder = pEntity.partBuilder;
		builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
	}
	
	@Override
	protected RenderType getRenderType(SplittedFulgastraEntity pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) 
	{
		return RenderType.entityTranslucent(this.getTextureLocation(pLivingEntity));
	}

	@Override
	public ResourceLocation getTextureLocation(SplittedFulgastraEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/splitted_fulgastra.png");
	}
}
