package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDeepAbyssMob extends AbstractBTAMob
{
	public AbstractDeepAbyssMob(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, this.getBodyRotationSpeed(), 0.02F, 0.1F, true);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
	}
	
	@Override
	public MobType getMobType() 
	{
		return MobType.WATER;
	}
    
    @Override
    protected PathNavigation createNavigation(Level p_27480_) 
    {
    	return new WaterBoundPathNavigation(this, p_27480_);
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
	public void tick()
	{
		super.tick();
		//for keep update body rotation speed
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, this.getBodyRotationSpeed(), 0.02F, 0.1F, true);
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
	
	public abstract int getBodyRotationSpeed();
}
