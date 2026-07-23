package com.min01.beyondtheabyss.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class KinematicChain 
{
	public final Entity entity;
	public final List<ChainSegment> segments;
	public Vec3 target = Vec3.ZERO;
	public Vec3 anchorPos;
	public Vec2 initialRot = Vec2.ZERO;
	public boolean rotLerp;
	public float speed = 1.0F;
	public float lerpSpeed = 1.0F;
	
	public KinematicChain(Entity entity, int length) 
	{
		this(entity, length, 0.0F);
	}
	
	public KinematicChain(Entity entity, int length, float distance) 
	{
		this.entity = entity;
		this.segments = new ArrayList<>(length);
		this.createSegments(distance);
	}
	
	public void createSegments(float distance)
	{
		this.setupSegments(distance);
		this.setupPos();
	}
	
	public void setupSegments(float distance)
	{
		Collections.fill(this.segments, new ChainSegment(this.initialRot, distance));
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
		if(!this.target.equals(Vec3.ZERO))
		{
			this.tick();
		}
		else if(this.anchorPos != null)
		{
			for(int i = 1; i < this.segments.size(); i++)
			{
				ChainSegment current = this.segments.get(i);
				ChainSegment next = this.segments.get(i - 1);
				ChainSegment tip = this.getTipSegment();
		        Vec3 toTarget = this.anchorPos.subtract(current.getPos());
		        double dist = toTarget.length();
		        double moveDist = Math.min(dist, tip.distance);
				Vec2 rot = this.lookAt(current.getPos(), next.getPos());
				if(moveDist > 0.1F)
				{
					current.setPos(this.getLookPos(rot, current.getPos(), 0.0F, 0.0F, moveDist * this.speed));
				}
				else
				{
					current.setRot(this.initialRot);
					current.setPos(this.anchorPos);
				}
			}
		}
	}
	
	public void tick() 
	{
		if(!this.target.equals(Vec3.ZERO))
		{
			ChainSegment tip = this.getTipSegment();
			Vec3 tipPos = tip.getPos();
	        Vec3 toTarget = this.target.subtract(tipPos);
	        double dist = toTarget.length();
	        double moveDist = Math.min(dist, tip.distance);
	        if(this.rotLerp)
	        {
	            tip.setRot(this.lookAt(tipPos, this.target, tip.getRot()));
	        }
	        else
	        {
		        tip.setRot(this.lookAt(tipPos, this.target));
	        }
	        tip.setPos(this.getLookPos(tip.getRot(), tipPos, 0.0F, 0.0F, moveDist * this.speed));
		}
		
		for(int i = 1; i < this.segments.size(); i++)
		{
		    int index = i - 1;
		    ChainSegment current = this.segments.get(this.segments.size() - i - 1);
		    ChainSegment next = this.segments.get(this.segments.size() - index - 1);
	        if(this.rotLerp)
	        {
			    current.setRot(this.lookAt(current.getPos(), next.getPos(), current.getRot()));
	        }
	        else
	        {
			    current.setRot(this.lookAt(current.getPos(), next.getPos()));
	        }
		    current.setPos(this.getLookPos(current.getRot(), next.getPos(), 0.0F, 0.0F, -current.distance));
		}
		
		if(this.anchorPos != null)
		{
			this.segments.get(0).setPos(this.anchorPos);
		}
		
		for(int i = 0; i < this.segments.size() - 1; i++)
		{
			ChainSegment current = this.segments.get(i);
			ChainSegment next = this.segments.get(i + 1);
			if(this.rotLerp)
			{
				current.setRot(this.lookAt(current.getPos(), next.getPos(), current.getRot()));
			}
			else
			{
				current.setRot(this.lookAt(current.getPos(), next.getPos()));
			}
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
	
	public Vec2 lookAt(Vec3 startPos, Vec3 pos, Vec2 rot)
	{
        Vec2 targetRot = this.lookAt(startPos, pos);
        float xRot = BTAUtil.rotlerp(rot.x, targetRot.x, this.lerpSpeed);
        float yRot = BTAUtil.rotlerp(rot.y, targetRot.y, this.lerpSpeed);
        return new Vec2(xRot, yRot);
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
	
	public List<ChainSegment> getSegments()
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
		return this.segments.get(this.segments.size() - 1);
	}
	
	public ChainSegment getLastSegment()
	{
		return this.segments.get(this.segments.size() - 2);
	}
	
	public static class ChainSegment
	{
		protected Vec3 position = Vec3.ZERO;
		protected Vec3 oldPosition = Vec3.ZERO;
		protected Vec2 rotation = Vec2.ZERO;
		protected Vec2 oldRotation = Vec2.ZERO;
		protected float distance = 1.0F;
		
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
