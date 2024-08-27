package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class MetalWindowBlock extends Block implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<WindowType> WINDOW_TYPE = EnumProperty.create("window_type", WindowType.class);
	
	public MetalWindowBlock()
	{
		super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
		this.registerDefaultState(this.defaultBlockState().setValue(WINDOW_TYPE, WindowType.SINGLE));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_)
	{
    	LevelAccessor level = p_49820_.getLevel();
    	BlockPos blockPos = p_49820_.getClickedPos();
		return this.updateState(this.defaultBlockState(), level, blockPos).setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(blockPos).getType() == Fluids.WATER));
	}
	
	@Override
	public void neighborChanged(BlockState p_55666_, Level p_55667_, BlockPos p_55668_, Block p_55669_, BlockPos p_55670_, boolean p_55671_)
	{
		if(!p_55667_.isClientSide)
		{
			p_55667_.setBlock(p_55668_, this.updateState(p_55666_, p_55667_, p_55668_), 2);
		}
	}
	
	public BlockState updateState(BlockState state, LevelAccessor level, BlockPos pos)
	{
		BlockState above = level.getBlockState(pos.above());
		BlockState below = level.getBlockState(pos.below());
		BlockState left = level.getBlockState(pos.relative(Direction.EAST, 1));
		BlockState right = level.getBlockState(pos.relative(Direction.EAST, -1));
		BlockState forward = level.getBlockState(pos.relative(Direction.NORTH, 1));
		BlockState backward = level.getBlockState(pos.relative(Direction.NORTH, -1));
		if(!(above.is(BTABlocks.METAL_WINDOW.get())) && below.is(BTABlocks.METAL_WINDOW.get()))
		{
			if(!(right.is(BTABlocks.METAL_WINDOW.get())) && left.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_RIGHT);
			}
			else if(right.is(BTABlocks.METAL_WINDOW.get()) && left.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_TOP);
			}
			else if(right.is(BTABlocks.METAL_WINDOW.get()) && !(left.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_LEFT);
			}
			else if(!(forward.is(BTABlocks.METAL_WINDOW.get())) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_LEFT_Y);
			}
			else if(forward.is(BTABlocks.METAL_WINDOW.get()) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_TOP_Y);
			}
			else if(forward.is(BTABlocks.METAL_WINDOW.get()) && !(backward.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_RIGHT_Y);
			}
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_TOP);
		}
		if(above.is(BTABlocks.METAL_WINDOW.get()) && below.is(BTABlocks.METAL_WINDOW.get()))
		{
			if(!(right.is(BTABlocks.METAL_WINDOW.get())) && left.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_RIGHT);
			}
			else if(right.is(BTABlocks.METAL_WINDOW.get()) && left.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.TRANSLUCENT);
			}
			else if(right.is(BTABlocks.METAL_WINDOW.get()) && !(left.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_LEFT);
			}
			if(!(forward.is(BTABlocks.METAL_WINDOW.get())) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_LEFT_Y);
			}
			if(forward.is(BTABlocks.METAL_WINDOW.get()) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.TRANSLUCENT);
			}
			if(forward.is(BTABlocks.METAL_WINDOW.get()) && !(backward.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_RIGHT_Y);
			}
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_MIDDLE);
		}
		if(above.is(BTABlocks.METAL_WINDOW.get()) && !(below.is(BTABlocks.METAL_WINDOW.get())))
		{
			if(!(right.is(BTABlocks.METAL_WINDOW.get())) && left.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_RIGHT_BOTTOM);
			}
			else if(right.is(BTABlocks.METAL_WINDOW.get()) && left.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_BOTTOM);
			}
			else if(right.is(BTABlocks.METAL_WINDOW.get()) && !(left.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_LEFT_BOTTOM);
			}
			else if(!(forward.is(BTABlocks.METAL_WINDOW.get())) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_LEFT_BOTTOM_Y);
			}
			else if(forward.is(BTABlocks.METAL_WINDOW.get()) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_BOTTOM_Y);
			}
			else if(forward.is(BTABlocks.METAL_WINDOW.get()) && !(backward.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_RIGHT_BOTTOM_Y);
			}
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_BOTTOM);
		}
		
		if(!(right.is(BTABlocks.METAL_WINDOW.get())) && left.is(BTABlocks.METAL_WINDOW.get()))
		{
			if(!(forward.is(BTABlocks.METAL_WINDOW.get())) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_RIGHT_BOTTOM_X);
			}
			if(forward.is(BTABlocks.METAL_WINDOW.get()) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_RIGHT_X);
			}
			if(forward.is(BTABlocks.METAL_WINDOW.get()) && !(backward.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_RIGHT_X);
			}
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_TOP_XY);
		}
		if(right.is(BTABlocks.METAL_WINDOW.get()) && left.is(BTABlocks.METAL_WINDOW.get()))
		{
			if(!(forward.is(BTABlocks.METAL_WINDOW.get())) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_BOTTOM_X);
			}
			if(forward.is(BTABlocks.METAL_WINDOW.get()) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.TRANSLUCENT);
			}
			if(forward.is(BTABlocks.METAL_WINDOW.get()) && !(backward.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_TOP_X);
			}
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_MIDDLE_XY);
		}
		if(right.is(BTABlocks.METAL_WINDOW.get()) && !(left.is(BTABlocks.METAL_WINDOW.get())))
		{
			if(!(forward.is(BTABlocks.METAL_WINDOW.get())) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_LEFT_X);
			}
			if(forward.is(BTABlocks.METAL_WINDOW.get()) && backward.is(BTABlocks.METAL_WINDOW.get()))
			{
				return state.setValue(WINDOW_TYPE, WindowType.MIDDLE_LEFT_X);
			}
			if(forward.is(BTABlocks.METAL_WINDOW.get()) && !(backward.is(BTABlocks.METAL_WINDOW.get())))
			{
				return state.setValue(WINDOW_TYPE, WindowType.CORNER_LEFT_BOTTOM_X);
			}
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_BOTTOM_XY);
		}
		
		if(!(forward.is(BTABlocks.METAL_WINDOW.get())) && backward.is(BTABlocks.METAL_WINDOW.get()))
		{
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_TOP_X);
		}
		if(forward.is(BTABlocks.METAL_WINDOW.get()) && backward.is(BTABlocks.METAL_WINDOW.get()))
		{
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_MIDDLE_X);
		}
		if(forward.is(BTABlocks.METAL_WINDOW.get()) && !(backward.is(BTABlocks.METAL_WINDOW.get())))
		{
			return state.setValue(WINDOW_TYPE, WindowType.SINGLE_BOTTOM_X);
		}
		return state.setValue(WINDOW_TYPE, WindowType.SINGLE);
	}
	
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(WATERLOGGED, WINDOW_TYPE);
    }
	
	public static enum WindowType implements StringRepresentable
	{
		SINGLE("single"),
		SINGLE_TOP("single_top"),
		SINGLE_TOP_XY("single_top_xy"),
		SINGLE_MIDDLE_XY("single_middle_xy"),
		SINGLE_BOTTOM_XY("single_bottom_xy"),
		SINGLE_TOP_X("single_top_x"),
		SINGLE_MIDDLE_X("single_middle_x"),
		SINGLE_BOTTOM_X("single_bottom_x"),
		SINGLE_MIDDLE("single_middle"),
		SINGLE_BOTTOM("single_bottom"),
		MIDDLE_TOP("middle_top"),
		MIDDLE_BOTTOM("middle_bottom"),
		MIDDLE_TOP_X("middle_top_x"),
		MIDDLE_BOTTOM_X("middle_bottom_x"),
		MIDDLE_RIGHT("middle_right"),
		MIDDLE_LEFT("middle_left"),
		MIDDLE_RIGHT_X("middle_right_x"),
		MIDDLE_LEFT_X("middle_left_x"),
		CORNER_RIGHT("corner_right"),
		CORNER_LEFT("corner_left"),
		CORNER_RIGHT_X("corner_right_x"),
		CORNER_LEFT_X("corner_left_x"),
		CORNER_RIGHT_BOTTOM("corner_right_bottom"),
		CORNER_LEFT_BOTTOM("corner_left_bottom"),
		CORNER_RIGHT_BOTTOM_X("corner_right_bottom_x"),
		CORNER_LEFT_BOTTOM_X("corner_left_bottom_x"),
		MIDDLE_TOP_Y("middle_top_y"),
		MIDDLE_BOTTOM_Y("middle_bottom_y"),
		MIDDLE_RIGHT_Y("middle_right_y"),
		MIDDLE_LEFT_Y("middle_left_y"),
		CORNER_RIGHT_Y("corner_right_y"),
		CORNER_LEFT_Y("corner_left_y"),
		CORNER_RIGHT_BOTTOM_Y("corner_right_bottom_y"),
		CORNER_LEFT_BOTTOM_Y("corner_left_bottom_y"),
		TRANSLUCENT("translucent");
		
		private final String name;
		
		private WindowType(String name)
		{
			this.name = name;
		}

		@Override
		public String getSerializedName() 
		{
			return this.name;
		}
	}
}
