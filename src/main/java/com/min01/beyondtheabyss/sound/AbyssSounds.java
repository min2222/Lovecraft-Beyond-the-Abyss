package com.min01.beyondtheabyss.sound;

import com.min01.beyondtheabyss.BeyondtheAbyss;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AbyssSounds
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BeyondtheAbyss.MODID);
	
	/*private static RegistryObject<SoundEvent> registerSound(String name) 
	{
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(BeyondtheAbyss.MODID, name)));
    }*/
}
