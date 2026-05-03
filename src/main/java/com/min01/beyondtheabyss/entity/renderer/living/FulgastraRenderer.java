package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.FulgastraEntity;
import com.min01.beyondtheabyss.entity.model.FulgastraModel;
import com.min01.beyondtheabyss.entity.renderer.layer.FulgastraLayer;

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
	public ResourceLocation getTextureLocation(FulgastraEntity pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/fulgastra.png");
	}
}
