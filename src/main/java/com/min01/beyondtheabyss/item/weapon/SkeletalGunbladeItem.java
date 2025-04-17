package com.min01.beyondtheabyss.item.weapon;

import java.util.function.Consumer;

import com.min01.beyondtheabyss.item.renderer.BTAItemRenderer;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SkeletalGunbladeItem extends SwordItem
{
    public static final String GUNBLADE_OPEN = "GunbladeOpen";
    public static final String GUNBLADE_CLOSE = "GunbladeClose";
    public static final String GUNBLADE_OPENED = "GunbladeOpened";
    public static final String GUNBLADE_CLOSED = "GunbladeClosed";
    public static final String GUNBLADE_BRING_OUT = "GunbladeBringOut";
    public static final String GUNBLADE_PUT_DOWN = "GunbladePutDown";
    public static final String GUNBLADE_SWING = "GunbladeSwing";
    public static final String GUNBLADE_SHOOT_LIGHT = "GunbladeShootLight";
    public static final String GUN_MODE = "GunMode";
    public static final String IS_SELECTED = "isSelected";
    public int tickCount;
    
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
	        	BTAUtil.startItemAnimation(stack, GUNBLADE_CLOSE, this.tickCount);
				BTAUtil.startPlayerAnimation(p_41433_, GUNBLADE_CLOSE);
	        	p_41433_.playSound(BTASounds.GUNBLADE_GUN_TO_BLADE.get());
	        	p_41433_.getCooldowns().addCooldown(stack.getItem(), 20);
			}
			else
			{
	        	BTAUtil.startItemAnimation(stack, GUNBLADE_OPEN, this.tickCount);
				BTAUtil.startPlayerAnimation(p_41433_, GUNBLADE_OPEN);
	        	p_41433_.playSound(BTASounds.GUNBLADE_BLADE_TO_GUN.get());
	        	p_41433_.getCooldowns().addCooldown(stack.getItem(), 20);
			}
        	setGunMode(stack, !isGunMode);
		}
		else
		{
			if(isGunMode)
			{
				BTAUtil.startPlayerAnimation(p_41433_, GUNBLADE_SHOOT_LIGHT);
	        	p_41433_.getCooldowns().addCooldown(stack.getItem(), 10);
			}
		}
		return super.use(p_41432_, p_41433_, p_41434_);
	}
	
	@Override
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) 
	{
		this.tickCount++;
 		if(p_41406_ instanceof Player player)
 		{
 			BTAUtil.updateItemTick(player, p_41404_);
 		}
		AnimationState bringOutState = BTAUtil.getPlayerAnimationState(p_41406_, GUNBLADE_BRING_OUT);
		if(p_41408_ && !bringOutState.isStarted() && !isSelected(p_41404_))
		{
			//FIXME
			//BTAUtil.startPlayerAnimation(p_41406_, GUNBLADE_BRING_OUT);
		}
		setSelected(p_41404_, p_41408_);
		super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) 
	{
		boolean isGunMode = isGunMode(stack);
		if(!isGunMode)
		{
			BTAUtil.startPlayerAnimation(entity, GUNBLADE_SWING);
			//TODO play with correct timing
			//entity.playSound(BTASounds.GUNBLADE_SWING.get());
		}
		return true;
	}
	

 	@Override
 	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) 
 	{
 		this.tickCount++;
 		if(entity.getOwner() instanceof LivingEntity living)
 		{
 			BTAUtil.updateItemTick(living, stack);
 		}
 		return super.onEntityItemUpdate(stack, entity);
 	}
	
	public static boolean isSelected(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null && tag.getBoolean(IS_SELECTED);
	}
	
	public static void setSelected(ItemStack stack, boolean isSelected) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean(IS_SELECTED, isSelected);
	}
	
	public static boolean isGunMode(ItemStack stack) 
	{
		CompoundTag tag = stack.getTag();
		return tag != null && tag.getBoolean(GUN_MODE);
	}
	
	public static void setGunMode(ItemStack stack, boolean isGunMode) 
	{
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean(GUN_MODE, isGunMode);
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
}
