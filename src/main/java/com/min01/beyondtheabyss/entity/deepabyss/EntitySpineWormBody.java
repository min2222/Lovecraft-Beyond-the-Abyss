package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.solomonlib.multipart.EntityPartBuilder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EntitySpineWormBody extends AbstractSpineWormPart
{
	public EntitySpineWormBody(EntityType<? extends AbstractSpineWormPart> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(15);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 50.0F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractSpineWormPart> createBuilder()
	{
		EntityPartBuilder<EntitySpineWormBody> partBuilder = new EntityPartBuilder<EntitySpineWormBody>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	protected boolean isAffectedByFluids() 
	{
		return false;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.getHead() != null)
		{
			EntitySpineWormHead head = (EntitySpineWormHead) this.getHead();
			if(head.chain != null)
			{
				ChainSegment segment = head.chain.getSegments()[this.getIndex()];
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
