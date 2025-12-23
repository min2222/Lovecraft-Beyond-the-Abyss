package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.FulgastraSplitGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EntityFulgastra extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Boolean> IS_CHARGED = SynchedEntityData.defineId(EntityFulgastra.class, EntityDataSerializers.BOOLEAN);
	
	public final SmoothAnimationState splittingAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState reformingAnimationState = new SmoothAnimationState();
	
	public EntityFulgastra(EntityType<? extends Monster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(10);
	}

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<EntityFulgastra> partBuilder = new EntityPartBuilder<EntityFulgastra>(this);
    	return partBuilder;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 60.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.45F)
    			.add(Attributes.FOLLOW_RANGE, 30.0F);
    }

	@Override
	protected void registerGoals()
	{
		super.registerGoals();
		this.goalSelector.addGoal(0, new FulgastraSplitGoal(this));
	}
	
    @Override
    protected void defineSynchedData()
    {
    	super.defineSynchedData();
    	this.entityData.define(IS_CHARGED, false);
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
			this.splittingAnimationState.updateWhen(this.getAnimationState() == 1, this.tickCount);
			this.reformingAnimationState.updateWhen(this.getAnimationState() == 2, this.tickCount);
		}
		
		Player player = this.level.getNearestPlayer(this.getX(), this.getY(), this.getZ(), 5.0F, true);
		if(player != null && this.getAnimationState() == 1)
		{
	        Vec3 vec3 = DefaultRandomPos.getPosAway(this, 16, 7, player.position());
	        if(vec3 != null)
	        {
	        	this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 2.0F);
	        }
		}
	}
	
	@Override
	public void push(Entity pEntity) 
	{
		if(!(pEntity instanceof EntityFulgastra) && !(pEntity instanceof EntitySplittedFulgastra))
		{
			super.push(pEntity);
		}
	}
	
	@Override
	protected void updateWalkAnimation(float pPartialTick) 
	{
		float f = Math.min(pPartialTick * 16.0F, 1.0F);
		this.walkAnimation.update(f, 0.4F);
	}
	
	@Override
	public void moveToTarget() 
	{
		if(this.getAnimationState() != 1)
		{
			super.moveToTarget();
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putBoolean("isCharged", this.isCharged());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) 
	{
		super.readAdditionalSaveData(pCompound);
		this.setCharged(pCompound.getBoolean("isCharged"));
	}
	
	public static boolean checkFulgastraSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	public void setCharged(boolean value)
	{
		this.entityData.set(IS_CHARGED, value);
	}
	
	public boolean isCharged()
	{
		return this.entityData.get(IS_CHARGED);
	}
}
