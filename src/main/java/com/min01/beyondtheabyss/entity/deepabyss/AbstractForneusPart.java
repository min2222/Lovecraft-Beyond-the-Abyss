package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractForneusPart extends AbstractDeepAbyssWormPart<AbstractForneusPart>
{
	public AbstractForneusPart(EntityType<? extends AbstractDeepAbyssWormPart<AbstractForneusPart>> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.BOSS;
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
		return 12.5F;
	}
	
	@Override
	protected void doPush(Entity pEntity)
	{
		if(!(pEntity instanceof AbstractForneusPart))
		{
			super.doPush(pEntity);
		}
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
	public void tick() 
	{
		super.tick();
		if(this.getHead() != null)
		{
			ForneusHeadEntity head = (ForneusHeadEntity) this.getHead();
			if(head.chain != null)
			{
				ChainSegment segment = head.chain.getSegments()[Math.max(head.chain.getSegments().length - (this.getIndex() + 2), 0)];
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
	}
}
