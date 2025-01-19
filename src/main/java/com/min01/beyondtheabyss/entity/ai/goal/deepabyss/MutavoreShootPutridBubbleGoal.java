package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.projectile.EntityPutridBubble;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.world.phys.Vec3;

public class MutavoreShootPutridBubbleGoal extends AbstractMutavoreSkillGoal
{
	public MutavoreShootPutridBubbleGoal(EntityMutavore mob) 
	{
		super(mob);
	}
	
	@Override
	public boolean additionalStartCondition()
	{
		return this.mob.distanceTo(this.mob.getTarget()) >= 8.0F;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.isMouthOpened())
		{
			if(this.mob.tickCount % 1 == 0)
			{
				for(int i = 0; i < 3; i++)
				{
					EntityPutridBubble bubble = new EntityPutridBubble(BTAEntities.PUTRID_BUBBLE.get(), this.mob.level);
					bubble.setOwner(this.mob);
					if(this.mob.posArray[11] != null)
					{
						Vec3 pos = this.mob.posArray[11].subtract(0.0F, 0.5F, 0.0F);
						bubble.setPos(pos.add(this.mob.level.random.nextFloat(), this.mob.level.random.nextFloat(), this.mob.level.random.nextFloat()));
						bubble.setDeltaMovement(BTAUtil.fromToVector(pos, BTAUtil.getSpreadPosition(this.mob.getTarget(), 6.0F), 0.25F));
					}
					bubble.lookAt(Anchor.EYES, this.mob.getTarget().getEyePosition());
					this.mob.level.addFreshEntity(bubble);
				}
			}
		}
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(3);
		this.mob.setAnimationTick(5);
	}
	
	@Override
	protected int getSkillUsingTime() 
	{
		return 60;
	}

	@Override
	protected int getSkillUsingInterval() 
	{
		return 150;
	}
}
