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
	
	public static final RegistryObject<SoundEvent> GHIDRUTH_HURT = registerFixedSound("ghidruth_hurt", 10.0F);
	public static final RegistryObject<SoundEvent> GHIDRUTH_AMBIENT = registerFixedSound("ghidruth_ambient", 30.0F);
	public static final RegistryObject<SoundEvent> GHIDRUTH_STUN = registerFixedSound("ghidruth_stun", 30.0F);
	public static final RegistryObject<SoundEvent> GHIDRUTH_EYE_FLASH = registerFixedSound("ghidruth_eye_flash", 30.0F);
	public static final RegistryObject<SoundEvent> GHIDRUTH_CHARGE_START = registerFixedSound("ghidruth_charge_start", 30.0F);
	public static final RegistryObject<SoundEvent> GHIDRUTH_CHARGE_LOOP = registerFixedSound("ghidruth_charge_loop", 30.0F);
	public static final RegistryObject<SoundEvent> GHIDRUTH_BITE = registerFixedSound("ghidruth_bite", 10.0F);
	public static final RegistryObject<SoundEvent> GHIDRUTH_AWAKEN = registerFixedSound("ghidruth_awaken", 30.0F);
	public static final RegistryObject<SoundEvent> GUNBLADE_BLADE_TO_GUN = registerSound("gunblade_blade_to_gun");
	public static final RegistryObject<SoundEvent> GUNBLADE_GUN_TO_BLADE = registerSound("gunblade_gun_to_blade");
	public static final RegistryObject<SoundEvent> GUNBLADE_SWING = registerSound("gunblade_swing");
	public static final RegistryObject<SoundEvent> GUNBLADE_CHARGE = registerSound("gunblade_charge");
	public static final RegistryObject<SoundEvent> SIAMSERPENT_AMBIENT = registerFixedSound("siamserpent_ambient", 10.0F);
	public static final RegistryObject<SoundEvent> SIAMSERPENT_HURT = registerSound("siamserpent_hurt");
	public static final RegistryObject<SoundEvent> SIAMSERPENT_DEATH = registerSound("siamserpent_death");
	public static final RegistryObject<SoundEvent> SIAMSERPENT_BEAM_CHARGE = registerFixedSound("siamserpent_beam_charge", 15.0F);
	public static final RegistryObject<SoundEvent> SIAMSERPENT_BEAM_SHOOT = registerFixedSound("siamserpent_beam_shoot", 15.0F);
	
	public static RegistryObject<SoundEvent> registerFixedSound(String name, float range) 
	{
		return SOUNDS.register(name, () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name), 16.0F * range));
    }
	
	public static RegistryObject<SoundEvent> registerSound(String name) 
	{
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, name)));
    }
}
