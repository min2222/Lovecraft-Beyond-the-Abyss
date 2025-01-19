package com.min01.beyondtheabyss.item.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.model.ModelGhidruthHarpoon;
import com.min01.beyondtheabyss.item.model.ModelHarpoon;
import com.min01.beyondtheabyss.item.weapon.HarpoonItem;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class HarpoonRenderer extends BlockEntityWithoutLevelRenderer
{
	private final ModelHarpoon model;
	private final ModelGhidruthHarpoon modelGhidruth;
	
	public HarpoonRenderer() 
	{
		super(BTAClientUtil.MC.getBlockEntityRenderDispatcher(), BTAClientUtil.MC.getEntityModels());
		this.model = new ModelHarpoon(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelHarpoon.LAYER_LOCATION));
		this.modelGhidruth = new ModelGhidruthHarpoon(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelGhidruthHarpoon.LAYER_LOCATION));
	}
	
	@Override
	public void renderByItem(ItemStack p_108830_, TransformType p_108831_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_)
	{
		if(p_108830_.getItem() instanceof HarpoonItem harpoon)
		{
	        p_108832_.pushPose();
	        p_108832_.scale(-1.0F, -1.0F, 1.0F);
	        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.entityCutoutNoCull(this.getTextureLocation(harpoon)), false, p_108830_.hasFoil());
	        this.getModel(harpoon).renderToBuffer(p_108832_, consumer, p_108834_, p_108835_, 1.0F, 1.0F, 1.0F, 1.0F);
	        p_108832_.popPose();
		}
	}
	
	public Model getModel(HarpoonItem item) 
	{
		return item == BTAItems.RUSTY_HARPOON.get() ? this.model : this.modelGhidruth;
	}
	
	public ResourceLocation getTextureLocation(HarpoonItem item)
	{
		return item == BTAItems.RUSTY_HARPOON.get() ? new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/rusty_harpoon.png") : new ResourceLocation(BeyondtheAbyss.MODID, "textures/entity/ghidruth_harpoon.png");
	}
}
