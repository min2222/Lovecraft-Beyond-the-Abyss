package com.min01.beyondtheabyss.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WormKinematicChain extends KinematicChain
{
	protected final float distance = 0.2F;
	protected final float speed = 0.5F;
	public WormKinematicChain(Entity entity) 
	{
		super(entity);
	}

	@Override
	public void tick() 
	{
		for(ChainSegment segment : this.segments)
		{
			ChainSegment parent = segment.getParent();
			
			if(parent != null)
			{
				float xz = 0.0F;
				float yRot = 0.0F;
				float xRot = 0.0F;
				
				segment.setRot(this.lookAt(segment.getPos(), parent.getPos()));
				segment.setPos(new Vec3(
						parent.getPos().x - this.calculateViewVector(segment.getRot().x, segment.getRot().y).x * this.distance,
						parent.getPos().y - this.calculateViewVector(segment.getRot().x, segment.getRot().y).y * this.distance,
						parent.getPos().z - this.calculateViewVector(segment.getRot().x, segment.getRot().y).z * this.distance));
				yRot = segment.getRot().y;
				xRot = segment.getRot().x;
				
				if(180.0F + yRot + 180.0F - segment.getRot().y < Math.abs(segment.getRot().y - yRot)) 
				{
					xz = (180.0F + yRot + 180.0F - segment.getRot().y) * -1.0F * this.speed;
				}
				else if((180.0F + segment.getRot().y + 180.0F) - yRot < Math.abs(segment.getRot().y - yRot)) 
				{
					xz = ((180.0F + segment.getRot().y + 180.0F) - yRot) * 1.0F * this.speed;
				}
				else
				{
					xz = (segment.getRot().y - yRot) * this.speed;
				}
				
				segment.setRot(new Vec2(xRot + (segment.getRot().x - xRot) * this.speed, yRot + xz));
				segment.setPos(new Vec3(
						parent.getPos().x - this.calculateViewVector(segment.getRot().x, segment.getRot().y).x * this.distance,
						parent.getPos().y - this.calculateViewVector(segment.getRot().x, segment.getRot().y).y * this.distance,
						parent.getPos().z - this.calculateViewVector(segment.getRot().x, segment.getRot().y).z * this.distance));
			}
		}
	}
	
	public Vec3 calculateViewVector(float x, float y)
	{
		float f = x * ((float)Math.PI / 180.0F);
		float f1 = -y * ((float)Math.PI / 180.0F);
		float f2 = Mth.cos(f1);
		float f3 = Mth.sin(f1);
		float f4 = Mth.cos(f);
		float f5 = Mth.sin(f);
		return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
	}
}
