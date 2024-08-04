package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;
import java.util.function.Predicate;

import com.min01.beyondtheabyss.entity.deepabyss.AbstractDeepAbyssMob;
import com.min01.beyondtheabyss.entity.deepabyss.IFlocking;
import com.mojang.datafixers.DataFixUtils;

import net.minecraft.world.entity.ai.goal.Goal;

public class DeepAbyssFollowFlockLeaderGoal extends Goal
{
	private static final int INTERVAL_TICKS = 200;
	private final AbstractDeepAbyssMob mob;
	private int timeToRecalcPath;
	private int nextStartTick;

	public DeepAbyssFollowFlockLeaderGoal(AbstractDeepAbyssMob p_25249_) 
	{
		this.mob = p_25249_;
		this.nextStartTick = this.nextStartTick(p_25249_);
	}

	protected int nextStartTick(AbstractDeepAbyssMob p_25252_)
	{
		return reducedTickDelay(INTERVAL_TICKS + p_25252_.getRandom().nextInt(INTERVAL_TICKS) % 20);
	}

	@Override
	public boolean canUse() 
	{
		if(((IFlocking) this.mob).hasFollowers())
		{
			return false;
		}
		else if(((IFlocking) this.mob).isFollower())
		{
			return true;
		} 
		else if(this.nextStartTick > 0) 
		{
			--this.nextStartTick;
			return false;
		}
		else 
		{
			this.nextStartTick = this.nextStartTick(this.mob);
			Predicate<AbstractDeepAbyssMob> predicate = (p_25258_) -> 
			{
				return ((IFlocking) p_25258_).canBeFollowed() || !((IFlocking) p_25258_).isFollower();
			};
			List<? extends AbstractDeepAbyssMob> list = this.mob.level.getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
			AbstractDeepAbyssMob fish = DataFixUtils.orElse(list.stream().filter(t -> ((IFlocking) t).canBeFollowed()).findAny(), this.mob);
			((IFlocking) fish).addFollowers(list.stream().filter((p_25255_) -> 
			{
				return !((IFlocking) p_25255_).isFollower();
			}));
			return ((IFlocking) this.mob).isFollower();
		}
	}

	@Override
	public boolean canContinueToUse() 
	{
		return ((IFlocking) this.mob).isFollower() && ((IFlocking) this.mob).inRangeOfLeader();
	}

	@Override
	public void start()
	{
		this.timeToRecalcPath = 0;
	}

	@Override
	public void stop() 
	{
		((IFlocking) this.mob).stopFollowing();
	}

	@Override
	public void tick() 
	{
		if(--this.timeToRecalcPath <= 0)
		{
			this.timeToRecalcPath = this.adjustedTickDelay(10);
			((IFlocking) this.mob).pathToLeader();
		}
	}
}
