package com.min01.beyondtheabyss.blockentity.renderer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.block.model.ModelCrabTrap;
import com.min01.beyondtheabyss.blockentity.deepabyss.CrabTrapBlockEntity;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CrabTrapRenderer implements BlockEntityRenderer<CrabTrapBlockEntity>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(BeyondtheAbyss.MODID, "textures/block/crab_trap.png");
    
	private final ModelCrabTrap model;
	
	public CrabTrapRenderer(BlockEntityRendererProvider.Context p_172550_)
	{
		this.model = new ModelCrabTrap(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelCrabTrap.LAYER_LOCATION));
	}

	@Override
	public void render(CrabTrapBlockEntity p_112307_, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) 
	{
		BlockState blockState = p_112307_.getBlockState();
		p_112309_.pushPose();
		p_112309_.translate(0.5F, 0.5F, 0.5F);
		this.rotate(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), p_112309_);
		p_112309_.scale(-1.0F, -1.0F, 1.0F);
		p_112309_.translate(0.0F, -1.01F, 0.0F);
		VertexConsumer consumer = p_112310_.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
		this.model.setupAnim(p_112307_, 0, 0, p_112307_.tickCount + p_112308_, 0, 0);
		this.model.renderToBuffer(p_112309_, consumer, p_112311_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		p_112309_.popPose();
	}
	
	public void rotate(Direction direction, PoseStack poseStack)
	{
		switch(direction)
		{
		case DOWN:
			break;
		case EAST:
			poseStack.mulPose(Axis.YP.rotationDegrees(90));
			break;
		case NORTH:
			break;
		case SOUTH:
			poseStack.mulPose(Axis.YP.rotationDegrees(180));
			break;
		case UP:
			break;
		case WEST:
			poseStack.mulPose(Axis.YP.rotationDegrees(270));
			break;
		default:
			break;
		}
	}
}
