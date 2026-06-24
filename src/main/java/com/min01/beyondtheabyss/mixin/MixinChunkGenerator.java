package com.min01.beyondtheabyss.mixin;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.min01.beyondtheabyss.world.BTASavedData;
import com.min01.beyondtheabyss.world.structure.OneTimePlacedStructure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

@Mixin(ChunkGenerator.class)
public class MixinChunkGenerator 
{
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/Structure;generate(Lnet/minecraft/core/RegistryAccess;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/level/biome/BiomeSource;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;JLnet/minecraft/world/level/ChunkPos;ILnet/minecraft/world/level/LevelHeightAccessor;Ljava/util/function/Predicate;)Lnet/minecraft/world/level/levelgen/structure/StructureStart;"), method = "tryGenerateStructure")
	private StructureStart generate(Structure instance, RegistryAccess pRegistryAccess, ChunkGenerator pChunkGenerator, BiomeSource pBiomeSource, RandomState pRandomState, StructureTemplateManager pStructureTemplateManager, long pSeed, ChunkPos pChunkPos, int pReferences, LevelHeightAccessor pHeightAccessor, Predicate<Holder<Biome>> pValidBiome, Operation<StructureStart> original, StructureSet.StructureSelectionEntry pStructureSelectionEntry, StructureManager pStructureManager, RegistryAccess pRegistryAccess1, RandomState pRandom, StructureTemplateManager pStructureTemplateManager1, long pSeed1, ChunkAccess pChunk, ChunkPos pChunkPos1, SectionPos pSectionPos)
	{
		if(instance instanceof OneTimePlacedStructure structure && pStructureManager.level instanceof Level level)
		{
			BTASavedData data = BTASavedData.get(level);
			if(!data.getStructurePos(structure.getStructureKey()).equals(BlockPos.ZERO))
			{
				return StructureStart.INVALID_START;
			}
		}
		return original.call(instance, pRegistryAccess, pChunkGenerator, pBiomeSource, pRandomState, pStructureTemplateManager, pSeed, pChunkPos, pReferences, pHeightAccessor, pValidBiome);
	}
}
