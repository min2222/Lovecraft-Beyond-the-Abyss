package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.effect.BTAEffects;
import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.DeepVampireBiteGoal;
import com.min01.beyondtheabyss.entity.part.AbstractBTAEntityPart;
import com.min01.beyondtheabyss.entity.part.BasicBTAEntityPart;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
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
		this.posArray = new Vec3[6];
		this.xpReward = this.random.nextInt(4);
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
    }
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 2;
    }
    
	public static boolean checkDeepVampireSpawnRules(EntityType<? extends AbstractDeepAbyssMob> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(30) == 0 && pPos.getY() >= -400 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
	
    @Override
    protected ResourceLocation getDefaultLootTable() 
    {
    	return new ResourceLocation(BeyondtheAbyss.MODID, "entity/deep_vampire");
    }
    
    @Override
    public void aiStep() 
    {
        super.aiStep();
        DeepAbyssUtil.fishFlopping(this);

        this.setPartPosition(this.tailEdge2, this.posArray[5]);
        this.setPartPosition(this.tailEdge, this.posArray[4]);
        this.setPartPosition(this.tail2, this.posArray[3]);
        this.setPartPosition(this.tail, this.posArray[2]);
        this.setPartPosition(this.body2, this.posArray[1]);
        this.setPartPosition(this.body, this.posArray[0]);
    }
    
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) 
	{
        if(ANIMATION_STATE.equals(p_219422_) && this.level.isClientSide) 
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
    		if(p_21372_ instanceof LivingEntity living)
    		{
    			if(Math.random() <= 0.1)
    			{
        			living.addEffect(new MobEffectInstance(BTAEffects.BLEEDING.get(), 40));
    			}
    		}
        	this.heal(this.random.nextInt(1, 3));
    	}
    	return flag;
    }
	
    @Override
    public BTAMobType getBTAMobType()
    {
    	return BTAMobType.HOSTILE;
    }
	
	@Override
	public AbstractBTAEntityPart<AbstractBTAMob>[] getDeepAbyssEntityParts()
	{
		return this.parts;
	}
}
