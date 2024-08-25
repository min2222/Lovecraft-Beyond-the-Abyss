package com.min01.beyondtheabyss.world;

import java.util.List;
import java.util.Map;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.structure.deepabyss.RuinPiece;
import com.min01.beyondtheabyss.world.structure.deepabyss.RuinStructure;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAStructures
{
	public static class BTAStructureKeys
	{
		public static final ResourceKey<Structure> RUIN = register("ruin");
		public static final ResourceKey<Structure> UNDERWATER_BASE = register("underwater_base");
		
	    private static ResourceKey<Structure> register(String p_209873_)
	    {
	    	return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(BeyondtheAbyss.MODID, p_209873_));
	    }
	}
	
	public static class BTAStructureSetKeys
	{
		public static final ResourceKey<StructureSet> RUIN = register("ruin");
		public static final ResourceKey<StructureSet> UNDERWATER_BASE = register("underwater_base");
		
		private static ResourceKey<StructureSet> register(String p_209839_) 
		{
			return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(BeyondtheAbyss.MODID, p_209839_));
		}
	}
	
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, BeyondtheAbyss.MODID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<StructureType<RuinStructure>> RUIN = STRUCTURE_TYPES.register("ruin", () -> () -> RuinStructure.CODEC);
    public static final RegistryObject<StructurePieceType> RUIN_PIECE = STRUCTURE_PIECE_TYPES.register("ruin_piece", () -> RuinPiece::new);

	public static void bootstrapStructures(BootstapContext<Structure> context) 
	{
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(BTAStructureKeys.RUIN, new RuinStructure(structure(biomes, BiomeTags.IS_OCEAN, TerrainAdjustment.NONE)));
	}
	
	public static void bootstrapSets(BootstapContext<StructureSet> context)
	{
		HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
		context.register(BTAStructureSetKeys.RUIN, new StructureSet(List.of(StructureSet.entry(structures.getOrThrow(BTAStructureKeys.RUIN))), new RandomSpreadStructurePlacement(20, 8, RandomSpreadType.LINEAR, 1984567320)));
	}
	
    private static Structure.StructureSettings structure(HolderGetter<Biome> biome, TagKey<Biome> p_236543_, TerrainAdjustment p_236544_) 
    {
        return structure(biome, p_236543_, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, p_236544_);
    }
    
    private static Structure.StructureSettings structure(HolderGetter<Biome> biome, TagKey<Biome> p_236546_, Map<MobCategory, StructureSpawnOverride> p_236547_, GenerationStep.Decoration p_236548_, TerrainAdjustment p_236549_) 
    {
    	return new Structure.StructureSettings(biome.getOrThrow(p_236546_), p_236547_, p_236548_, p_236549_);
    }
}
