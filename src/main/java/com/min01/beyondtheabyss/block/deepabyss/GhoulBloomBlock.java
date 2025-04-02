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
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GhoulBloomBlock extends SeagrassBlock
{
	//GrowingPlantHeadBlock
	public static final BooleanProperty GROWN = BooleanProperty.create("grown");
	protected static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	
	public GhoulBloomBlock()
	{
		super(BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.COLOR_BROWN).noCollission().randomTicks().sound(SoundType.CROP));
		this.registerDefaultState(this.stateDefinition.any().setValue(GROWN, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		return AABB;
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState p_54991_, BlockGetter p_54992_, BlockPos p_54993_) 
	{
		return p_54991_.isFaceSturdy(p_54992_, p_54993_, Direction.UP) && p_54991_.is(BTABlocks.ROT_SOIL.get());
	}
	
	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) 
	{
		return new ItemStack(BTAItems.GHOUL_BLOOM_SEED_POD.get());
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> p_49915_) 
	{
		p_49915_.add(GROWN);
	}
	
	@Override
	public void performBonemeal(ServerLevel p_222423_, RandomSource p_222424_, BlockPos p_222425_, BlockState p_222426_) 
	{
		//TODO tall ghoul bloom;
	}
}
