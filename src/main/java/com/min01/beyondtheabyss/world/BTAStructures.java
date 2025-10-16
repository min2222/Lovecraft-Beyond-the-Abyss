package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.structure.HutStructure;
import com.min01.beyondtheabyss.world.structure.HutStructurePiece;
import com.min01.beyondtheabyss.world.structure.deepabyss.DeepAbyssPortalStructure;
import com.min01.beyondtheabyss.world.structure.deepabyss.DeepAbyssPortalStructurePiece;
import com.min01.beyondtheabyss.world.structure.deepabyss.LargeFossilStructure;
import com.min01.beyondtheabyss.world.structure.deepabyss.LargeFossilStructurePiece;
import com.min01.beyondtheabyss.world.structure.deepabyss.SpireHollowStructure;
import com.min01.beyondtheabyss.world.structure.deepabyss.SpireHollowStructurePiece;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTAStructures
{
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, BeyondtheAbyss.MODID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, BeyondtheAbyss.MODID);

    public static final RegistryObject<StructureType<SpireHollowStructure>> SPIRE_HOLLOW = STRUCTURE_TYPES.register("spire_hollow", () -> () -> SpireHollowStructure.CODEC);
    public static final RegistryObject<StructureType<HutStructure>> HUT = STRUCTURE_TYPES.register("hut", () -> () -> HutStructure.CODEC);
    public static final RegistryObject<StructureType<LargeFossilStructure>> LARGE_FOSSIL = STRUCTURE_TYPES.register("large_fossil", () -> () -> LargeFossilStructure.CODEC);
    public static final RegistryObject<StructureType<DeepAbyssPortalStructure>> DEEP_ABYSS_PORTAL = STRUCTURE_TYPES.register("deep_abyss_portal", () -> () -> DeepAbyssPortalStructure.CODEC);
    
    public static final RegistryObject<StructurePieceType.StructureTemplateType> HUT_PIECE = STRUCTURE_PIECE_TYPES.register("hut_piece", () -> HutStructurePiece::new);
    public static final RegistryObject<StructurePieceType> SPIRE_HOLLOW_PIECE = STRUCTURE_PIECE_TYPES.register("spire_hollow_piece", () -> SpireHollowStructurePiece::new);
    public static final RegistryObject<StructurePieceType.StructureTemplateType> LARGE_FOSSIL_PIECE = STRUCTURE_PIECE_TYPES.register("large_fossil_piece", () -> LargeFossilStructurePiece::new);
    public static final RegistryObject<StructurePieceType.StructureTemplateType> DEEP_ABYSS_PORTAL_PIECE = STRUCTURE_PIECE_TYPES.register("deep_abyss_portal_piece", () -> DeepAbyssPortalStructurePiece::new);

    public static class Keys
    {
    	public static final ResourceKey<Structure> LARGE_FOSSIL = createKey("large_fossil");
    	
    	private static ResourceKey<Structure> createKey(String name) 
    	{
    		return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(BeyondtheAbyss.MODID, name));
    	}
    }
}
