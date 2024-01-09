package com.min01.beyondtheabyss.entity.deepabyss.living;

import java.util.List;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.entity.deepabyss.AbstractMultipartDeepAbyssEntity;
import com.min01.beyondtheabyss.entity.goals.deepabyss.GhidruthBiteGoal;
import com.min01.beyondtheabyss.entity.goals.deepabyss.GhidruthDashPrepareGoal;
import com.min01.beyondtheabyss.entity.goals.deepabyss.GhidruthTailSwingGoal;
import com.min01.beyondtheabyss.entity.parts.BasicBTAEntityPart;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.phys.Vec3;

public class EntityGhidruth extends AbstractMultipartDeepAbyssEntity
{
	public BasicBTAEntityPart head = new BasicBTAEntityPart(this, 4.5F, 4.5F);
	public BasicBTAEntityPart body = new BasicBTAEntityPart(this, 5.5F, 4.5F);
	public BasicBTAEntityPart tail = new BasicBTAEntityPart(this, 5.5F, 4.3F);
	public BasicBTAEntityPart[] parts = { this.head, this.body, this.tail };
	public AnimationState swimAnimationState = new AnimationState();
	public AnimationState biteRightAnimationState = new AnimationState();
	public AnimationState biteLeftAnimationState = new AnimationState();
	public AnimationState tailSwingRightAnimationState = new AnimationState();
	public AnimationState tailSwingLeftAnimationState = new AnimationState();
	public AnimationState dashAnimationState = new AnimationState();
	public AnimationState dashPrepareAnimationState = new AnimationState();
	
	public static final EntityDataAccessor<BlockPos> TAIL_ROTATION = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.BLOCK_POS);
	public static final EntityDataAccessor<BlockPos> HEAD_ROTATION = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.BLOCK_POS);
	public static final EntityDataAccessor<BlockPos> DASH_POSITION = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.BLOCK_POS);
	public static final EntityDataAccessor<Boolean> IS_DASH = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> ATTACK_COUNT = SynchedEntityData.defineId(EntityGhidruth.class, EntityDataSerializers.INT);
	
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
    			.add(Attributes.MOVEMENT_SPEED, 1.8D)
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
        this.entityData.define(TAIL_ROTATION, BlockPos.ZERO);
        this.entityData.define(HEAD_ROTATION, BlockPos.ZERO);
        this.entityData.define(ATTACK_COUNT, 0);
        this.entityData.define(DASH_POSITION, BlockPos.ZERO);
        this.entityData.define(IS_DASH, false);
    }
    
    public void setDashPos(BlockPos value)
    {
    	this.entityData.set(DASH_POSITION, value);
    }
      
    public BlockPos getDashPos() 
    {
    	return this.entityData.get(DASH_POSITION);
    }
    
    public void setDash(boolean value)
    {
    	this.entityData.set(IS_DASH, value);
    }
      
    public boolean isDash() 
    {
    	return this.entityData.get(IS_DASH);
    }
    
    public void setAttackCount(int count)
    {
    	this.entityData.set(ATTACK_COUNT, count);
    }
      
    public int getAttackCount() 
    {
    	return this.entityData.get(ATTACK_COUNT);
    }
      
    public void setHeadRotation(BlockPos rotation)
    {
    	this.entityData.set(HEAD_ROTATION, rotation);
    }
      
    public BlockPos getHeadRotation() 
    {
    	return this.entityData.get(HEAD_ROTATION);
    }
      
    public BlockPos getTailRotation() 
    {
    	return this.entityData.get(TAIL_ROTATION);
    }
      
    public void setTailRotation(BlockPos rotation)
    {
    	this.entityData.set(TAIL_ROTATION, rotation);
    }
    
    @Override
    public boolean canBeCollidedWith() 
    {
    	return true;
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
        			this.swimAnimationState.start(this.tickCount);
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
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			if(this.random.nextBoolean())
        			{
            			this.tailSwingRightAnimationState.start(this.tickCount);
        			}
        			else
        			{
            			this.tailSwingLeftAnimationState.start(this.tickCount);
        			}
        			break;
        		}
        		case 3:
        		{
        			this.stopAllAnimationStates();
        			this.dashPrepareAnimationState.start(this.tickCount);
        			break;
        		}
        		case 4:
        		{
        			this.stopAllAnimationStates();
        			this.dashAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
        super.onSyncedDataUpdated(p_219422_);
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.biteRightAnimationState.stop();
		this.biteLeftAnimationState.stop();
		this.tailSwingRightAnimationState.stop();
		this.tailSwingLeftAnimationState.stop();
		this.dashPrepareAnimationState.stop();
		this.dashAnimationState.stop();
	}
    
    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(4, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED), 10));
        this.goalSelector.addGoal(4, new GhidruthBiteGoal(this));
        this.goalSelector.addGoal(4, new GhidruthTailSwingGoal(this));
        this.goalSelector.addGoal(4, new GhidruthDashPrepareGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Player>(this, Player.class, false, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Drowned>(this, Drowned.class, false, false));
    }
    
    @Override
    public void tick()
    {
    	super.tick();
    	
		BlockPos tailRot = this.getTailRotation();
		BlockPos headRot = this.getHeadRotation();
		
		Vec3 headLookPos = BTAUtil.getLookPos(headRot.getX() + this.getXRot(), headRot.getY() + this.yHeadRot, 0, 4);
		Vec3 bodyLookPos = BTAUtil.getLookPos(tailRot.getX() + this.getXRot(), tailRot.getY() + this.yBodyRot, 0, -5);
		Vec3 tailLookPos = BTAUtil.getLookPos(tailRot.getX() + this.getXRot(), tailRot.getY() + this.yBodyRot, 0, -10);
		
        this.setPartPosition(this.tail, tailLookPos.x, tailLookPos.y + 1, tailLookPos.z);
        this.setPartPosition(this.body, bodyLookPos.x, bodyLookPos.y + 0.5, bodyLookPos.z);
        this.setPartPosition(this.head, headLookPos.x, headLookPos.y, headLookPos.z);

        if(this.isDash())
        {
        	Vec3 vec = new Vec3(this.getDashPos().getX(), this.getDashPos().getY(), this.getDashPos().getZ());
			this.getNavigation().moveTo(this.getDashPos().getX(), this.getDashPos().getY(), this.getDashPos().getZ(), this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
			this.lookAt(Anchor.FEET, vec);
			
			List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.head.getBoundingBox().inflate(3));
			list.removeIf((living) -> living == this);
			list.forEach((living) -> living.hurt(DamageSource.mobAttack(this), 4));
	        
	        if(this.distanceToSqr(vec) <= 3)
	        {
	        	this.stopDash();
	        }
        }
    }
    
    public void stopDash()
    {
		this.setAnimationState(0);
		this.setDash(false);
		this.setAttackCount(0);
		this.setDashPos(BlockPos.ZERO);
		this.setCanLookOrMove(true);
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.8);
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
