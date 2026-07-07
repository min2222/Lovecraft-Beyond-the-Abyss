package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.AbstractAnimationGoal;
import com.min01.beyondtheabyss.entity.deepabyss.MutavoreEntity;
import com.min01.beyondtheabyss.entity.projectile.PutridBubbleEntity;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MutavorePutridBubbleGoal extends AbstractAnimationGoal<MutavoreEntity>
{
	private int interval;
	
	public MutavorePutridBubbleGoal(MutavoreEntity mob)
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setAnimationState(1);
		this.mob.playSound(BTASounds.MUTAVORE_MOUTH_OPEN_BUBBLE.get(), 5.0F, 1.0F);
	}
	
	@Override
	public boolean stopMovingWhenStart()
	{
		return false;
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) <= 14.0F;
	}
	
	@Override
	public void tick() 
	{
		if(this.mob.getAnimationTick() <= this.getSkillUsingTime() - 10 && this.mob.getTarget() != null) 
		{
			if(this.interval++ == 2)
			{
				this.interval = 0;
				this.mob.playSound(BTASounds.MUTAVORE_BUBBLE_SPEW.get(), 4.0F, 1.0F);
				for(int i = 0; i < 5; i++)
				{
					PutridBubbleEntity bubble = new PutridBubbleEntity(BTAEntities.PUTRID_BUBBLE.get(), this.mob.level);
					bubble.setOwner(this.mob);
					Vec3 lookPos = BTAUtil.getLookPos(new Vec2(this.mob.getXRot(), this.mob.getYHeadRot()), this.mob.position(), 0, 1.0F, 3.5F);
					bubble.setPos(lookPos.add(this.mob.level.random.nextFloat() * 0.1F, this.mob.level.random.nextFloat() * 0.1F, this.mob.level.random.nextFloat() * 0.1F));
					bubble.setDeltaMovement(BTAUtil.getVelocityTowards(bubble.position(), BTAUtil.getSpreadPosition(this.mob.getRandom(), this.mob.getTarget().position(), new Vec3(2.5F, 2.5F, 2.5F)), 0.65F));
					bubble.lookAt(Anchor.EYES, this.mob.getTarget().getEyePosition());
					this.mob.level.addFreshEntity(bubble);
				}
			}
		}
	}

	@Override
	public void performSkill()
	{
		this.mob.setAnimationState(2);
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(0);
		this.interval = 0;
	}

	@Override
	public int getSkillUsingTime() 
	{
		return 80;
	}
	
	@Override
	public int getSkillWarmupTime() 
	{
		return 70;
	}

	@Override
	public int getSkillUsingInterval()
	{
		return 40;
	}
}