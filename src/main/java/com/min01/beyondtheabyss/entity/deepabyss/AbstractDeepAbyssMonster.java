package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.IDeepAbyssMob;
import com.min01.beyondtheabyss.entity.ai.control.BTASwimmingMoveControl;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public abstract class AbstractDeepAbyssMonster extends AbstractBTAMonster implements IDeepAbyssMob
{
	public AbstractDeepAbyssMonster(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.noCulling = this.getBTAMobType() == BTAMobType.BOSS;
		this.moveControl = this.getSwimmingMoveControl();
		this.lookControl = this.getSwimmingLookControl();
	}
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
    	if(this.isSwimable())
     	{
    		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED), 40)
    		{
    			@Override
    			public boolean canUse() 
    			{
    				return super.canUse() && AbstractDeepAbyssMonster.this.canSwim();
    			}
    		});
     	}
    }
	
	@Override
	public MobType getMobType() 
	{
		return MobType.WATER;
	}
    
    @Override
    protected PathNavigation createNavigation(Level p_27480_) 
    {
    	return this.isSwimable() ? new WaterBoundPathNavigation(this, p_27480_) : super.createNavigation(p_27480_);
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
		return new BTASwimmingMoveControl(this, 0.1F, false);
	}
    
    @Override
    public void travel(Vec3 p_27490_) 
    {
    	if(this.isEffectiveAi() && this.isInWater() && this.isSwimable())
    	{
    		this.moveRelative(this.getSpeed(), p_27490_);
    		this.move(MoverType.SELF, this.getDeltaMovement());
    		this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
    	}
    	else
    	{
    		super.travel(p_27490_);
    	}
    }
	
    @Override
    public void setXRot(float p_146927_) 
    {
    	super.setXRot(BTAUtil.rotlerp(this.getXRot(), p_146927_, this.maxTurnX()));
    }
	
	@Override
	public void setYRot(float p_146923_)
	{
		super.setYRot(BTAUtil.rotlerp(this.getYRot(), p_146923_, this.maxTurnY()));
	}
	
	@Override
	public boolean canSwim()
	{
		return (!this.isUsingSkill() || this.getTarget() == null) && this.getNavigation().isDone();
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
