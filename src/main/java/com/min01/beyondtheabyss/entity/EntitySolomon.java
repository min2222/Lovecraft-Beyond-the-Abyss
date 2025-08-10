package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntitySolomon extends AbstractBTACreature
{
	public EntitySolomon(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 20.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTACreature> createBuilder()
	{
    	EntityPartBuilder<EntitySolomon> partBuilder = new EntityPartBuilder<EntitySolomon>(this);
    	return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.MISC;
	}
	
	@Override
	protected void registerGoals() 
	{
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
	}
	
	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) 
	{
		if(!p_21016_.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
		{
			return false;
		}
		return super.hurt(p_21016_, p_21017_);
	}
	
	@Override
	public void setDeltaMovement(Vec3 p_20257_)
	{
		super.setDeltaMovement(new Vec3(0, p_20257_.y, 0));
	}
}
