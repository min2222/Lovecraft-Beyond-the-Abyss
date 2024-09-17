package com.min01.beyondtheabyss.item.weapon;

import java.util.function.Consumer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.min01.beyondtheabyss.entity.projectile.EntityThrownHarpoon;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.item.renderer.HarpoonRenderer;
import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class HarpoonItem extends SwordItem
{
	public HarpoonItem(Item.Properties properties)
	{
		super(Tiers.IRON, 0, 0.0F, properties.tab(DeepAbyssTabs.ABYSS_WEAPONS));
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
					p_41413_.playSound(player, harpoon, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
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
				return new HarpoonRenderer();
			}
		});
	}
	
	@Override
	public int getUseDuration(ItemStack p_43419_) 
	{
		return 72000;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) 
	{
		boolean isReinforced = stack.getItem() == BTAItems.GHIDRUTH_SCALE_HARPOON.get();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool Modifier", !isReinforced ? 5.0D : 8.0D, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool Modifier", !isReinforced ? -3.2D : -3.0D, AttributeModifier.Operation.ADDITION));
		return slot == EquipmentSlot.MAINHAND ? builder.build() : ImmutableMultimap.of();
	}
	
	@Override
	public boolean canPerformAction(ItemStack stack, ToolAction toolAction)
	{
		return toolAction == ToolActions.SWORD_DIG;
	}
}
