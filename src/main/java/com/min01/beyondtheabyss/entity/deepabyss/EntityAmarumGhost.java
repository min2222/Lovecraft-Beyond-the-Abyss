package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.misc.BTAMobType;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class EntityAmarumGhost extends AbstractDeepAbyssMob
{
	public EntityAmarumGhost(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(5);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 15)
    			.add(Attributes.MOVEMENT_SPEED, 0.3F)
        		.add(Attributes.FOLLOW_RANGE, 10);
    }

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
}
