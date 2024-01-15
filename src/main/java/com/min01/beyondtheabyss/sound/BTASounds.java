package com.min01.beyondtheabyss.sound;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BTASounds
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BeyondtheAbyss.MODID);
	
	public static final RegistryObject<SoundEvent> GHIDRUTH_HURT = registerSound("ghidruth_hurt");
	public static final RegistryObject<SoundEvent> GHIDRUTH_AMBIENT = registerSound("ghidruth_ambient");
	
	private static RegistryObject<SoundEvent> registerSound(String name) 
	{
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(BeyondtheAbyss.MODID, name)));
    }
}
