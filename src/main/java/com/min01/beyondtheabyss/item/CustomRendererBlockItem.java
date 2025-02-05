package com.min01.beyondtheabyss.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.min01.beyondtheabyss.block.deepabyss.AbstractMultiPartSkeletonBlock;
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
	
	public CustomRendererBlockItem(Block p_40565_, Properties p_40566_, Supplier<BlockEntity> blockEntity) 
	{
		super(p_40565_, p_40566_);
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
	protected boolean placeBlock(BlockPlaceContext p_40561_, BlockState p_40562_)
	{
		if(p_40562_.getBlock() instanceof AbstractMultiPartSkeletonBlock)
		{
			return p_40561_.getLevel().setBlock(p_40561_.getClickedPos(), p_40562_, 26);
		}
		return super.placeBlock(p_40561_, p_40562_);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult place(BlockPlaceContext p_40577_) 
	{
		Level level = p_40577_.getLevel();
		BlockPos pos = p_40577_.getClickedPos();
		if(this.getBlock() instanceof AbstractMultiPartSkeletonBlock skeleton)
		{
			Direction direction = skeleton.getPartDirection(skeleton.getStateForPlacement(p_40577_));
			BlockPos blockpos = pos.relative(direction);
			boolean flag = !level.isEmptyBlock(blockpos) && !level.getBlockState(blockpos).liquid();
			if(flag)
			{
				return InteractionResult.FAIL;
			}
		}
		return super.place(p_40577_);
	}
}	
