package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreShootPutridBubbleGoal;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityMutavore extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Boolean> IS_USING_TONGUE = SynchedEntityData.defineId(EntityMutavore.class, EntityDataSerializers.BOOLEAN);

	public final AnimationState mouthOpeningAnimationState = new AnimationState();
	public final AnimationState mouthOpenAnimationState = new AnimationState();
	public final AnimationState mouthCloseAnimationState = new AnimationState();
	public final AnimationState swimAnimationState = new AnimationState();
	
	public EntityMutavore(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.posArray = new Vec3[1];
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 150.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
        		.add(Attributes.FOLLOW_RANGE, 50.0F)
        		.add(Attributes.ARMOR, 8.0F);
    }
    
    @Override
    protected void registerGoals()
    {
    	super.registerGoals();
    	//this.goalSelector.addGoal(4, new MutavoreTongueBiteGoal(this));
    	this.goalSelector.addGoal(4, new MutavoreShootPutridBubbleGoal(this));
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(IS_USING_TONGUE, false);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntityMutavore> partBuilder = new EntityPartBuilder<EntityMutavore>(this);
		return partBuilder;
	}
	
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) 
	{
        if(ANIMATION_STATE.equals(p_219422_) && this.level.isClientSide) 
        {
            switch(this.getAnimationState()) 
            {
        		case 0: 
        		{
        			this.stopAllAnimationStates();
        			break;
        		}
        		case 1:
        		{
        			this.stopAllAnimationStates();
        			this.mouthOpeningAnimationState.start(this.tickCount);
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			this.mouthOpenAnimationState.start(this.tickCount);
        			break;
        		}
        		case 3:
        		{
        			this.stopAllAnimationStates();
        			this.mouthCloseAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.mouthOpeningAnimationState.stop();
		this.mouthOpenAnimationState.stop();
		this.mouthCloseAnimationState.stop();
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.level.isClientSide)
		{
			BTAClientUtil.animateWhen(this.swimAnimationState, BTAUtil.isMoving(this), this.tickCount);
		}
		if(this.getAnimationState() == 2 && !this.hasTarget())
		{
			this.setAnimationState(3);
			this.setAnimationTick(5);
		}
		if(this.getAnimationState() == 3 && this.getAnimationTick() <= 0)
		{
			this.setAnimationState(0);
			this.setUsingTongue(false);
			this.setUsingSkill(false);
			this.setCanMove(true);
		}
	}
	
	@Override
	public int maxTurnX()
	{
		return !this.hasTarget() ? 65 : 45;
	}
	
	@Override
	public int maxTurnY() 
	{
		return !this.hasTarget() ? 1 : 5;
	}
	
	public void setUsingTongue(boolean value)
	{
		this.entityData.set(IS_USING_TONGUE, value);
	}
	
	public boolean isUsingTongue()
	{
		return this.entityData.get(IS_USING_TONGUE);
	}
}
