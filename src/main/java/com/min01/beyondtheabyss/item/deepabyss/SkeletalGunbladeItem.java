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
    public static final String GUNBLADE_OPEN = "GunbladeOpen";
    public static final String GUNBLADE_CLOSE = "GunbladeClose";
    public static final String GUNBLADE_CHARGE = "GunbladeCharge";
    public static final String GUNBLADE_SHOOT_LIGHT = "GunbladeShootLight";
    public static final String GUNBLADE_SHOOT = "GunbladeShoot";
    public static final String GUNBLADE_SWING = "GunbladeSwing";
    
	public SkeletalGunbladeItem(Item.Properties properties) 
	{
		super(Tiers.DIAMOND, 0, 0.0F, properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_)
	{
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		boolean isGunMode = isGunMode(stack);
		if(BTAUtil.getPlayerAnimationState(p_41433_) == 0)
		{
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
					BTAUtil.setPlayerAnimationState(p_41433_, 3);
					BTAUtil.setPlayerAnimationTick(p_41433_, 72000);
				}
			}
			return InteractionResultHolder.consume(stack);
		}
		return InteractionResultHolder.fail(stack);
	}
	
	@Override
	public void onUseTick(Level p_41428_, LivingEntity p_41429_, ItemStack p_41430_, int p_41431_) 
	{
		if(getCharge(p_41430_) < 3 && p_41431_ % 25 == 0 && p_41428_.isClientSide)
		{
			setCharge(p_41430_, getCharge(p_41430_) + 1);
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
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_)
	{
		if(!p_41408_ && getCharge(p_41404_) > 0)
		{
			stop(p_41404_, p_41406_);
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
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int p_41415_) 
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
