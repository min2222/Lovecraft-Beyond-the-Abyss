package com.min01.beyondtheabyss.misc;

import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class BTAArmorMaterials
{
	public static final ArmorMaterial COPPER_DIVING_SET = new BTAArmorMaterial("copper_diving_set", new int[]{195, 225, 240, 165}, new int[]{2, 5, 6, 2}, 10, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.1F, () -> Ingredient.of(Items.COPPER_INGOT));
	public static final ArmorMaterial FELMETAL_DIVING_SET = new BTAArmorMaterial("felmetal_diving_set", new int[]{430, 495, 530, 365}, new int[]{3, 6, 7, 3}, 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0F, 0.0F, () -> Ingredient.of(Items.IRON_INGOT));
	public static final ArmorMaterial GHIDRUTH_DIVING_SET = new BTAArmorMaterial("ghidruth_diving_set", new int[]{550, 650, 750, 450}, new int[]{7, 8, 10, 7}, 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.0F, 0.3F, () -> Ingredient.of(BTAItems.GHIDRUTH_SCALE.get()));
}
