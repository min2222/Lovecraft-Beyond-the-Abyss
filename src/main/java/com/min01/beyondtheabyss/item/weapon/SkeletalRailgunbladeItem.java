package com.min01.beyondtheabyss.item.weapon;

import java.util.function.Consumer;

import com.min01.beyondtheabyss.item.renderer.BTAItemRenderer;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SkeletalRailgunbladeItem extends SwordItem
{
    public static final String RAILGUNBLADE_OPEN = "RailgunbladeOpen";
    public static final String RAILGUNBLADE_CLOSE = "RailgunbladeClose";
    public static final String RAILGUNBLADE_OPENED = "RailgunbladeOpened";
    public static final String RAILGUNBLADE_CLOSED = "RailgunbladeClosed";
    public static final String RAILGUNBLADE_BRING_OUT = "RailgunbladeBringOut";
    public static final String RAILGUNBLADE_PUT_DOWN = "RailgunbladePutDown";
    public static final String RAILGUNBLADE_SWING = "RailgunbladeSwing";
    public static final String GUN_MODE = "GunMode";
	public SkeletalRailgunbladeItem(Item.Properties properties) 
	{
		super(Tiers.DIAMOND, 0, 0.0F, properties.tab(DeepAbyssTabs.ABYSS_WEAPONS));
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
	        	BTAUtil.startItemAnimation(stack, RAILGUNBLADE_CLOSE, p_41433_.tickCount);
	        	p_41433_.playSound(BTASounds.RAILGUNBLADE_GUN_TO_BLADE.get());
	        	p_41433_.getCooldowns().addCooldown(stack.getItem(), 20);
			}
			else
			{
	        	BTAUtil.startItemAnimation(stack, RAILGUNBLADE_OPEN, p_41433_.tickCount);
	        	p_41433_.playSound(BTASounds.RAILGUNBLADE_BLADE_TO_GUN.get());
	        	p_41433_.getCooldowns().addCooldown(stack.getItem(), 20);
			}
        	setGunMode(stack, !isGunMode);
		}
		if(!isGunMode)
		{
			
		}
		return super.use(p_41432_, p_41433_, p_41434_);
	}
	
	@Override
	public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) 
	{
		AnimationState bringOutState = BTAUtil.getPlayerAnimationState(p_41406_, RAILGUNBLADE_BRING_OUT);
		if(p_41408_ && !bringOutState.isStarted())
		{
			//BTAUtil.startPlayerAnimation(p_41406_, RAILGUNBLADE_BRING_OUT);
		}
		super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) 
	{
		boolean isGunMode = isGunMode(stack);
		AnimationState swingState = BTAUtil.getPlayerAnimationState(entity, RAILGUNBLADE_SWING);
		if(!isGunMode && !swingState.isStarted())
		{
			BTAUtil.startPlayerAnimation(entity, RAILGUNBLADE_SWING);
		}
		return true;
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
