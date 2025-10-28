package com.min01.beyondtheabyss.enchantment;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAEnchantments 
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BeyondtheAbyss.MODID);
	
	public static final EnchantmentCategory TOOTH_SHOTGUN = EnchantmentCategory.create("tooth_shotgun", (item -> item == BTAItems.TOOTH_SHOTGUN.get()));
	
	public static final RegistryObject<Enchantment> GOLDEN_TOOTH = ENCHANTMENTS.register("golden_tooth", () -> new GoldenToothEnchantment());
	public static final RegistryObject<Enchantment> BRITTLE = ENCHANTMENTS.register("brittle", () -> new BrittleEnchantment());
	public static final RegistryObject<Enchantment> FRACTURE = ENCHANTMENTS.register("fracture", () -> new FractureEnchantment());
	
	public static void addAllEnchantsToCreativeTab(CreativeModeTab.Output output, EnchantmentCategory enchantmentCategory)
	{
		for(RegistryObject<Enchantment> enchantObject : ENCHANTMENTS.getEntries())
		{
			if(enchantObject.isPresent())
			{
				Enchantment enchant = enchantObject.get();
				if(enchant.category == enchantmentCategory)
				{
					EnchantmentInstance instance = new EnchantmentInstance(enchant, enchant.getMaxLevel());
					output.accept(EnchantedBookItem.createForEnchantment(instance));
				}
			}
		}
	}
}
