package com.min01.beyondtheabyss.mixin.gravity;

import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.MirroredCityUtil;
import com.min01.gravityapi.util.RotationUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(BlockRenderDispatcher.class)
public class MixinBlockRenderDispatcher 
{
	@Inject(method = "renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;Lnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V", at = @At("HEAD"), cancellable = true, remap = false)
	private void renderBatched(BlockState p_234356_, BlockPos p_234357_, BlockAndTintGetter p_234358_, PoseStack p_234359_, VertexConsumer p_234360_, boolean p_234361_, RandomSource p_234362_, net.minecraftforge.client.model.data.ModelData modelData, net.minecraft.client.renderer.RenderType renderType, CallbackInfo ci) 
	{
		if(MirroredCityUtil.isBlockUpsideDown(p_234357_, BTAClientUtil.MC.level))
		{
			//FIXME proper rotation;
			p_234359_.translate(0.5F, 0.5F, 0.5F);
			Quaternionf quat = new Quaternionf(RotationUtil.getWorldRotationQuaternion(Direction.UP));
			p_234359_.mulPose(quat);
			p_234359_.translate(-0.5F, -0.5F, -0.5F);
		}
	}
}
