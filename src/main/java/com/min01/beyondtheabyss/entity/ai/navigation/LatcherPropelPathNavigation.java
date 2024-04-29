package com.min01.beyondtheabyss.entity.ai.navigation;

import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LatcherPropelPathNavigation extends WaterBoundPathNavigation
{
	public LatcherPropelPathNavigation(Mob p_26594_, Level p_26595_)
	{
		super(p_26594_, p_26595_);
	}

	@Override
	public void tick() 
	{
		++this.tick;
		if (this.hasDelayedRecomputation) 
		{
			this.recomputePath();
		}
		
		if (!this.isDone())
		{
			if (this.canUpdatePath())
			{
				this.followThePath();
			} 
			else if (this.path != null && !this.path.isDone())
			{
				Vec3 vec3 = this.getTempMobPos();
				Vec3 vec31 = this.path.getNextEntityPos(this.mob);
				if (vec3.y > vec31.y && !this.mob.isOnGround() && Mth.floor(vec3.x) == Mth.floor(vec31.x) && Mth.floor(vec3.z) == Mth.floor(vec31.z)) {
					this.path.advance();
				}
			}
			
			DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
			if (!this.isDone()) 
			{
				Vec3 vec32 = this.path.getNextEntityPos(this.mob);
				float f2 = -0.1F + this.mob.getRandom().nextFloat() * 0.4F;
				this.mob.setDeltaMovement(this.mob.getDeltaMovement().x, f2, this.mob.getDeltaMovement().z);
				this.mob.getMoveControl().setWantedPosition(vec32.x, this.getGroundY(vec32), vec32.z, this.speedModifier);
			}
		}
	}
}
