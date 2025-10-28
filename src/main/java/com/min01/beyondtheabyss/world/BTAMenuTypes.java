package com.min01.beyondtheabyss.world;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.world.inventory.BiocrafterMenu;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAMenuTypes
{
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BeyondtheAbyss.MODID);
    
    public static final RegistryObject<MenuType<BiocrafterMenu>> BIOCRATER = MENU_TYPES.register("biocrafter", () -> new MenuType<>(BiocrafterMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
