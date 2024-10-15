package com.min01.beyondtheabyss.gui.overlay;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.item.armor.AbstractDivingSetItem;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class OxygenOverlay 
{
	public static void draw(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight)
	{
		Minecraft mc = BTAClientUtil.MC;
		Player player = mc.player;
		ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
		if(!stack.isEmpty())
		{
			if(stack.getItem() instanceof AbstractDivingSetItem item)
			{
				int oxygen = AbstractDivingSetItem.getOxygen(stack);
				float maxOxygen = item.getMaxOxygen();
				int progress = (int) (64.0F - oxygen / maxOxygen * 64.0F);
				int progress2 = (int) (64.0F - progress);
				int posX = 100;
				int posY = 45;
		        RenderSystem.setShader(GameRenderer::getPositionTexShader);
		        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		        RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondtheAbyss.MODID, "textures/gui/oxygen_tank.png"));
				GuiComponent.blit(poseStack, screenWidth / 2 + posX + 32, screenHeight / 2 + posY + 64, 0, 0, -32, -progress2, 32, 64);
		        RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 1.0F);
		        RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondtheAbyss.MODID, "textures/gui/anti_oxygen_tank.png"));
				GuiComponent.blit(poseStack, screenWidth / 2 + posX, screenHeight / 2 + posY, 0, 0, 32, progress, 32, 64);
			}
		}
	}
}
