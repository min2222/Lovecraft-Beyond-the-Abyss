package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.DeepVampireBiteGoal;
import com.min01.beyondtheabyss.entity.part.AbstractBTAEntityPart;
import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
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
    			.add(Attributes.MOVEMENT_SPEED, 0.6F)
        		.add(Attributes.ATTACK_DAMAGE, 5)
        		.add(Attributes.FOLLOW_RANGE, 25);
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
    	return 1;
    }
    
	public static boolean checkDeepVampireSpawnRules(EntityType<? extends AbstractDeepAbyssMob> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
        if (!pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER))
        {
            return false;
        }
        else
        {
        	boolean flag = pServerLevel.getDifficulty() != Difficulty.PEACEFUL && (pMobSpawnType == MobSpawnType.SPAWNER || pServerLevel.getFluidState(pPos).is(FluidTags.WATER));
            return pRandom.nextInt(350) == 0 && pPos.getY() >= -400 && flag;
        }
    }
    
    @Override
    public void tick() 
    {
    	super.tick();
    	Vec3 tailEdge2Pos = BTAUtil.getLookPos(this.getXRot(), (float) (this.yHeadRot + this.getTailRot().y), 0, -3.9F);
    	Vec3 tailEdgePos = BTAUtil.getLookPos(this.getXRot(), (float) (this.yHeadRot + this.getTailRot().y), 0, -3.3F);
    	Vec3 tail2Pos = BTAUtil.getLookPos(this.getXRot(), (float) (this.yHeadRot + this.getBodyRot().y), 0, -2.6F);
    	Vec3 tailPos = BTAUtil.getLookPos(this.getXRot(), (float) (this.yHeadRot + this.getBodyRot().y), 0, -2F);
    	Vec3 body2Pos = BTAUtil.getLookPos(this.getXRot(), (float) (this.yHeadRot + this.getHeadRot().y), 0, -1.3F);
    	Vec3 bodyPos = BTAUtil.getLookPos(this.getXRot(), (float) (this.yHeadRot + this.getHeadRot().y), 0, -0.7F);

        this.setPartPosition(this.tailEdge2, tailEdge2Pos.x + this.getBodyPos().x, tailEdge2Pos.y + 0.2F, tailEdge2Pos.z);
        this.setPartPosition(this.tailEdge, tailEdgePos.x + this.getBodyPos().x, tailEdgePos.y + 0.2F, tailEdgePos.z);
        this.setPartPosition(this.tail2, tail2Pos.x + this.getBodyPos().x, tail2Pos.y + 0.2F, tail2Pos.z);
        this.setPartPosition(this.tail, tailPos.x + this.getBodyPos().x, tailPos.y + 0.2F, tailPos.z);
        this.setPartPosition(this.body2, body2Pos.x + this.getBodyPos().x, body2Pos.y + 0.2F, body2Pos.z);
        this.setPartPosition(this.body, bodyPos.x + this.getBodyPos().x, bodyPos.y + 0.2F, bodyPos.z);
    }
    
    @Override
    public void aiStep() 
    {
        super.aiStep();
        BTAUtil.fishFlopping(this);
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
	public AbstractBTAEntityPart<AbstractBTAMob>[] getDeepAbyssEntityParts()
	{
		return this.parts;
	}
}
