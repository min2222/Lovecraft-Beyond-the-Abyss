package com.min01.beyondtheabyss.item.armor;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.item.model.ModelFelmetalDiverSet;
import com.min01.beyondtheabyss.misc.BTAArmorMaterials;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class FelmetalDivingSetItem extends AbstractDivingSetItem
{
	public FelmetalDivingSetItem(ArmorItem.Type type)
	{
		super(BTAArmorMaterials.DIVING_SET, type, 0.2F);
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) 
	{
		consumer.accept(new IClientItemExtensions()
		{
			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) 
			{
				ModelFelmetalDiverSet<?> diverModel = new ModelFelmetalDiverSet<>(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelFelmetalDiverSet.LAYER_LOCATION));
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
	public int getMaxOxygen() 
	{
		//10 minutes;
		return 12000;
	}
	
	@Override
	public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) 
	{
		return "beyondtheabyss:textures/armor/felmetal_diver_set.png";
	}
}
