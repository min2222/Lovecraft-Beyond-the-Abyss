package com.min01.beyondtheabyss.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class KinematicChain 
{
	private Entity entity;
	private Entity target;
	private ChainSegment[] segments = new ChainSegment[13];
	
	public KinematicChain(Entity entity) 
	{
		this.entity = entity;
		this.createSegments();
	}
	
	public void createSegments()
	{
		this.setupSegments();
		this.setupParents();
		this.setupPos();
	}
	
	public void setupSegments()
	{
		for(int i = 0; i < this.segments.length - 1; i++)
		{
			this.segments[i] = new ChainSegment("segment");
		}
		
		this.segments[this.segments.length - 1] = new ChainSegment("tip");
	}
	
	public void setupParents()
	{
		for(int i = 0; i < this.segments.length - 1; i++)
		{
			this.segments[i].setParent(this.segments[i + 1]);
		}
	}
	
	public void setupPos()
	{
		for(ChainSegment segment : this.segments)
		{
			segment.setPos(this.entity.position());
		}
	}
	
	public void tick()
	{
		this.segments[0].setPos(this.entity.position().add(BTAUtil.getLookPos(this.entity.getXRot(), this.entity.getYRot(), 0, -1)));
		
		for(ChainSegment segment : this.segments)
		{
			ChainSegment parent = segment.getParent();
			Vec3 pos = segment.getPos();
			
			if(parent != null)
			{
				segment.setRot(this.lookAt(pos, parent.getPos()));
				if(segment != this.segments[0])
				{
					segment.setPos(this.getLookPos(this.lookAt(parent.getPos(), pos), parent.getPos(), 0, 0, 1.0D));
				}
				
				parent.setRot(this.lookAt(parent.getPos(), pos));
				parent.setPos(this.getLookPos(this.lookAt(pos, parent.getPos()), pos, 0, 0, 1.0D));
			}
			
			if(segment.getName() == "tip")
			{
				this.followTarget();
			}
		}
	}
	
	public void followTarget()
	{
		ChainSegment tip = this.segments[this.segments.length - 1];
		if(this.target != null)
		{
			Vec3 pos = this.target.position().add(0, this.entity.getEyeHeight(), 0);
			tip.setRot(this.lookAt(tip.getPos(), pos));
			tip.setPos(this.getLookPos(this.lookAt(pos, tip.getPos()), pos, 0, 0, 0.1D));
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
	
	public ChainSegment[] getSegments()
	{
		return this.segments;
	}
	
	public Entity getEntity()
	{
		return this.entity;
	}
	
	public void setTarget(Entity target)
	{
		this.target = target;
	}
	
	public Entity getTarget()
	{
		return this.target;
	}
	
	public static class ChainSegment
	{
		protected Vec3 position = Vec3.ZERO;
		protected Vec2 rotation = Vec2.ZERO;
		protected String name;
		protected ChainSegment parent;
		
		public ChainSegment(String name)
		{
			this.name = name;
		}
		
		public void setRot(Vec2 rot)
		{
			this.rotation = rot;
		}
		
		public Vec2 getRot()
		{
			return this.rotation;
		}
		
		public void setPos(Vec3 pos)
		{
			this.position = pos;
		}
		
		public Vec3 getPos()
		{
			return this.position;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public void setParent(ChainSegment parent)
		{
			this.parent = parent;
		}
		
		public ChainSegment getParent()
		{
			return this.parent;
		}
	}
}
