package com.min01.beyondtheabyss.item.deepabyss;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.misc.EntityBTACameraShake;
import com.min01.beyondtheabyss.entity.misc.EntityDeepAbyssPortal;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.tabs.DeepAbyssTabs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

public class ItemGuidingClam extends Item
{
	public ItemGuidingClam()
	{
		super(new Item.Properties().tab(DeepAbyssTabs.ABYSS_MISC).stacksTo(1).rarity(BTAItems.RARITY_ABYSS));
	}
	
	@Override
	public boolean isFoil(ItemStack p_41453_)
	{
		return true;
	}
	
	@Override
	public void inventoryTick(ItemStack p_41404_, Level level, Entity entity, int p_41407_, boolean p_41408_) 
	{
        boolean isDeepOcean = entity.level.getBiome(entity.blockPosition()).is(BiomeTags.IS_DEEP_OCEAN);
    	setOpen(p_41404_, isDeepOcean);
	}
	
	public static boolean isOpen(ItemStack p_40933_) 
	{
		CompoundTag compoundtag = p_40933_.getTag();
		return compoundtag != null && compoundtag.getBoolean("Open");
	}
	
	public static void setOpen(ItemStack p_40885_, boolean p_40886_) 
	{
		CompoundTag compoundtag = p_40885_.getOrCreateTag();
		compoundtag.putBoolean("Open", p_40886_);
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) 
	{
        boolean overworld = entity.getLevel().dimension().location().getPath().equals("overworld");
        boolean isDeepOcean = entity.level.getBiome(entity.blockPosition()).is(BiomeTags.IS_DEEP_OCEAN);
        boolean isInWater = entity.isEyeInFluidType(Fluids.WATER.getFluidType());
        if(overworld && isDeepOcean && isInWater && entity.getThrowingEntity() != null)
        {
        	entity.setDeltaMovement(entity.getDeltaMovement().subtract(0, 0.01F, 0));
        	entity.setGlowingTag(true);
        	if(entity.isOnGround() && entity.tickCount % 20 == 0)
        	{
            	EntityBTACameraShake.cameraShake(entity.level, entity.position(), 30, 0.1F, 15, 25);
            	entity.discard();
            	if(!entity.level.isClientSide)
            	{
            		EntityDeepAbyssPortal portal = new EntityDeepAbyssPortal(BTAEntities.DEEP_ABYSS_PORTAL.get(), entity.level);
            		portal.setPos(entity.position());
            		entity.level.addFreshEntity(portal);
            	}
        	}
        }
		return super.onEntityItemUpdate(stack, entity);
	}
}
