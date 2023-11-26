package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.goals.deepabyss.GhidruthBiteGoal;
import com.min01.beyondtheabyss.entity.goals.deepabyss.GhidruthTailSwingGoal;
import com.min01.beyondtheabyss.entity.parts.BasicAbyssEntityPart;
import com.min01.beyondtheabyss.util.AbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class EntityGhidruth extends AbstractMultipartDeepAbyssEntity
{
	public BasicAbyssEntityPart head = new BasicAbyssEntityPart(this, 4.5F, 4.5F);
	public BasicAbyssEntityPart body = new BasicAbyssEntityPart(this, 5.5F, 4.5F);
	public BasicAbyssEntityPart tail = new BasicAbyssEntityPart(this, 5.5F, 4.3F);
	public BasicAbyssEntityPart[] parts = { this.head, this.body, this.tail };
	public AnimationState swimAnimationState = new AnimationState();
	public AnimationState biteAnimationState = new AnimationState();
	public AnimationState tailSwingAnimationState = new AnimationState();
	
	public static final EntityDataAccessor<Float> TAIL_Y_ROT = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.FLOAT);	  
	public static final EntityDataAccessor<Float> HEAD_Y_ROT = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.FLOAT);	  
	public static final EntityDataAccessor<Float> HEAD_X_POS = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.FLOAT);  
	public static final EntityDataAccessor<Float> HEAD_Y_POS = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> HEAD_Z_POS = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.FLOAT);
	
	public EntityGhidruth(EntityType<? extends PathfinderMob> p_33002_, Level p_33003_) 
	{
		super(p_33002_, p_33003_);
		this.setAsBoss();
		this.xpReward = 1000;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 300)
    			.add(Attributes.MOVEMENT_SPEED, 1.5D)
        		.add(Attributes.ATTACK_DAMAGE, 5)
        		.add(Attributes.FOLLOW_RANGE, 70)
        		.add(Attributes.ARMOR, 20)
        		.add(Attributes.ARMOR_TOUGHNESS, 20)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 10);
    }
    
    @Override
    protected void defineSynchedData() 
    {
        super.defineSynchedData();
        this.entityData.define(TAIL_Y_ROT, 0.0F);
        this.entityData.define(HEAD_Y_ROT, 0.0F);
        this.entityData.define(HEAD_X_POS, 0.0F);
        this.entityData.define(HEAD_Y_POS, 0.0F);
        this.entityData.define(HEAD_Z_POS, 0.0F);
    }
    
    public void setHeadXPos(float x) 
    {
    	this.entityData.set(HEAD_X_POS, x);
    }
      
    public float getHeadXPos() 
    {
    	return this.entityData.get(HEAD_X_POS);
    }
      
    public void setHeadYPos(float y) 
    {
    	this.entityData.set(HEAD_Y_POS, y);
    }
      
    public float getHeadYPos()
    {
    	return this.entityData.get(HEAD_Y_POS);
    }
    
    public void setHeadZPos(float z) 
    {
    	this.entityData.set(HEAD_Z_POS, z);
    }
      
    public float getHeadZPos() 
    {
    	return this.entityData.get(HEAD_Z_POS);
    }
      
    public void setHeadYRot(float yRot)
    {
    	this.entityData.set(HEAD_Y_ROT, yRot);
    }
      
    public float getHeadYRot() 
    {
    	return this.entityData.get(HEAD_Y_ROT);
    }
      
    public float getTailYRot() 
    {
    	return this.entityData.get(TAIL_Y_ROT);
    }
      
    public void setTailYRot(float yRot)
    {
    	this.entityData.set(TAIL_Y_ROT, yRot);
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
    
    @Override
    public boolean canBeCollidedWith() 
    {
    	return true;
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
        			this.biteAnimationState.start(this.tickCount);
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			this.tailSwingAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
        super.onSyncedDataUpdated(p_219422_);
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.biteAnimationState.stop();
		this.tailSwingAnimationState.stop();
	}
    
    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.8F, 10));
        this.goalSelector.addGoal(4, new GhidruthBiteGoal(this));
        this.goalSelector.addGoal(4, new GhidruthTailSwingGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Player>(this, Player.class, false, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Drowned>(this, Drowned.class, false, false));
    }
    
    @Override
    public void aiStep()
    {
    	super.aiStep();
    	
    	float piDividedBy180 = (float) Math.PI / 180.0F;
    	
        float f17 = this.getYRot() * piDividedBy180;
        float pitch = this.getXRot() * piDividedBy180;
        float headPitch = this.getHeadYRot() * 6 * piDividedBy180;
        
        float f3 = Mth.sin(f17) * (1 - Math.abs(this.getXRot() / 90F));
        float f18 = Mth.cos(f17) * (1 - Math.abs(this.getXRot() / 90F));

        float tailYRot = this.getTailYRot() * piDividedBy180;
        float tailX = Mth.sin(tailYRot) * (1 - Math.abs(this.getXRot() / 90F));
        float tailZ = Mth.cos(tailYRot) * (1 - Math.abs(this.getXRot() / 90F));

        this.setPartPosition(this.tail, tailX * 11F, -pitch * 0.5F, -tailZ * 11F);
        this.setPartPosition(this.body, (f3) * 5.5F, -pitch * 3F, (f18) * -5.5F);
        this.setPartPosition(this.head, f3 * -4.5F, -headPitch * 1F - 1F, -f18 * -4.5F);
        
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
    public int getBodyRotationSpeed() 
    {
    	return 2;
    }

	@Override
	public BasicAbyssEntityPart[] getDeepAbyssEntityParts() 
	{
		return this.parts;
	}
}
