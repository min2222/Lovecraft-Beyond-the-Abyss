package com.min01.beyondtheabyss.block.deepabyss;

import java.util.Arrays;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

//FIXME soil type not randomized when generated via chunk
public class RotSoilBlock extends Block
{
	public static final EnumProperty<SoilType> SOIL_TYPE = EnumProperty.create("soil_type", SoilType.class);
	public RotSoilBlock()
	{
		super(BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT).randomTicks());
		this.registerDefaultState(this.stateDefinition.any().setValue(SOIL_TYPE, SoilType.NORMAL));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_)
	{
		return this.randomizeSoil(this.defaultBlockState());
	}
	
	//TODO temp method
	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState p_222508_, ServerLevel p_222509_, BlockPos p_222510_, RandomSource p_222511_)
	{
		if(p_222508_.getValue(SOIL_TYPE) == SoilType.NORMAL) 
		{
			if(p_222509_.isAreaLoaded(p_222510_, 1))
			{
				p_222509_.setBlockAndUpdate(p_222510_, this.randomizeSoil(p_222508_));
			}
		}
	}
	
	public BlockState randomizeSoil(BlockState state)
	{
		List<SoilType> list = Arrays.asList(SoilType.VARIANT_1, SoilType.SPINE, SoilType.NO_BONE);
		int random = (int) Math.floor(Math.random() * list.size());
		if(Math.random() <= 0.01F)
		{
			return state.setValue(SOIL_TYPE, SoilType.SKULL);
		}
		else if(Math.random() <= 0.05F)
		{
			return state.setValue(SOIL_TYPE, SoilType.FISH);
		}
		else
		{
			return state.setValue(SOIL_TYPE, list.get(random));
		}
	}
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(SOIL_TYPE);
    }
	
	public static enum SoilType implements StringRepresentable
	{
		VARIANT_1("variant_1"),
		NORMAL("normal"),
		SPINE("spine"),
		SKULL("skull"),
		NO_BONE("no_bone"),
		FISH("fish");
		
		private final String name;
		
		private SoilType(String name)
		{
			this.name = name;
		}

		@Override
		public String getSerializedName() 
		{
			return this.name;
		}
	}
}
