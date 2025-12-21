package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFulgastra;
import com.min01.beyondtheabyss.entity.model.ModelFulgastra;
import com.min01.beyondtheabyss.entity.renderer.layer.FulgastraLayer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FulgastraRenderer extends MobRenderer<EntityFulgastra, ModelFulgastra>
{
	public FulgastraRenderer(Context pContext)
	{
		super(pContext, new ModelFulgastra(pContext.bakeLayer(ModelFulgastra.LAYER_LOCATION)), 0.5F);
		this.addLayer(new FulgastraLayer(this, this.model));
	}
	
	@Override
	protected RenderType getRenderType(EntityFulgastra pLivingEntity, boolean pBodyVisible, boolean pTranslucent, boolean pGlowing) 
	{
		return RenderType.entityTranslucent(this.getTextureLocation(pLivingEntity));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityFulgastra pEntity) 
	{
		return ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/fulgastra.png");
	}
}
