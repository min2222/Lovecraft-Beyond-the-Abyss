package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NoBonemealSeagrassBlock extends SeagrassBlock
{
	public final boolean isBlockShape;
	
	public NoBonemealSeagrassBlock(Properties pProperties, boolean isBlockShape)
	{
		super(pProperties);
		this.isBlockShape = isBlockShape;
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		if(this.isBlockShape)
		{
			return Shapes.block();
		}
		return super.getShape(pState, pLevel, pPos, pContext);
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) 
	{
		return false;
	}
	
	@Override
	public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState)
	{
		
	}
}
