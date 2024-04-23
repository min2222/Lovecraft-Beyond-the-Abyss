package com.min01.beyondtheabyss.block;

import java.util.function.ToIntFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BTALightBlock extends Block implements SimpleWaterloggedBlock 
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final ToIntFunction<BlockState> LIGHT_EMISSION = (p_153701_) ->
	{
		return 35;
	};

	public BTALightBlock(BlockBehaviour.Properties p_153662_)
	{
		super(p_153662_);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, true));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_153687_)
	{
		p_153687_.add(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState p_153668_, BlockGetter p_153669_, BlockPos p_153670_, CollisionContext p_153671_) 
	{
		return p_153671_.isHoldingItem(Items.LIGHT) ? Shapes.block() : Shapes.empty();
	}

	@Override
	public boolean propagatesSkylightDown(BlockState p_153695_, BlockGetter p_153696_, BlockPos p_153697_) 
	{
		return true;
	}

	@Override
	public RenderShape getRenderShape(BlockState p_153693_)
	{
		return RenderShape.INVISIBLE;
	}

	@Override
	public float getShadeBrightness(BlockState p_153689_, BlockGetter p_153690_, BlockPos p_153691_)
	{
		return 1.0F;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState p_153680_, Direction p_153681_, BlockState p_153682_, LevelAccessor p_153683_, BlockPos p_153684_, BlockPos p_153685_)
	{
		if (p_153680_.getValue(WATERLOGGED))
		{
			p_153683_.scheduleTick(p_153684_, Fluids.WATER, Fluids.WATER.getTickDelay(p_153683_));
		}

		return super.updateShape(p_153680_, p_153681_, p_153682_, p_153683_, p_153684_, p_153685_);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState p_153699_)
	{
		return p_153699_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_153699_);
	}
}
