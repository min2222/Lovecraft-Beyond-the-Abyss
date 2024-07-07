package com.min01.beyondtheabyss.gui.overlay;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class HallucinationOverlay
{
	public static final List<Eyes> EYES = new ArrayList<>();
	
	public static void draw(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight)
	{
		Player player = BTAClientUtil.MC.player;
		Level level = BTAClientUtil.MC.level;
		if(player.hasEffect(BTAEffects.HALLUCINATION.get()))
		{
	        RenderSystem.setShader(GameRenderer::getPositionTexShader);
	        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
	        RenderSystem.setShaderTexture(0, getTexture(level));
			GuiComponent.blit(poseStack, screenWidth / 2 - 32, screenHeight / 2 - 32, 0, 0, 64, 64, 64, 64);
			
			if(player.tickCount % 20 == 0)
			{
				Eyes eyes = new Eyes(level.random.nextInt(screenWidth), level.random.nextInt(screenHeight), 0, level.random.nextInt(3) + 1);
				EYES.add(eyes);
			}
			
			EYES.forEach(t -> 
			{
				t.tick();
				t.draw(poseStack, screenWidth, screenHeight);
			});
			EYES.removeIf(t -> t.alpha <= 0.0F);
		}
		else if(!EYES.isEmpty())
		{
			EYES.clear();
		}
	}
	
	public static class Eyes
	{
		public int posX;
		public int posY;
		public int size;
		public int number;
		public float alpha = 0.5F;
		
		public Eyes(int posX, int posY, int size, int number) 
		{
			this.posX = posX;
			this.posY = posY;
			this.size = size;
			this.number = number;
		}
		
		public void tick()
		{
			this.alpha -= 0.01F;
		}
		
		public void draw(PoseStack stack, int screenWidth, int screenHeight)
		{
	        RenderSystem.setShader(GameRenderer::getPositionTexShader);
	        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
	        RenderSystem.setShaderTexture(0, new ResourceLocation(String.format("%s:textures/effect/small_eye_%d.png", BeyondtheAbyss.MODID, this.number)));
			GuiComponent.blit(stack, (screenWidth - this.posX) - 16, (screenHeight - this.posY) - 16, 0, 0, 32, 32, 32, 32);
		}
	}
	
	public static ResourceLocation getTexture(Level level)
	{
		int frame = BTAClientUtil.getCurrentFrame(level, 4);
		return new ResourceLocation(String.format("%s:textures/effect/hallucination_eye%d.png", BeyondtheAbyss.MODID, frame));
	}
}
