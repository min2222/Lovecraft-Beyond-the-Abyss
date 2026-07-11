package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.entity.ai.control.BoidMoveControl;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.MobClassification;
import com.min01.beyondtheabyss.sound.BTASounds;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeMod;

public class GloomfishEntity extends AbstractBTACreature
{	
	public GloomfishEntity(EntityType<? extends AbstractBTACreature> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = 1;
		this.moveControl = new BoidMoveControl<>(this);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 2.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.2F)
    			.add(ForgeMod.SWIM_SPEED.get(), 0.5F);
    }

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.PASSIVE;
	}
	
	@Override
	public MobClassification getMobClassification() 
	{
		return MobClassification.WATER;
	}
	
	@Override
	protected void doPush(Entity pEntity) 
	{
		
	}
	
	@Override
	protected SoundEvent getAmbientSound()
	{
		return BTASounds.GLOOMFISH_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() 
	{
		return BTASounds.GLOOMFISH_DEATH.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource)
	{
		return BTASounds.GLOOMFISH_HURT.get();
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		DeepAbyssUtil.fishFlopping(this);
	}
	
	@Override
	public int getMaxSpawnClusterSize() 
	{
		return Integer.MAX_VALUE;
	}
	
	public static boolean checkGloomfishSpawnRules(EntityType<? extends AbstractBTACreature> pType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
}