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
	
	public NoBonemealSeagrassBlock(Properties p_154496_, boolean isBlockShape) 
	{
		super(p_154496_);
		this.isBlockShape = isBlockShape;
	}
	
	@Override
	public VoxelShape getShape(BlockState p_154525_, BlockGetter p_154526_, BlockPos p_154527_, CollisionContext p_154528_)
	{
		if(this.isBlockShape)
		{
			return Shapes.block();
		}
		return super.getShape(p_154525_, p_154526_, p_154527_, p_154528_);
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader p_255857_, BlockPos p_154511_, BlockState p_154512_, boolean p_154513_) 
	{
		return false;
	}
	
	@Override
	public void performBonemeal(ServerLevel p_222423_, RandomSource p_222424_, BlockPos p_222425_, BlockState p_222426_)
	{
		
	}
}
