package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavorePutridBubbleGoal;
import com.min01.beyondtheabyss.entity.ai.goal.deepabyss.MutavoreTongueGoal;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class EntityMutavore extends AbstractDeepAbyssMonster
{
	public final Worm worm1 = new Worm();
	public final Worm worm6 = new Worm();
	
	public final Worm worm2 = new Worm();
	public final Worm worm7 = new Worm();
	
	public final Worm worm3 = new Worm();
	public final Worm worm8 = new Worm();
	
	public final Worm worm4 = new Worm();
	public final Worm worm9 = new Worm();
	
	public final Worm worm5 = new Worm();
	public final Worm worm10 = new Worm();
	
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState bubbleStartAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState bubbleStopAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState tongueStartAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState tongueLoopAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState tongueStopAnimationState = new SmoothAnimationState();
	
	public EntityMutavore(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 150.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
        		.add(Attributes.FOLLOW_RANGE, 50.0F)
        		.add(Attributes.ATTACK_DAMAGE, 8.0F)
        		.add(Attributes.ARMOR, 8.0F);
    }
    
    @Override
    protected void registerGoals() 
    {
    	super.registerGoals();
    	this.goalSelector.addGoal(0, new MutavorePutridBubbleGoal(this));
    	this.goalSelector.addGoal(0, new MutavoreTongueGoal(this));
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
		EntityPartBuilder<EntityMutavore> partBuilder = new EntityPartBuilder<EntityMutavore>(this);
		return partBuilder;
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
		
		this.worm1.setOldPosAndRot();
		this.worm2.setOldPosAndRot();
		this.worm3.setOldPosAndRot();
		this.worm4.setOldPosAndRot();
		this.worm5.setOldPosAndRot();
		this.worm6.setOldPosAndRot();
		this.worm7.setOldPosAndRot();
		this.worm8.setOldPosAndRot();
		this.worm9.setOldPosAndRot();
		this.worm10.setOldPosAndRot();
		
		float speed = 0.15F;
    	
    	WormChain.tick(this.worm1, this, 0.0F, speed);
    	WormChain.tick(this.worm2, this.worm1, 0.0F, speed);
    	
    	WormChain.tick(this.worm3, this, 0.0F, speed);
    	WormChain.tick(this.worm4, this.worm3, 0.0F, speed);
    	
    	WormChain.tick(this.worm5, this, 0.0F, speed);
    	WormChain.tick(this.worm6, this.worm5, 0.0F, speed);
    	
    	WormChain.tick(this.worm7, this, 0.0F, speed);
    	WormChain.tick(this.worm8, this.worm7, 0.0F, speed);
    	
    	WormChain.tick(this.worm9, this, 0.0F, speed);
    	WormChain.tick(this.worm10, this.worm9, 0.0F, speed);
    	
    	if(this.level.isClientSide)
    	{
    		this.idleAnimationState.updateWhen(this.getAnimationState() == 0 && this.isInWater(), this.tickCount);
    		this.bubbleStartAnimationState.updateWhen(this.isUsingSkill(1), this.tickCount);
    		this.bubbleStopAnimationState.updateWhen(this.isUsingSkill(2), this.tickCount);
    		this.tongueStartAnimationState.updateWhen(this.isUsingSkill(3), this.tickCount);
    		this.tongueLoopAnimationState.updateWhen(this.isUsingSkill(4), this.tickCount);
    		this.tongueStopAnimationState.updateWhen(this.isUsingSkill(5), this.tickCount);
    	}
	}
	
	@Override
	protected void updateWalkAnimation(float p_268283_) 
	{
		float f = Math.min(p_268283_ * 10.0F, 1.0F);
		this.walkAnimation.update(f, 0.4F);
	}
	
	@Override
	public int maxTurnX() 
	{
		return !this.hasTarget() ? 55 : 75;
	}
	
	@Override
	public int maxTurnY() 
	{
		return !this.hasTarget() ? 3 : 5;
	}
	
	public static boolean checkMutavoreSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER) && pPos.getY() <= 40;
    }
}
