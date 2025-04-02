package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreShootPutridBubbleGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
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

public class EntityMutavore extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Boolean> IS_USING_TONGUE = SynchedEntityData.defineId(EntityMutavore.class, EntityDataSerializers.BOOLEAN);

	public final AnimationState mouthOpeningAnimationState = new AnimationState();
	public final AnimationState mouthOpenAnimationState = new AnimationState();
	public final AnimationState mouthCloseAnimationState = new AnimationState();
	public final AnimationState swimAnimationState = new AnimationState();
	
	public final Worm worm1 = new Worm();
	public final Worm worm6 = new Worm();
	public final Worm worm11 = new Worm();
	
	public final Worm worm2 = new Worm();
	public final Worm worm7 = new Worm();
	public final Worm worm12 = new Worm();
	
	public final Worm worm3 = new Worm();
	public final Worm worm8 = new Worm();
	public final Worm worm13 = new Worm();
	
	public final Worm worm4 = new Worm();
	public final Worm worm9 = new Worm();
	public final Worm worm14 = new Worm();
	
	public final Worm worm5 = new Worm();
	public final Worm worm10 = new Worm();
	public final Worm worm15 = new Worm();
	
	public EntityMutavore(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
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
		this.worm1.setOldPosAndRot();
		this.worm2.setOldPosAndRot();
		this.worm3.setOldPosAndRot();
		this.worm4.setOldPosAndRot();
		this.worm5.setOldPosAndRot();
		this.worm6.setOldPosAndRot();
		this.worm7.setOldPosAndRot();
		this.worm8.setOldPosAndRot();
		this.worm9.setOldPosAndRot();
		this.worm10.setOldPosAndRot();
		this.worm11.setOldPosAndRot();
		this.worm12.setOldPosAndRot();
		this.worm13.setOldPosAndRot();
		this.worm14.setOldPosAndRot();
		this.worm15.setOldPosAndRot();
		
		float speed = 0.35F;
    	
    	WormChain.tick(this.worm1, this, 0.0F, speed);
    	WormChain.tick(this.worm2, this.worm1, 0.0F, speed);
    	WormChain.tick(this.worm3, this.worm2, 0.0F, speed);
    	
    	WormChain.tick(this.worm4, this, 0.0F, speed);
    	WormChain.tick(this.worm5, this.worm4, 0.0F, speed);
    	WormChain.tick(this.worm6, this.worm5, 0.0F, speed);
    	
    	WormChain.tick(this.worm7, this, 0.0F, speed);
    	WormChain.tick(this.worm8, this.worm7, 0.0F, speed);
    	WormChain.tick(this.worm9, this.worm8, 0.0F, speed);
    	
    	WormChain.tick(this.worm10, this, 0.0F, speed);
    	WormChain.tick(this.worm11, this.worm10, 0.0F, speed);
    	WormChain.tick(this.worm12, this.worm11, 0.0F, speed);
    	
    	WormChain.tick(this.worm13, this, 0.0F, speed);
    	WormChain.tick(this.worm14, this.worm13, 0.0F, speed);
    	WormChain.tick(this.worm15, this.worm14, 0.0F, speed);
    	
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
		return !this.hasTarget() ? 55 : 75;
	}
	
	@Override
	public int maxTurnY() 
	{
		return !this.hasTarget() ? 3 : 5;
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
