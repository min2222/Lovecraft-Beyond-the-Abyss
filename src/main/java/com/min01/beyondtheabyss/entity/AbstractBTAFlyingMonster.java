package com.min01.beyondtheabyss.entity;

import com.min01.beyondtheabyss.entity.ai.control.BTAFlyingLookControl;
import com.min01.beyondtheabyss.entity.ai.control.BTAFlyingMoveControl;
import com.min01.beyondtheabyss.entity.ai.goal.LookAtTargetGoal;
import com.min01.beyondtheabyss.entity.ai.goal.MoveToTargetGoal;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.EntityBounds;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBTAFlyingMonster extends AbstractAnimatableFlyingMonster implements IMultipart, IBTAMob
{
	public static final EntityDataAccessor<Vec3> LAST_LOOK_POS = SynchedEntityData.defineId(AbstractBTAFlyingMonster.class, BTAEntityDataSerializers.VEC3.get());
	
	public final EntityPartBuilder<? extends AbstractBTAFlyingMonster> partBuilder;
	
	public AbstractBTAFlyingMonster(EntityType<? extends Monster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.lookControl = new BTAFlyingLookControl(this);
		this.moveControl = new BTAFlyingMoveControl(this);
		this.partBuilder = this.createBuilder();
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(LAST_LOOK_POS, Vec3.ZERO);
	}
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(0, new MoveToTargetGoal<>(this));
		this.goalSelector.addGoal(0, new LookAtTargetGoal<>(this));
        if(this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, false, false)
            {
            	@Override
            	protected AABB getTargetSearchArea(double pTargetDistance) 
            	{
            		return AbstractBTAFlyingMonster.this.getTargetSearchArea(pTargetDistance);
            	}
            });
        }
        if(this.getBTAMobType() == BTAMobType.NETURAL || this.getBTAMobType().alwaysHostile)
        {
            this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        }
	}
	
	public AABB getTargetSearchArea(double pTargetDistance)
	{
		return this.getBoundingBox().inflate(pTargetDistance, 4.0D, pTargetDistance);
	}
	
	@Override
	protected boolean shouldDespawnInPeaceful()
	{
		return this.getBTAMobType().despawnInPeaceful;
	}
	
	@Override
	public boolean removeWhenFarAway(double pDistanceToClosestPlayer) 
	{
		return this.getBTAMobType().removeWhenFarAway;
	}
	
	@Override
	public boolean isPreventingPlayerRest(Player pPlayer) 
	{
		return this.getBTAMobType().alwaysHostile;
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
	
	@Override
	public void tick() 
	{
		super.tick();
		
		if(this.partBuilder != null)
		{
			this.partBuilder.tick(1.0F);
		}
	}
	
    @Override
    public void lookAt(EntityAnchorArgument.Anchor pAnchor, Vec3 pTarget) 
    {
		Vec3 vec3 = pAnchor.apply(this);
		double d0 = pTarget.x - vec3.x;
		double d1 = pTarget.y - vec3.y;
		double d2 = pTarget.z - vec3.z;
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float yRot = (float)(Mth.atan2(d2, d0) * (double)(180.0F / (float)Math.PI)) - 90.0F;
		float xRot = (float)(-(Mth.atan2(d1, d3) * (double)(180.0F / (float)Math.PI)));
		this.setXRot(BTAUtil.rotlerp(this.getXRot(), xRot, this.maxTurnX()));
		this.setYRot(BTAUtil.rotlerp(this.getYRot(), yRot, this.maxTurnY()));
		this.setYHeadRot(this.getYRot());
		this.xRotO = this.getXRot();
		this.yRotO = this.getYRot();
		this.yHeadRotO = this.yHeadRot;
		this.yBodyRot = this.yHeadRot;
		this.yBodyRotO = this.yBodyRot;
    }
	
	@Override
	public void lookAtTarget() 
	{
		if(this.getLastLookPos().equals(Vec3.ZERO))
		{
			this.lookAt(Anchor.EYES, this.getTarget().getEyePosition());
		}
		else
		{
			this.lookAt(Anchor.FEET, this.getLastLookPos());
		}
	}
	
	public abstract EntityPartBuilder<? extends AbstractBTAFlyingMonster> createBuilder();
	
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
