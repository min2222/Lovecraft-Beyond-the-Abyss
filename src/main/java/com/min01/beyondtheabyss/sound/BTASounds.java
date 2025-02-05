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
	public static final RegistryObject<SoundEvent> GHIDRUTH_STUN = registerSound("ghidruth_stun");
	public static final RegistryObject<SoundEvent> GHIDRUTH_EYE_FLASH = registerSound("ghidruth_eye_flash");
	public static final RegistryObject<SoundEvent> GHIDRUTH_CHARGE_START = registerSound("ghidruth_charge_start");
	public static final RegistryObject<SoundEvent> GHIDRUTH_CHARGE_LOOP = registerSound("ghidruth_charge_loop");
	public static final RegistryObject<SoundEvent> GHIDRUTH_BITE = registerSound("ghidruth_bite");
	public static final RegistryObject<SoundEvent> GHIDRUTH_AWAKEN = registerSound("ghidruth_awaken");
	public static final RegistryObject<SoundEvent> ABYSS_PORTAL_OPENING = registerSound("abyss_portal_opening");
	public static final RegistryObject<SoundEvent> RAILGUNBLADE_BLADE_TO_GUN = registerSound("railgunblade_blade_to_gun");
	public static final RegistryObject<SoundEvent> RAILGUNBLADE_GUN_TO_BLADE = registerSound("railgunblade_gun_to_blade");
	
	private static RegistryObject<SoundEvent> registerSound(String name) 
	{
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(BeyondtheAbyss.MODID, name)));
    }
}
