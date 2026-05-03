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
	public static final RegistryObject<SoundEvent> CORPSE_ANGLER_AMBIENT = registerSound("corpse_angler_ambient");
	public static final RegistryObject<SoundEvent> CORPSE_ANGLER_DEATH = registerSound("corpse_angler_death");
	public static final RegistryObject<SoundEvent> CORPSE_ANGLER_HURT = registerSound("corpse_angler_hurt");
	public static final RegistryObject<SoundEvent> GLOOMFISH_AMBIENT = registerSound("gloomfish_ambient");
	public static final RegistryObject<SoundEvent> GLOOMFISH_DEATH = registerSound("gloomfish_death");
	public static final RegistryObject<SoundEvent> GLOOMFISH_HURT = registerSound("gloomfish_hurt");
	public static final RegistryObject<SoundEvent> GNASHER_AMBIENT = registerSound("gnasher_ambient");
	public static final RegistryObject<SoundEvent> GNASHER_BITE = registerSound("gnasher_bite");
	public static final RegistryObject<SoundEvent> GNASHER_DEATH = registerSound("gnasher_death");
	public static final RegistryObject<SoundEvent> GNASHER_HURT = registerSound("gnasher_hurt");
	public static final RegistryObject<SoundEvent> MUTAVORE_AMBIENT = registerSound("mutavore_ambient");
	public static final RegistryObject<SoundEvent> MUTAVORE_BITE_LOOP = registerSound("mutavore_bite_loop");
	public static final RegistryObject<SoundEvent> MUTAVORE_BUBBLE_SPEW = registerSound("mutavore_bubble_spew");
	public static final RegistryObject<SoundEvent> MUTAVORE_CYST_EXPLODE = registerSound("mutavore_cyst_explode");
	public static final RegistryObject<SoundEvent> MUTAVORE_CYST_SHOOT = registerSound("mutavore_cyst_shoot");
	public static final RegistryObject<SoundEvent> MUTAVORE_DEATH = registerSound("mutavore_death");
	public static final RegistryObject<SoundEvent> MUTAVORE_HURT = registerSound("mutavore_hurt");
	public static final RegistryObject<SoundEvent> MUTAVORE_MOUTH_LAUNCH = registerSound("mutavore_mouth_launch");
	public static final RegistryObject<SoundEvent> MUTAVORE_MOUTH_OPEN_BUBBLE = registerSound("mutavore_mouth_open_bubble");
	public static final RegistryObject<SoundEvent> NECROSHELL_AMBIENT = registerSound("necroshell_ambient");
	public static final RegistryObject<SoundEvent> NECROSHELL_DEATH = registerSound("necroshell_death");
	public static final RegistryObject<SoundEvent> NECROSHELL_HURT = registerSound("necroshell_hurt");
	public static final RegistryObject<SoundEvent> NECROSHELL_INTIMIDATE_CLAW = registerSound("necroshell_intimidate_claw");
	public static final RegistryObject<SoundEvent> NECROSHELL_INTIMIDATE_CLAW_SOFTER = registerSound("necroshell_intimidate_claw_softer");
	public static final RegistryObject<SoundEvent> NECROSHELL_INTIMIDATE_HISS = registerSound("necroshell_intimidate_hiss");
	public static final RegistryObject<SoundEvent> SPINEWORM_AMBIENT = registerSound("spineworm_ambient");
	public static final RegistryObject<SoundEvent> SPINEWORM_DEATH = registerSound("spineworm_death");
	public static final RegistryObject<SoundEvent> SPINEWORM_HURT = registerSound("spineworm_hurt");
	
	public static RegistryObject<SoundEvent> registerSound(String name) 
	{
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name)));
    }
}
