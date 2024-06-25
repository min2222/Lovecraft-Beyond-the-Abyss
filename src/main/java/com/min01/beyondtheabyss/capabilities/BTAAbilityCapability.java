package com.min01.beyondtheabyss.capabilities;

import java.util.Map;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTAAbilityImpl.BTAAbilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

public interface BTAAbilityCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = new ResourceLocation(BeyondtheAbyss.MODID, "bta_ability");

	void setEntity(LivingEntity entity);

	void update();
	
	void addAbility(BTAAbilities ability);
	
	void removeAbility(BTAAbilities ability);
	
	void setTickCount(BTAAbilities ability, int tickCount);
	
	int getTickCount(BTAAbilities ability);
	
	Map<BTAAbilities, Integer> getAbilities();
}
