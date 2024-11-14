package com.min01.beyondtheabyss.misc;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.BTAConfiguredFeatures;
import com.min01.beyondtheabyss.world.BTAPlacedFeatures;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class BTADataProvider extends DatapackBuiltinEntriesProvider
{
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.PLACED_FEATURE, BTAPlacedFeatures::bootstrap)
			.add(Registries.CONFIGURED_FEATURE, (context) -> BTAConfiguredFeatures.bootstrap(context));
    
	public BTADataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) 
	{
		super(output, provider, BUILDER, Set.of(BeyondtheAbyss.MODID));
	}
}
