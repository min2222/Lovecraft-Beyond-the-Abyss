package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.item.BasicBTAFoodItem;
import com.min01.beyondtheabyss.misc.BTADamageSource;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GhidruthFleshItem extends BasicBTAFoodItem
{
	private boolean isRaw;
	
	public GhidruthFleshItem(FoodProperties properties, boolean isRaw) 
	{
		super(properties);
		this.isRaw = isRaw;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) 
	{		
		if(pLivingEntity instanceof Player player && !player.getAbilities().instabuild) 
		{
			if(this.isRaw)
			{
				pLivingEntity.hurt(BTADamageSource.causeGhidruthFleshDamage(pLevel.registryAccess()), 0.5F);
			}
		}
		return super.finishUsingItem(pStack, pLevel, pLivingEntity);
	}
}
