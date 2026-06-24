package com.min01.beyondtheabyss.world.structure;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public abstract class OneTimePlacedStructure extends Structure
{
	public OneTimePlacedStructure(StructureSettings pSettings) 
	{
		super(pSettings);
	}
	
	public abstract ResourceKey<Structure> getStructureKey();
}
