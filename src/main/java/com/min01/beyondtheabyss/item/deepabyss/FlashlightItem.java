package com.min01.beyondtheabyss.item.deepabyss;

import java.util.function.Consumer;

import com.min01.beyondtheabyss.item.renderer.FlashlightRenderer;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.solomonlib.misc.IDynamicLightItem;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class FlashlightItem extends Item implements IDynamicLightItem
{
	public static final String ON = "On";
	
	public FlashlightItem()
	{
		super(new Item.Properties().stacksTo(1));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) 
	{
		ItemStack stack = pPlayer.getItemInHand(pUsedHand);
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

	@Override
	public Vec3 getDynamicLightPos(Entity player, ItemStack stack) 
	{
    	Vec3 lightPos = BTAUtil.getLookPos(player.getRotationVector(), player.getEyePosition(), 0.0F, 0.0F, 8.0F);
    	HitResult result = player.level.clip(new ClipContext(player.getEyePosition(), lightPos, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    	return result.getLocation();
	}

	@Override
	public boolean shouldUpdateDynamicLight(Entity player, ItemStack stack)
	{
		return isOn(stack);
	}
}
