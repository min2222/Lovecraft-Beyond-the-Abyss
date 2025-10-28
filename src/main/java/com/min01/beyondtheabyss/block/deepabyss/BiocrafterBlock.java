package com.min01.beyondtheabyss.block.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.blockentity.deepabyss.BiocrafterBlockEntity;
import com.min01.beyondtheabyss.world.inventory.BiocrafterMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BiocrafterBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty PROCESSING = BooleanProperty.create("processing");
	public static final EnumProperty<BiocrafterPart> BIOCRAFTER_PART = EnumProperty.create("biocrafter_part", BiocrafterPart.class);
	protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);
	
	public BiocrafterBlock()
	{
		super(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(BIOCRAFTER_PART, BiocrafterPart.LOWER).setValue(PROCESSING, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) 
	{
		if(p_60555_.getValue(BIOCRAFTER_PART) == BiocrafterPart.UPPER)
		{
			return AABB;
		}
		return Shapes.block();
	}
	
	@Override
	public InteractionResult use(BlockState p_52233_, Level p_52234_, BlockPos p_52235_, Player p_52236_, InteractionHand p_52237_, BlockHitResult p_52238_) 
	{
		if(p_52234_.isClientSide) 
		{
			return InteractionResult.SUCCESS;
		} 
		else 
		{
			p_52236_.openMenu(p_52233_.getMenuProvider(p_52234_, p_52235_));
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public MenuProvider getMenuProvider(BlockState p_52240_, Level p_52241_, BlockPos p_52242_) 
	{
		return new SimpleMenuProvider((p_52229_, p_52230_, p_52231_) -> 
		{
			return new BiocrafterMenu(p_52229_, p_52230_, ContainerLevelAccess.create(p_52241_, p_52242_));
		}, Component.translatable("container.biocrafter"));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> p_152043_) 
	{
		p_152043_.add(BIOCRAFTER_PART, WATERLOGGED, PROCESSING);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_49232_)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_)
	{
		return new BiocrafterBlockEntity(p_153215_, p_153216_);
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_)
    {
		if(p_153274_.getValue(BIOCRAFTER_PART) == BiocrafterPart.LOWER)
		{
	        return createTicker(p_153273_, p_153275_, BTABlocks.BIOCRAFTER_BLOCK_ENTITY.get());
		}
		return null;
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<BiocrafterBlockEntity> p_151990_)
    {
        return createTickerHelper(p_151989_, p_151990_, BiocrafterBlockEntity::update);
    }
	
	@Override
	public void playerWillDestroy(Level p_49505_, BlockPos p_49506_, BlockState p_49507_, Player p_49508_) 
	{
		if(!p_49505_.isClientSide)
		{
			BiocrafterPart biocrafterPart = p_49507_.getValue(BIOCRAFTER_PART);
			BlockPos blockPos = p_49506_.relative(this.getNeighbourDirection(biocrafterPart));
			BlockState blockState = p_49505_.getBlockState(blockPos);
			if(blockState.is(this))
			{
				p_49505_.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
				p_49505_.levelEvent(p_49508_, 2001, blockPos, Block.getId(blockState));
			}
		}
		super.playerWillDestroy(p_49505_, p_49506_, p_49507_, p_49508_);
	}
	
	public Direction getNeighbourDirection(BiocrafterPart p_49534_)
	{
		return p_49534_ == BiocrafterPart.LOWER ? Direction.UP : Direction.DOWN;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setPlacedBy(Level p_49499_, BlockPos p_49500_, BlockState p_49501_, @Nullable LivingEntity p_49502_, ItemStack p_49503_) 
	{
		super.setPlacedBy(p_49499_, p_49500_, p_49501_, p_49502_, p_49503_);
		if(!p_49499_.isClientSide)
		{
			BlockPos blockPos = p_49500_.relative(Direction.UP);
			boolean flag = p_49499_.isEmptyBlock(blockPos) || p_49499_.getBlockState(blockPos).liquid();
			if(flag)
			{
				p_49499_.setBlock(blockPos, p_49501_.setValue(BIOCRAFTER_PART, BiocrafterPart.UPPER), 3);
				p_49499_.blockUpdated(p_49500_, Blocks.AIR);
				p_49501_.updateNeighbourShapes(p_49499_, p_49500_, 3);
			}
		}
	}
	
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_152019_)
    {
    	Level level = p_152019_.getLevel();
    	BlockPos pos = p_152019_.getClickedPos();
    	return this.defaultBlockState().setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }
    
    @Override
    public FluidState getFluidState(BlockState p_152045_)
    {
    	return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
	
	public static enum BiocrafterPart implements StringRepresentable
	{
		UPPER("upper"),
		LOWER("lower");

		private final String name;
		
		private BiocrafterPart(String p_61339_)
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
