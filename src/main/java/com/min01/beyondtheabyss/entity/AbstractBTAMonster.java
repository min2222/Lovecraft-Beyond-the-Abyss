package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.EntityBounds;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.multipart.IMultipart;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBTAMonster extends AbstractAnimatableMonster implements IMultipart
{
	public static final EntityDataAccessor<Vec3> LAST_LOOK_POS = SynchedEntityData.defineId(AbstractBTAMonster.class, BTAEntityDataSerializers.VEC3.get());
	
	public final EntityPartBuilder<? extends AbstractBTAMonster> partBuilder;
	
	public AbstractBTAMonster(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.partBuilder = this.createBuilder();
	}
	
	@Override
	protected void registerGoals() 
	{
		if(this.getMobType() != MobType.WATER)
		{
			super.registerGoals();
		}
        if(this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Player>(this, Player.class, false, false));
        }
        if(this.getBTAMobType() == BTAMobType.NETURAL || this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        }
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(LAST_LOOK_POS, Vec3.ZERO);
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful()
	{
		return this.getBTAMobType().despawnInPeaceful;
	}
	
	@Override
	public boolean removeWhenFarAway(double p_21542_) 
	{
		return this.getBTAMobType().removeWhenFarAway;
	}
	
	@Override
	public boolean isPreventingPlayerRest(Player p_33036_) 
	{
		return this.getBTAMobType() == BTAMobType.HOSTILE;
	}
	
	@Override
	public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) 
	{
		return this.partBuilder.hitbox.getBox(bounds);
	}

	@Override
	public EntityBounds getBounds() 
	{
		return this.partBuilder.hitbox;
	}
	
	@Override
	public EntityPartBuilder<?> getPartBuilder() 
	{
		return this.partBuilder;
	}
	
	public abstract EntityPartBuilder<? extends AbstractBTAMonster> createBuilder();
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
		
		if(this.getTarget() != null)
		{
			if(this.getBTAMobType().moveToTarget && this.canMove())
			{
				this.moveToTarget();
			}
			if(this.getBTAMobType().lookTarget)
			{
				this.lookTarget();
			}
		}
	}
	
	public void moveToTarget()
	{
		this.getNavigation().moveTo(this.getTarget(), this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
	}
	
	public void lookTarget()
	{
		if(this.canLook())
		{
			this.lookAt(Anchor.EYES, this.getTarget().getEyePosition());
			//this.getLookControl().setLookAt(this.getTarget(), 360.0F, 360.0F);
		}
		else if(!this.getLastLookPos().equals(Vec3.ZERO))
		{
			this.lookAt(Anchor.EYES, this.getLastLookPos());
			//Vec3 pos = this.getLastLookPos();
			//this.getLookControl().setLookAt(pos.x, pos.y, pos.z, 360.0F, 360.0F);
		}
	}
	
	public abstract BTAMobType getBTAMobType();
	
    public void setLastLookPos(Vec3 value)
    {
        this.entityData.set(LAST_LOOK_POS, value);
    }
    
    public Vec3 getLastLookPos()
    {
        return this.entityData.get(LAST_LOOK_POS);
    }
}
