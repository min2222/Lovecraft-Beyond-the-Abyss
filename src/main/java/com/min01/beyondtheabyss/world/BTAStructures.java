package com.min01.beyondtheabyss.world;

import java.util.List;
import java.util.Map;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.structure.deepabyss.RuinPiece;
import com.min01.beyondtheabyss.world.structure.deepabyss.RuinStructure;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
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
	    	return ResourceKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, p_209873_));
	    }
	}
	
	public static class BTAStructureSetKeys
	{
		public static final ResourceKey<StructureSet> RUIN = register("ruin");
		public static final ResourceKey<StructureSet> UNDERWATER_BASE = register("underwater_base");
		
		private static ResourceKey<StructureSet> register(String p_209839_) 
		{
			return ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(BeyondtheAbyss.MODID, p_209839_));
		}
	}
	
	public static class BTAStructureHolders
	{
	    public static final Holder<Structure> RUIN = register(BTAStructureKeys.RUIN, new RuinStructure(structure(BiomeTags.IS_OCEAN, TerrainAdjustment.NONE)));
	    
	    private static Holder<Structure> register(ResourceKey<Structure> p_236534_, Structure p_236535_) 
	    {
	        return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, p_236534_, p_236535_);
	    }
	}
	
	public static class BTAStructureSetHolders
	{
		public static final Holder<StructureSet> RUIN = register(BTAStructureSetKeys.RUIN, new StructureSet(List.of(StructureSet.entry(BTAStructureHolders.RUIN)), new RandomSpreadStructurePlacement(20, 8, RandomSpreadType.LINEAR, 1984567320)));
	    
		public static Holder<StructureSet> register(ResourceKey<StructureSet> p_211129_, StructureSet p_211130_)
		{
			return BuiltinRegistries.register(BuiltinRegistries.STRUCTURE_SETS, p_211129_, p_211130_);
		}
	}
	
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, BeyondtheAbyss.MODID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<StructureType<RuinStructure>> RUIN = STRUCTURE_TYPES.register("ruin", () -> () -> RuinStructure.CODEC);
    public static final RegistryObject<StructurePieceType> RUIN_PIECE = STRUCTURE_PIECE_TYPES.register("ruin_piece", () -> RuinPiece::new);
    
    private static Structure.StructureSettings structure(TagKey<Biome> p_236543_, TerrainAdjustment p_236544_) 
    {
        return structure(p_236543_, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, p_236544_);
    }
    
    private static Structure.StructureSettings structure(TagKey<Biome> p_236546_, Map<MobCategory, StructureSpawnOverride> p_236547_, GenerationStep.Decoration p_236548_, TerrainAdjustment p_236549_) 
    {
    	return new Structure.StructureSettings(biomes(p_236546_), p_236547_, p_236548_, p_236549_);
    }
    
    @SuppressWarnings("deprecation")
	private static HolderSet<Biome> biomes(TagKey<Biome> p_236537_) 
    {
    	return BuiltinRegistries.BIOME.getOrCreateTag(p_236537_);
    }
}
