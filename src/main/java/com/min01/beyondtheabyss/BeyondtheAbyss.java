package com.min01.beyondtheabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.config.BTAConfig;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.particle.BTAParticles;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.world.BTABiomes;
import com.min01.beyondtheabyss.world.BTAConfiguredFeatures;
import com.min01.beyondtheabyss.world.BTAFeatures;
import com.min01.beyondtheabyss.world.BTAPlacedFeatures;
import com.min01.beyondtheabyss.world.BTAStructures;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(BeyondtheAbyss.MODID)
public class BeyondtheAbyss
{
	public static final String MODID = "beyondtheabyss";
	
	public BeyondtheAbyss() 
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext ctx = ModLoadingContext.get();
        bus.addListener(this::setupCurios);
		BTAEntities.ENTITY_TYPES.register(bus);
		BTAItems.ITEMS.register(bus);
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
		BTAConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
		BTAPlacedFeatures.PLACED_FEATURES.register(bus);
		BTABiomes.BIOMES.register(bus);
		
		BTANetwork.registerMessages();
		ctx.registerConfig(Type.COMMON, BTAConfig.CONFIG_SPEC, "beyond-the-abyss.toml");
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, BTACapabilities::attachEntityCapability);
	}
	
    public void setupCurios(InterModEnqueueEvent event) 
    {
        for(SlotTypePreset type : SlotTypePreset.values()) 
        {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> type.getMessageBuilder().build());
        }
    }
}
