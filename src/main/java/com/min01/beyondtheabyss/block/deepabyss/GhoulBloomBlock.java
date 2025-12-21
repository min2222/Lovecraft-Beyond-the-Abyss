package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GhoulBloomBlock extends SeagrassBlock
{
	//GrowingPlantHeadBlock
	public static final BooleanProperty GROWN = BooleanProperty.create("grown");
	public static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	
	public GhoulBloomBlock()
	{
		super(BlockBehaviour.Properties.of().noCollission().randomTicks().sound(SoundType.CROP));
		this.registerDefaultState(this.stateDefinition.any().setValue(GROWN, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		return AABB;
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) 
	{
		return pState.isFaceSturdy(pLevel, pPos, Direction.UP) && pState.is(BTABlocks.ROT_SOIL.get());
	}
	
	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) 
	{
		return new ItemStack(BTAItems.GHOUL_BLOOM_SEED_POD.get());
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) 
	{
		pBuilder.add(GROWN);
	}
	
	@Override
	public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) 
	{
		if(!pState.getValue(GROWN))
		{
			pLevel.setBlock(pPos, pState.setValue(GROWN, true), 2);
		}
	}
}
