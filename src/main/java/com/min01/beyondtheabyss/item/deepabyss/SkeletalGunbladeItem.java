package com.min01.beyondtheabyss.item.deepabyss;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.item.renderer.BTAItemRenderer;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateSkeletalGunbladeItemPacket;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SkeletalGunbladeItem extends SwordItem implements IAnimatableItem
{
	public SkeletalGunbladeItem(Item.Properties properties) 
	{
		super(Tiers.DIAMOND, 0, 0.0F, properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand)
	{
		ItemStack stack = pPlayer.getItemInHand(pUsedHand);
		boolean isGunMode = isGunMode(stack);
		if(BTAUtil.getPlayerAnimationState(pPlayer) == 0)
		{
			if(pPlayer.isShiftKeyDown())
			{
				if(isGunMode)
				{
					BTAUtil.setItemAnimationState(stack, 2);
					BTAUtil.setItemAnimationTick(stack, 40);
					pPlayer.playSound(BTASounds.GUNBLADE_GUN_TO_BLADE.get());
					pPlayer.getCooldowns().addCooldown(stack.getItem(), 20);
				}
				else
				{
					BTAUtil.setItemAnimationState(stack, 1);
					BTAUtil.setItemAnimationTick(stack, 40);
					pPlayer.playSound(BTASounds.GUNBLADE_BLADE_TO_GUN.get());
					pPlayer.getCooldowns().addCooldown(stack.getItem(), 20);
				}
	        	setGunMode(stack, !isGunMode);
			}
			else
			{
				if(isGunMode)
				{
					pPlayer.playSound(BTASounds.GUNBLADE_CHARGE.get());
					pPlayer.startUsingItem(pUsedHand);
					BTAUtil.setPlayerAnimationState(pPlayer, 3);
					BTAUtil.setPlayerAnimationTick(pPlayer, 72000);
				}
			}
			return InteractionResultHolder.consume(stack);
		}
		return InteractionResultHolder.fail(stack);
	}
	
	@Override
	public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) 
	{
		if(getCharge(pStack) < 3 && pRemainingUseDuration % 25 == 0 && pLevel.isClientSide)
		{
			setCharge(pStack, getCharge(pStack) + 1);
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity)
	{
		return true;
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) 
	{
		boolean isGunMode = isGunMode(stack);
		int tick = BTAUtil.getPlayerAnimationTick(entity);
		int state = BTAUtil.getPlayerAnimationState(entity);
		if(!isGunMode && tick <= 0 && state == 0)
		{
			entity.playSound(BTASounds.GUNBLADE_SWING.get());
			BTAUtil.setPlayerAnimationState(entity, 5);
			BTAUtil.setPlayerAnimationTick(entity, 40);
		}
		return true;
	}
	
	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected)
	{
		if(!pIsSelected && getCharge(pStack) > 0)
		{
			stop(pStack, pEntity);
		}
	}
	
	public static void stop(ItemStack stack, Entity entity)
	{
		setLaserVisible(stack, false);
		setLaserLength(stack, 0);
		setCharge(stack, 0);
		if(entity instanceof Player player)
		{
			player.getCooldowns().addCooldown(stack.getItem(), 100);
		}
	}
	
	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int pTimeCharged)
	{
		if(getCharge(stack) > 0 && level.isClientSide)
		{
			BTAUtil.setPlayerAnimationState(entity, 4);
			BTAUtil.setPlayerAnimationTick(entity, 95);
			setLaserVisible(stack, true);
			BTANetwork.sendToServer(new UpdateSkeletalGunbladeItemPacket(stack, entity.getUUID()));
		}
	}
	
	public void releaseUsingServer(ItemStack stack, LivingEntity entity)
	{
		BTAUtil.setPlayerAnimationState(entity, 4);
		BTAUtil.setPlayerAnimationTick(entity, 95);
	}

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) 
    {
    	return ImmutableMultimap.of();
    }
	
	@Override
	public boolean isFirstPersonAnim(ItemStack stack, Entity entity)
	{
		return BTAUtil.getPlayerAnimationState(entity) != 0;
	}
	
	@Override
	public int getUseDuration(ItemStack pStack) 
	{
		if(isGunMode(pStack))
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
