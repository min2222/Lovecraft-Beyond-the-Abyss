package com.min01.beyondtheabyss.item.deepabyss;

import java.util.function.Consumer;

import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.item.renderer.BTAItemRenderer;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ClamOfGuidanceItem extends Item implements IAnimatableItem
{
	public static final String OPEN = "Open";
    public static final String CLAM_OPEN = "ClamOpen";
	
	public ClamOfGuidanceItem()
	{
		super(new Item.Properties().stacksTo(1).rarity(BTAItems.RARITY_DEEP_ABYSS));
	}
	
	@Override
	public boolean isFoil(ItemStack pStack)
	{
		return true;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) 
	{
		ItemStack stack = pPlayer.getItemInHand(pUsedHand);
		setOpen(stack, !isOpen(stack));
		pPlayer.getCooldowns().addCooldown(this, 10);
		return InteractionResultHolder.pass(stack);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) 
	{
		consumer.accept(new IClientItemExtensions() 
		{
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer()
			{
				return new BTAItemRenderer(BTAClientUtil.MC.getBlockEntityRenderDispatcher(), BTAClientUtil.MC.getEntityModels());
			}
		});
	}
	
    public static boolean isOpen(ItemStack stack)
    {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getBoolean(OPEN) : false;
    }

    public static void setOpen(ItemStack stack, boolean open)
    {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(OPEN, open);
    }
}
