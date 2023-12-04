package com.min01.beyondtheabyss.misc;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class BTADamageSource extends EntityDamageSource
{
	public BTADamageSource(String p_19394_, Entity p_19395_)
	{
		super(p_19394_, p_19395_);
	}

	public static DamageSource causeHardFleshDamage(String string, Entity source)
	{
		return new BTADamageSource(string, source).bypassArmor();
	}
	
	public static DamageSource causeGhidruthFleshDamage(Entity source)
	{
		return causeHardFleshDamage("ghidruth_flesh", source);
	}
}
