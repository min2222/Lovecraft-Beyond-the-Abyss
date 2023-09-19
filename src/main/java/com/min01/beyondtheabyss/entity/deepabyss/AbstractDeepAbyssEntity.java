package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractAbyssEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDeepAbyssEntity extends AbstractAbyssEntity
{
	private AbstractDeepAbyssEntity.DeepAbyssSkills currentSkill = AbstractDeepAbyssEntity.DeepAbyssSkills.NONE;
	
	public AbstractDeepAbyssEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		//this.moveControl = new DeepAbyssEntityMoveControl(this);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
	}
    
    @Override
	public void move(MoverType p_19973_, Vec3 p_19974_) 
	{
		if(this.shouldMove())
		{
			super.move(p_19973_, p_19974_);
		}
		else if(!this.shouldMove())
		{
			super.move(p_19973_, Vec3.ZERO);
		}
	}
	
	protected AbstractDeepAbyssEntity.DeepAbyssSkills getCurrentSkill() 
	{
		return !this.level.isClientSide ? this.currentSkill : AbstractDeepAbyssEntity.DeepAbyssSkills.byId(this.entityData.get(DATA_SKILL_ID));
	}
	
	public void setIsUsingSkill(AbstractDeepAbyssEntity.DeepAbyssSkills p_33728_) 
	{
		this.currentSkill = p_33728_;
		this.entityData.set(DATA_SKILL_ID, (byte)p_33728_.id);
	}
	
	public static enum DeepAbyssSkills
	{
		NONE(0),
		GHIDRUTH_DASH(1),
		GHIDRUTH_BITE(2),
		GHIDRUTH_TAIL_SLAP(3);
		
		int id;

		private DeepAbyssSkills(int p_33754_) 
		{
			this.id = p_33754_;
		}
		
		public static AbstractDeepAbyssEntity.DeepAbyssSkills byId(int p_33759_)
		{
			for(AbstractDeepAbyssEntity.DeepAbyssSkills skils : values()) 
			{
				if (p_33759_ == skils.id) 
				{
					return skils;
				}
			}
			return NONE;
		}
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
}
