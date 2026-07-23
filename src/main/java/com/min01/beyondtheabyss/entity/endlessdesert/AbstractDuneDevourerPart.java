package com.min01.beyondtheabyss.entity.endlessdesert;

import com.min01.beyondtheabyss.entity.AbstractWormPart;
import com.min01.beyondtheabyss.entity.BTACameraShakeEntity;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDuneDevourerPart extends AbstractWormPart<AbstractDuneDevourerPart>
{
	public boolean inWall;
	
	public AbstractDuneDevourerPart(EntityType<? extends AbstractWormPart<AbstractDuneDevourerPart>> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.noCulling = true;
	}
	
	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.NETURAL;
	}
	
	@Override
	public int getChainLength() 
	{
		return 33;
	}
	
	@Override
	public float getChainSpeed() 
	{
		return 0.35F;
	}
	
	@Override
	public float getSegmentDistance(int index) 
	{
		return 6.0F;
	}
	
	@Override
	protected void doPush(Entity pEntity)
	{
		if(!(pEntity instanceof AbstractDuneDevourerPart))
		{
			super.doPush(pEntity);
		}
	}
	
	@Override
	public boolean displayFireAnimation() 
	{
		return false;
	}
	
	@Override
	protected void pushEntities() 
	{
		
	}
	
	@Override
	public boolean isWormChain() 
	{
		return false;
	}
	
	@Override
	public boolean removeWhenFarAway(double pDistanceToClosestPlayer) 
	{
		return false;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(!this.getBlockStateOn().isAir() && !this.inWall)
		{
			BTACameraShakeEntity.cameraShake(this.level, this.position(), 50.0F, 0.05F, 0, 5);
			this.inWall = true;
		}
		if(this.getBlockStateOn().isAir() && this.inWall)
		{
			BTACameraShakeEntity.cameraShake(this.level, this.position(), 50.0F, 0.05F, 0, 5);
			this.inWall = false;
		}
		if(this.getHead() != null)
		{
			DuneDevourerHeadEntity head = (DuneDevourerHeadEntity) this.getHead();
			if(head.chain != null)
			{
				ChainSegment segment = head.chain.getSegments().get(Math.max(head.chain.getSegments().size() - (this.getIndex() + 2), 0));
				Vec3 pos = segment.getPos();
				Vec2 rot = segment.getRot();
				this.setPos(pos);
				this.setXRot(rot.x);
				this.setYRot(rot.y);
				this.setYBodyRot(rot.y);
				this.setYHeadRot(rot.y);
				
				this.xRotO = rot.x;
				this.yRotO = rot.y;
				this.yHeadRotO = rot.y;
				this.yBodyRotO = rot.y;
			}
		}
		BTAUtil.forceTick(this);
	}
}
