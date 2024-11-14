package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.structure.deepabyss.RuinPiece;
import com.min01.beyondtheabyss.world.structure.deepabyss.RuinStructure;
import com.min01.beyondtheabyss.world.structure.deepabyss.SpireHollowStructure;
import com.min01.beyondtheabyss.world.structure.deepabyss.SpireHollowStructurePiece;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAStructures
{
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, BeyondtheAbyss.MODID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<StructureType<RuinStructure>> RUIN = STRUCTURE_TYPES.register("ruin", () -> () -> RuinStructure.CODEC);
    public static final RegistryObject<StructurePieceType> RUIN_PIECE = STRUCTURE_PIECE_TYPES.register("ruin_piece", () -> RuinPiece::new);
    
    public static final RegistryObject<StructureType<SpireHollowStructure>> SPIRE_HOLLOW = STRUCTURE_TYPES.register("spire_hollow", () -> () -> SpireHollowStructure.CODEC);
    public static final RegistryObject<StructurePieceType> SPIRE_HOLLOW_PIECE = STRUCTURE_PIECE_TYPES.register("spire_hollow_piece", () -> SpireHollowStructurePiece::new);
}
