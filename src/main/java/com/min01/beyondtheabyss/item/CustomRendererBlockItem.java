package com.min01.beyondtheabyss.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.block.deepabyss.AbstractMultiPartSkeletonBlock;
import com.min01.beyondtheabyss.block.deepabyss.BiocrafterBlock;
import com.min01.beyondtheabyss.item.renderer.BTABlockEntityItemRenderer;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CustomRendererBlockItem extends BlockItem
{
	private final Supplier<BlockEntity> blockEntity;
	
	public CustomRendererBlockItem(Block pBlock, Properties pProperties, Supplier<BlockEntity> blockEntity) 
	{
		super(pBlock, pProperties);
		this.blockEntity = blockEntity;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) 
	{
		consumer.accept(new IClientItemExtensions()
		{
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() 
			{
				return new BTABlockEntityItemRenderer(CustomRendererBlockItem.this.blockEntity.get(), BTAClientUtil.MC.getBlockEntityRenderDispatcher(), BTAClientUtil.MC.getEntityModels());
			}
		});
	}
	
	@Override
	protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState)
	{
		if(pState.getBlock() instanceof AbstractMultiPartSkeletonBlock || pState.getBlock() instanceof BiocrafterBlock)
		{
			return pContext.getLevel().setBlock(pContext.getClickedPos(), pState, 26);
		}
		return super.placeBlock(pContext, pState);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult place(BlockPlaceContext pContext) 
	{
		Level level = pContext.getLevel();
		BlockPos pos = pContext.getClickedPos();
		if(this.getBlock() instanceof AbstractMultiPartSkeletonBlock skeleton)
		{
			Direction direction = skeleton.getPartDirection(skeleton.getStateForPlacement(pContext));
			BlockPos blockpos = pos.relative(direction);
			boolean flag = !level.isEmptyBlock(blockpos) && !level.getBlockState(blockpos).liquid();
			if(flag)
			{
				return InteractionResult.FAIL;
			}
		}
		if(this.getBlock() instanceof BiocrafterBlock)
		{
			BlockPos blockpos = pos.relative(Direction.UP);
			boolean flag = !level.isEmptyBlock(blockpos) && !level.getBlockState(blockpos).liquid();
			if(flag)
			{
				return InteractionResult.FAIL;
			}
		}
		return super.place(pContext);
	}
}	
