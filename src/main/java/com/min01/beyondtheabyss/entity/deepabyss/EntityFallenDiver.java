package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.FallenDiverMeleeAttackGoal;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTAMobType;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class EntityFallenDiver extends AbstractDeepAbyssMonster
{
	public final WaterBoundPathNavigation waterNavigation;
	public final GroundPathNavigation groundNavigation;
	   
	public EntityFallenDiver(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.moveControl = new FallenDiverMoveControl(this);
		this.waterNavigation = new WaterBoundPathNavigation(this, p_21684_);
		this.groundNavigation = new GroundPathNavigation(this, p_21684_);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 20.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.23F)
        		.add(Attributes.ATTACK_DAMAGE, 3.5F)
        		.add(Attributes.FOLLOW_RANGE, 15.0F);
    }
    
    @Override
    public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
    {
    	EntityPartBuilder<EntityFallenDiver> partBuilder = new EntityPartBuilder<EntityFallenDiver>(this);
    	return partBuilder;
    }
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
        this.goalSelector.addGoal(2, new FallenDiverMeleeAttackGoal(this, 1.0F, false));
    }

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	protected SoundEvent getAmbientSound() 
	{
		return this.isInWater() ? SoundEvents.DROWNED_AMBIENT_WATER : SoundEvents.DROWNED_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_32386_) 
	{
		return this.isInWater() ? SoundEvents.DROWNED_HURT_WATER : SoundEvents.DROWNED_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return this.isInWater() ? SoundEvents.DROWNED_DEATH_WATER : SoundEvents.DROWNED_DEATH;
	}
	
	@Override
	public boolean isPushedByFluid() 
	{
		return !this.isSwimming();
	}
	
	@Override
	protected boolean isAffectedByFluids() 
	{
		return !this.isSwimming();
	}
	
	@Override
	public boolean isSwimable() 
	{
		return this.getTarget() != null && this.getTarget().isInWater();
	}
	
	@Override
	public void updateSwimming() 
	{
		if(!this.level.isClientSide) 
		{
			if(this.isEffectiveAi() && this.isInWater() && this.isSwimable())
			{
				this.navigation = this.waterNavigation;
				this.setSwimming(true);
			} 
			else
			{
				this.navigation = this.groundNavigation;
				this.setSwimming(false);
			}
		}
	}
	
	@Override
	public MoveControl getSwimmingMoveControl() 
	{
		return new FallenDiverMoveControl(this);
	}
	
	@Override
	public LookControl getSwimmingLookControl() 
	{
		return new LookControl(this);
	}
	
	public static class FallenDiverMoveControl extends MoveControl 
	{
		private final EntityFallenDiver diver;

		public FallenDiverMoveControl(EntityFallenDiver p_32433_) 
		{
			super(p_32433_);
			this.diver = p_32433_;
		}

		@Override
		public void tick() 
		{
			LivingEntity livingentity = this.diver.getTarget();
			if(this.diver.isSwimable() && this.diver.isInWater())
			{
				if(livingentity != null && livingentity.getY() > this.diver.getY()) 
				{
					this.diver.setDeltaMovement(this.diver.getDeltaMovement().add(0.0D, 0.002D, 0.0D));
				}

				if(this.operation != MoveControl.Operation.MOVE_TO || this.diver.getNavigation().isDone())
				{
					this.diver.setSpeed(0.0F);
					return;
				}

				double d0 = this.wantedX - this.diver.getX();
				double d1 = this.wantedY - this.diver.getY();
				double d2 = this.wantedZ - this.diver.getZ();
				double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
				d1 /= d3;
				float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
				this.diver.setYRot(this.rotlerp(this.diver.getYRot(), f, 90.0F));
				this.diver.yBodyRot = this.diver.getYRot();
				float f1 = (float)(this.speedModifier * this.diver.getAttributeValue(Attributes.MOVEMENT_SPEED));
				float f2 = Mth.lerp(0.125F, this.diver.getSpeed(), f1);
				this.diver.setSpeed(f2);
				this.diver.setDeltaMovement(this.diver.getDeltaMovement().add((double)f2 * d0 * 0.005D, (double)f2 * d1 * 0.1D, (double)f2 * d2 * 0.005D));
			} 
			else 
			{
				if(!this.diver.onGround())
				{
					this.diver.setDeltaMovement(this.diver.getDeltaMovement().add(0.0D, -0.008D, 0.0D));
				}

				super.tick();
			}
		}
	}
	
	public static boolean checkFallenDiverSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pPos.getY() >= 30 && pPos.getY() <= 80 && pServerLevel.getBlockState(pPos.below()).is(BTABlocks.ROT_SOIL.get());
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BTAItems.DIVING_HELMET.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BTAItems.DIVING_SUIT.get()));
		this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(BTAItems.DIVING_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlot.FEET, new ItemStack(BTAItems.DIVING_BOOTS.get()));
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
}
