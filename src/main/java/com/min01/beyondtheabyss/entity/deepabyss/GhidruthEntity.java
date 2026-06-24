package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.BTACameraShakeEntity;
import com.min01.beyondtheabyss.entity.FallingStoneEntity;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthBiteGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthChargePrepareGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.GhidruthTailSwingGoal;
import com.min01.beyondtheabyss.misc.BTABossEvent;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.MobClassification;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.particle.DustCloudParticle;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.solomonlib.multipart.EntityPartBuilder;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class GhidruthEntity extends AbstractBTAMonster
{
	public static final EntityDataAccessor<Boolean> IS_CHARGE = SynchedEntityData.defineId(GhidruthEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_STUN = SynchedEntityData.defineId(GhidruthEntity.class, EntityDataSerializers.BOOLEAN);
	
	public final SmoothAnimationState biteRightAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState biteLeftAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState tailSwingRightAnimationState = new SmoothAnimationState(0.5F, true);
	public final SmoothAnimationState tailSwingLeftAnimationState = new SmoothAnimationState(0.5F, true);
	public final SmoothAnimationState chargePrepareAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState stunnedAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState stunLoopAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState stunEndAnimationState = new SmoothAnimationState();
	
	public int stunTick;
	public int chargeTick;
	
	public final BTABossEvent bossEvent = (BTABossEvent) new BTABossEvent(this.getDisplayName(), this).setDarkenScreen(true);
	
	public GhidruthEntity(EntityType<? extends AbstractBTAMonster> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
		this.xpReward = 1000 + this.random.nextInt(100);
		this.posArray = new Vec3[1];
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 300.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.25F)
        		.add(Attributes.ATTACK_DAMAGE, 20.0F)
        		.add(Attributes.FOLLOW_RANGE, 100.0F)
        		.add(Attributes.ARMOR, 20.0F)
        		.add(Attributes.ARMOR_TOUGHNESS, 20.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 10.0F);
    }
	
    @Override
    public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
    {
    	EntityPartBuilder<GhidruthEntity> partBuilder = new EntityPartBuilder<GhidruthEntity>(this)
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
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(IS_CHARGE, false);
    	this.entityData.define(IS_STUN, false);
    }
    
    @Override
    protected void registerGoals()
    {
    	super.registerGoals();
    	this.goalSelector.addGoal(0, new GhidruthBiteGoal(this));
    	this.goalSelector.addGoal(0, new GhidruthTailSwingGoal(this));
    	this.goalSelector.addGoal(0, new GhidruthChargePrepareGoal(this));
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void tick()
    {
    	super.tick();
    	
    	if(!this.level.isClientSide)
    	{
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    	}
    	
    	if(this.level.isClientSide)
    	{
    		this.biteRightAnimationState.updateWhen(this.isAnimationPlaying(1), this.tickCount);
    		this.biteLeftAnimationState.updateWhen(this.isAnimationPlaying(2), this.tickCount);
    		this.tailSwingRightAnimationState.updateWhen(this.isAnimationPlaying(3), this.tickCount);
    		this.tailSwingLeftAnimationState.updateWhen(this.isAnimationPlaying(4), this.tickCount);
    		this.chargePrepareAnimationState.updateWhen(this.isAnimationPlaying(5), this.tickCount);
    		this.stunnedAnimationState.updateWhen(this.isAnimationPlaying(6), this.tickCount);
    		this.stunLoopAnimationState.updateWhen(this.getAnimationState() == 0 && this.isStun(), this.tickCount);
    		this.stunEndAnimationState.updateWhen(this.isAnimationPlaying(7), this.tickCount);
    	}
        
    	if(this.isStun() && this.getAnimationTick() <= 0)
    	{
    		this.stunTick++;
    		if(this.getAnimationState() == 0 && this.stunTick >= 150)
    		{
    			this.setAnimationState(7);
    			this.setAnimationTick(25);
				this.playSound(BTASounds.GHIDRUTH_AWAKEN.get(), 10.0F, 1.0F);
    		}
    	}
    	
    	if(this.isCharge())
    	{
    		if(this.horizontalCollision || this.verticalCollision)
    		{
				this.setCharge(false);
    			this.setStopLookTick(Integer.MAX_VALUE);
    			this.setStopMoveTick(Integer.MAX_VALUE);
				this.setStun(true);
				this.setAnimationState(6);
				this.setAnimationTick(35);
				this.setLastLookPos(Vec3.ZERO);
				this.setDeltaMovement(Vec3.ZERO);
				BTACameraShakeEntity.cameraShake(this.level, this.position(), 100.0F, 0.35F, 0, 25);
				this.playSound(BTASounds.GHIDRUTH_STUN.get(), 10.0F, 1.0F);
				this.getNavigation().stop();
				this.chargeTick = 0;
				this.fallStones();
    		}
    		else if(!this.getLastLookPos().equals(Vec3.ZERO))
    		{
    			this.chargeTick++;
    			if(this.position().distanceTo(this.getLastLookPos()) <= 4.0F || this.chargeTick >= 200)
    			{
    				this.setCharge(false);
        			this.setStopLookTick(0);
        			this.setStopMoveTick(0);
    				this.setLastLookPos(Vec3.ZERO);
    				this.setDeltaMovement(Vec3.ZERO);
    				this.getNavigation().stop();
    				this.chargeTick = 0;
    			}
    			else
    			{
    				Vec3 pos = this.getLastLookPos();
    				this.lookAt(Anchor.FEET, pos);
    				this.getNavigation().moveTo(pos.x, pos.y, pos.z, 1.5F);
            		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.5F), t -> t != this && !t.isAlliedTo(this));
            		list.forEach(t ->
            		{
            			this.doHurtTarget(t);
            		});
    			}
    		}
    	}
    	else if(!this.level.isClientSide)
    	{
    		if(this.tickCount % 5 == 0)
    		{
    			if(ForgeEventFactory.getMobGriefingEvent(this.level, this)) 
    			{
                    boolean flag = false;
                    for(int x = (int) -this.getBbWidth(); x <= this.getBbWidth(); ++x)
                    {
                    	for(int z = (int) -this.getBbWidth(); z <= this.getBbWidth(); ++z)
                    	{
                    		for(int y = 1; y <= this.getBbHeight(); ++y) 
                    		{
                    			BlockPos pos = this.blockPosition().offset(x, y, z);
                    			BlockState state = this.level.getBlockState(pos);
                    			if(!state.liquid() && !state.is(BlockTags.DRAGON_IMMUNE) && !state.is(BlockTags.FIRE) && state.canEntityDestroy(this.level, pos, this) && ForgeEventFactory.onEntityDestroyBlock(this, pos, state)) 
                    			{
                    				flag = this.level.destroyBlock(pos, true, this) || flag;
                    			}
                    		}
                    	}
                    }
                    if(flag) 
                    {
                    	this.level.levelEvent(null, 1022, this.blockPosition(), 0);
                    }
    			}
    		}
    	}
	    BTAUtil.forceTick(this);
    }
    
    public void fallStones()
    {
    	this.level.broadcastEntityEvent(this, (byte) 99);
    	for(int i = 0; i < this.random.nextInt(20, 35); i++)
    	{
    		Vec3 spreadPos = BTAUtil.getSpreadPosition(this, new Vec3(15, 2, 15));
    		HitResult result = this.level.clip(new ClipContext(this.position(), spreadPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
    		Vec3 pos = result.getLocation();
    		BlockPos ceilingPos = BTAUtil.getCeilingPos(this.level, pos.x, this.getY(), pos.z);
    		FallingStoneEntity stone = new FallingStoneEntity(BTAEntities.FALLING_STONE.get(), this.level);
    		stone.setOwner(this);
    		stone.setPos(Vec3.atCenterOf(ceilingPos.below()));
    		stone.setBlockState(this.level.getBlockState(ceilingPos));
    		stone.setRotation(this.random.nextFloat() * 270.0F);
    		stone.setDelay(this.random.nextInt(15, 60));
    		this.level.addFreshEntity(stone);
    	}
    }
    
    @Override
    public void handleEntityEvent(byte pId) 
    {
    	super.handleEntityEvent(pId);
    	if(pId == 99)
    	{
        	for(int i = 0; i < this.random.nextInt(20, 35); i++)
        	{
        		Vec3 spreadPos = BTAUtil.getSpreadPosition(this, new Vec3(15, 2, 15));
        		HitResult result = this.level.clip(new ClipContext(this.position(), spreadPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        		Vec3 pos = result.getLocation();
        		BlockPos ceilingPos = BTAUtil.getCeilingPos(this.level, pos.x, this.getY(), pos.z);
        		BlockPos below = ceilingPos.below(2);
        		for(int j = 0; j < 150; j++)
        		{
    				this.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.level.getBlockState(ceilingPos)), below.getX(), below.getY(), below.getZ(), this.random.nextGaussian() * 0.5F, 0.0F, this.random.nextGaussian() * 0.5F);
        		}
        	}
        	
        	for(int i = 0; i < this.random.nextInt(10, 20); i++)
        	{
        		Vec3 spreadPos = BTAUtil.getSpreadPosition(this, new Vec3(15, 4, 15));
        		for(int j = 0; j < 30; j++)
        		{
        			this.level.addParticle(new DustCloudParticle.DustCloudParticleOption(BTABlocks.ABYSSALITH.get().defaultBlockState(), 1.5F), spreadPos.x, spreadPos.y, spreadPos.z, this.random.nextGaussian() * 0.5F, this.random.nextGaussian() * 0.5F, this.random.nextGaussian() * 0.5F);
        		}
        	}
    	}
    }
    
    @Override
    public boolean onAnimationEnd(int animationState)
    {
		if(animationState == 7)
		{
			this.setStopLookTick(0);
			this.setStopMoveTick(0);
			this.setStun(false);
			this.stunTick = 0;
		}
		return super.onAnimationEnd(animationState);
    }
	
	@Override
	public void push(double pX, double pY, double pZ) 
	{
		
	}
    
    @Override
    public boolean hurt(DamageSource pDamageSource, float pAmount) 
    {
    	if(this.isStun())
    	{
        	this.walkAnimation.setSpeed(0.0F);
        	pAmount *= 2.0F;
    	}
    	else if(!pDamageSource.is(DamageTypeTags.BYPASSES_ARMOR) && !pDamageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
    	{
    		pAmount *= 0.1F;
    	}
    	return super.hurt(pDamageSource, pAmount);
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) 
    {
    	return BTASounds.GHIDRUTH_HURT.get();
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
    	return BTASounds.GHIDRUTH_AMBIENT.get();
    }
	
	@Override
	protected float getSoundVolume() 
	{
		return 2.0F;
	}
	
    @Override
    public float maxSwimTurnX() 
    {
    	return 65;
    }

    @Override
    public float maxSwimTurnY() 
    {
    	return 8;
    }
    
    @Override
    public BTAMobType getBTAMobType()
    {
    	return BTAMobType.BOSS;
    }
    
	@Override
	public MobClassification getMobClassification() 
	{
		return MobClassification.WATER;
	}
    
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) 
    {
    	super.addAdditionalSaveData(pCompound);
    	pCompound.putBoolean("isCharge", this.isCharge());
    	pCompound.putBoolean("isStun", this.isStun());
    	pCompound.putInt("StunTick", this.stunTick);
    	pCompound.putInt("ChargeTick", this.chargeTick);
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound)
    {
    	super.readAdditionalSaveData(pCompound);
    	this.setCharge(pCompound.getBoolean("isCharge"));
    	this.setStun(pCompound.getBoolean("isStun"));
    	this.stunTick = pCompound.getInt("StunTick");
    	this.chargeTick = pCompound.getInt("ChargeTick");
        if(this.hasCustomName()) 
        {
        	this.bossEvent.setName(this.getDisplayName());
        }
    }
    
    @Override
    public void setCustomName(@Nullable Component pName) 
    {
    	super.setCustomName(pName);
    	this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer pServerPlayer)
    {
        super.startSeenByPlayer(pServerPlayer);
        this.bossEvent.addPlayer(pServerPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer pServerPlayer)
    {
    	super.stopSeenByPlayer(pServerPlayer);
    	this.bossEvent.removePlayer(pServerPlayer);
    }
    
    public void setCharge(boolean value)
    {
    	this.entityData.set(IS_CHARGE, value);
    }
    
    public boolean isCharge()
    {
    	return this.entityData.get(IS_CHARGE);
    }
    
    public void setStun(boolean value)
    {
    	this.entityData.set(IS_STUN, value);
    }
    
    public boolean isStun()
    {
    	return this.entityData.get(IS_STUN);
    }
}
