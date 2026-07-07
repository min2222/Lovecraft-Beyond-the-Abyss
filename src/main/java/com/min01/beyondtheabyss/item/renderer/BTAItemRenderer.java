package com.min01.beyondtheabyss.item.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.deepabyss.ClamOfGuidanceItem;
import com.min01.beyondtheabyss.item.deepabyss.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.deepabyss.ToothShotgunItem;
import com.min01.beyondtheabyss.item.model.ClamOfGuidanceModel;
import com.min01.beyondtheabyss.item.model.SkeletalGunbladeModel;
import com.min01.beyondtheabyss.item.model.ToothShotgunModel;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BTAItemRenderer extends BlockEntityWithoutLevelRenderer
{
	public static final ResourceLocation SHOTGUN_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/item/tooth_shotgun.png");
	public static final ResourceLocation CLAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/item/clam_of_guidance_in_hand.png");
	
	public final SkeletalGunbladeModel modelGunblade;
	public final ToothShotgunModel modelShotgun;
	public final ClamOfGuidanceModel modelClam;
	
	public BTAItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) 
	{
		super(dispatcher, modelSet);
		this.modelGunblade = new SkeletalGunbladeModel(modelSet.bakeLayer(SkeletalGunbladeModel.LAYER_LOCATION));
		this.modelShotgun = new ToothShotgunModel(modelSet.bakeLayer(ToothShotgunModel.LAYER_LOCATION));
		this.modelClam = new ClamOfGuidanceModel(modelSet.bakeLayer(ClamOfGuidanceModel.LAYER_LOCATION));
	}
	
	@Override
	public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay)
	{
 		if(pStack.getItem() instanceof SkeletalGunbladeItem)
		{
 			if(pDisplayContext.firstPerson() || pDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || pDisplayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) 
 			{
 				this.modelGunblade.EnergyRay.visible = SkeletalGunbladeItem.isLaserVisible(pStack);
 			}
 			else
 			{
 				this.modelGunblade.EnergyRay.visible = false;
 			}
	        pPoseStack.pushPose();
	        pPoseStack.translate(0.5F, 0.6F, 0.3F);
	        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
	        pPoseStack.translate(0.0F, -1.0F, 0.0F);
	        ResourceLocation texture = ResourceLocation.parse(String.format("%s:textures/item/skeletal_gunblade%d.png", BeyondtheAbyss.MODID, SkeletalGunbladeItem.getCharge(pStack)));
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(pBuffer, RenderType.entityCutoutNoCull(texture), false, pStack.hasFoil());
			this.modelGunblade.setupAnim(pStack, 0, 0, 0, 0, 0);
	        this.modelGunblade.renderToBuffer(pPoseStack, consumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
	        pPoseStack.popPose();

	        pPoseStack.pushPose();
	        pPoseStack.translate(0.5F, 0.6F, 0.3F);
	        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
	        pPoseStack.translate(0.0F, -1.0F, 0.0F);
	        ResourceLocation layerTexture = ResourceLocation.parse(String.format("%s:textures/item/skeletal_gunblade_layer%d.png", BeyondtheAbyss.MODID, SkeletalGunbladeItem.getCharge(pStack)));
	        VertexConsumer eyeConsumer = pBuffer.getBuffer(BTARenderType.eyesFix(layerTexture));
			this.modelGunblade.setupAnim(pStack, 0, 0, 0, 0, 0);
	        this.modelGunblade.renderToBuffer(pPoseStack, eyeConsumer, pPackedLight, pPackedOverlay, 0.7F, 0.7F, 0.7F, 1.0F);
	        pPoseStack.popPose();
		}
 		if(pStack.getItem() instanceof ToothShotgunItem)
		{
	        pPoseStack.pushPose();
	        if(pDisplayContext == ItemDisplayContext.GUI)
	        {
		        pPoseStack.translate(1.0F, 0.5F, 0.0F);
	        }
	        else
	        {
		        pPoseStack.translate(0.5F, 1.0F, -0.1F);
	        }
	        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
	        pPoseStack.translate(0.0F, -1.0F, 0.0F);
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(pBuffer, RenderType.entityCutoutNoCull(SHOTGUN_TEXTURE), false, pStack.hasFoil());
			this.modelShotgun.setupAnim(pStack, 0, 0, 0, 0, 0);
	        this.modelShotgun.renderToBuffer(pPoseStack, consumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
	        pPoseStack.popPose();
		}
 		if(pStack.getItem() instanceof ClamOfGuidanceItem)
		{
	        pPoseStack.pushPose();
	        pPoseStack.translate(0.5F, 0.75F, 0.6F);
	        pPoseStack.scale(0.5F, 0.5F, 0.5F);
	        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
	        pPoseStack.translate(0.0F, -1.0F, 0.0F);
	        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(pBuffer, RenderType.entityCutoutNoCull(CLAM_TEXTURE), false, pStack.hasFoil());
			this.modelClam.setupAnim(pStack, 0, 0, 0, 0, 0);
	        this.modelClam.renderToBuffer(pPoseStack, consumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
	        if(pStack.getOrCreateTag().contains("PortalPos") && ClamOfGuidanceItem.isOpen(pStack))
	        {
				BlockPos pos = NbtUtils.readBlockPos(pStack.getTag().getCompound("PortalPos"));
				Component component = Component.literal(pos.toShortString()).withStyle(ChatFormatting.AQUA);
				pPoseStack.pushPose();
				pPoseStack.scale(0.01F, 0.01F, 0.01F);
				pPoseStack.translate(0.0F, 0.0F, 5.0F);
				BTAClientUtil.MC.font.drawInBatch(component, -35, 100, 0, false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
				pPoseStack.popPose();
	        }
	        pPoseStack.popPose();
		}
	}
}
