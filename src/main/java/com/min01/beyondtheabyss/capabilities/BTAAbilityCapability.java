package com.min01.beyondtheabyss.capabilities;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTAAbilityImpl.BTAAbility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface BTAAbilityCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = new ResourceLocation(BeyondtheAbyss.MODID, "bta_ability");

	void setEntity(LivingEntity entity);

	void update();
	
	void addAbility(BTAAbility ability);
	
	void removeAbility(BTAAbility ability);
	
	void setTickCount(BTAAbility ability, int tickCount);
	
	int getTickCount(BTAAbility ability);
	
	List<BTAAbility> getAbilities();
}
