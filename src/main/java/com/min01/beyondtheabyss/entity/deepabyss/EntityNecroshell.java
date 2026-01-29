package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.control.BTASwimmingMoveControl;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.NecroshellAttackGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.NecroshellHidingGoal;
import com.min01.beyondtheabyss.entity.ai.navigation.BTAGroundPathNavigation;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public class EntityNecroshell extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Integer> SHELL_TYPE = SynchedEntityData.defineId(EntityNecroshell.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_HIDING = SynchedEntityData.defineId(EntityNecroshell.class, EntityDataSerializers.BOOLEAN);
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState intimidateAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState hideAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState unhideAnimationState = new SmoothAnimationState();
	
	public EntityNecroshell(EntityType<? extends Monster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = this.random.nextInt(5);
		this.setMaxUpStep(1);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 40.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.15F)
        		.add(Attributes.FOLLOW_RANGE, 10.0F)
        		.add(Attributes.ATTACK_DAMAGE, 4.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 5.0F)
        		.add(Attributes.ARMOR, 12.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntityNecroshell> partBuilder = new EntityPartBuilder<EntityNecroshell>(this);
		return partBuilder;
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(SHELL_TYPE, 0);
		this.entityData.define(IS_HIDING, false);
	}
	
	@Override
	protected void registerGoals() 
	{
		super.registerGoals();
		this.goalSelector.addGoal(0, new NecroshellAttackGoal(this));
		this.goalSelector.addGoal(0, new NecroshellHidingGoal(this));
	}
	
	@Override
	public void registerDefaultGoals()
	{
		this.goalSelector.addGoal(0, new RandomStrollGoal(this, 1.0F)
		{
			@Override
			public boolean canUse()
			{
				return super.canUse() && EntityNecroshell.this.canMoveAround();
			}
		});
		this.goalSelector.addGoal(0, new RandomLookAroundGoal(this)
		{
			@Override
			public boolean canUse()
			{
				return super.canUse() && EntityNecroshell.this.canLookAround();
			}
		});
		this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F)
		{
			@Override
			public boolean canUse()
			{
				return super.canUse() && EntityNecroshell.this.canLookAround();
			}
		});
		this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Mob.class, 8.0F)
		{
			@Override
			public boolean canUse()
			{
				return super.canUse() && EntityNecroshell.this.canLookAround();
			}
		});
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.level.isClientSide)
		{
			this.idleAnimationState.updateWhen(this.getAnimationState() == 0 && !this.isHiding(), this.tickCount);
			this.attackAnimationState.updateWhen(this.isUsingSkill(1), this.tickCount);
			this.intimidateAnimationState.updateWhen(this.hasTarget() && this.getAnimationState() == 0 && !this.isHiding(), this.tickCount);
			this.hideAnimationState.updateWhen(this.isHiding(), this.tickCount);
			this.unhideAnimationState.updateWhen(this.isUsingSkill(2), this.tickCount);
		}

		Player player = this.level.getNearestPlayer(this.getX(), this.getY(), this.getZ(), 3.5F, true);
		if(player != null && !this.isUsingSkill() && !this.isHiding())
		{
	        Vec3 vec3 = DefaultRandomPos.getPosAway(this, 16, 7, player.position());
	        if(vec3 != null)
	        {
	        	this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 2.0F);
	        }
		}
		
    	if(this.isEyeInFluidType(ForgeMod.WATER_TYPE.get()))
    	{
    		if(this.navigation instanceof BTAGroundPathNavigation)
    		{
        		this.navigation = this.createNavigation(this.level);
        		this.moveControl = new BTASwimmingMoveControl(this);
        		this.lookControl = new SmoothSwimmingLookControl(this, 10);
    		}
    	}
    	else if(this.navigation instanceof WaterBoundPathNavigation)
    	{
    		this.navigation = new BTAGroundPathNavigation(this, this.level);
    		this.moveControl = new MoveControl(this);
    		this.lookControl = new LookControl(this);
    	}
	}
	
	@Override
	public List<String> getCollidePart() 
	{
		if(this.isSlasherShell())
		{
			return List.of("SlasherSkull");
		}
		return List.of("BlasterSkull");
	}

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.NETURAL;
	}
	
	@Override
	public boolean canBreathOutsideWater() 
	{
		return true;
	}
	
	@Override
	public boolean isSwimable() 
	{
		return false;
	}
	
	@Override
	public float moveSpeed()
	{
		return 1.0F;
	}
	
	@Override
	protected boolean isAffectedByFluids()
	{
		return false;
	}
	
	@Override
	public boolean canMoveAround() 
	{
		return super.canMoveAround() && !this.isHiding();
	}
	
	@Override
	public boolean canLookAround() 
	{
		return super.canLookAround() && !this.isHiding();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag)
	{
		int type = 0;
		if(this.random.nextBoolean())
		{
			type = 1;
		}
		this.setShellType(type);
		if(pReason == MobSpawnType.NATURAL)
		{
			BlockPos floorPos = BTAUtil.getGroundPos(this.level, this.getX(), this.getY(), this.getZ()).above();
			Vec3 pos = Vec3.atBottomCenterOf(floorPos);
			this.moveTo(pos);
		}
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}
	
	public static boolean checkNecroshellSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound)
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("ShellType", this.getShellType());
		pCompound.putBoolean("isHiding", this.isHiding());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound)
	{
		super.readAdditionalSaveData(pCompound);
		this.setShellType(pCompound.getInt("ShellType"));
		this.setHiding(pCompound.getBoolean("isHiding"));
	}
	
	@Override
	protected void doPush(Entity pEntity)
	{
		
	}
	
	@Override
	public void push(double pX, double pY, double pZ) 
	{
		
	}
	
	public void setHiding(boolean value)
	{
		this.entityData.set(IS_HIDING, value);
	}
	
	public boolean isHiding()
	{
		return this.entityData.get(IS_HIDING);
	}
	
	public void setShellType(int value)
	{
		this.entityData.set(SHELL_TYPE, value);
	}
	
	public int getShellType()
	{
		return this.entityData.get(SHELL_TYPE);
	}
	
	public boolean isBlasterShell()
	{
		return this.getShellType() == 0;
	}
	
	public boolean isSlasherShell()
	{
		return this.getShellType() == 1;
	}
}
