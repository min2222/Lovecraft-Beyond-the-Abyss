package com.min01.beyondtheabyss.entity.deepabyss.living;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.AbstractMultipartDeepAbyssEntity;
import com.min01.beyondtheabyss.entity.goals.deepabyss.GhidruthBiteGoal;
import com.min01.beyondtheabyss.entity.goals.deepabyss.GhidruthTailSwingGoal;
import com.min01.beyondtheabyss.entity.parts.BasicBTAEntityPart;
import com.min01.beyondtheabyss.util.BTAUtil;

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
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
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
	public BasicBTAEntityPart head = new BasicBTAEntityPart(this, 4.5F, 4.5F);
	public BasicBTAEntityPart body = new BasicBTAEntityPart(this, 5.5F, 4.5F);
	public BasicBTAEntityPart tail = new BasicBTAEntityPart(this, 5.5F, 4.3F);
	public BasicBTAEntityPart[] parts = { this.head, this.body, this.tail };
	public AnimationState swimAnimationState = new AnimationState();
	public AnimationState biteAnimationState = new AnimationState();
	public AnimationState tailSwingAnimationState = new AnimationState();
	
	public static final EntityDataAccessor<Float> TAIL_Y_ROT = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> HEAD_Y_ROT = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.FLOAT);
	
	public static final EntityDataAccessor<Float> RENDER_X_ROT = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.FLOAT);	
	
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
    			.add(Attributes.MOVEMENT_SPEED, 2D)
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
        this.entityData.define(RENDER_X_ROT, 0.0F);
    }
    
    public void setRenderXRot(float xRot)
    {
    	this.entityData.set(RENDER_X_ROT, xRot);
    }
      
    public float getRenderXRot() 
    {
    	return this.entityData.get(RENDER_X_ROT);
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
    public void tick()
    {
    	super.tick();
    	
    	//for dynamically update body rotation speed
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, this.getBodyRotationSpeed(), 0.02F, 0.1F, true);
    	
    	float piDividedBy180 = (float) Math.PI / 180.0F;
    	
        float yRot = this.getYRot() * piDividedBy180;
        float pitch = this.getRenderXRot() * piDividedBy180;
        float headPitch = (this.getHeadYRot() + this.getRenderXRot()) * 4 * piDividedBy180;
        
        float xRot = Mth.sin(yRot) * (1 - Math.abs(this.getRenderXRot() / 90F));
        float zRot = Mth.cos(yRot) * (1 - Math.abs(this.getRenderXRot() / 90F));

        float tailYRot = (this.getTailYRot() + this.yBodyRot) * piDividedBy180;
        float tailXRot = Mth.sin(tailYRot) * (1 - Math.abs(this.getRenderXRot() / 90F));
        float tailZRot = Mth.cos(tailYRot) * (1 - Math.abs(this.getRenderXRot() / 90F));

        this.setPartPosition(this.tail, tailXRot * 11F, -pitch * -7F, -tailZRot * 11F);
        this.setPartPosition(this.body, (xRot) * 5.5F, -pitch * -4F, (zRot) * -5.5F);
        this.setPartPosition(this.head, xRot * -5F, -headPitch * 1F, -zRot * -5F);
        
    	if(this.level.isClientSide) 
    	{
    		if(BTAUtil.isMoving(this) && this.isAlive())
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
    	return this.getTarget() == null ? 2 : 6;
    }

	@Override
	public BasicBTAEntityPart[] getDeepAbyssEntityParts() 
	{
		return this.parts;
	}
}
