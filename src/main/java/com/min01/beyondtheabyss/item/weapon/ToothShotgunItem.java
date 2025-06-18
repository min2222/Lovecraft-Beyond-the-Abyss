package com.min01.beyondtheabyss.item.weapon;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.min01.beyondtheabyss.item.animation.IAnimatableItem;
import com.min01.beyondtheabyss.item.renderer.BTAItemRenderer;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ToothShotgunItem extends Item implements IAnimatableItem
{
    public static final String FREAKY = "Freaky";
    
	public ToothShotgunItem(Properties p_41383_)
	{
		super(p_41383_);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_)
	{
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		if(!p_41432_.isClientSide)
		{
			BTAUtil.stopItemAnimation(stack, FREAKY);
			BTAUtil.startItemAnimation(stack, FREAKY);
		}
    	p_41433_.getCooldowns().addCooldown(stack.getItem(), 20);
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
				return new BTAItemRenderer(BTAClientUtil.MC.getBlockEntityRenderDispatcher(), BTAClientUtil.MC.getEntityModels());
			}
			
			@Override
			public @Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack)
			{
				return ArmPose.BOW_AND_ARROW;
			}
		});
	}
}
