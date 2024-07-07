package com.min01.beyondtheabyss.gui.overlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
			
			if(player.tickCount % 30 == 0 && !BTAClientUtil.MC.isPaused())
			{
				Random random = new Random();
				Eyes eyes = new Eyes(level.random.nextInt(screenWidth), level.random.nextInt(screenHeight), 3, level.random.nextInt(3) + 1, random.nextFloat(0.001F, 0.01F));
				EYES.add(eyes);
			}
			
			EYES.forEach(t -> 
			{
				if(!BTAClientUtil.MC.isPaused())
				{
					t.tick();
				}
				t.draw(poseStack, screenWidth, screenHeight);
			});
			
			EYES.removeIf(t -> t.remove);
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
		public float alpha;
		public float amount;
		public boolean decrease;
		public boolean remove;
		
		public Eyes(int posX, int posY, int size, int number, float amount) 
		{
			this.posX = posX;
			this.posY = posY;
			this.size = size;
			this.number = number;
			this.amount = amount;
		}
		
		public void tick()
		{
			if(this.alpha <= 1.0F && !this.decrease)
			{
				this.alpha += this.amount;
			}
			
			if(this.alpha >= 1.0F)
			{
				this.decrease = true;
			}
			
			if(this.decrease && this.alpha > 0.0F)
			{
				this.alpha -= this.amount;
			}
			
			if(this.decrease && this.alpha <= 0.0F)
			{
				this.remove = true;
			}
		}
		
		public void draw(PoseStack stack, int screenWidth, int screenHeight)
		{
			if(this.number == 1)
			{
		        RenderSystem.setShader(GameRenderer::getPositionTexShader);
		        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		        RenderSystem.setShaderTexture(0, new ResourceLocation(String.format("%s:textures/effect/small_eye_%d.png", BeyondtheAbyss.MODID, 1)));
				GuiComponent.blit(stack, (screenWidth - this.posX) - 9 * this.size / 2, (screenHeight - this.posY) - 6 * this.size / 2, 0, 0, 9 * this.size, 6 * this.size, 9 * this.size, 6 * this.size);
			}
			else if(this.number == 2)
			{
		        RenderSystem.setShader(GameRenderer::getPositionTexShader);
		        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		        RenderSystem.setShaderTexture(0, new ResourceLocation(String.format("%s:textures/effect/small_eye_%d.png", BeyondtheAbyss.MODID, 2)));
				GuiComponent.blit(stack, (screenWidth - this.posX) - 9 * this.size / 2, (screenHeight - this.posY) - 9 * this.size / 2, 0, 0, 9 * this.size, 9 * this.size, 9 * this.size, 9 * this.size);
			}
			else if(this.number == 3)
			{
		        RenderSystem.setShader(GameRenderer::getPositionTexShader);
		        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		        RenderSystem.setShaderTexture(0, new ResourceLocation(String.format("%s:textures/effect/small_eye_%d.png", BeyondtheAbyss.MODID, 3)));
				GuiComponent.blit(stack, (screenWidth - this.posX) - 17 * this.size / 2, (screenHeight - this.posY) - 7 * this.size / 2, 0, 0, 17 * this.size, 7 * this.size, 17 * this.size, 7 * this.size);
			}
		}
	}
	
	public static ResourceLocation getTexture(Level level)
	{
		int frame = BTAClientUtil.getCurrentFrame(level, 4);
		return new ResourceLocation(String.format("%s:textures/effect/hallucination_eye%d.png", BeyondtheAbyss.MODID, frame));
	}
}
