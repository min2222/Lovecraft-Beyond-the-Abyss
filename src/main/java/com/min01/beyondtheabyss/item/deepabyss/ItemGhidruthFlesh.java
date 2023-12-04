package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.misc.BTADamageSource;

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
		super(new Item.Properties().tab(BeyondtheAbyss.ABYSS_FOODS).food(properties));
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
			//TODO
			//increase defense of player
		}
		
		if(p_41411_ instanceof Player && !((Player)p_41411_).getAbilities().instabuild) 
		{
			p_41409_.shrink(1);
		}
		return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
	}
}
