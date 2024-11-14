package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ToothvineBlock extends BushBlock implements SimpleWaterloggedBlock, BonemealableBlock
{
	public static final EnumProperty<VineState> VINE_STATE = EnumProperty.create("state", VineState.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	
	protected static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	
	public ToothvineBlock()
	{
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().sound(SoundType.CROP));
		this.registerDefaultState(this.stateDefinition.any().setValue(VINE_STATE, VineState.BASE));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		return AABB;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) 
	{
    	LevelAccessor level = p_49820_.getLevel();
    	BlockPos blockPos = p_49820_.getClickedPos();
		return this.defaultBlockState().setValue(WATERLOGGED, level.getFluidState(blockPos).getType() == Fluids.WATER);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54977_)
	{
		p_54977_.add(WATERLOGGED, VINE_STATE);
	}
	
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
	
	@Override
	public boolean isValidBonemealTarget(LevelReader p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) 
	{
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) 
	{
		VineState state = p_220881_.getValue(VINE_STATE);
		boolean flag = p_220878_.isEmptyBlock(p_220880_.above()) || p_220878_.getBlockState(p_220880_.above()).liquid();
		return state == VineState.TIP ? flag : true;
	}
	
	@Override
	public boolean canSurvive(BlockState p_51028_, LevelReader p_51029_, BlockPos p_51030_) 
	{
		VineState state = p_51028_.getValue(VINE_STATE);
		BlockState below = p_51029_.getBlockState(p_51030_.below());
		BlockState above = p_51029_.getBlockState(p_51030_.above());
		if(state == VineState.VARIANT_1 || state == VineState.VARIANT_2)
		{
			return below.is(BTABlocks.TOOTHVINE.get()) && above.is(BTABlocks.TOOTHVINE.get());
		}
		else if(state == VineState.BASE)
		{
			return below.is(BTABlocks.ROT_SOIL.get()) && above.is(BTABlocks.TOOTHVINE.get());
		}
		else if(state == VineState.TIP)
		{
			return below.is(BTABlocks.TOOTHVINE.get());
		}
		return false;
	}

	@Override
	public void performBonemeal(ServerLevel p_220874_, RandomSource p_220875_, BlockPos p_220876_, BlockState p_220877_)
	{
		//TODO grow when use bone meal
		//VineState state = p_220877_.getValue(VINE_STATE);
		//p_220874_.setBlock(p_220876_, p_220877_.setValue(VINE_STATE, state), 2);
	}
	
	public static enum VineState implements StringRepresentable
	{
		BASE("base"),
		TIP("tip"),
		VARIANT_1("variant_1"),
		VARIANT_2("variant_2");

		private final String name;
		
		private VineState(String p_61339_)
		{
			this.name = p_61339_;
		}

		@Override
		public String toString()
		{
			return this.name;
		}

		@Override
		public String getSerializedName()
		{
			return this.name;
		}
	}
}
