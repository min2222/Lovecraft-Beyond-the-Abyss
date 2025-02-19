package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.sound.BTASounds;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityGhidruth extends AbstractDeepAbyssMonster
{
	public EntityGhidruth(EntityType<? extends Monster> p_33002_, Level p_33003_) 
	{
		super(p_33002_, p_33003_);
		this.xpReward = 1000 + this.random.nextInt(100);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 300.0F)
    			.add(Attributes.MOVEMENT_SPEED, 1.0F)
        		.add(Attributes.ATTACK_DAMAGE, 5.0F)
        		.add(Attributes.FOLLOW_RANGE, 100.0F)
        		.add(Attributes.ARMOR, 150.0F)
        		.add(Attributes.ARMOR_TOUGHNESS, 150.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 10.0F);
    }
	
    @Override
    public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
    {
    	EntityPartBuilder<EntityGhidruth> partBuilder = new EntityPartBuilder<EntityGhidruth>(this)
    	{
    		@Override
    		public Vec3 getOffset()
    		{
    			return new Vec3(0.0F, 2.25F, 0.0F);
    		}
    		
    		@Override
    		public float getRenderScale() 
    		{
    			return 1.5F;
    		}
    	};
    	return partBuilder;
    }
    
    @Override
    protected void registerGoals()
    {
    	super.registerGoals();
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
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
            }
        }
        super.onSyncedDataUpdated(p_219422_);
	}
	
	@Override
	public void stopAllAnimationStates() 
	{

	}
    
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) 
    {
    	return BTASounds.GHIDRUTH_HURT.get();
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
    	return BTASounds.GHIDRUTH_AMBIENT.get();
    }
    
    @Override
    public void tick()
    {
    	super.tick();
    	//TODO
    }
	
	@Override
	protected float getSoundVolume() 
	{
		return 0.45F;
	}

    @Override
    public int maxTurnY() 
    {
    	return !this.hasTarget() ? 2 : 6;
    }
    
    @Override
    public BTAMobType getBTAMobType()
    {
    	return BTAMobType.BOSS;
    }
    
    @Override
    public int getSwimRadius()
    {
    	return 20;
    }
}
