package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.DeepVampireBiteGoal;
import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class EntityDeepVampire extends AbstractMultipartDeepAbyssMob
{
	public AnimationState biteRightAnimationState = new AnimationState();
	public AnimationState biteLeftAnimationState = new AnimationState();
	public BasicBTAEntityPart body = new BasicBTAEntityPart(this, 0.6F, 0.5F);
	public BasicBTAEntityPart body2 = new BasicBTAEntityPart(this, 0.6F, 0.5F);
	public BasicBTAEntityPart tail = new BasicBTAEntityPart(this, 0.6F, 0.5F);
	public BasicBTAEntityPart tail2 = new BasicBTAEntityPart(this, 0.6F, 0.5F);
	public BasicBTAEntityPart tailEdge = new BasicBTAEntityPart(this, 0.6F, 0.5F);
	public BasicBTAEntityPart tailEdge2 = new BasicBTAEntityPart(this, 0.6F, 0.5F);
	public BasicBTAEntityPart[] parts = { this.body, this.body2, this.tail, this.tail2, this.tailEdge, this.tailEdge2 };
	
	public EntityDeepVampire(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = 20;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 40)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
        		.add(Attributes.ATTACK_DAMAGE, 5)
        		.add(Attributes.FOLLOW_RANGE, 40);
    }
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
    	this.goalSelector.addGoal(4, new DeepVampireBiteGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<EntityRunicFish>(this, EntityRunicFish.class, false, false));
    }
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 3;
    }
    
    @Override
    public float getInsideWaterSpeed() 
    {
    	return !this.level.isClientSide && this.getTarget() != null ? 0.4F : super.getInsideWaterSpeed();
    }
    
	public static boolean checkDeepVampireSpawnRules(EntityType<? extends AbstractDeepAbyssMob> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
        if (!pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER))
        {
            return false;
        }
        else
        {
            return pRandom.nextInt(50) == 0 && pPos.getY() >= -360 && pServerLevel.getFluidState(pPos).is(FluidTags.WATER) && pServerLevel.getDifficulty() != Difficulty.PEACEFUL;
        }
    }
    
    @Override
    public void tick() 
    {
    	super.tick();
    	Vec3 tailEdge2Pos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + this.getTailRot(), 0, -3.9F);
    	Vec3 tailEdgePos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + this.getTailRot(), 0, -3.3F);
    	Vec3 tail2Pos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + this.getBodyRot(), 0, -2.6F);
    	Vec3 tailPos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + this.getBodyRot(), 0, -2F);
    	Vec3 body2Pos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + this.getHeadRot(), 0, -1.3F);
    	Vec3 bodyPos = BTAUtil.getLookPos(this.getXRot(), this.yHeadRot + this.getHeadRot(), 0, -0.7F);

        this.setPartPosition(this.tailEdge2, tailEdge2Pos.x, tailEdge2Pos.y + 0.2F, tailEdge2Pos.z);
        this.setPartPosition(this.tailEdge, tailEdgePos.x, tailEdgePos.y + 0.2F, tailEdgePos.z);
        this.setPartPosition(this.tail2, tail2Pos.x, tail2Pos.y + 0.2F, tail2Pos.z);
        this.setPartPosition(this.tail, tailPos.x, tailPos.y + 0.2F, tailPos.z);
        this.setPartPosition(this.body2, body2Pos.x, body2Pos.y + 0.2F, body2Pos.z);
        this.setPartPosition(this.body, bodyPos.x, bodyPos.y + 0.2F, bodyPos.z);
    }
    
    @Override
    public void aiStep() 
    {
        if (!this.isInWater() && this.onGround && this.verticalCollision) 
        {
        	this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), (double)0.5F, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
        	this.onGround = false;
        	this.hasImpulse = true;
        	this.playSound(SoundEvents.COD_FLOP, this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();
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
        			if(this.random.nextBoolean())
        			{
            			this.biteRightAnimationState.start(this.tickCount);
        			}
        			else
        			{
            			this.biteLeftAnimationState.start(this.tickCount);
        			}
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.biteRightAnimationState.stop();
		this.biteLeftAnimationState.stop();
	}
    
    @Override
    public boolean doHurtTarget(Entity p_21372_) 
    {
    	boolean flag = super.doHurtTarget(p_21372_);
    	if(flag)
    	{
        	this.heal(this.random.nextInt(1, 3));
    	}
    	return flag;
    }
	
	@Override
	public boolean isHostile()
	{
		return true;
	}
	
	@Override
	public BasicBTAEntityPart[] getDeepAbyssEntityParts()
	{
		return this.parts;
	}
}
