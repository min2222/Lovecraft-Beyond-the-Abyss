package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityFulgastra;
import com.min01.beyondtheabyss.entity.deepabyss.EntitySplittedFulgastra;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class FulgastraSplitGoal extends BasicBTASkillGoal<EntityFulgastra>
{
	public FulgastraSplitGoal(EntityFulgastra mob)
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setCharged(true);
	}
	
	@Override
	public boolean additionalStartCondition() 
	{
		return this.mob.getHealth() > 10;
	}

	@Override
	protected void performSkill() 
	{
		int count = this.mob.getRandom().nextInt(8, 10);
		if(count * 5 >= this.mob.getHealth())
		{
			count = Mth.floor((this.mob.getHealth() - 10) / 5);
		}
		for(int i = 0; i < count; i++)
		{
			Vec2 rotation = new Vec2(this.mob.getXRot(), this.mob.getYHeadRot());
			Vec3 lookPos = BTAUtil.getLookPos(rotation, this.mob.getEyePosition(), 0, 0, 2.0F);
			Vec3 endPos = BTAUtil.getLookPos(rotation, this.mob.getEyePosition(), this.mob.getRandom().nextGaussian() * 0.5F, this.mob.getRandom().nextGaussian() * 0.5F, 4.0F);
			EntitySplittedFulgastra splitted = new EntitySplittedFulgastra(BTAEntities.SPLITTED_FULGASTRA.get(), this.mob.level);
			splitted.setOwner(this.mob);
			splitted.setPos(lookPos);
			splitted.setDeltaMovement(BTAUtil.fromToVector(splitted.position(), endPos, 1.5F));
			this.mob.level.addFreshEntity(splitted);
		}
		this.mob.setHealth(this.mob.getHealth() - (count * 5));
		this.mob.setAnimationState(1);
		this.mob.setAnimationTick(10);
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setCharged(false);
	}

	@Override
	protected int getSkillUsingTime()
	{
		return 20;
	}

	@Override
	protected int getSkillUsingInterval()
	{
		return 100;
	}
}
