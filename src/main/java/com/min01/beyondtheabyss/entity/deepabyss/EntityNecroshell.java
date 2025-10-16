package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.NecroshellAttackGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.NecroshellHidingGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;

public class EntityNecroshell extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Integer> SHELL_TYPE = SynchedEntityData.defineId(EntityNecroshell.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_HIDING = SynchedEntityData.defineId(EntityNecroshell.class, EntityDataSerializers.BOOLEAN);
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState intimidateAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState hideAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState unhideAnimationState = new SmoothAnimationState();
	
	public EntityNecroshell(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.setMaxUpStep(1);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 40.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.45F)
        		.add(Attributes.FOLLOW_RANGE, 10.0F)
        		.add(Attributes.ATTACK_DAMAGE, 4.0F)
        		.add(Attributes.ARMOR, 6.0F);
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
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.5F)
		{
			@Override
			public boolean canUse()
			{
				return super.canUse() && EntityNecroshell.this.canRandomStroll();
			}
		});
		this.goalSelector.addGoal(0, new NecroshellAttackGoal(this));
		this.goalSelector.addGoal(0, new NecroshellHidingGoal(this));
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
		return BTAMobType.HOSTILE;
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
	public boolean canRandomStroll() 
	{
		return super.canRandomStroll() && !this.isHiding();
	}
	
	@Override
	public boolean canLookAround() 
	{
		return super.canLookAround() && !this.isHiding();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		this.setShellType(this.random.nextInt(0, 2));
		if(p_21436_ == MobSpawnType.NATURAL)
		{
			Vec3 pos = Vec3.atBottomCenterOf(p_21434_.getHeightmapPos(Types.OCEAN_FLOOR_WG, this.blockPosition()).above());
			this.moveTo(pos);
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	public static boolean checkNecroshellSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_)
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putInt("ShellType", this.getShellType());
		p_21484_.putBoolean("isHiding", this.isHiding());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_)
	{
		super.readAdditionalSaveData(p_21450_);
		this.setShellType(p_21450_.getInt("ShellType"));
		this.setHiding(p_21450_.getBoolean("isHiding"));
	}
	
	@Override
	protected void doPush(Entity p_20971_)
	{
		if(!this.isHiding())
		{
			super.doPush(p_20971_);
		}
	}
	
	@Override
	public void push(double p_20286_, double p_20287_, double p_20288_) 
	{
		if(!this.isHiding())
		{
			super.push(p_20286_, p_20287_, p_20288_);
		}
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
