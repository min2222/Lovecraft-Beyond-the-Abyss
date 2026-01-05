package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.item.BTAItems;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SubmarineItem extends Item
{
	public SubmarineItem() 
	{
		super(new Item.Properties().rarity(BTAItems.RARITY_DEEP_ABYSS));
	}
	
	@Override
	public InteractionResult useOn(UseOnContext pContext) 
	{
		Level level = pContext.getLevel();
        ItemStack stack = pContext.getItemInHand();
        Player player = pContext.getPlayer();
		Vec3 pos = pContext.getClickLocation();
        EntitySubmarine submarine = new EntitySubmarine(BTAEntities.SUBMARINE.get(), level);
        if(!player.getAbilities().instabuild)
        {
        	stack.shrink(1);
        }
        submarine.setPos(pos);
        level.addFreshEntity(submarine);
        return InteractionResult.SUCCESS;
	}
}
