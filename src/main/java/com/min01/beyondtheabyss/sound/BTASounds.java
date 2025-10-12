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
	public static final RegistryObject<SoundEvent> GUNBLADE_BLADE_TO_GUN = registerSound("gunblade_blade_to_gun");
	public static final RegistryObject<SoundEvent> GUNBLADE_GUN_TO_BLADE = registerSound("gunblade_gun_to_blade");
	public static final RegistryObject<SoundEvent> GUNBLADE_SWING = registerSound("gunblade_swing");
	public static final RegistryObject<SoundEvent> GUNBLADE_CHARGE = registerSound("gunblade_charge");
	public static final RegistryObject<SoundEvent> SIAMSERPENT_AMBIENT = registerSound("siamserpent_ambient");
	public static final RegistryObject<SoundEvent> SIAMSERPENT_HURT = registerSound("siamserpent_hurt");
	public static final RegistryObject<SoundEvent> SIAMSERPENT_DEATH = registerSound("siamserpent_death");
	public static final RegistryObject<SoundEvent> SIAMSERPENT_BEAM_CHARGE = registerSound("siamserpent_beam_charge");
	public static final RegistryObject<SoundEvent> SIAMSERPENT_BEAM_SHOOT = registerSound("siamserpent_beam_shoot");
	
	private static RegistryObject<SoundEvent> registerSound(String name) 
	{
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BeyondtheAbyss.MODID, name)));
    }
}
