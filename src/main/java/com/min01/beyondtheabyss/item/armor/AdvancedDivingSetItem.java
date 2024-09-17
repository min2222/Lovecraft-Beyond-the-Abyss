package com.min01.beyondtheabyss.item.armor;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.item.model.ModelAdvancedDiverSet;
import com.min01.beyondtheabyss.misc.BTAArmorMaterials;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class AdvancedDivingSetItem extends AbstractDivingSetItem
{
	public AdvancedDivingSetItem(EquipmentSlot slot)
	{
		super(BTAArmorMaterials.ADVANCED_DIVING_SET, slot, 1.0F);
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) 
	{
		consumer.accept(new IClientItemExtensions()
		{
			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) 
			{
				ModelAdvancedDiverSet<?> diverModel = new ModelAdvancedDiverSet<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAdvancedDiverSet.LAYER_LOCATION));
				diverModel.Head.visible = equipmentSlot == EquipmentSlot.HEAD;
				diverModel.Body.visible = equipmentSlot == EquipmentSlot.CHEST;
				diverModel.LeftArm.visible = equipmentSlot == EquipmentSlot.CHEST;
				diverModel.RightArm.visible = equipmentSlot == EquipmentSlot.CHEST;
				diverModel.LeftLeg.visible = equipmentSlot == EquipmentSlot.LEGS;
				diverModel.RightLeg.visible = equipmentSlot == EquipmentSlot.LEGS;
				diverModel.Left_boots.visible = equipmentSlot == EquipmentSlot.FEET;
				diverModel.Right_boots.visible = equipmentSlot == EquipmentSlot.FEET;
				return diverModel;
			}
		});
	}
	
	@Override
	public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) 
	{
		return "beyondtheabyss:textures/models/armor/advanced_diver_set.png";
	}
}
