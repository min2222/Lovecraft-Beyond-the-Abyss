package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.ArmorAbilityCapabilityHandler.AbyssArmorAbilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

public interface IArmorAbilityCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = new ResourceLocation(BeyondtheAbyss.MODID, "cap_armor_ability");

	void setEntity(LivingEntity entity);

	void update();
	
	void setAbility(AbyssArmorAbilities ability);
	
	AbyssArmorAbilities getAbility();
}
