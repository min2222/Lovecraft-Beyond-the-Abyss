package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFulgastra;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySplittedFulgastra;
import com.min01.beyondtheabyss.misc.BTADamageSource;

import net.minecraft.world.entity.LivingEntity;

public class FulgastraChargeGoal extends BasicBTASkillGoal<EntitySplittedFulgastra>
{
	public FulgastraChargeGoal(EntitySplittedFulgastra mob)
	{
		super(mob);
	}

	@Override
	public void start() 
	{
		super.start();
		this.mob.setAnimationState(1);
		this.mob.setCharged(true);
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.getAnimationState() == 0 && (this.mob.distanceTo(this.mob.getTarget()) <= 3.0F || this.mob.getOwner() == null || this.mob.tickCount >= this.mob.level.random.nextInt(200));
	}

	@Override
	public void performSkill() 
	{
		this.mob.setAnimationState(2);
		List<LivingEntity> list = this.mob.level.getEntitiesOfClass(LivingEntity.class, this.mob.getBoundingBox().inflate(5), t -> t != this.mob && !t.isAlliedTo(this.mob) && !(t instanceof EntitySplittedFulgastra) && !(t instanceof EntityFulgastra));
		list.forEach(t ->
		{
			t.hurt(BTADamageSource.causeElectronicDamage(this.mob.level.registryAccess(), this.mob), 5.0F);
		});
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(3);
		this.mob.setCharged(false);
		if(this.mob.getOwner() == null)
		{
			this.mob.kill();
		}
	}

	@Override
	public int getSkillUsingTime()
	{
		return 50;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 20;
	}

	@Override
	public int getSkillUsingInterval()
	{
		return 50;
	}
}
