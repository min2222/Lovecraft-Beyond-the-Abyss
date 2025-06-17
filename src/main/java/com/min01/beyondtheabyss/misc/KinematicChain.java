package com.min01.beyondtheabyss.misc;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class KinematicChain 
{
	protected Entity entity;
	protected Vec3 target;
	protected Vec3 anchorPos;
	protected ChainSegment[] segments;
	protected Vec2 initialRot = Vec2.ZERO;
	
	public KinematicChain(Entity entity, int length) 
	{
		this(entity, length, 0.0F);
	}
	
	public KinematicChain(Entity entity, int length, float distance) 
	{
		this.entity = entity;
		this.segments = new ChainSegment[length];
		this.createSegments(distance);
	}
	
	public void createSegments(float distance)
	{
		this.setupSegments(distance);
		this.setupPos();
	}
	
	public void setupSegments(float distance)
	{
		for(int i = 0; i < this.segments.length; i++)
		{
			this.segments[i] = new ChainSegment(this.initialRot, distance);
		}
	}
	
	public void setupPos()
	{
		for(ChainSegment segment : this.segments)
		{
			segment.setPos(this.entity.position());
		}
	}
	
	public void setOldPosAndRot()
	{
		for(ChainSegment segment : this.segments)
		{
			segment.setOldPos(segment.position);
			segment.setOldRot(segment.rotation);
		}
	}
	
	public void tickBobbit()
	{
		if(this.target != null)
		{
			if(!this.target.equals(Vec3.ZERO))
			{
				ChainSegment tip = this.getTipSegment();
				if(tip.getPos().distanceTo(this.target) > 2.0F)
				{
					tip.setRot(this.lookAt(tip.getPos(), this.target));
					tip.setPos(this.getLookPos(tip.getRot(), tip.getPos(), 0.0F, 0.0F, tip.distance));
				}
				for(int i = 2; i < this.segments.length; i++)
				{
					int index = i - 1;
					ChainSegment current = this.segments[this.segments.length - i];
					ChainSegment next = this.segments[this.segments.length - index];
					current.setRot(this.lookAt(current.getPos(), next.getPos()));
					current.setPos(this.getLookPos(current.getRot(), next.getPos(), 0.0F, 0.0F, -current.distance));
				}
				
				for(int i = 0; i < this.segments.length - 1; i++)
				{
					ChainSegment current = this.segments[i];
					ChainSegment next = this.segments[i + 1];
					current.setRot(this.lookAt(current.getPos(), next.getPos()));
					next.setPos(this.getLookPos(current.getRot(), current.getPos(), 0.0F, 0.0F, next.distance));
				}
			}
			else if(this.anchorPos != null)
			{
				ChainSegment tip = this.getTipSegment();
				if(tip.getPos().distanceTo(this.anchorPos) > 2.0F)
				{
					for(int i = 1; i < this.segments.length; i++)
					{
						ChainSegment current = this.segments[i];
						ChainSegment next = this.segments[i - 1];
						Vec2 rot = this.lookAt(current.getPos(), next.getPos());
						current.setPos(this.getLookPos(rot, current.getPos(), 0.0F, 0.0F, current.distance));
					}
				}
				else
				{
					for(ChainSegment segment : this.segments)
					{
						segment.setRot(this.initialRot);
						segment.setPos(this.anchorPos);
					}
				}
			}
		}
		
		if(this.anchorPos != null)
		{
			this.segments[0].setPos(this.anchorPos);
		}
	}
	
	public void tick() 
	{
		if(this.target != null)
		{
			ChainSegment tip = this.getTipSegment();
			Vec3 tipPos = tip.getPos();
			Vec3 dir = this.target.subtract(tipPos);
			double minDistance = 0.001;
			if(dir.lengthSqr() < minDistance * minDistance)
			{
			    dir = dir.normalize().scale(minDistance);
			}
			Vec3 safeTarget = tipPos.add(dir);
			tip.setRot(this.lookAt(safeTarget, this.target));
			tip.setPos(this.getLookPos(tip.getRot(), safeTarget, 0.0F, 0.0F, tip.distance));
		}
		
		if(this.anchorPos != null)
		{
			this.segments[0].setPos(this.anchorPos);
		}
		
		for(int i = 1; i < this.segments.length; i++)
		{
		    int index = i - 1;
		    ChainSegment current = this.segments[this.segments.length - i - 1];
		    ChainSegment next = this.segments[this.segments.length - index - 1];
		    current.setRot(this.lookAt(current.getPos(), next.getPos()));
		    current.setPos(this.getLookPos(current.getRot(), next.getPos(), 0.0F, 0.0F, -current.distance));
		}
		
		for(int i = 0; i < this.segments.length - 1; i++)
		{
			ChainSegment current = this.segments[i];
			ChainSegment next = this.segments[i + 1];
			current.setRot(this.lookAt(current.getPos(), next.getPos()));
			next.setPos(this.getLookPos(current.getRot(), current.getPos(), 0.0F, 0.0F, next.distance));
		}
	}
	
	public void addParticle(Vec3 pos, double deltaX, double deltaY, double deltaZ, double speed, int count)
	{
		Level level = this.entity.level;
		for(int i = 0; i < count; i++)
		{
            double d1 = level.random.nextGaussian() * deltaX;
            double d3 = level.random.nextGaussian() * deltaY;
            double d5 = level.random.nextGaussian() * deltaZ;
            double d6 = level.random.nextGaussian() * speed;
            double d7 = level.random.nextGaussian() * speed;
            double d8 = level.random.nextGaussian() * speed;
			level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE, pos.x + d1, pos.y + d3, pos.z + d5, d6, d7, d8);
		}
	}
	
	public Vec2 lookAt(Vec3 startPos, Vec3 pos)
	{
		Vec3 vec3 = startPos;
		double d0 = pos.x - vec3.x;
		double d1 = pos.y - vec3.y;
		double d2 = pos.z - vec3.z;
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float xRot = Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180.0F / (float)Math.PI))));
		float yRot = Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180.0F / (float)Math.PI)) - 90.0F);
	    return new Vec2(xRot, yRot);
	}
	
	//net.minecraft.commands.arguments.coordinates.LocalCoordinates;
	public Vec3 getLookPos(Vec2 rotation, Vec3 position, double left, double up, double forwards) 
	{
		Vec2 vec2 = rotation;
		Vec3 vec3 = position;
		float f = Mth.cos((vec2.y + 90.0F) * ((float)Math.PI / 180.0F));
		float f1 = Mth.sin((vec2.y + 90.0F) * ((float)Math.PI / 180.0F));
		float f2 = Mth.cos(-vec2.x * ((float)Math.PI / 180.0F));
		float f3 = Mth.sin(-vec2.x * ((float)Math.PI / 180.0F));
		float f4 = Mth.cos((-vec2.x + 90.0F) * ((float)Math.PI / 180.0F));
		float f5 = Mth.sin((-vec2.x + 90.0F) * ((float)Math.PI / 180.0F));
		Vec3 vec31 = new Vec3((double)(f * f2), (double)f3, (double)(f1 * f2));
		Vec3 vec32 = new Vec3((double)(f * f4), (double)f5, (double)(f1 * f4));
		Vec3 vec33 = vec31.cross(vec32).scale(-1.0D);
		double d0 = vec31.x * forwards + vec32.x * up + vec33.x * left;
		double d1 = vec31.y * forwards + vec32.y * up + vec33.y * left;
		double d2 = vec31.z * forwards + vec32.z * up + vec33.z * left;
		return new Vec3(vec3.x + d0, vec3.y + d1, vec3.z + d2);
	}
	
	public void setInitialRot(Vec2 rot)
	{
		this.initialRot = rot;
	}
	
	public Vec2 getInitialRot()
	{
		return this.initialRot;
	}
	
	public ChainSegment[] getSegments()
	{
		return this.segments;
	}
	
	public Entity getEntity()
	{
		return this.entity;
	}
	
	public void setAnchorPos(Vec3 target)
	{
		this.anchorPos = target;
	}
	
	public Vec3 getAnchorPos()
	{
		return this.anchorPos;
	}
	
	public void setTarget(Vec3 target)
	{
		this.target = target;
	}
	
	public Vec3 getTarget()
	{
		return this.target;
	}
	
	public ChainSegment getTipSegment()
	{
		return this.segments[this.segments.length - 1];
	}
	
	public static class ChainSegment
	{
		protected Vec3 position = Vec3.ZERO;
		protected Vec3 oldPosition = Vec3.ZERO;
		protected Vec2 rotation = Vec2.ZERO;
		protected Vec2 oldRotation = Vec2.ZERO;
		protected float distance;
		
		public ChainSegment(Vec2 initialRot, float distance) 
		{
			this.rotation = initialRot;
			this.distance = distance;
		}
		
    	public Vec3 position(float partialTicks)
    	{
    		return this.oldPosition.lerp(this.position, partialTicks);
    	}
    	
    	public Vec2 getRot(float partialTick)
    	{
            float xRot = Mth.lerp(partialTick, this.oldRotation.x, this.rotation.x);
            float yRot = Mth.rotLerp(partialTick, this.oldRotation.y, this.rotation.y);
    		return new Vec2(xRot, yRot);
    	}
		
		public void setRot(Vec2 rot)
		{
			this.rotation = rot;
		}
		
		public Vec2 getRot()
		{
			return this.rotation;
		}
		
		public void setOldRot(Vec2 rot)
		{
			this.oldRotation = rot;
		}
		
		public Vec2 getOldRot()
		{
			return this.oldRotation;
		}
		
		public void setPos(Vec3 pos)
		{
			this.position = pos;
		}
		
		public Vec3 getPos()
		{
			return this.position;
		}
		
		public void setOldPos(Vec3 pos)
		{
			this.oldPosition = pos;
		}
		
		public Vec3 getOldPos()
		{
			return this.oldPosition;
		}
		
		public void setDistance(float distance)
		{
			this.distance = distance;
		}
		
		public float getDistance()
		{
			return this.distance;
		}
	}
}
