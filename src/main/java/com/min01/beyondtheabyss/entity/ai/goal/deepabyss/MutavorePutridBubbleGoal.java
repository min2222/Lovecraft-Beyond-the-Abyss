package com.min01.beyondtheabyss.entity.ai.goal.deepabyss;

import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.goal.BasicBTASkillGoal;
import com.min01.beyondtheabyss.entity.deepabyss.EntityMutavore;
import com.min01.beyondtheabyss.entity.projectile.EntityPutridBubble;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MutavorePutridBubbleGoal extends BasicBTASkillGoal<EntityMutavore>
{
	public MutavorePutridBubbleGoal(EntityMutavore mob)
	{
		super(mob);
	}
	
	@Override
	public void start() 
	{
		super.start();
		this.mob.setAnimationState(1);
	}
	
	@Override
	public boolean canUse() 
	{
		return super.canUse() && this.mob.distanceTo(this.mob.getTarget()) >= 8.0F;
	}
	
	@Override
	public void tick() 
	{
		if(this.mob.getAnimationTick() <= this.getSkillUsingTime() - 10 && this.mob.getTarget() != null) 
		{
			if(this.mob.tickCount % 5 == 0)
			{
				this.mob.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP);
				for(int i = 0; i < 3; i++)
				{
					EntityPutridBubble bubble = new EntityPutridBubble(BTAEntities.PUTRID_BUBBLE.get(), this.mob.level);
					bubble.setOwner(this.mob);
					Vec3 lookPos = BTAUtil.getLookPos(new Vec2(this.mob.getXRot(), this.mob.getYHeadRot()), this.mob.position(), 0, 1.5F, 4.0F);
					bubble.setPos(lookPos.add(this.mob.level.random.nextFloat() * 0.1F, this.mob.level.random.nextFloat() * 0.1F, this.mob.level.random.nextFloat() * 0.1F));
					bubble.setDeltaMovement(BTAUtil.fromToVector(bubble.position(), BTAUtil.getSpreadPosition(this.mob.getTarget(), 1.5F), 0.25F));
					bubble.lookAt(Anchor.EYES, this.mob.getTarget().getEyePosition());
					this.mob.level.addFreshEntity(bubble);
				}
			}
		}
	}

	@Override
	protected void performSkill()
	{
		this.mob.setAnimationState(2);
	}
	
	@Override
	public void stop() 
	{
		super.stop();
		this.mob.setAnimationState(0);
	}

	@Override
	protected int getSkillUsingTime() 
	{
		return 20 + 60;
	}
	
	@Override
	protected int getSkillWarmupTime() 
	{
		return 70;
	}

	@Override
	protected int getSkillUsingInterval()
	{
		return 100;
	}
}
