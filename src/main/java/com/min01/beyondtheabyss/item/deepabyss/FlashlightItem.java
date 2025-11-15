package com.min01.beyondtheabyss.item.deepabyss;

import java.util.function.Consumer;

import com.min01.beyondtheabyss.item.renderer.FlashlightRenderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class FlashlightItem extends Item
{
	public static final String PREV_POS = "PrevPos";
	public static final String ON = "On";
	
	public FlashlightItem()
	{
		super(new Item.Properties().stacksTo(1));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) 
	{
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		boolean isOn = isOn(stack);
		setOn(stack, !isOn);
		return InteractionResultHolder.consume(stack);
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer)
	{
		consumer.accept(new IClientItemExtensions()
		{
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() 
			{
				return new FlashlightRenderer();
			}
		});
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
	
    public static boolean isOn(ItemStack stack)
    {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getBoolean(ON) : false;
    }

    public static void setOn(ItemStack stack, boolean on)
    {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(ON, on);
    }
}
