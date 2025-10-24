package com.min01.beyondtheabyss.item.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.deepabyss.SkeletalGunbladeItem;
import com.min01.beyondtheabyss.item.deepabyss.ToothShotgunItem;
import com.min01.beyondtheabyss.item.model.ModelSkeletalGunblade;
import com.min01.beyondtheabyss.item.model.ModelToothShotgun;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BTAItemRenderer extends BlockEntityWithoutLevelRenderer
{
	public static final ResourceLocation SHOTGUN_TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/item/tooth_shotgun.png");
	public final ModelSkeletalGunblade modelGunblade;
	public final ModelToothShotgun modelShotgun;
	public BTAItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) 
	{
		super(dispatcher, modelSet);
		this.modelGunblade = new ModelSkeletalGunblade(modelSet.bakeLayer(ModelSkeletalGunblade.LAYER_LOCATION));
		this.modelShotgun = new ModelToothShotgun(modelSet.bakeLayer(ModelToothShotgun.LAYER_LOCATION));
	}
	
	@Override
	public void renderByItem(ItemStack p_108830_, ItemDisplayContext p_108831_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_)
	{
 		if(p_108830_.getItem() instanceof SkeletalGunbladeItem)
		{
 			if(p_108831_.firstPerson() || p_108831_ == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || p_108831_ == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) 
 			{
 				this.modelGunblade.EnergyRay.visible = SkeletalGunbladeItem.isLaserVisible(p_108830_);
 			}
 			else
 			{
 				this.modelGunblade.EnergyRay.visible = false;
 			}
	        p_108832_.pushPose();
	        p_108832_.translate(0.5F, 0.6F, 0.3F);
	        p_108832_.scale(-1.0F, -1.0F, 1.0F);
	        p_108832_.translate(0.0F, -1.0F, 0.0F);
	        ResourceLocation texture = new ResourceLocation(String.format("%s:textures/item/skeletal_gunblade%d.png", BeyondtheAbyss.MODID, SkeletalGunbladeItem.getCharge(p_108830_)));
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.entityCutoutNoCull(texture), false, p_108830_.hasFoil());
			this.modelGunblade.setupAnim(p_108830_, 0, 0, BTAUtil.getItemTickCount(p_108830_) + BTAClientUtil.MC.getFrameTime(), 0, 0);
	        this.modelGunblade.renderToBuffer(p_108832_, consumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
	        p_108832_.popPose();

	        p_108832_.pushPose();
	        p_108832_.translate(0.5F, 0.6F, 0.3F);
	        p_108832_.scale(-1.0F, -1.0F, 1.0F);
	        p_108832_.translate(0.0F, -1.0F, 0.0F);
	        ResourceLocation layerTexture = new ResourceLocation(String.format("%s:textures/item/skeletal_gunblade_layer%d.png", BeyondtheAbyss.MODID, SkeletalGunbladeItem.getCharge(p_108830_)));
	        VertexConsumer eyeConsumer = p_108833_.getBuffer(BTARenderType.eyesFix(layerTexture));
			this.modelGunblade.setupAnim(p_108830_, 0, 0, BTAUtil.getItemTickCount(p_108830_) + BTAClientUtil.MC.getFrameTime(), 0, 0);
	        this.modelGunblade.renderToBuffer(p_108832_, eyeConsumer, p_108834_, p_108835_, 0.7F, 0.7F, 0.7F, 1.0F);
	        p_108832_.popPose();
		}
 		if(p_108830_.getItem() instanceof ToothShotgunItem)
		{
	        p_108832_.pushPose();
	        if(p_108831_ == ItemDisplayContext.GUI)
	        {
		        p_108832_.translate(1.0F, 0.5F, 0.0F);
	        }
	        else
	        {
		        p_108832_.translate(0.5F, 1.0F, -0.1F);
	        }
	        p_108832_.scale(-1.0F, -1.0F, 1.0F);
	        p_108832_.translate(0.0F, -1.0F, 0.0F);
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.entityCutoutNoCull(SHOTGUN_TEXTURE), false, p_108830_.hasFoil());
			this.modelShotgun.setupAnim(p_108830_, 0, 0, BTAUtil.getItemTickCount(p_108830_) + BTAClientUtil.MC.getFrameTime(), 0, 0);
	        this.modelShotgun.renderToBuffer(p_108832_, consumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
	        p_108832_.popPose();
		}
	}
}
