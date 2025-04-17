package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.FallenDiverMeleeAttackGoal;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraft.world.level.block.Blocks;

public class EntityFallenDiver extends AbstractDeepAbyssMonster
{
	public final WaterBoundPathNavigation waterNavigation;
	public final GroundPathNavigation groundNavigation;
	   
	public EntityFallenDiver(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.waterNavigation = new WaterBoundPathNavigation(this, p_21684_);
		this.groundNavigation = new GroundPathNavigation(this, p_21684_);
		
		this.navigation = this.groundNavigation;
		this.lookControl = new LookControl(this);
		this.moveControl = new MoveControl(this);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 20.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.5F)
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
	public boolean isAffectedByFluids()
	{
		return false;
	}
	
	@Override
	public boolean rotateHead()
	{
		return false;
	}
	
	@Override
	public boolean canSwim()
	{
		return this.isInWater();
	}
	
	@Override
	public void updateSwimming() 
	{
		if(!this.isSwimming() && this.canSwim())
		{
			this.navigation = this.waterNavigation;
			this.lookControl = this.getSwimmingLookControl();
			this.moveControl = this.getSwimmingMoveControl();
			this.setSwimming(true);
		}
		if(this.isSwimming() && !this.canSwim())
		{
			this.navigation = this.groundNavigation;
			this.lookControl = new LookControl(this);
			this.moveControl = new MoveControl(this);
			this.setSwimming(false);
		}
	}
	
	public static boolean checkFallenDiverSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pPos.getY() >= 10 && pPos.getY() <= 40 && pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
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
