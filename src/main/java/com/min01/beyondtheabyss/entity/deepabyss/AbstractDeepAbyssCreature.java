package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.entity.ai.control.BTASwimmingMoveControl;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDeepAbyssCreature extends AbstractBTACreature
{
	public AbstractDeepAbyssCreature(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.noCulling = this.getBTAMobType() == BTAMobType.BOSS;
		if(this.isSwimable())
		{
			this.moveControl = this.getSwimmingMoveControl();
			this.lookControl = this.getSwimmingLookControl();
		}
	}
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
		this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1.0F, 40)
		{
			@Override
			public boolean canUse() 
			{
				return super.canUse() && AbstractDeepAbyssCreature.this.canMoveAround();
			}
		});
    }
    
    @Override
    public void registerDefaultGoals() 
    {
    	if(!this.isSwimable())
    	{
    		super.registerDefaultGoals();
    	}
    }
	
	@Override
	public MobType getMobType() 
	{
		return MobType.WATER;
	}
    
    @Override
    protected PathNavigation createNavigation(Level pLevel) 
    {
    	return new WaterBoundPathNavigation(this, pLevel);
    }
    
    @Override
    public boolean checkSpawnObstruction(LevelReader worldReader)
    {
        return worldReader.isUnobstructed(this);
    }
	
	@Override
	public boolean canBreatheUnderwater() 
	{
		return true;
	}
	
	@Override
	public boolean isPushedByFluid() 
	{
		return false;
	}
	
	@Override
	protected boolean isAffectedByFluids()
	{
		return false;
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
		this.lookAt(Anchor.EYES, this.getTarget().getEyePosition());
	}
	
	public void handleAirSupply(int supply) 
	{
		if(this.isAlive() && !this.isInWaterOrBubble() && !this.canBreathOutsideWater())
		{
			this.setAirSupply(supply - 1);
			if(this.getAirSupply() == -20) 
			{
				this.setAirSupply(0);
				this.hurt(this.damageSources().drown(), 2.0F);
			}
		}
		else 
		{
			this.setAirSupply(300);
		}
	}
	
	@Override
	public void baseTick() 
	{
		super.baseTick();
		this.handleAirSupply(this.getAirSupply());
	}
	
	public LookControl getSwimmingLookControl()
	{
		return new SmoothSwimmingLookControl(this, 10);
	}
	
	public MoveControl getSwimmingMoveControl()
	{
		return new BTASwimmingMoveControl(this);
	}
    
    @Override
    public void travel(Vec3 pTravelVector) 
    {
    	if(this.isEffectiveAi() && this.isInWater() && this.isSwimable())
    	{
    		this.moveRelative(this.getSpeed(), pTravelVector);
    		this.move(MoverType.SELF, this.getDeltaMovement());
    		this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
    	}
    	else
    	{
    		super.travel(pTravelVector);
    	}
    }
	
	public boolean canBreathOutsideWater()
	{
		return false;
	}
	
	public boolean isSwimable()
	{
		return true;
	}
}
