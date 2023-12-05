package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.entity.BTAEntityType;
import com.min01.beyondtheabyss.entity.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.deepabyss.EntityDeepAbyssPortal;
import com.min01.beyondtheabyss.misc.BTACreativeTabs;

import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.Fluids;

public class ItemGuidingClam extends Item
{
	public ItemGuidingClam()
	{
		super(new Item.Properties().tab(BTACreativeTabs.ABYSS_MISC).stacksTo(1).rarity(Rarity.EPIC));
	}
	
	@Override
	public boolean isFoil(ItemStack p_41453_)
	{
		return true;
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) 
	{
        boolean overworld = entity.getLevel().dimension().location().getPath().equals("overworld");
        boolean isDeepOcean = entity.level.getBiome(entity.blockPosition()).containsTag(BiomeTags.IS_DEEP_OCEAN);
        boolean isInWater = entity.isEyeInFluidType(Fluids.WATER.getFluidType());
        if(overworld && isDeepOcean && isInWater)
        {
        	entity.setDeltaMovement(entity.getDeltaMovement().subtract(0, 0.01F, 0));
        	entity.setGlowingTag(true);
        	if(entity.isOnGround() && entity.tickCount % 20 == 0)
        	{
            	EntityBTACameraShake.cameraShake(entity.level, entity.position(), 30, 0.1F, 15, 25);
            	entity.discard();
            	if(!entity.level.isClientSide)
            	{
            		EntityDeepAbyssPortal portal = new EntityDeepAbyssPortal(BTAEntityType.DEEP_ABYSS_PORTAL.get(), entity.level);
            		portal.setPos(entity.position());
            		entity.level.addFreshEntity(portal);
            	}
        	}
        }
		return super.onEntityItemUpdate(stack, entity);
	}
}
