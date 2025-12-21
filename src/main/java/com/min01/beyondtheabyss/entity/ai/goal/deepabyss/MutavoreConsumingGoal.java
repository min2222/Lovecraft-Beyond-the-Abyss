package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore.MutationType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

public class MutavoreConsumingGoal extends Goal
{
	private final EntityMutavore mob;
	private UUID itemUUID;
	
	public MutavoreConsumingGoal(EntityMutavore mob) 
	{
		this.mob = mob;
	}
	
	@Override
	public void start()
	{
		this.mob.setConsume(true);
	}
	
	@Override
	public boolean canUse()
	{
		List<MutationType> types = Lists.newArrayList(MutationType.values());
		types.removeIf(t -> this.mob.isMutated(t));
		if(types.isEmpty())
		{
			return false;
		}
		if(this.mob.tickCount % 60 == 1 && this.itemUUID == null)
		{
			List<ItemEntity> list = this.mob.getConsumableItems(this.mob.getBoundingBox().inflate(10.0F));
			if(!list.isEmpty())
			{
				this.itemUUID = list.get(0).getUUID();
			}
		}
		return !this.mob.isUsingSkill() && this.mob.isInWater() && this.itemUUID != null;
	}
	
	@Override
	public boolean canContinueToUse() 
	{
		Entity entity = BTAUtil.getEntityByUUID(this.mob.level, this.itemUUID);
		return this.canUse() && entity != null;
	}
	
	@Override
	public boolean requiresUpdateEveryTick() 
	{
		return true;
	}
	
	@Override
	public void tick() 
	{
		Entity entity = BTAUtil.getEntityByUUID(this.mob.level, this.itemUUID);
		if(entity != null)
		{
			this.mob.getMoveControl().setWantedPosition(entity.getX(), entity.getY(), entity.getZ(), 1.25F);
			this.mob.getLookControl().setLookAt(entity, 100.0F, 100.0F);
		}
	}
	
	@Override
	public void stop() 
	{
		this.mob.setConsume(false);
		this.itemUUID = null;
	}
}
