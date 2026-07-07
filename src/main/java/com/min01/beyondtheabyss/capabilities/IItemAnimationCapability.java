package com.min01.beyondtheabyss.capabilities;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

@AutoRegisterCapability
public interface IItemAnimationCapability extends ICapabilitySerializable<CompoundTag>
{
	ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "item_animation");

    long getInstanceId();
    
    void setInstanceId(long id);
    
    int getAnimationState();
    
    void setAnimationState(int state);
    
    int getAnimationTick();
    
    void setAnimationTick(int tick);
    
    void sync(int animationState, int animationTick);
    
    void tick();
}
