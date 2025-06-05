package com.min01.beyondtheabyss.block.deepabyss;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class RotSoilBlock extends Block
{
	public static final EnumProperty<SoilType> SOIL_TYPE = EnumProperty.create("soil_type", SoilType.class);
	public RotSoilBlock()
	{
		super(BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT));
		this.registerDefaultState(this.stateDefinition.any().setValue(SOIL_TYPE, SoilType.VARIANT_1));
	}
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_)
    {
    	p_152043_.add(SOIL_TYPE);
    }
	
	public static enum SoilType implements StringRepresentable
	{
		VARIANT_1("variant_1"),
		VARIANT_2("variant_2"),
		VARIANT_3("variant_3"),
		VARIANT_4("variant_4"),
		VARIANT_5("variant_5"),
		VARIANT_6("variant_6"),
		VARIANT_7("variant_7"),
		VARIANT_8("variant_8"),
		VARIANT_9("variant_9"),
		VARIANT_10("variant_10");
		
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
