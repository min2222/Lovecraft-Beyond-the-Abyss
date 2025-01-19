package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreShootPutridBubbleGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreTongueBiteGoal;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.KinematicChain;

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
	
	public final KinematicChain[] chains = new KinematicChain[] { 
			new KinematicChain(this, 3, 1.75F),
			new KinematicChain(this, 3, 1.75F),
			new KinematicChain(this, 3, 1.75F),
			new KinematicChain(this, 3, 1.75F),
			new KinematicChain(this, 3, 1.75F)
	};
	
	public final KinematicChain tongueChain = new KinematicChain(this, 3, 0.875F);
	
	public final AnimationState mouthOpeningAnimationState = new AnimationState();
	public final AnimationState mouthOpenAnimationState = new AnimationState();
	public final AnimationState mouthCloseAnimationState = new AnimationState();
	
	public EntityMutavore(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.posArray = new Vec3[13];
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
    	this.goalSelector.addGoal(4, new MutavoreTongueBiteGoal(this));
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
		for(int i = 0; i < this.chains.length; i++)
		{
			KinematicChain chain = this.chains[i];
			chain.tick();
			if(this.posArray[i] != null)
			{
				chain.setAnchorPos(this.posArray[i]);
			}
			if(this.posArray[i + 5] != null)
			{
				chain.setTarget(this.posArray[i + 5]);
			}
		}
		if(this.posArray[11] != null)
		{
			this.tongueChain.setAnchorPos(this.posArray[11]);
		}
		if(this.posArray[12] != null)
		{
			this.tongueChain.tick();
			this.tongueChain.setTarget(this.posArray[12]);
		}
		if(this.getTarget() != null)
		{
			this.posArray[12] = this.getTarget().getEyePosition().subtract(0.0F, 0.5F, 0.0F);
			BTANetwork.sendToAll(new UpdatePosArrayPacket(this, this.getTarget().getEyePosition().subtract(0.0F, 0.5F, 0.0F), 12));
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
	
	public void setUsingTongue(boolean value)
	{
		this.entityData.set(IS_USING_TONGUE, value);
	}
	
	public boolean isUsingTongue()
	{
		return this.entityData.get(IS_USING_TONGUE);
	}
}
