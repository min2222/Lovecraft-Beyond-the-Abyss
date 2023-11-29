package com.min01.beyondtheabyss.config;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;

public class BTAConfig 
{
    private static ForgeConfigSpec.Builder builder;
    public static ForgeConfigSpec config;

	public static ForgeConfigSpec.BooleanValue cameraShakesAllowed;
    
    public static void loadConfig(ForgeConfigSpec config, String path) 
    {
        CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
    
    static 
    {
    	builder = new ForgeConfigSpec.Builder();
    	BTAConfig.init(BTAConfig.builder);
        config = BTAConfig.builder.build();
    }
	
    public static void init(ForgeConfigSpec.Builder config) 
    {
    	config.push("Client Settings");
    	BTAConfig.cameraShakesAllowed = config.comment("Setting this to false will disable camera shakes.").define("cameraShakesAllowed", true);
        config.pop();
    }
}
