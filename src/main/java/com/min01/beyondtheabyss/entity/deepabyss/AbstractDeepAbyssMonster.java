package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.control.BTASwimmingMoveControl;
import com.min01.beyondtheabyss.misc.BTAMobType;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDeepAbyssMonster extends AbstractBTAMonster
{
	public AbstractDeepAbyssMonster(EntityType<? extends Monster> pEntityType, Level pLevel) 
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
		this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1.0F, 50)
		{
			@Override
			public boolean canUse() 
			{
				return super.canUse() && AbstractDeepAbyssMonster.this.canSwim();
			}
		});
    }
    
    @Override
    public void registerDefaultGoals() 
    {
    	
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
	
	public boolean canSwim()
	{
		return this.canMove() && !this.hasTarget();
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
