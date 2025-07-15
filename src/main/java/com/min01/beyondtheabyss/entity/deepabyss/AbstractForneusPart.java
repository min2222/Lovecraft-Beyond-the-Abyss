package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractWormPart;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractForneusPart extends AbstractWormPart<AbstractForneusPart>
{
	public AbstractForneusPart(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.BOSS;
	}
	
	@Override
	public float insideWaterSpeed() 
	{
		return 0.8F;
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
	protected void doPush(Entity p_21294_)
	{
		if(!(p_21294_ instanceof AbstractForneusPart))
		{
			super.doPush(p_21294_);
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
	public boolean canSwim()
	{
		return false;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.getHead() != null)
		{
			EntityForneusHead head = (EntityForneusHead) this.getHead();
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
