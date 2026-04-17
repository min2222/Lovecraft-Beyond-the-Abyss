package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySiamserpentHead.HeadType;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentSlasher;
import com.min01.beyondtheabyss.entity.renderer.layer.SiamserpentSlasherLayer;
import com.min01.solomonlib.multipart.IMultiModel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentSlasherRenderer extends MobRenderer<EntitySiamserpentHead, ModelSiamserpentSlasher> implements IMultiModel<EntitySiamserpentHead>
{
	private static final ResourceLocation TEXTURE_SLASHER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher.png");
	private static final ResourceLocation DISABLED_SLASHER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_disabled.png");
	private static final ResourceLocation DORMANT_SLASHER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_dormant.png");

	private final SiamserpentBlasterRenderer blasterRenderer;
	
	public SiamserpentSlasherRenderer(Context pContext)
	{
		super(pContext, new ModelSiamserpentSlasher(pContext.bakeLayer(ModelSiamserpentSlasher.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SiamserpentSlasherLayer(this, this.model));
		this.blasterRenderer = new SiamserpentBlasterRenderer(pContext);
	}
	
	@Override
	public void render(EntitySiamserpentHead pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		if(pEntity.getHeadType() == HeadType.BLASTER)
		{
			this.blasterRenderer.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		}
		else
		{
			super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		}
	}
	
	@Override
	public HierarchicalModel<EntitySiamserpentHead> getModel(EntitySiamserpentHead entity) 
	{
		if(entity.getHeadType() == HeadType.BLASTER)
		{
			return this.blasterRenderer.getModel();
		}
		return this.getModel();
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntitySiamserpentHead pEntity) 
	{
		return pEntity.isDisabled() ? DISABLED_SLASHER : pEntity.isDormant() ? DORMANT_SLASHER : TEXTURE_SLASHER;
	}
}
