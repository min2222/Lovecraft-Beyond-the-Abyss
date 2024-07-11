package com.min01.beyondtheabyss.gui.overlay;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class HallucinationOverlay
{
	public static final List<Eyes> EYES = new ArrayList<>();
	public static boolean ADD;
	public static float ALPHA = 0.5F;
	public static int TICK;
	public static int FRAME;
	
	public static void draw(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight)
	{
		Minecraft mc = BTAClientUtil.MC;
		Player player = mc.player;
		Level level = mc.level;
		if(player.hasEffect(BTAEffects.HALLUCINATION.get()))
		{
			TICK++;
			
			int tick = 1800;
			int interval = 150;

			if(TICK < tick && TICK % interval == 0 && FRAME < 3)
			{
				FRAME++;
			}
			
			if(TICK >= tick && TICK % interval == 0 && FRAME > 0)
			{
				FRAME--;
			}
			
			if(TICK >= tick && FRAME == 0)
			{
				ALPHA -= 0.001F;
			}
			
	        RenderSystem.setShader(GameRenderer::getPositionTexShader);
	        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, ALPHA);
	        RenderSystem.setShaderTexture(0, getTexture(level));
	        int posX = FRAME >= 3 ? level.random.nextInt(38, 42) : 40;
	        int posY = FRAME >= 3 ? level.random.nextInt(30, 34) : 32;
			GuiComponent.blit(poseStack, screenWidth / 2 - posX, screenHeight / 2 - posY, 0, 0, 80, 64, 80, 64);
			
			if(ADD)
			{
				/*for(int i = 0; i < 10; i++)
				{
					Random random = new Random();
					Eyes eyes = new Eyes(level.random.nextInt(screenWidth), level.random.nextInt(screenHeight), 3, level.random.nextInt(3) + 1, random.nextFloat(0.001F, 0.005F));
					EYES.add(eyes);
				}*/
				ADD = false;
			}
			else if(EYES.isEmpty() && !mc.isPaused())
			{
				//ALPHA -= 0.001F;
			}
			
			EYES.forEach(t -> 
			{
				if(!mc.isPaused())
				{
					t.tick();
				}
				t.draw(poseStack, screenWidth, screenHeight);
			});
			
			EYES.removeIf(t -> t.remove);
		}
		else
		{
			reset();
		}
	}
	
	public static void reset()
	{
		ALPHA = 0.5F;
		TICK = 0;
		FRAME = 0;
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
		        RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondtheAbyss.MODID, "textures/effect/small_eye_1.png"));
				GuiComponent.blit(stack, (screenWidth - this.posX) - 9 * this.size / 2, (screenHeight - this.posY) - 6 * this.size / 2, 0, 0, 9 * this.size, 6 * this.size, 9 * this.size, 6 * this.size);
			}
			else if(this.number == 2)
			{
		        RenderSystem.setShader(GameRenderer::getPositionTexShader);
		        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		        RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondtheAbyss.MODID, "textures/effect/small_eye_2.png"));
				GuiComponent.blit(stack, (screenWidth - this.posX) - 9 * this.size / 2, (screenHeight - this.posY) - 9 * this.size / 2, 0, 0, 9 * this.size, 9 * this.size, 9 * this.size, 9 * this.size);
			}
			else if(this.number == 3)
			{
		        RenderSystem.setShader(GameRenderer::getPositionTexShader);
		        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		        RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondtheAbyss.MODID, "textures/effect/small_eye_3.png"));
				GuiComponent.blit(stack, (screenWidth - this.posX) - 17 * this.size / 2, (screenHeight - this.posY) - 7 * this.size / 2, 0, 0, 17 * this.size, 7 * this.size, 17 * this.size, 7 * this.size);
			}
		}
	}
	
	public static ResourceLocation getTexture(Level level)
	{
		return new ResourceLocation(String.format("%s:textures/effect/hallucination_eye%d.png", BeyondtheAbyss.MODID, FRAME));
	}
}
