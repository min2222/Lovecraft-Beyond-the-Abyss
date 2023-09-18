package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.parts.AbstractDeepAbyssEntityPart;
import com.min01.beyondtheabyss.entity.parts.AbyssEntityPart;
import com.min01.beyondtheabyss.util.AbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class EntityGhidruth extends AbstractMultipartDeepAbyssEntity
{
	public AbyssEntityPart head = new AbyssEntityPart(this, 4F, 2.5F);
	public AbyssEntityPart body = new AbyssEntityPart(this, 4.5F, 3F);
	public AbyssEntityPart tail = new AbyssEntityPart(this, 5.5F, 3.5F);
	public AbyssEntityPart[] parts = { this.head, this.body, this.tail };
	public AnimationState swimAnimationState = new AnimationState();
	public AnimationState biteAnimationState = new AnimationState();
	public AnimationState tailSlapAnimationState = new AnimationState();
	
	public EntityGhidruth(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_) 
	{
		super(p_33002_, p_33003_);
		this.isBoss = true;
		this.xpReward = 1000;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 300)
    			.add(Attributes.MOVEMENT_SPEED, 0.9D)
        		.add(Attributes.ATTACK_DAMAGE, 5)
        		.add(Attributes.FOLLOW_RANGE, 70)
        		.add(Attributes.ARMOR, 20)
        		.add(Attributes.ARMOR_TOUGHNESS, 20);
    }
    
    @Override
    public int getMaxHeadYRot() 
    {
    	return 25;
    }
    
    public static boolean checkGhidruthSpawnRules(EntityType<EntityGhidruth> p_218956_, ServerLevelAccessor p_218957_, MobSpawnType p_218958_, BlockPos p_218959_, RandomSource p_218960_) 
    {
    	if (!p_218957_.getFluidState(p_218959_.below()).is(FluidTags.WATER)) 
    	{
    		return false;
    	} 
    	else
    	{
    		boolean flag = p_218957_.getDifficulty() != Difficulty.PEACEFUL && (p_218958_ == MobSpawnType.SPAWNER || p_218957_.getFluidState(p_218959_).is(FluidTags.WATER));
			return isDeepEnoughToSpawn(p_218957_, p_218959_) && flag;
    	}
    }

	private static boolean isDeepEnoughToSpawn(LevelAccessor p_32367_, BlockPos p_32368_) 
    {
    	return p_32368_.getY() <= -200;
    }
    
    @Override
    protected ResourceLocation getDefaultLootTable() 
    {
    	return new ResourceLocation(BeyondtheAbyss.MODID, "entity/ghidruth");
    }
    
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) 
	{
        if (ANIMATION_STATE.equals(p_219422_) && this.level.isClientSide) 
        {
            switch (this.getAnimationState()) 
            {
        		case 0: 
        		{
        			this.stopAllAnimationStates();
        			break;
        		}
        		case 1:
        		{
        			this.stopAllAnimationStates();
        			break;
        		}
            }
        }
        super.onSyncedDataUpdated(p_219422_);
	}
    
    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1, 10));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 0.9F, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Player>(this, Player.class, false, false));
    }
    
    @Override
    public void aiStep()
    {
    	super.aiStep();
    	Vec3 head = AbyssUtil.caculateForwardVector(this, new Vec3(4, this.getEyeHeight(), 4));
    	Vec3 body = AbyssUtil.caculateBackwardVector(this, new Vec3(4, this.getEyeHeight(), 4));
    	Vec3 tail = AbyssUtil.caculateBackwardVector(this, new Vec3(9, this.getEyeHeight(), 9));
    	//Vec3 tailAnim = AbyssUtil.caculateSideVector(this, new Vec3(2, this.getEyeHeight(), 2));
    	this.head.moveTo(head.x, this.getY(), head.z);
    	this.body.moveTo(body.x, this.getY(), body.z);
    	this.tail.moveTo(tail.x, this.getY(), tail.z);
    	if(this.level.isClientSide)
    	{
    		if(AbyssUtil.isMoving(this) && this.isAlive())
    		{
    			this.swimAnimationState.startIfStopped(this.tickCount);
    		}
    		else if(!this.isAlive())
    		{
    			this.swimAnimationState.stop();
    		}
    	}
    }
    
    @Override
    public void travel(Vec3 p_27490_) 
    {
    	if (this.isEffectiveAi() && this.isInWater()) 
    	{
    		this.moveRelative(this.getSpeed(), p_27490_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) 
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
    protected BodyRotationControl createBodyControl()
    {
    	return new GhidruthBodyRotationControl(this);
    }
    
    class GhidruthBodyRotationControl extends BodyRotationControl
    {
    	private final Mob mob;
    	private int headStableTime;
    	private float lastStableYHeadRot;
		public GhidruthBodyRotationControl(Mob p_24879_) 
		{
			super(p_24879_);
			this.mob = p_24879_;
		}
    	
		@Override
		public void clientTick()
		{
			if (this.isMoving())
			{
				this.mob.yBodyRot = this.mob.getYRot();
				this.rotateHeadIfNecessary();
				this.lastStableYHeadRot = this.mob.yHeadRot;
				this.headStableTime = 0;
			} 
			else 
			{
				if (this.notCarryingMobPassengers()) 
				{
					if (Math.abs(this.mob.yHeadRot - this.lastStableYHeadRot) > 45.0F)
					{
						this.headStableTime = 0;
						this.lastStableYHeadRot = this.mob.yHeadRot;
						this.rotateBodyIfNecessary();
					} 
					else
					{
						++this.headStableTime;
						if (this.headStableTime > 40) 
						{
							this.rotateHeadTowardsFront();
						}
					}
				}
			}
		}
		
		private void rotateBodyIfNecessary() 
		{
			this.mob.yBodyRot = Mth.rotateIfNecessary(this.mob.yBodyRot, this.mob.yHeadRot, (float)this.mob.getMaxHeadYRot()) * 0.8F;
		}

		private void rotateHeadIfNecessary() 
		{
			this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, (float)this.mob.getMaxHeadYRot()) * 0.8F;
		}

		private void rotateHeadTowardsFront()
		{
			int i = this.headStableTime - 10;
			float f = Mth.clamp((float)i / 10.0F, 0.0F, 1.0F);
			float f1 = (float)this.mob.getMaxHeadYRot() * (1.0F - f);
			this.mob.yBodyRot = Mth.rotateIfNecessary(this.mob.yBodyRot, this.mob.yHeadRot, f1);
		}

		private boolean notCarryingMobPassengers()
		{
			return !(this.mob.getFirstPassenger() instanceof Mob);
		}

		private boolean isMoving()
		{
			double d0 = this.mob.getX() - this.mob.xo;
			double d1 = this.mob.getZ() - this.mob.zo;
			return d0 * d0 + d1 * d1 > (double)2.5000003E-7F;
		}
    }

	@Override
	public AbstractDeepAbyssEntityPart<?>[] getDeepAbyssEntityParts() 
	{
		return this.parts;
	}
}
