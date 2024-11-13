package com.min01.beyondtheabyss.block.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GhoulBloomBlock extends BushBlock implements SimpleWaterloggedBlock, BonemealableBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
	
	protected static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	   
	public GhoulBloomBlock()
	{
		super(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_BROWN).noCollission().randomTicks().sound(SoundType.CROP));
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		return AABB;
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState p_54991_, BlockGetter p_54992_, BlockPos p_54993_) 
	{
		return p_54991_.is(BTABlocks.ROT_SOIL.get());
	}

	@Override
	public boolean isRandomlyTicking(BlockState p_54979_) 
	{
		return p_54979_.getValue(AGE) < 6;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) 
	{
    	LevelAccessor level = p_49820_.getLevel();
    	BlockPos blockPos = p_49820_.getClickedPos();
		return this.defaultBlockState().setValue(WATERLOGGED, level.getFluidState(blockPos).getType() == Fluids.WATER);
	}

	@Override
	public void randomTick(BlockState p_221806_, ServerLevel p_221807_, BlockPos p_221808_, RandomSource p_221809_)
	{
		int i = p_221806_.getValue(AGE);
		if(i < 6 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_221807_, p_221808_, p_221806_, p_221809_.nextInt(10) == 0))
		{
			p_221806_ = p_221806_.setValue(AGE, i + 1);
			p_221807_.setBlock(p_221808_, p_221806_, 2);
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_221807_, p_221808_, p_221806_);
		}
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter p_54973_, BlockPos p_54974_, BlockState p_54975_)
	{
		return new ItemStack(BTAItems.GHOUL_BLOOM_SEED_POD.get());
	}
	
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54977_)
	{
		p_54977_.add(WATERLOGGED, AGE);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) 
	{
		return p_50899_.getValue(AGE) < 6;
	}

	@Override
	public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) 
	{
		int age = p_220881_.getValue(AGE);
		boolean flag = p_220878_.isEmptyBlock(p_220880_.above()) || p_220878_.getBlockState(p_220880_.above()).getMaterial().isLiquid();
		return age == 5 ? flag : true;
	}
	
	@Override
	public boolean canSurvive(BlockState p_51028_, LevelReader p_51029_, BlockPos p_51030_) 
	{
		int age = p_51028_.getValue(AGE);
		if(age < 6)
		{
			return this.mayPlaceOn(p_51029_.getBlockState(p_51030_.below()), p_51029_, p_51030_.below());
		}
		else if(age == 6)
		{
			BlockState top = p_51029_.getBlockState(p_51030_.above());
			return this.mayPlaceOn(p_51029_.getBlockState(p_51030_.below()), p_51029_, p_51030_.below()) && top.is(BTABlocks.GHOUL_BLOOM.get()) && top.getValue(AGE) == 7;
		}
		else if(age == 7)
		{
			return this.isTop(p_51029_.getBlockState(p_51030_.below()));
		}
		return false;
	}

	@Override
	public void performBonemeal(ServerLevel p_220874_, RandomSource p_220875_, BlockPos p_220876_, BlockState p_220877_)
	{
		int age = p_220877_.getValue(AGE) + Mth.nextInt(p_220874_.random, 2, 3);
		int maxAge = 6;
		if(age > maxAge)
		{
			age = maxAge;
		}
		
		if(age == maxAge)
		{
			if(p_220874_.isEmptyBlock(p_220876_.above()) || p_220874_.getBlockState(p_220876_.above()).getMaterial().isLiquid())
			{
				p_220874_.setBlock(p_220876_.above(), p_220877_.setValue(AGE, maxAge + 1), 2);
			}
		}

		p_220874_.setBlock(p_220876_, p_220877_.setValue(AGE, age), 2);
	}
	
	public boolean isTop(BlockState state)
	{
		return state.is(BTABlocks.GHOUL_BLOOM.get()) && state.getValue(AGE) == 6;
	}
}
