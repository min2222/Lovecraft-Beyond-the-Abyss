package com.min01.beyondtheabyss.entity;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFlyingMonster extends AbstractBTAMonster
{
	private Vec3 moveTargetPoint = Vec3.ZERO;
	private BlockPos anchorPoint = BlockPos.ZERO;
	private AbstractFlyingMonster.AttackPhase attackPhase = AbstractFlyingMonster.AttackPhase.CIRCLE;

	public AbstractFlyingMonster(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.moveControl = new PhantomMoveControl(this);
		this.lookControl = new PhantomLookControl(this);
	}

	@Override
	protected void registerGoals() 
	{
		this.goalSelector.addGoal(3, new PhantomCircleAroundAnchorGoal());
		this.targetSelector.addGoal(1, new PhantomAttackPlayerTargetGoal());
	}

	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33126_, DifficultyInstance p_33127_, MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) 
	{
		this.anchorPoint = this.blockPosition().above(5);
		return super.finalizeSpawn(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag p_33132_) 
	{
		super.readAdditionalSaveData(p_33132_);
		if(p_33132_.contains("AX"))
		{
			this.anchorPoint = new BlockPos(p_33132_.getInt("AX"), p_33132_.getInt("AY"), p_33132_.getInt("AZ"));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag p_33141_)
	{
		super.addAdditionalSaveData(p_33141_);
		p_33141_.putInt("AX", this.anchorPoint.getX());
		p_33141_.putInt("AY", this.anchorPoint.getY());
		p_33141_.putInt("AZ", this.anchorPoint.getZ());
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double p_33107_) 
	{
		return true;
	}

	@Override
	protected BodyRotationControl createBodyControl() 
	{
		return new PhantomBodyRotationControl(this);
	}

	@Override
	protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_)
	{
		
	}

	@Override
	public void travel(Vec3 p_20818_)
	{
		if(this.isControlledByLocalInstance())
		{
			if(this.isInWater())
			{
				this.moveRelative(0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
			} 
			else if(this.isInLava()) 
			{
				this.moveRelative(0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
			} 
			else 
			{
				BlockPos ground = this.getBlockPosBelowThatAffectsMyMovement();
				float f = 0.91F;
				if(this.onGround())
				{
					f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
				}
				float f1 = 0.16277137F / (f * f * f);
				f = 0.91F;
				if(this.onGround())
				{
					f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
				}
				this.moveRelative(this.onGround() ? 0.1F * f1 : 0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
			}
		}
		this.calculateEntityAnimation(false);
	}

	@Override
	public boolean onClimbable()
	{
		return false;
	}

	public abstract class PhantomMoveTargetGoal extends Goal
	{
		public PhantomMoveTargetGoal()
		{
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		protected boolean touchingTarget()
		{
			return AbstractFlyingMonster.this.moveTargetPoint.distanceToSqr(AbstractFlyingMonster.this.getX(), AbstractFlyingMonster.this.getY(), AbstractFlyingMonster.this.getZ()) < 4.0D;
		}
	}

	public static enum AttackPhase 
	{
		CIRCLE, SWOOP;
	}
	
	public class PhantomAttackPlayerTargetGoal extends Goal
	{
		private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
		private int nextScanTick = reducedTickDelay(20);

		@Override
		public boolean canUse() 
		{
			if(this.nextScanTick > 0)
			{
				--this.nextScanTick;
				return false;
			} 
			else
			{
				this.nextScanTick = reducedTickDelay(60);
				List<Player> list = AbstractFlyingMonster.this.level().getNearbyPlayers(this.attackTargeting, AbstractFlyingMonster.this, AbstractFlyingMonster.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
				if(!list.isEmpty())
				{
					list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());
					for(Player player : list)
					{
						if(AbstractFlyingMonster.this.canAttack(player, TargetingConditions.DEFAULT)) 
						{
							AbstractFlyingMonster.this.setTarget(player);
							return true;
						}
					}
				}
				return false;
			}
		}

		@Override
		public boolean canContinueToUse() 
		{
			LivingEntity livingentity = AbstractFlyingMonster.this.getTarget();
			return livingentity != null ? AbstractFlyingMonster.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
		}
	}

	public class PhantomCircleAroundAnchorGoal extends PhantomMoveTargetGoal 
	{
		private float angle;
		private float distance;
		private float height;
		private float clockwise;

		@Override
		public boolean canUse()
		{
			return AbstractFlyingMonster.this.getTarget() == null || AbstractFlyingMonster.this.attackPhase == AbstractFlyingMonster.AttackPhase.CIRCLE;
		}

		@Override
		public void start() 
		{
			this.distance = 15.0F + AbstractFlyingMonster.this.random.nextFloat() * 10.0F;
			this.height = -4.0F + AbstractFlyingMonster.this.random.nextFloat() * 9.0F;
			this.clockwise = AbstractFlyingMonster.this.random.nextBoolean() ? 1.0F : -1.0F;
			this.selectNext();
		}

		@Override
		public void tick() 
		{
			if(AbstractFlyingMonster.this.random.nextInt(this.adjustedTickDelay(350)) == 0)
			{
				this.height = -4.0F + AbstractFlyingMonster.this.random.nextFloat() * 9.0F;
			}

			if(AbstractFlyingMonster.this.random.nextInt(this.adjustedTickDelay(250)) == 0) 
			{
				++this.distance;
				if(this.distance > 15.0F) 
				{
					this.distance = 5.0F;
					this.clockwise = -this.clockwise;
				}
			}

			if(AbstractFlyingMonster.this.random.nextInt(this.adjustedTickDelay(450)) == 0) 
			{
				this.angle = AbstractFlyingMonster.this.random.nextFloat() * 2.0F * (float) Math.PI;
				this.selectNext();
			}

			if(this.touchingTarget()) 
			{
				this.selectNext();
			}

			if(AbstractFlyingMonster.this.moveTargetPoint.y < AbstractFlyingMonster.this.getY() && !AbstractFlyingMonster.this.level.isEmptyBlock(AbstractFlyingMonster.this.blockPosition().below(1)))
			{
				this.height = Math.max(1.0F, this.height);
				this.selectNext();
			}

			if(AbstractFlyingMonster.this.moveTargetPoint.y > AbstractFlyingMonster.this.getY() && !AbstractFlyingMonster.this.level.isEmptyBlock(AbstractFlyingMonster.this.blockPosition().above(1))) 
			{
				this.height = Math.min(-1.0F, this.height);
				this.selectNext();
			}
		}

		private void selectNext() 
		{
			if(BlockPos.ZERO.equals(AbstractFlyingMonster.this.anchorPoint)) 
			{
				AbstractFlyingMonster.this.anchorPoint = AbstractFlyingMonster.this.blockPosition();
			}
			this.angle += this.clockwise * 15.0F * ((float) Math.PI / 180.0F);
			AbstractFlyingMonster.this.moveTargetPoint = Vec3.atLowerCornerOf(AbstractFlyingMonster.this.anchorPoint).add((double) (this.distance * Mth.cos(this.angle)), (double) (-4.0F + this.height), (double) (this.distance * Mth.sin(this.angle)));
		}
	}

	public class PhantomBodyRotationControl extends BodyRotationControl
	{
		public PhantomBodyRotationControl(Mob p_33216_)
		{
			super(p_33216_);
		}

		@Override
		public void clientTick() 
		{
			AbstractFlyingMonster.this.yHeadRot = AbstractFlyingMonster.this.yBodyRot;
			AbstractFlyingMonster.this.yBodyRot = AbstractFlyingMonster.this.getYRot();
		}
	}

	public class PhantomLookControl extends LookControl
	{
		public PhantomLookControl(Mob p_33235_) 
		{
			super(p_33235_);
		}

		@Override
		public void tick()
		{
			
		}
	}

	public class PhantomMoveControl extends MoveControl
	{
		private float speed = 0.5F;

		public PhantomMoveControl(Mob p_33241_) 
		{
			super(p_33241_);
		}

		@Override
		public void tick() 
		{
			if(AbstractFlyingMonster.this.horizontalCollision) 
			{
				AbstractFlyingMonster.this.setYRot(AbstractFlyingMonster.this.getYRot() + 180.0F);
				this.speed = 0.1F;
			}
			double d0 = AbstractFlyingMonster.this.moveTargetPoint.x - AbstractFlyingMonster.this.getX();
			double d1 = AbstractFlyingMonster.this.moveTargetPoint.y - AbstractFlyingMonster.this.getY();
			double d2 = AbstractFlyingMonster.this.moveTargetPoint.z - AbstractFlyingMonster.this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			if(Math.abs(d3) > (double) 1.0E-5F)
			{
				double d4 = 1.0D - Math.abs(d1 * (double) 0.7F) / d3;
				d0 *= d4;
				d2 *= d4;
				d3 = Math.sqrt(d0 * d0 + d2 * d2);
				double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
				float f = AbstractFlyingMonster.this.getYRot();
				float f1 = (float) Mth.atan2(d2, d0);
				float f2 = Mth.wrapDegrees(AbstractFlyingMonster.this.getYRot() + 90.0F);
				float f3 = Mth.wrapDegrees(f1 * (180.0F / (float) Math.PI));
				AbstractFlyingMonster.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
				AbstractFlyingMonster.this.yBodyRot = AbstractFlyingMonster.this.getYRot();
				if(Mth.degreesDifferenceAbs(f, AbstractFlyingMonster.this.getYRot()) < 3.0F)
				{
					this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
				}
				else
				{
					this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
				}
				float f4 = (float) (-(Mth.atan2(-d1, d3) * (double) (180.0F / (float) Math.PI)));
				AbstractFlyingMonster.this.setXRot(f4);
				float f5 = AbstractFlyingMonster.this.getYRot() + 90.0F;
				double d6 = (double) (this.speed * Mth.cos(f5 * ((float) Math.PI / 180.0F))) * Math.abs(d0 / d5);
				double d7 = (double) (this.speed * Mth.sin(f5 * ((float) Math.PI / 180.0F))) * Math.abs(d2 / d5);
				double d8 = (double) (this.speed * Mth.sin(f4 * ((float) Math.PI / 180.0F))) * Math.abs(d1 / d5);
				Vec3 vec3 = AbstractFlyingMonster.this.getDeltaMovement();
				AbstractFlyingMonster.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
			}
		}
	}
}
