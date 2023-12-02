package com.min01.beyondtheabyss.item.armor;

import java.util.UUID;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.model.ModelDiverSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;

public class ItemDiverSet extends ArmorItem
{
	public static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	public Multimap<Attribute, AttributeModifier> modifers;
	
	public ItemDiverSet(ArmorMaterial p_40386_, EquipmentSlot p_40387_)
	{
		super(p_40386_, p_40387_, new Item.Properties().tab(BeyondtheAbyss.ABYSS_ARMORS));
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[p_40387_.getIndex()];
		builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double)p_40386_.getDefenseForSlot(p_40387_), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double)p_40386_.getToughness(), AttributeModifier.Operation.ADDITION));
		if (p_40386_.getKnockbackResistance() > 0)
		{
			builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double)p_40386_.getKnockbackResistance(), AttributeModifier.Operation.ADDITION));
		}
		builder.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.randomUUID(), "Swim speed", 0.5D, AttributeModifier.Operation.ADDITION));
		this.modifers = builder.build();
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) 
	{
		return slot == EquipmentSlot.FEET && stack.getItem() == BTAItems.DIVING_BOOTS.get() ? this.modifers : super.getAttributeModifiers(slot, stack);
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) 
	{
		consumer.accept(new IClientItemExtensions()
		{
			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) 
			{
				ModelDiverSet<?> diverModel = new ModelDiverSet<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelDiverSet.LAYER_LOCATION));
				diverModel.Head.visible = equipmentSlot == EquipmentSlot.HEAD;
				diverModel.Body.visible = equipmentSlot == EquipmentSlot.CHEST;
				diverModel.LeftArm.visible = equipmentSlot == EquipmentSlot.CHEST;
				diverModel.RightArm.visible = equipmentSlot == EquipmentSlot.CHEST;
				diverModel.LeftLeg.visible = equipmentSlot == EquipmentSlot.LEGS;
				diverModel.RightLeg.visible = equipmentSlot == EquipmentSlot.LEGS;
				diverModel.LeftFeet.visible = equipmentSlot == EquipmentSlot.FEET;
				diverModel.RightFeet.visible = equipmentSlot == EquipmentSlot.FEET;
				return diverModel;
			}
		});
	}
	
	@Override
	public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) 
	{
		return "beyondtheabyss:textures/models/armor/diver_set.png";
	}
	
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player)
	{
		if(player.getItemBySlot(EquipmentSlot.HEAD).getItem() == BTAItems.DIVING_HELMET.get())
		{
			if(player.isEyeInFluidType(Fluids.WATER.getFluidType()))
			{
				player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false));
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20, 0, false, false));
			}
		}
	}
}
