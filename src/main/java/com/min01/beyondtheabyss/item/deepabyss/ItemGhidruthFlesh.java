package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.capabilities.BTAAbilitiesCapabilityHandler.BTAAbilities;
import com.min01.beyondtheabyss.capabilities.BTACapabilities;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.misc.BTACreativeTabs;
import com.min01.beyondtheabyss.misc.BTADamageSource;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemGhidruthFlesh extends Item
{
	private boolean isRaw;
	
	public ItemGhidruthFlesh(FoodProperties properties, boolean isRaw) 
	{
		super(new Item.Properties().tab(BTACreativeTabs.ABYSS_FOODS).food(properties));
		this.isRaw = isRaw;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) 
	{
		if(this.isRaw)
		{
			p_41411_.hurt(BTADamageSource.causeGhidruthFleshDamage(p_41411_), 0.5F);
		}
		else
		{
			//6000 = 5 minutes
			p_41411_.addEffect(new MobEffectInstance(BTAEffects.GHIDRUTHS_SCALES.get(), 6000, 0, false, false));
			p_41411_.getCapability(BTACapabilities.BTA_ABILITY).ifPresent((cap) -> 
			{
				cap.setAbility(BTAAbilities.GHIDRUTHS_SCALES);
			});
		}
		
		if(p_41411_ instanceof Player && !((Player)p_41411_).getAbilities().instabuild) 
		{
			p_41409_.shrink(1);
		}
		return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
	}
}
