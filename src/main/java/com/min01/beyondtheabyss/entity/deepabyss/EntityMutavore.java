package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.KinematicChain;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityMutavore extends AbstractDeepAbyssMonster
{
	public final KinematicChain[] chains = new KinematicChain[] { 
			new KinematicChain(this, 3, 1.75F),
			new KinematicChain(this, 3, 1.75F),
			new KinematicChain(this, 3, 1.75F),
			new KinematicChain(this, 3, 1.75F),
			new KinematicChain(this, 3, 1.75F)
	};
	
	public final KinematicChain tongueChain = new KinematicChain(this, 3, 0.875F);
	
	public EntityMutavore(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.posArray = new Vec3[7];
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 150.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
        		.add(Attributes.FOLLOW_RANGE, 50.0F)
        		.add(Attributes.ARMOR, 8.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntityMutavore> partBuilder = new EntityPartBuilder<EntityMutavore>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		for(int i = 0; i < this.chains.length; i++)
		{
			KinematicChain chain = this.chains[i];
			chain.tick();
			if(this.posArray[i] != null)
			{
				chain.setAnchorPos(this.posArray[i]);
			}
		}
		if(this.posArray[5] != null)
		{
			this.tongueChain.setAnchorPos(this.posArray[5]);
		}
		if(this.posArray[6] != null)
		{
			this.tongueChain.tick();
			this.tongueChain.setTarget(this.posArray[6]);
		}
		if(this.getTarget() != null)
		{
			this.posArray[6] = this.getTarget().getEyePosition();
			BTANetwork.sendToAll(new UpdatePosArrayPacket(this, this.getTarget().getEyePosition(), 6));
		}
	}
}
