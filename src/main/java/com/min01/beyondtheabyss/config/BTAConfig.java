package com.min01.beyondtheabyss.config;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;

public class BTAConfig 
{
    private static ForgeConfigSpec.Builder BUILDER;
    public static ForgeConfigSpec CONFIG;

	public static ForgeConfigSpec.BooleanValue cameraShakesAllowed;
	public static ForgeConfigSpec.BooleanValue enableAbyssShader;
    
    public static void loadConfig(ForgeConfigSpec config, String path) 
    {
        CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
    
    static 
    {
    	BUILDER = new ForgeConfigSpec.Builder();
    	BTAConfig.init(BTAConfig.BUILDER);
    	CONFIG = BTAConfig.BUILDER.build();
    }
	
    public static void init(ForgeConfigSpec.Builder config) 
    {
    	config.push("Client Settings");
    	BTAConfig.cameraShakesAllowed = config.comment("Setting this to false will disable camera shakes.").define("cameraShakesAllowed", true);
    	BTAConfig.enableAbyssShader = config.comment("on/off green filter in deep abyss dimension").define("abyssShader", true);
        config.pop();
    }
}
