package com.min01.beyondtheabyss.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WormChain 
{
	protected Entity entity;
	protected Vec3 targetPos;
	protected ChainSegment[] segments;
	
	public WormChain(Entity entity, int length) 
	{
		this.entity = entity;
		this.segments = new ChainSegment[length + 1];
		this.createSegments();
	}
	
	public void createSegments()
	{
		this.setupSegments();
		this.setupParents();
	}
	
	public void setupSegments()
	{
		for(int i = 0; i < this.segments.length; i++)
		{
			this.segments[i] = new ChainSegment("segment");
		}
	}
	
	public void setupParents()
	{
		for(int i = 0; i < this.segments.length - 1; i++)
		{
			this.segments[i].setParent(this.segments[i + 1]);
		}
	}
	
	//TODO
	public void tick()
	{
		for(ChainSegment segment : this.segments)
		{
			ChainSegment parent = segment.parent;
			if(parent != null)
			{
				Vec2 rot = this.lookAt(segment.position, parent.position, 10, segment.rotation.y);
				Vec3 pos = this.getLookPos(rot, parent.position, 0, 0, -0.6F);
				segment.setPos(pos);
				segment.setRot(rot);
			}
		}
		
		if(this.targetPos != null)
		{
			ChainSegment last = this.segments[this.segments.length - 1];
			Vec2 rot = this.lookAt(last.position, this.targetPos, 10, last.rotation.y);
			Vec3 pos = this.getLookPos(rot, this.targetPos, 0, 0, -0.7F);
			last.setPos(pos);
			last.setRot(rot);
		}
	}
	
	public Vec2 lookAt(Vec3 startPos, Vec3 targetPos, float rotationSpeed, float currentYRot)
	{
		Vec3 vec3 = startPos;
		double d0 = targetPos.x - vec3.x;
		double d1 = targetPos.y - vec3.y;
		double d2 = targetPos.z - vec3.z;
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float xRot = Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180.0F / (float)Math.PI))));
		float yRot = Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180.0F / (float)Math.PI)) - 90.0F);
	    return new Vec2(xRot, BTAUtil.rotlerp(currentYRot, yRot, rotationSpeed));
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
	
	public void setTargetPos(Vec3 targetPos)
	{
		this.targetPos = targetPos;
	}
	
	public Vec3 getTargetPos()
	{
		return this.targetPos;
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
