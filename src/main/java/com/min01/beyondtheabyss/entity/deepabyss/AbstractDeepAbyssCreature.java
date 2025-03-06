package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.entity.IDeepAbyssMob;
import com.min01.beyondtheabyss.entity.ai.control.BTASwimmingMoveControl;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.BTASwimmingGoal;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDeepAbyssCreature extends AbstractBTACreature implements IDeepAbyssMob
{
	public AbstractDeepAbyssCreature(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.moveControl = this.getSwimmingMoveControl();
		this.lookControl = this.getSwimmingLookControl();
	}
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
        this.goalSelector.addGoal(4, new BTASwimmingGoal(this, this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED))
        {
        	@Override
        	public boolean canUse() 
        	{
        		return super.canUse() && AbstractDeepAbyssCreature.this.isSwimable();
        	}
        });
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
	public void lookAt(Anchor p_20033_, Vec3 p_20034_)
	{
		Vec3 vec3 = p_20033_.apply(this);
		double d0 = p_20034_.x - vec3.x;
		double d1 = p_20034_.y - vec3.y;
		double d2 = p_20034_.z - vec3.z;
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		float yRot = (float)(Mth.atan2(d2, d0) * (double)(180.0F / (float)Math.PI)) - 90.0F;
		this.setXRot(Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180.0F / (float)Math.PI)))));
		this.setYRot(BTAUtil.rotlerp(this.getYRot(), yRot, (float)this.maxTurnY()));
		this.setYHeadRot(this.getYRot());
		this.xRotO = this.getXRot();
		this.yRotO = this.getYRot();
		this.yHeadRotO = this.yHeadRot;
		this.yBodyRot = this.yHeadRot;
		this.yBodyRotO = this.yBodyRot;
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
