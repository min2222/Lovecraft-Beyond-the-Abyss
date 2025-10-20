package com.min01.beyondtheabyss.item.weapon;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.item.renderer.BTAItemRenderer;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SkeletalGunbladeItem extends SwordItem implements IAnimatableItem
{
    public static final String GUNBLADE_OPEN = "GunbladeOpen";
    public static final String GUNBLADE_CLOSE = "GunbladeClose";
    public static final String GUNBLADE_CHARGE = "GunbladeCharge";
    public static final String GUNBLADE_SHOOT = "GunbladeShoot";
    
	public SkeletalGunbladeItem(Item.Properties properties) 
	{
		super(Tiers.DIAMOND, 0, 0.0F, properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_)
	{
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		boolean isGunMode = isGunMode(stack);
		if(p_41433_.isShiftKeyDown())
		{
			if(isGunMode)
			{
				BTAUtil.setItemAnimationState(stack, 2);
				BTAUtil.setItemAnimationTick(stack, 40);
	        	p_41433_.playSound(BTASounds.GUNBLADE_GUN_TO_BLADE.get());
	        	p_41433_.getCooldowns().addCooldown(stack.getItem(), 20);
			}
			else
			{
				BTAUtil.setItemAnimationState(stack, 1);
				BTAUtil.setItemAnimationTick(stack, 40);
	        	p_41433_.playSound(BTASounds.GUNBLADE_BLADE_TO_GUN.get());
	        	p_41433_.getCooldowns().addCooldown(stack.getItem(), 20);
			}
        	setGunMode(stack, !isGunMode);
		}
		else
		{
			if(isGunMode)
			{
	        	p_41433_.playSound(BTASounds.GUNBLADE_CHARGE.get());
				p_41433_.startUsingItem(p_41434_);
				if(BTAUtil.getPlayerAnimationState(p_41433_) == 0)
				{
					BTAUtil.setPlayerAnimationState(p_41433_, 3);
					BTAUtil.setPlayerAnimationTick(p_41433_, 72000);
				}
			}
			else
			{
	        	p_41433_.playSound(BTASounds.GUNBLADE_SWING.get());
	        	//TODO swing animation;
			}
		}
		return InteractionResultHolder.consume(stack);
	}
	
	@Override
	public void onUseTick(Level p_41428_, LivingEntity p_41429_, ItemStack p_41430_, int p_41431_) 
	{
		if(getCharge(p_41430_) < 3 && p_41431_ % 25 == 0 && p_41429_.level.isClientSide)
		{
			setCharge(p_41430_, getCharge(p_41430_) + 1);
		}
	}
	
	@Override
	public void onStopUsing(ItemStack stack, LivingEntity entity, int count)
	{
		if(getCharge(stack) > 0)
		{
			BTAUtil.setPlayerAnimationState(entity, 4);
			BTAUtil.setPlayerAnimationTick(entity, 40);
			setLaserVisible(stack, true);
			setLaserLength(stack, 100);
			setCharge(stack, 0);
		}
	}
	
	@Override
	public int getUseDuration(ItemStack p_41454_) 
	{
		if(isGunMode(p_41454_))
		{
			return 72000;
		}
		return 0;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) 
	{
		return newStack.getItem() != this;
	}
	
	public static int getCharge(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null ? tag.getInt("Charge") : 0;
	}
	
	public static void setCharge(ItemStack stack, int charge) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt("Charge", charge);
	}
	
	public static boolean isGunMode(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null && tag.getBoolean("GunMode");
	}
	
	public static void setGunMode(ItemStack stack, boolean isGunMode) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean("GunMode", isGunMode);
	}
	
	public static int getLaserLength(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null ? tag.getInt("LaserLength") : 0;
	}
	
	public static void setLaserLength(ItemStack stack, int length) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt("LaserLength", length);
	}
	
	public static boolean isLaserVisible(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null && tag.getBoolean("LaserVisible");
	}
	
	public static void setLaserVisible(ItemStack stack, boolean LaserVisible) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean("LaserVisible", LaserVisible);
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
			
			@Override
			public @Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) 
			{
				return ArmPose.EMPTY;
			}
		});
	}
}
