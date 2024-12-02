package com.min01.beyondtheabyss.entity.renderer.layer;

import com.min01.beyondtheabyss.entity.model.ModelFallenDiver;
import com.min01.beyondtheabyss.item.armor.AbstractDivingSetItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class DivingSetLayer<T extends LivingEntity, M extends EntityModel<T>, A extends EntityModel<T>> extends RenderLayer<T, M>
{
	private final ResourceLocation resourceLocation;
	   
	public DivingSetLayer(RenderLayerParent<T, M> renderer, ResourceLocation resourceLocation) 
	{
		super(renderer);
		this.resourceLocation = resourceLocation;
	}
	
	@Override
	public void render(PoseStack poseStack, MultiBufferSource p_117097_, int p_117098_, T p_117099_, float p_117100_, float p_117101_, float p_117102_, float p_117103_, float p_117104_, float p_117105_) 
	{
		this.renderArmorPiece(poseStack, p_117097_, p_117099_, EquipmentSlot.CHEST, p_117098_);
		this.renderArmorPiece(poseStack, p_117097_, p_117099_, EquipmentSlot.LEGS, p_117098_);
		this.renderArmorPiece(poseStack, p_117097_, p_117099_, EquipmentSlot.FEET, p_117098_);
		this.renderArmorPiece(poseStack, p_117097_, p_117099_, EquipmentSlot.HEAD, p_117098_);
	}
	
	private void renderArmorPiece(PoseStack poseStack, MultiBufferSource p_117120_, T p_117121_, EquipmentSlot p_117122_, int p_117123_) 
	{
		ItemStack itemstack = p_117121_.getItemBySlot(p_117122_);
		if(itemstack.getItem() instanceof AbstractDivingSetItem) 
		{
			ArmorItem armoritem = (ArmorItem) itemstack.getItem();
			if(armoritem.getEquipmentSlot() == p_117122_) 
			{
				HumanoidModel<?> model = this.getArmorModelHook(p_117121_, itemstack, p_117122_);
				if(this.getParentModel() instanceof ModelFallenDiver diverModel)
				{
					model.rightArm.copyFrom(diverModel.RightArm);
					model.leftArm.copyFrom(diverModel.LeftArm);
					model.leftLeg.copyFrom(diverModel.LeftLeg);
					model.rightLeg.copyFrom(diverModel.RightLeg);
					model.head.copyFrom(diverModel.Head);
				}
				boolean flag1 = itemstack.hasFoil();
				this.renderModel(poseStack, p_117120_, p_117123_, flag1, model, 1.0F, 1.0F, 1.0F, this.resourceLocation);
			}
		}
	}
	
	private void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int p_117109_, boolean p_117111_, Model p_117112_, float p_117114_, float p_117115_, float p_117116_, ResourceLocation armorResource)
	{
		VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(armorResource), false, p_117111_);
		p_117112_.renderToBuffer(poseStack, consumer, p_117109_, OverlayTexture.NO_OVERLAY, p_117114_, p_117115_, p_117116_, 1.0F);
	}
	
	protected HumanoidModel<?> getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot) 
	{
		return IClientItemExtensions.of(itemStack).getHumanoidArmorModel(entity, itemStack, slot, null);
	}
}
