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
	        	BTAUtil.startItemAnimation(p_41433_, stack, RAILGUNBLADE_CLOSE);
	        	p_41433_.playSound(BTASounds.RAILGUNBLADE_GUN_TO_BLADE.get());
			}
			else
			{
	        	BTAUtil.startItemAnimation(p_41433_, stack, RAILGUNBLADE_OPEN);
	        	p_41433_.playSound(BTASounds.RAILGUNBLADE_BLADE_TO_GUN.get());
			}
        	setGunMode(stack, !isGunMode);
		}
		if(!isGunMode)
		{
			
		}
		return super.use(p_41432_, p_41433_, p_41434_);
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
