package com.min01.beyondtheabyss.capabilities;

import java.util.Map;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.BTAAbilitiesCapabilityHandler.BTAAbilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

public interface IBTAAbilitiesCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = new ResourceLocation(BeyondtheAbyss.MODID, "cap_bta_ability");

	void setEntity(LivingEntity entity);

	void update();
	
	void addAbility(BTAAbilities ability);
	
	void removeAbility(BTAAbilities ability);
	
	void setTickcount(BTAAbilities ability, int tickCount);
	
	int getTickcount(BTAAbilities ability);
	
	Map<BTAAbilities, Integer> getAbilities();
}
