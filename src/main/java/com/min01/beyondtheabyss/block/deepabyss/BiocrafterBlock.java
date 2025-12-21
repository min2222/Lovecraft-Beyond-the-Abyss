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
	public static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);
	
	public BiocrafterBlock()
	{
		super(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion());
		this.registerDefaultState(this.stateDefinition.any().setValue(BIOCRAFTER_PART, BiocrafterPart.LOWER).setValue(PROCESSING, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) 
	{
		if(pState.getValue(BIOCRAFTER_PART) == BiocrafterPart.UPPER)
		{
			return AABB;
		}
		return Shapes.block();
	}
	
	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) 
	{
		if(pLevel.isClientSide) 
		{
			return InteractionResult.SUCCESS;
		} 
		else 
		{
			pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) 
	{
		return new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> 
		{
			return new BiocrafterMenu(pContainerId, pPlayerInventory, ContainerLevelAccess.create(pLevel, pPos));
		}, Component.translatable("container.biocrafter"));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) 
	{
		pBuilder.add(BIOCRAFTER_PART, WATERLOGGED, PROCESSING);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState pState)
	{
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) 
	{
		return new BiocrafterBlockEntity(pPos, pState);
	}
	
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType)
    {
		if(pState.getValue(BIOCRAFTER_PART) == BiocrafterPart.LOWER)
		{
	        return createTicker(pLevel, pBlockEntityType, BTABlocks.BIOCRAFTER_BLOCK_ENTITY.get());
		}
		return null;
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level pLevel, BlockEntityType<T> pServerType, BlockEntityType<BiocrafterBlockEntity> pClientType)
    {
        return createTickerHelper(pServerType, pClientType, BiocrafterBlockEntity::update);
    }
	
	@Override
	public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) 
	{
		if(!pLevel.isClientSide)
		{
			BiocrafterPart biocrafterPart = pState.getValue(BIOCRAFTER_PART);
			BlockPos blockPos = pPos.relative(this.getNeighbourDirection(biocrafterPart));
			BlockState blockState = pLevel.getBlockState(blockPos);
			if(blockState.is(this))
			{
				pLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
				pLevel.levelEvent(pPlayer, 2001, blockPos, Block.getId(blockState));
			}
		}
		super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
	}
	
	public Direction getNeighbourDirection(BiocrafterPart part)
	{
		return part == BiocrafterPart.LOWER ? Direction.UP : Direction.DOWN;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) 
	{
		if(!pLevel.isClientSide)
		{
			BlockPos blockPos = pPos.relative(Direction.UP);
			boolean flag = pLevel.isEmptyBlock(blockPos) || pLevel.getBlockState(blockPos).liquid();
			if(flag)
			{
				pLevel.setBlock(blockPos, pState.setValue(BIOCRAFTER_PART, BiocrafterPart.UPPER), 3);
				pLevel.blockUpdated(pPos, Blocks.AIR);
				pState.updateNeighbourShapes(pLevel, pPos, 3);
			}
		}
	}
	
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
    	Level level = pContext.getLevel();
    	BlockPos pos = pContext.getClickedPos();
    	return this.defaultBlockState().setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }
    
    @Override
    public FluidState getFluidState(BlockState pState)
    {
    	return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
	
	public static enum BiocrafterPart implements StringRepresentable
	{
		UPPER("upper"),
		LOWER("lower");

		private final String name;
		
		private BiocrafterPart(String name)
		{
			this.name = name;
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
