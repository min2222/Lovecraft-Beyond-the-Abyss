package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.LatcherFindTargetGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.LatcherLatchingGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.LatcherPropelGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.LatcherUnlatchingGoal;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateVehiclePacket;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EntityLatcher extends AbstractDeepAbyssMonster
{
	public final AnimationState propelAnimationState = new AnimationState();
	public final AnimationState startLatchAnimationState = new AnimationState();
	public final AnimationState latchAnimationState = new AnimationState();
	public final AnimationState unlatchAnimationState = new AnimationState();
	
	public EntityLatcher(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(2);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 10.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.7F)
        		.add(Attributes.ATTACK_DAMAGE, 1.0F)
        		.add(Attributes.FOLLOW_RANGE, 2.5F)
        		.add(Attributes.ARMOR, 1.0F);
    }

    @Override
    public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
    {
    	EntityPartBuilder<EntityLatcher> partBuilder = new EntityPartBuilder<EntityLatcher>(this);
    	return partBuilder;
    }
    
    @Override
    public int getMaxSpawnClusterSize()
    {
    	return 1;
    }
    
    @Override
    protected void registerGoals() 
    {
        this.goalSelector.addGoal(4, new LatcherPropelGoal(this));
        this.goalSelector.addGoal(4, new LatcherLatchingGoal(this));
        this.goalSelector.addGoal(4, new LatcherUnlatchingGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0F, false));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new LatcherFindTargetGoal<Player>(this, Player.class, false, false));
    }
    
	public static boolean checkLatcherSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pRandom.nextInt(40) == 0 && pPos.getY() >= -400 && pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
	
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) 
	{
        if(ANIMATION_STATE.equals(p_219422_) && this.level.isClientSide) 
        {
            switch(this.getAnimationState()) 
            {
            	case -1:
            	{
        			this.stopAllAnimationStates();
            		this.propelAnimationState.start(this.tickCount);
        			break;
            	}
        		case 0: 
        		{
        			this.stopAllAnimationStates();
        			break;
        		}
        		case 1:
        		{
        			this.stopAllAnimationStates();
        			this.startLatchAnimationState.start(this.tickCount);
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			this.latchAnimationState.start(this.tickCount);
        			break;
        		}
        		case 3:
        		{
        			this.stopAllAnimationStates();
        			this.unlatchAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates()
	{
		this.propelAnimationState.stop();
		this.startLatchAnimationState.stop();
		this.latchAnimationState.stop();
		this.unlatchAnimationState.stop();
	}
    
    @Override
    public void tick() 
    {
    	super.tick();
    	DeepAbyssUtil.fishFlopping(this);
        
        if(this.getVehicle() != null)
        {
        	if(this.tickCount % 20 == 0)
        	{
        		this.getVehicle().hurt(DamageSource.mobAttack(this), (float) this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
        	}
        }
    }
    
    @Override
    public int maxTurnY() 
    {
    	return 40;
    }
    
    @Override
    public void stopRiding()
    {
    	if(!this.isInWater() || this.getVehicle() == null || !this.isAlive() || (this.getVehicle() instanceof Player player && player.isSpectator()))
    	{
    		this.setAnimationState(3);
        	super.stopRiding();
    	}
    }
    
    @Override
    public void setPos(double p_20210_, double p_20211_, double p_20212_) 
    {
    	if(this.getVehicle() != null)
    	{
    		Vec3 original = new Vec3(p_20210_, p_20211_, p_20212_);
    		Vec3 vec3 = BTAUtil.getLookPos(this.getVehicle().getXRot(), ((LivingEntity) this.getVehicle()).yHeadRot, 0, 0.5);
    		Vec3 pos = original.add(vec3);
        	super.setPos(pos.x, pos.y, pos.z);
    	}
    	else
    	{
        	super.setPos(p_20210_, p_20211_, p_20212_);
    	}
    }
    
    @Override
    public boolean startRiding(Entity p_20330_) 
    {
    	BTANetwork.sendToAll(new UpdateVehiclePacket(this, p_20330_));
    	return super.startRiding(p_20330_);
    }
    
    @Override
    public boolean doHurtTarget(Entity p_21372_) 
    {
    	if(this.getVehicle() == null)
    	{	
    		this.setAnimationState(1);
    		this.startRiding(p_21372_);
    	}
    	return super.doHurtTarget(p_21372_);
    }
	
    @Override
    public BTAMobType getBTAMobType()
    {
    	return BTAMobType.HOSTILE;
    }
}
