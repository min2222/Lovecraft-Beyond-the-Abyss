package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.BasicBTAFoodItem;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BTACreativeModeTabs 
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<CreativeModeTab> ABYSS_MOBS = CREATIVE_MODE_TAB.register("abyss_mobs", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_mobs"))
    		.icon(() -> new ItemStack(BTAItems.GHIDRUTH_SPAWN_EGG.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : BTAItems.ITEMS.getEntries())
    			{
    				if(item.get() instanceof ForgeSpawnEggItem)
    				{
    					output.accept(item.get());
    				}
    			}
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> ABYSS_MATERIALS = CREATIVE_MODE_TAB.register("abyss_materials", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_materials"))
    		.icon(() -> new ItemStack(BTAItems.OXYGEN_TANK.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
				output.accept(BTAItems.GHIDRUTH_SCALE.get());
				output.accept(BTAItems.OXYGEN_TANK.get());
				output.accept(BTAItems.VAMPIRE_MEMBRANE.get());
				output.accept(BTAItems.VAMPIRE_TOOTH.get());
				output.accept(BTAItems.GNASHER_EYE.get());
				output.accept(BTAItems.GNASHER_TOOTH.get());
				output.accept(BTAItems.GHOUL_BLOOM_SEED_POD.get());
				output.accept(BTAItems.FIBER.get());
				output.accept(BTAItems.ABERRANT_FLESH.get());
				output.accept(BTAItems.CHARGE_BULB.get());
				output.accept(BTAItems.SPLITTING_GEL.get());
				output.accept(BTAItems.SPINE_WORM_MANDIBLE.get());
				output.accept(BTAItems.SLASHER_BLADE.get());
				output.accept(BTAItems.BLASTER_SKULL.get());
				output.accept(BTAItems.SERPENT_HEART.get());
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> ABYSS_ARMORS = CREATIVE_MODE_TAB.register("abyss_armors", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_armors"))
    		.icon(() -> new ItemStack(BTAItems.DIVING_HELMET.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : BTAItems.ITEMS.getEntries())
    			{
    				if(item.get() instanceof ArmorItem)
    				{
    					output.accept(item.get());
    				}
    			}
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> ABYSS_MISC = CREATIVE_MODE_TAB.register("abyss_misc", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_misc"))
    		.icon(() -> new ItemStack(BTAItems.HEART_OF_FORNEUS.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
				output.accept(BTAItems.GUIDING_CLAM.get());
				output.accept(BTAItems.HEART_OF_FORNEUS.get());
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> ABYSS_FOODS = CREATIVE_MODE_TAB.register("abyss_foods", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_foods"))
    		.icon(() -> new ItemStack(BTAItems.RUNIC_FISH.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : BTAItems.ITEMS.getEntries())
    			{
    				if(item.get() instanceof BasicBTAFoodItem)
    				{
    					output.accept(item.get());
    				}
    			}
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> ABYSS_BLOCKS = CREATIVE_MODE_TAB.register("abyss_blocks", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_blocks"))
    		.icon(() -> new ItemStack(BTAItems.ABYSSALITH.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : BTAItems.ITEMS.getEntries())
    			{
    				if(item.get() instanceof BlockItem && !(item.get() instanceof ItemNameBlockItem))
    				{
    					output.accept(item.get());
    				}
    			}
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> ABYSS_WEAPONS = CREATIVE_MODE_TAB.register("abyss_weapons", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_weapons"))
    		.icon(() -> new ItemStack(BTAItems.RUSTY_HARPOON.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
				output.accept(BTAItems.RUSTY_HARPOON.get());
				output.accept(BTAItems.GHIDRUTH_SCALE_HARPOON.get());
				output.accept(BTAItems.SACRIFICIAL_DAGGER.get());
				output.accept(BTAItems.SKELETAL_RAILGUNBLADE.get());
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> ABYSS_TOOLS = CREATIVE_MODE_TAB.register("abyss_tools", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_tools"))
    		.icon(() -> new ItemStack(BTAItems.FLASHLIGHT.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
				output.accept(BTAItems.FLASHLIGHT.get());
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> ABYSS_ACCESSORIES = CREATIVE_MODE_TAB.register("abyss_accessories", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.abyss_accessories"))
    		.icon(() -> new ItemStack(BTAItems.HEMATHORN_AMULET.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
				output.accept(BTAItems.HEMATHORN_AMULET.get());
    		}).build());
}
