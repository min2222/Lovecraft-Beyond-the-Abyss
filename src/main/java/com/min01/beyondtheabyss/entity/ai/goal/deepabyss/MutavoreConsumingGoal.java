package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore.MutationType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MutavoreConsumingGoal extends Goal
{
	private final EntityMutavore mob;
	private Vec3 wantedPos;
	private ItemEntity item;
	
	public MutavoreConsumingGoal(EntityMutavore mob) 
	{
		this.mob = mob;
	}
	
	@Override
	public void start()
	{
		this.mob.setConsume(true);
		this.mob.setTarget(null);
		this.mob.getNavigation().moveTo(this.wantedPos.x, this.wantedPos.y, this.wantedPos.z, 1.0F);
	}
	
	@Override
	public boolean canUse()
	{
		if(this.mob.isUsingSkill())
		{
			return false;
		}
		return this.setWantedPos();
	}
	
	@Override
	public boolean canContinueToUse() 
	{
		return this.item != null && this.item.isAlive();
	}
	
	@Override
	public void tick() 
	{
		if(this.wantedPos != null && this.item != null && this.item.isAlive())
		{
			Vec3 lookPos = BTAUtil.getLookPos(new Vec2(this.mob.getXRot(), this.mob.yBodyRot), this.mob.position(), 0, 0.5, 3.0F);
			if(lookPos.subtract(this.wantedPos).length() <= 2.0F)
			{
				List<MutationType> types = Lists.newArrayList(MutationType.values());
				types.removeIf(t -> this.mob.isMutated(t));
				
				if(!types.isEmpty())
				{
					MutationType type = Util.getRandom(types, this.mob.getRandom());
					this.mob.playSound(SoundEvents.GENERIC_EAT);
					this.mob.doMutation(type, true);
					this.mob.getNavigation().stop();
					this.wantedPos = null;
					this.item.getItem().shrink(1);
				}
			}
			else
			{
				this.mob.getNavigation().moveTo(this.wantedPos.x, this.wantedPos.y, this.wantedPos.z, 1.25F);
			}
		}
	}
	
	@Override
	public void stop() 
	{
		this.mob.setConsume(false);
		this.mob.getNavigation().stop();
		this.mob.setAnimationState(0);
		this.wantedPos = null;
		this.item = null;
	}
	
	public boolean setWantedPos() 
	{
		this.wantedPos = this.findItem();
		return this.wantedPos != null;
	}
	
	@Nullable
	public Vec3 findItem()
	{
		List<ItemEntity> list = this.mob.getConsumableItems(this.mob.getBoundingBox().inflate(10.0F));
		double dist = -1.0D;
		Vec3 nearest = null;
		for(ItemEntity item : list)
		{
			if(!item.isInWater()) 
			{
				continue;
			}
			double dist1 = item.distanceToSqr(this.mob);
			if(dist == -1.0D || dist1 < dist) 
			{
				dist = dist1;
				nearest = item.position();
				this.item = item;
			}
		}
		return nearest;
	}
}
