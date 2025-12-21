package com.min01.beyondtheabyss.gui.screen;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.IDialogue;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class DialogueScreen extends Screen
{
	public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BeyondtheAbyss.MODID, "textures/gui/dialogue.png");
	
	public final String key;
	public final int maxIndex;
	public final IDialogue entity;
	
	public int textIndex = 0;
	public int chatIndex = 0;
	public long lastUpdate = 0;
	
	public DialogueScreen(String key, int maxIndex, Entity entity)
	{
		super(Component.literal("Dialogue"));
		this.key = key;
		this.maxIndex = maxIndex;
		this.entity = (IDialogue) entity;
	}
	
	@Override
	protected void init() 
	{
        int buttonX = (this.width / 2) - (22 / 2);
        int buttonY = (this.height / 2) - (16 / 2);
		if(this.entity != null)
		{
			this.chatIndex = this.entity.getChatIndex();
		}
		this.addRenderableWidget(new ArrowButton(new Button.Builder(Component.literal("Arrow"), p_93751_ -> 
		{
    		String text = Component.translatable("message.beyondtheabyss." + this.key + this.chatIndex).getString();
        	if(this.chatIndex < this.maxIndex)
        	{
        		if(this.entity != null)
        		{
        			int prev = this.entity.getPrevChatIndex();
        			if(this.chatIndex != prev)
        			{
                    	this.entity.trigger(this.chatIndex);
                    	this.entity.setPrevChatIndex(this.chatIndex);
        			}
        		}
        		if(this.textIndex < text.length())
        		{
            		this.textIndex = text.length();
        		}
        		else
        		{
                	this.chatIndex++;
                	this.textIndex = 0;
        		}
        	}
        	else if(this.textIndex < text.length())
        	{
        		this.textIndex = text.length();
        	}
		}).bounds(buttonX + 72, buttonY + 72, 22, 16)));
	}
	
	@Override
	public void onClose() 
	{
		super.onClose();
		if(this.entity != null)
		{
			this.entity.onClose(this.chatIndex);
			this.entity.setChatIndex(this.chatIndex);
		}
	}
	
	@Override
	public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
	{
	    int textureWidth = 176;
	    int textureHeight = 94;
	    int blitX = (this.width / 2) - (textureWidth / 2);
	    int blitY = (this.height / 2) - (textureHeight / 10);
	    pGuiGraphics.blit(TEXTURE, blitX, blitY, 0, 0, textureWidth, textureHeight);
	    int textX = blitX + 10;
	    int textY = blitY + 10;
	    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
	    String text = Component.translatable("message.beyondtheabyss." + this.key + this.chatIndex).getString();
	    long currentTime = Util.getMillis();
	    if(currentTime - this.lastUpdate >= 100)
	    {
	        this.lastUpdate = currentTime;
	        if(this.textIndex < text.length())
	        {
	            this.textIndex++;
	        }
	    }
	    this.textIndex = Mth.clamp(this.textIndex, 0, text.length());
	    String sub = text.substring(0, this.textIndex);
	    pGuiGraphics.drawWordWrap(this.font, Component.literal(sub).withStyle(ChatFormatting.BLACK), textX, textY, 160, -1);
	}
	
	@Override
	public boolean isPauseScreen() 
	{
		return false;
	}
	
	public static class ArrowButton extends Button
	{
		public ArrowButton(Builder builder)
		{
			super(builder);
		}
		
		@Override
		protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
		{
		    int textureWidth = this.isHovered ? 23 : 22;
		    int textureHeight = this.isHovered ? 16 : 15;
		    
		    int blitX = this.getX() + (this.width / 2) - (textureWidth / 2);
		    int blitY = this.getY() + (this.height / 2) - (textureHeight / 2);

		    if(this.isHovered)
		    {
		    	pGuiGraphics.blit(TEXTURE, blitX, blitY, 177, 73, 23, 16);
		    }
		    else
		    {
		    	pGuiGraphics.blit(TEXTURE, blitX, blitY, 149, 74, 22, 15);
		    }
		}
	}
}
