package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RodBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public abstract class AbstractConnectedBoneBlock extends RodBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty MIDDLE = BooleanProperty.create("middle");
	public static final BooleanProperty SOLO = BooleanProperty.create("solo");
	
	public AbstractConnectedBoneBlock(Properties p_49795_) 
	{
		super(p_49795_.dynamicShape().noOcclusion().requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK));
	}
	
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_)
    {
    	LevelAccessor level = p_152019_.getLevel();
    	BlockPos blockPos = p_152019_.getClickedPos();
    	Direction direction = p_152019_.getClickedFace();
    	BlockState above = level.getBlockState(blockPos.relative(direction, 1));
    	BlockState below = level.getBlockState(blockPos.relative(direction, -1));
    	Boolean isMiddle = Boolean.valueOf(above.is(BTABlocks.SMALL_BONE.get()) && below.is(BTABlocks.SMALL_BONE.get()));
    	Boolean isSolo = Boolean.valueOf(!(above.is(BTABlocks.SMALL_BONE.get())) && !(below.is(BTABlocks.SMALL_BONE.get())));
    	return this.getFacingStateForPlacement(p_152019_).setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(blockPos).getType() == Fluids.WATER)).setValue(MIDDLE, isMiddle).setValue(SOLO, isSolo);
    }
    
    public BlockState getFacingStateForPlacement(BlockPlaceContext p_53087_)
    {
    	Direction direction = p_53087_.getClickedFace();
    	BlockState blockstate = p_53087_.getLevel().getBlockState(p_53087_.getClickedPos().relative(direction.getOpposite()));
    	BlockState blockstate2 = p_53087_.getLevel().getBlockState(p_53087_.getClickedPos().relative(direction, -1));
    	BlockState blockstate3 = p_53087_.getLevel().getBlockState(p_53087_.getClickedPos().relative(direction, -2));
    	boolean flag = blockstate2.is(BTABlocks.SMALL_BONE.get()) && blockstate3.is(BTABlocks.SMALL_BONE.get());
    	return (blockstate.is(BTABlocks.SMALL_BONE.get()) && blockstate.getValue(FACING) == direction) || flag ? this.defaultBlockState().setValue(FACING, direction.getOpposite()) : this.defaultBlockState().setValue(FACING, direction);
    }
    
    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_)
    {
    	Direction direction = p_60541_.getValue(FACING);
    	BlockState above = p_60544_.getBlockState(p_60545_.relative(direction, 1));
    	BlockState below = p_60544_.getBlockState(p_60545_.relative(direction, -1));
    	Boolean isMiddle = Boolean.valueOf(above.is(BTABlocks.SMALL_BONE.get()) && below.is(BTABlocks.SMALL_BONE.get()));
    	Boolean isSolo = Boolean.valueOf(!(above.is(BTABlocks.SMALL_BONE.get())) && !(below.is(BTABlocks.SMALL_BONE.get())));
    	return p_60541_.setValue(MIDDLE, isMiddle).setValue(SOLO, isSolo);
    }
    
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(WATERLOGGED);
    	p_152043_.add(FACING);
    	p_152043_.add(MIDDLE);
    	p_152043_.add(SOLO);
    }
}
