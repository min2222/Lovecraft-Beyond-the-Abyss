package com.min01.beyondtheabyss.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BTAConfig 
{
	public static final ForgeConfigSpec CONFIG_SPEC = build();

	public static ForgeConfigSpec.BooleanValue cameraShakes;
	public static ForgeConfigSpec.BooleanValue bossMusic;
	public static ForgeConfigSpec.BooleanValue worldShaders;
	
	public static ForgeConfigSpec.BooleanValue enableDragonRevive;
	
    public static ForgeConfigSpec build() 
    {
    	ForgeConfigSpec.Builder config = new ForgeConfigSpec.Builder();
    	
    	config.push("Client Settings");
    	cameraShakes = config.comment("whether camera shaking effects should be enabled in various situations.").define("cameraShakes", true);
    	bossMusic = config.comment("whether to play custom music during boss fights.").define("bossMusic", true);
    	worldShaders = config.comment("whether unique shader effects should be enabled in some dimensions.").define("worldShaders", true);
        config.pop();
        
    	config.push("Common Settings");
    	enableDragonRevive = config.comment("whether display message after kill dragon even if dragon is revived.").define("enableDragonRevive", false);
        config.pop();
        
        return config.build();
    }
}
