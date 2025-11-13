package com.min01.beyondtheabyss.entity.renderer;

import com.min01.beyondtheabyss.entity.EntityFallingStone;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.model.data.ModelData;

public class FallingStoneRenderer extends EntityRenderer<EntityFallingStone>
{
	private final BlockRenderDispatcher dispatcher;
	
	public FallingStoneRenderer(Context p_174008_)
	{
		super(p_174008_);
		this.dispatcher = p_174008_.getBlockRenderDispatcher();
	}
	
	@Override
	public void render(EntityFallingStone p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_)
	{
		BlockState state = p_114485_.getBlockState();
		if(state.getRenderShape() == RenderShape.MODEL) 
		{
			Level level = p_114485_.level;
			if(state != level.getBlockState(p_114485_.blockPosition()) && state.getRenderShape() != RenderShape.INVISIBLE) 
			{
				p_114488_.pushPose();
				BlockPos pos = BlockPos.containing(p_114485_.getX(), p_114485_.getBoundingBox().maxY, p_114485_.getZ());
				p_114488_.mulPose(Axis.ZP.rotationDegrees(p_114485_.getRotation()));
				p_114488_.mulPose(Axis.YP.rotationDegrees(p_114485_.getRotation()));
				p_114488_.mulPose(Axis.XP.rotationDegrees(p_114485_.getRotation()));
				p_114488_.translate(-0.5F, 0.0F, -0.5F);
				BakedModel model = this.dispatcher.getBlockModel(state);
				for(RenderType renderType : model.getRenderTypes(state, RandomSource.create(state.getSeed(p_114485_.blockPosition())), ModelData.EMPTY))
				{
					renderType = RenderTypeHelper.getMovingBlockRenderType(renderType);
					this.dispatcher.getModelRenderer().tesselateBlock(level, model, state, pos, p_114488_, p_114489_.getBuffer(renderType), false, RandomSource.create(), state.getSeed(p_114485_.blockPosition()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
				}
				p_114488_.popPose();
			}
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(EntityFallingStone p_114482_)
	{
		return null;
	}
}
