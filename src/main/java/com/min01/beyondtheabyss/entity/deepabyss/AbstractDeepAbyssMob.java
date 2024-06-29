package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.ai.control.AbyssFishMoveControl;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDeepAbyssMob extends AbstractBTAMob
{
	public AbstractDeepAbyssMob(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
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
	
	public void handleAirSupply(int p_30344_) 
	{
		if(this.isAlive() && !this.isInWaterOrBubble() && !this.canBreathOutsideWater())
		{
			this.setAirSupply(p_30344_ - 1);
			if(this.getAirSupply() == -20) 
			{
				this.setAirSupply(0);
				this.hurt(DamageSource.DROWN, 2.0F);
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
		int i = this.getAirSupply();
		this.handleAirSupply(i);
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.isSwimable())
		{
			this.moveControl = this.getFishMoveControl();
			this.lookControl = new SmoothSwimmingLookControl(this, 10);
		}
	}
	
	public MoveControl getFishMoveControl()
	{
		return new AbyssFishMoveControl(this, this.getBodyRotationSpeed(), this.getInsideWaterSpeed());
	}
    
    @Override
    public void travel(Vec3 p_27490_) 
    {
    	if(this.isEffectiveAi() && this.isInWater() && this.isSwimable())
    	{
    		this.moveRelative(this.getSpeed(), p_27490_);
    		this.move(MoverType.SELF, this.getDeltaMovement());
    		this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
    		if(this.getTarget() == null) 
    		{
    			this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
    		}
    	}
    	else
    	{
    		super.travel(p_27490_);
    	}
    }
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
    	if(this.isSwimable())
    	{
            this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED), 20));
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
		float yRot = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
		this.setXRot(Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)))));
		this.setYRot(BTAUtil.rotlerp(this.getYRot(), yRot, (float)this.getBodyRotationSpeed()));
		this.setYHeadRot(this.getYRot());
		this.xRotO = this.getXRot();
		this.yRotO = this.getYRot();
		this.yHeadRotO = this.yHeadRot;
		this.yBodyRot = this.yHeadRot;
		this.yBodyRotO = this.yBodyRot;
	}
	
	public int getBodyRotationSpeed()
	{
		return 10;
	}
	
	public float getInsideWaterSpeed()
	{
		return 0.3F;
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
