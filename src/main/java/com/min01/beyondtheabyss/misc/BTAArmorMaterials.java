package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class BTAArmorMaterials
{
	public static final ArmorMaterial DIVING_SET = new BTAArmorMaterial("diving_set", new int[]{450, 550, 500, 250}, new int[]{3, 4, 5, 2}, 10, SoundEvents.ARMOR_EQUIP_ELYTRA, 0, 0, () -> Ingredient.EMPTY);
	public static final ArmorMaterial ADVANCED_DIVING_SET = new BTAArmorMaterial("advanced_diving_set", new int[]{650, 750, 700, 450}, new int[]{4, 5, 6, 3}, 15, SoundEvents.ARMOR_EQUIP_ELYTRA, 1, 0, () -> Ingredient.EMPTY);
	public static final ArmorMaterial GHIDRUTH_DIVING_SET = new BTAArmorMaterial("ghidruth_diving_set", new int[]{1650, 1750, 1700, 1450}, new int[]{14, 15, 16, 13}, 25, SoundEvents.ARMOR_EQUIP_NETHERITE, 15, 0.5F, () -> Ingredient.of(BTAItems.GHIDRUTH_SCALE.get()));
}
