package com.min01.beyondtheabyss.gui.screen;

import com.min01.beyondtheabyss.entity.IDialogue;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.lwjgl.glfw.GLFW;

public class DialogueScreen extends Screen
{
	private static final int MARGIN = 20;
	private static final int TEXT_COLOR = 0xFFFFFF;
	private static final int NEXT_COLOR = 0xFFFFFF;
	private static final int NEXT_HOVER_COLOR = 0xFFFF00;

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
		if(this.entity != null)
		{
			this.chatIndex = this.entity.getChatIndex();
		}
		Component nextLabel = Component.translatable("gui.beyondtheabyss.dialogue.next");
		int buttonWidth = this.font.width(nextLabel) + 4;
		int buttonHeight = 9;
		int buttonX = this.width - buttonWidth - MARGIN;
		int buttonY = this.height - MARGIN - buttonHeight;
		this.addRenderableWidget(new NextButton(buttonX, buttonY, buttonWidth, buttonHeight, nextLabel, pButton -> this.advanceDialogue()));
	}

	private void advanceDialogue()
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
	}

	private int getOverlayHeight()
	{
		return this.height / 3;
	}

	private void renderBottomVignette(GuiGraphics guiGraphics)
	{
		int overlayHeight = this.getOverlayHeight();
		int topY = this.height - overlayHeight;
		int bandHeight = overlayHeight / 4;
		guiGraphics.fillGradient(0, topY, this.width, topY + bandHeight, 0x00000000, 0x50000000);
		topY += bandHeight;
		guiGraphics.fillGradient(0, topY, this.width, topY + bandHeight, 0x50000000, 0x90000000);
		topY += bandHeight;
		guiGraphics.fillGradient(0, topY, this.width, topY + bandHeight, 0x90000000, 0xC0000000);
		topY += bandHeight;
		guiGraphics.fillGradient(0, topY, this.width, this.height, 0xC0000000, 0xE8000000);
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
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
	{
		this.renderBottomVignette(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
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
		int overlayHeight = this.getOverlayHeight();
		int textX = MARGIN;
		int textY = this.height - overlayHeight + MARGIN;
		int lineWidth = this.width - MARGIN * 2;
		guiGraphics.drawWordWrap(this.font, Component.literal(sub).withStyle(ChatFormatting.WHITE), textX, textY, lineWidth, TEXT_COLOR);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if(keyCode == GLFW.GLFW_KEY_SPACE || keyCode == GLFW.GLFW_KEY_ENTER)
		{
			this.advanceDialogue();
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean isPauseScreen()
	{
		return false;
	}

	public class NextButton extends Button
	{
		private final Component label;

		public NextButton(int x, int y, int width, int height, Component label, OnPress onPress)
		{
			super(x, y, width, height, label, onPress, DEFAULT_NARRATION);
			this.label = label;
		}

		@Override
		protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
		{
			int color = this.isHovered() ? NEXT_HOVER_COLOR : NEXT_COLOR;
			guiGraphics.drawString(DialogueScreen.this.font, this.label, this.getX(), this.getY(), color, true);
		}
	}
}
