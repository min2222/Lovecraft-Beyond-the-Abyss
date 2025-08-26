package com.min01.beyondtheabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTACreativeModeTabs;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.particle.BTAParticles;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.world.BTAChunkGenerators;
import com.min01.beyondtheabyss.world.BTADensityFunctions;
import com.min01.beyondtheabyss.world.BTAFeatures;
import com.min01.beyondtheabyss.world.BTAStructures;
import com.min01.beyondtheabyss.world.BTASurfaceRules;
import com.min01.beyondtheabyss.world.BTAWorldCarvers;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BeyondtheAbyss.MODID)
public class BeyondtheAbyss
{
	public static final String MODID = "beyondtheabyss";
	
	public BeyondtheAbyss() 
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext ctx = ModLoadingContext.get();
		BTAEntities.ENTITY_TYPES.register(bus);
		BTAItems.DEEP_ABYSS_ITEMS.register(bus);
		BTAItems.EVERGREEN_ITEMS.register(bus);
		BTAItems.MIRRORED_CITY_ITEMS.register(bus);
		BTAItems.MOON_ITEMS.register(bus);
		BTAItems.ENDLESS_DESERT_ITEMS.register(bus);
		BTAItems.PURGATORY_ITEMS.register(bus);
		BTAItems.OUTER_SPACE_ITEMS.register(bus);
		BTABlocks.BLOCKS.register(bus);
		BTABlocks.BLOCK_ENTITIES.register(bus);
		BTASounds.SOUNDS.register(bus);
		BTAEffects.EFFECTS.register(bus);
		BTAEffects.POTIONS.register(bus);
		BTAEntityDataSerializers.SERIALIZERS.register(bus);
		BTAParticles.PARTICLES.register(bus);
		BTAStructures.STRUCTURE_TYPES.register(bus);
		BTAStructures.STRUCTURE_PIECE_TYPES.register(bus);
		BTAFeatures.FEATURES.register(bus);
		BTACreativeModeTabs.CREATIVE_MODE_TAB.register(bus);
		BTAChunkGenerators.CHUNK_GENERATORS.register(bus);
		BTASurfaceRules.RULE_SOURCES.register(bus);
		BTAWorldCarvers.WORLD_CARVERS.register(bus);
		BTADensityFunctions.DENSITY_FUNCTIONS.register(bus);
		
		BTANetwork.registerMessages();
		ctx.registerConfig(Type.COMMON, BTAConfig.CONFIG_SPEC, "beyond-the-abyss.toml");
		MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, BTACapabilities::attachItemStackCapability);
	}
}
