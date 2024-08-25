package com.min01.beyondtheabyss.item.weapon;

import java.util.function.Consumer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.beyondtheabyss.entity.projectile.EntityThrownHarpoon;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.renderer.HarpoonItemRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class HarpoonItem extends Item
{
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	   
	public HarpoonItem(Item.Properties properties, boolean isReinforced) 
	{
		super(properties);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", !isReinforced ? 5.0D : 8.0D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", !isReinforced ? -3.2D : - 3.0D, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack p_41452_)
	{
		return UseAnim.SPEAR;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_)
	{
		ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
		if(itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1)
		{
			return InteractionResultHolder.fail(itemstack);
		}
		else
		{
			p_41433_.startUsingItem(p_41434_);
			return InteractionResultHolder.consume(itemstack);
		}
	}
	
	@Override
	public void releaseUsing(ItemStack p_41412_, Level p_41413_, LivingEntity p_41414_, int p_41415_) 
	{
		if(p_41414_ instanceof Player player)
		{
			int i = this.getUseDuration(p_41412_) - p_41415_;
			
			if(i >= 10)
			{
				if(!p_41413_.isClientSide)
				{
					p_41412_.hurtAndBreak(1, player, (p_43388_) ->
					{
						p_43388_.broadcastBreakEvent(p_41414_.getUsedItemHand());
					});
					EntityThrownHarpoon harpoon = new EntityThrownHarpoon(p_41413_, player, p_41412_, this == BTAItems.GHIDRUTH_SCALE_HARPOON.get());
					harpoon.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, this == BTAItems.GHIDRUTH_SCALE_HARPOON.get() ? 2.0F : 1.2F, 1.0F);
					if(player.getAbilities().instabuild) 
					{
						harpoon.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
					}

					p_41413_.addFreshEntity(harpoon);
					p_41413_.playSound((Player)null, harpoon, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
					if(!player.getAbilities().instabuild)
					{
						player.getInventory().removeItem(p_41412_);
					}
				}

				player.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer)
	{
		consumer.accept(new IClientItemExtensions()
		{
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() 
			{
				return new HarpoonItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
			}
		});
	}
	
	@Override
	public boolean hurtEnemy(ItemStack p_43390_, LivingEntity p_43391_, LivingEntity p_43392_) 
	{
		p_43390_.hurtAndBreak(1, p_43392_, (p_43414_) ->
		{
			p_43414_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		return true;
	}
	
	@Override
	public boolean mineBlock(ItemStack p_43399_, Level p_43400_, BlockState p_43401_, BlockPos p_43402_, LivingEntity p_43403_) 
	{
		if((double)p_43401_.getDestroySpeed(p_43400_, p_43402_) != 0.0D) 
		{
			p_43399_.hurtAndBreak(2, p_43403_, (p_43385_) ->
			{
				p_43385_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}
		return true;
	}
	
	@Override
	public int getUseDuration(ItemStack p_43419_) 
	{
		return 72000;
	}
	
	@SuppressWarnings("deprecation")
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43383_)
	{
		return p_43383_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43383_);
	}
}
