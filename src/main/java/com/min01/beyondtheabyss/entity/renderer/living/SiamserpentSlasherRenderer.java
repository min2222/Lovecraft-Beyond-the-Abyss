package com.min01.beyondtheabyss.entity.renderer.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity;
import com.min01.beyondtheabyss.entity.deepabyss.SiamserpentHeadEntity.HeadType;
import com.min01.beyondtheabyss.entity.model.SiamserpentSlasherModel;
import com.min01.beyondtheabyss.entity.renderer.layer.SiamserpentSlasherLayer;
import com.min01.solomonlib.multipart.EntityPartBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SiamserpentSlasherRenderer extends MobRenderer<SiamserpentHeadEntity, SiamserpentSlasherModel>
{
	private static final ResourceLocation TEXTURE_SLASHER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher.png");
	private static final ResourceLocation DISABLED_SLASHER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_disabled.png");
	private static final ResourceLocation DORMANT_SLASHER = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/entity/siamserpent_slasher_dormant.png");

	private final SiamserpentBlasterRenderer blasterRenderer;
	
	public SiamserpentSlasherRenderer(Context pContext)
	{
		super(pContext, new SiamserpentSlasherModel(pContext.bakeLayer(SiamserpentSlasherModel.LAYER_LOCATION)), 0.0F);
		this.addLayer(new SiamserpentSlasherLayer(this, this.model));
		this.blasterRenderer = new SiamserpentBlasterRenderer(pContext);
	}
	
	@Override
	public void render(SiamserpentHeadEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		if(pEntity.getHeadType() == HeadType.BLASTER)
		{
			this.blasterRenderer.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
		}
		else
		{
			super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
			EntityPartBuilder builder = pEntity.partBuilder;
			builder.send(this.model, pEntity, pPartialTicks, stack -> this.setupRotations(pEntity, stack, this.getBob(pEntity, pPartialTicks), builder.defaultBodyRotation(pEntity, pPartialTicks), pPartialTicks), stack -> this.scale(pEntity, stack, pPartialTicks));
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(SiamserpentHeadEntity pEntity) 
	{
		return pEntity.isDisabled() ? DISABLED_SLASHER : pEntity.isDormant() ? DORMANT_SLASHER : TEXTURE_SLASHER;
	}
}
