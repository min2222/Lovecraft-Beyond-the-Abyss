package com.min01.beyondtheabyss.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.util.MirroredCityUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class MixinBlockStateBase 
{
	@Inject(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    private void getShape(BlockGetter p_60652_, BlockPos p_60653_, CollisionContext p_60654_, CallbackInfoReturnable<VoxelShape> cir)
    {
    	VoxelShape shape = cir.getReturnValue();
    	if(p_60652_ instanceof Level level)
    	{
            if(!shape.isEmpty() && MirroredCityUtil.isBlockUpsideDown(p_60653_, level))
            {
                VoxelShape flipped = Shapes.empty();
                for(AABB box : shape.toAabbs()) 
                {
                    double minY = 1.0 - box.maxY;
                    double maxY = 1.0 - box.minY;
                    AABB flippedBox = new AABB(box.minX, minY, box.minZ, box.maxX, maxY, box.maxZ);
                    flipped = Shapes.or(flipped, Shapes.create(flippedBox));
                }
                cir.setReturnValue(flipped);
            }
    	}
    }
}
