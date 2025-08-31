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
    
    public static final RegistryObject<CreativeModeTab> DEEP_ABYSS = CREATIVE_MODE_TAB.register("deep_abyss", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.deep_abyss"))
    		.icon(() -> new ItemStack(BTAItems.HEART_OF_FORNEUS.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : BTAItems.DEEP_ABYSS_ITEMS.getEntries())
    			{
      				if(item.get() instanceof ForgeSpawnEggItem && item != BTAItems.SOLOMON_SPAWN_EGG)
    				{
    					output.accept(item.get());
    				}
    				if(item.get() instanceof BasicBTAFoodItem)
    				{
    					output.accept(item.get());
    				}
    			}
				output.accept(BTAItems.GUIDING_CLAM.get());
				output.accept(BTAItems.HEART_OF_FORNEUS.get());
				output.accept(BTAItems.FLYING_FISH.get());
				output.accept(BTAItems.GHIDRUTH_SCALE.get());
				output.accept(BTAItems.GNASHER_EYE.get());
				output.accept(BTAItems.GNASHER_TOOTH.get());
				output.accept(BTAItems.GHOUL_BLOOM_SEED_POD.get());
				output.accept(BTAItems.FIBER.get());
				output.accept(BTAItems.ABERRANT_FLESH.get());
				output.accept(BTAItems.CHARGE_CORE.get());
				output.accept(BTAItems.SPLITTING_GEL.get());
				output.accept(BTAItems.SPINE_WORM_MANDIBLE.get());
				output.accept(BTAItems.SLASHER_BLADE.get());
				output.accept(BTAItems.BLASTER_SKULL.get());
				output.accept(BTAItems.SERPENT_HEART.get());
				output.accept(BTAItems.RUSTY_HARPOON.get());
				output.accept(BTAItems.GHIDRUTH_SCALE_HARPOON.get());
				output.accept(BTAItems.SKELETAL_GUNBLADE.get());
				output.accept(BTAItems.TOOTH_SHOTGUN.get());
				output.accept(BTAItems.FLASHLIGHT.get());
    			for(RegistryObject<Item> item : BTAItems.DEEP_ABYSS_ITEMS.getEntries())
    			{
    				if(item.get() instanceof ArmorItem)
    				{
    					output.accept(item.get());
    				}
    				if(item.get() instanceof BlockItem && !(item.get() instanceof ItemNameBlockItem))
    				{
    					output.accept(item.get());
    				}
    			}
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> MIRRORED_CITY = CREATIVE_MODE_TAB.register("mirrored_city", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.mirrored_city"))
    		.icon(() -> new ItemStack(BTAItems.OVERSEER_SPAWN_EGG.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : BTAItems.MIRRORED_CITY_ITEMS.getEntries())
    			{
					output.accept(item.get());
    			}
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> MOON = CREATIVE_MODE_TAB.register("moon", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.moon"))
    		.icon(() -> new ItemStack(BTAItems.MOONSTONE.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : BTAItems.MOON_ITEMS.getEntries())
    			{
					output.accept(item.get());
    			}
    		}).build());
    
    public static final RegistryObject<CreativeModeTab> PURGATORY = CREATIVE_MODE_TAB.register("purgatory", () -> CreativeModeTab.builder()
    		.title(Component.translatable("itemGroup.purgatory"))
    		.icon(() -> new ItemStack(BTAItems.MOLTEN_STONE.get()))
    		.displayItems((enabledFeatures, output) -> 
    		{
    			for(RegistryObject<Item> item : BTAItems.PURGATORY_ITEMS.getEntries())
    			{
					output.accept(item.get());
    			}
    		}).build());
}