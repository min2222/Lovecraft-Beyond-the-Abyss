package com.min01.beyondtheabyss.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class BTAConfig 
{
	public static final BTAConfig CONFIG;
	public static final ForgeConfigSpec CONFIG_SPEC;

	public static ForgeConfigSpec.BooleanValue cameraShakes;
	public static ForgeConfigSpec.BooleanValue worldShaders;
	public static ForgeConfigSpec.BooleanValue enableDragonRevive;
    
    static 
    {
    	Pair<BTAConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(BTAConfig::new);
    	CONFIG = pair.getLeft();
    	CONFIG_SPEC = pair.getRight();
    }
	
    public BTAConfig(ForgeConfigSpec.Builder config) 
    {
    	config.push("Client Settings");
    	cameraShakes = config.comment("whether camera shaking effects should be enabled in various situations.").define("cameraShakes", true);
    	worldShaders = config.comment("disable/enable shader effect in specific dimensions").define("worldShaders", true);
        config.pop();
        
    	config.push("Common Settings");
    	enableDragonRevive = config.comment("display message after kill dragon even if dragon is revived").define("enableDragonRevive", false);
        config.pop();
    }
}
