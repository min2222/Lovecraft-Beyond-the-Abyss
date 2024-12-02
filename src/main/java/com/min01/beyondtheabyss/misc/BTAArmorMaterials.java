package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BTAArmorMaterials
{
	public static final ArmorMaterial DIVING_SET = new BTAArmorMaterial("diving_set", new int[]{150, 200, 300, 100}, new int[]{2, 4, 6, 2}, 10, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(Items.IRON_INGOT));
	public static final ArmorMaterial ADVANCED_DIVING_SET = new BTAArmorMaterial("advanced_diving_set", new int[]{350, 450, 550, 250}, new int[]{4, 5, 7, 4}, 15, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, () -> Ingredient.of(Items.IRON_INGOT));
	public static final ArmorMaterial GHIDRUTH_DIVING_SET = new BTAArmorMaterial("ghidruth_diving_set", new int[]{550, 650, 750, 450}, new int[]{7, 8, 10, 7}, 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.0F, 0.3F, () -> Ingredient.of(BTAItems.GHIDRUTH_SCALE.get()));
}
