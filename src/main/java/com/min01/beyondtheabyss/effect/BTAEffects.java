package com.min01.beyondtheabyss.effect;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.deepabyss.CoordinationEffect;
import com.min01.beyondtheabyss.effect.deepabyss.DisorderEffect;
import com.min01.beyondtheabyss.effect.deepabyss.StoneSkinEffect;
import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTAEffects
{
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BeyondtheAbyss.MODID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<MobEffect> AIR_SWIM = MOB_EFFECTS.register("air_swim", () -> new BasicBTAEffect(MobEffectCategory.BENEFICIAL, 3407871));
	public static final RegistryObject<MobEffect> DISORDER = MOB_EFFECTS.register("disorder", () -> new DisorderEffect());
	public static final RegistryObject<MobEffect> COORDINATION = MOB_EFFECTS.register("coordination", () -> new CoordinationEffect());
	public static final RegistryObject<MobEffect> STONE_SKIN = MOB_EFFECTS.register("stone_skin", () -> new StoneSkinEffect());
	
	public static final RegistryObject<Potion> DISORDER_POTION = POTIONS.register("disorder", () -> new Potion(new MobEffectInstance(DISORDER.get(), 1)));
	public static final RegistryObject<Potion> STRONG_DISORDER_POTION = POTIONS.register("strong_disorder", () -> new Potion(new MobEffectInstance(DISORDER.get(), 1, 1)));
	
	public static final RegistryObject<Potion> COORDINATION_POTION = POTIONS.register("coordination", () -> new Potion(new MobEffectInstance(COORDINATION.get(), 3600)));
	public static final RegistryObject<Potion> STRONG_COORDINATION_POTION = POTIONS.register("strong_coordination", () -> new Potion(new MobEffectInstance(COORDINATION.get(), 1800, 1)));
	
	public static void init()
	{
		ItemStack awkward = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD);
		ItemStack disorder = PotionUtils.setPotion(new ItemStack(Items.POTION), BTAEffects.DISORDER_POTION.get());
		ItemStack strongDisorder = PotionUtils.setPotion(new ItemStack(Items.POTION), BTAEffects.STRONG_DISORDER_POTION.get());
		ItemStack coordination = PotionUtils.setPotion(new ItemStack(Items.POTION), BTAEffects.COORDINATION_POTION.get());
		ItemStack strongCoordination = PotionUtils.setPotion(new ItemStack(Items.POTION), BTAEffects.STRONG_COORDINATION_POTION.get());
		BrewingRecipeRegistry.addRecipe(Ingredient.of(awkward), Ingredient.of(BTAItems.GNASHER_EYE.get()), disorder);
		BrewingRecipeRegistry.addRecipe(Ingredient.of(disorder), Ingredient.of(Items.GLOWSTONE_DUST), strongDisorder);
		BrewingRecipeRegistry.addRecipe(Ingredient.of(disorder), Ingredient.of(Items.GLISTERING_MELON_SLICE), coordination);
		BrewingRecipeRegistry.addRecipe(Ingredient.of(coordination), Ingredient.of(Items.GLOWSTONE_DUST), strongCoordination);
	}
}
