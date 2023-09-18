package com.min01.beyondtheabyss.config;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;

public class AbyssConfig 
{
    private static ForgeConfigSpec.Builder builder;
    public static ForgeConfigSpec config;
    
    public static void loadConfig(ForgeConfigSpec config, String path) 
    {
        CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
    
    static 
    {
    	builder = new ForgeConfigSpec.Builder();
    	AbyssConfig.init(AbyssConfig.builder);
        config = AbyssConfig.builder.build();
    }
	
    public static void init(ForgeConfigSpec.Builder config) 
    {
    	
    }
}
