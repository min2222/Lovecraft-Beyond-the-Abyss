package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.block.BTABlocks;
import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class EntityCorpseAngler extends AbstractDeepAbyssMonster
{
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState openMouthAnimationState = new AnimationState();
	public final AnimationState closeMouthAnimationState = new AnimationState();
	public final AnimationState burrowAnimationState = new AnimationState();
	public final AnimationState unburrowAnimationState = new AnimationState();
	
	public final Worm worm1 = new Worm();
	public final Worm worm2 = new Worm();
	public final Worm worm3 = new Worm();
	public final Worm worm4 = new Worm();
	public final Worm worm5 = new Worm();
	public final Worm worm6 = new Worm();
	
	public static final List<String> LIST = List.of("Up", "Jaw2", "Tails", "TailEdge", "Body2", "Left", "Right");
	
	public EntityCorpseAngler(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.noCulling = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 80.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.65F)
        		.add(Attributes.FOLLOW_RANGE, 30.0F);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<EntityCorpseAngler> partBuilder = new EntityPartBuilder<EntityCorpseAngler>(this)
    	{
    		@Override
    		public boolean isInWater() 
    		{
    			return true;
    		}

    		@Override
    		public float getWaterOffset() 
    		{
    			return 1.5F;
    		}
    	};
    	return partBuilder;
	}
	
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) 
	{
        if(ANIMATION_STATE.equals(p_219422_) && this.level.isClientSide) 
        {
            switch(this.getAnimationState()) 
            {
        		case 0: 
        		{
        			this.stopAllAnimationStates();
        			break;
        		}
        		case 1:
        		{
        			this.stopAllAnimationStates();
        			this.openMouthAnimationState.start(this.tickCount);
        			break;
        		}
        		case 2:
        		{
        			this.stopAllAnimationStates();
        			this.closeMouthAnimationState.start(this.tickCount);
        			break;
        		}
        		case 3:
        		{
        			this.stopAllAnimationStates();
        			this.burrowAnimationState.start(this.tickCount);
        			break;
        		}
        		case 4:
        		{
        			this.stopAllAnimationStates();
        			this.unburrowAnimationState.start(this.tickCount);
        			break;
        		}
            }
        }
	}
	
	@Override
	public void stopAllAnimationStates() 
	{
		this.openMouthAnimationState.stop();
		this.closeMouthAnimationState.stop();
		this.burrowAnimationState.stop();
		this.unburrowAnimationState.stop();
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
		
		float speed = 0.35F;
    	
    	WormChain.tick(this.worm1, this, 0.0F, speed);
    	WormChain.tick(this.worm2, this.worm1, 0.0F, speed);
    	WormChain.tick(this.worm3, this.worm2, 0.0F, speed);
    	
    	WormChain.tick(this.worm4, this.worm3, 0.0F, speed);
    	WormChain.tick(this.worm5, this.worm4, 0.0F, speed);
    	WormChain.tick(this.worm6, this.worm5, 0.0F, speed);
    	
		DeepAbyssUtil.fishFlopping(this);
		boolean canBurrow = BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below()) && BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below(2)) && BTAUtil.isCollisionShapeFullBlock(this.level, this.blockPosition().below(3));
		if(this.level.isClientSide)
		{
			this.idleAnimationState.animateWhen(this.isInWater() && !BTAUtil.isMoving(this), this.tickCount);
		}
		if(this.getAnimationState() == 0 && this.isInWater())
		{
			if(canBurrow)
			{
				if(!this.level.isClientSide && this.getTarget() == null)
				{
					this.setAnimationState(3);
					this.setAnimationTick(40);
					this.setCanMove(false);
					this.setCanLook(false);
				}
			}
			else
			{
				//TODO move to ground;
			}
		}
		if(this.getAnimationState() == 3)
		{
			if(this.getAnimationTick() > 0)
			{
				this.spawnParticle();
			}
			else if(!canBurrow)
			{
				this.setAnimationState(4);
				this.setAnimationTick(20);
			}
		}
		if(this.getAnimationState() == 4)
		{
			if(this.getAnimationTick() > 0)
			{
				this.spawnParticle();
			}
			else
			{
				this.setCanMove(true);
				this.setCanLook(true);
			}
		}
	}
	
	public static boolean checkCorpseAnglerSpawnRules(EntityType<? extends AbstractDeepAbyssMonster> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pPos.getY() >= 0 && pPos.getY() <= 40 && pServerLevel.getBlockState(pPos.below()).is(BTABlocks.ROT_SOIL.get()) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }
	
	public void spawnParticle()
	{
		double motionX = this.random.nextGaussian() * 0.2D;
		double motionY = 0.03D;
		double motionZ = this.random.nextGaussian() * 0.2D;
		for(int i = 0; i < 50; i++)
		{
			double range = this.getBbWidth() * 2;
            double x = this.getX() + (this.random.nextDouble() - this.random.nextDouble()) * range + 0.5D;
            double z = this.getZ() + (this.random.nextDouble() - this.random.nextDouble()) * range + 0.5D;
			this.level.addAlwaysVisibleParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), x, this.getY(), z, motionX, motionY, motionZ);
		}
	}
	
	@Override
	public boolean canSwim() 
	{
		return super.canSwim() && this.canMove();
	}
	
    @Override
    protected void updateWalkAnimation(float p_268283_)
    {
        float f = Math.min(p_268283_ * 20.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }
	
	@Override
	protected void doPush(Entity p_21294_)
	{
		if(this.getAnimationState() != 3)
		{
			super.doPush(p_21294_);
		}
	}
	
	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) 
	{
		if(p_21016_.getDirectEntity() instanceof Player player && this.getAnimationState() == 3)
		{
	        String part = BTAUtil.getMultiPart(this.getBounds(), player);
	        if(part != null && LIST.contains(part))
	        {
				this.setAnimationState(4);
				this.setAnimationTick(20);
	        }
		}
		return super.hurt(p_21016_, p_21017_);
	}
    
	@Override
	public int maxTurnX() 
	{
		return 5;
	}
	
	@Override
	public int maxTurnY() 
	{
		return 5;
	}
}
