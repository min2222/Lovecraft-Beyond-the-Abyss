package com.min01.beyondtheabyss.entity.ai.control;

import com.min01.beyondtheabyss.entity.IDeepAbyssMob;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BTASwimmingMoveControl extends MoveControl 
{
	private final float outsideWaterSpeedModifier;
	private final boolean applyGravity;
	private float targetX, targetY, targetZ;

	public BTASwimmingMoveControl(Mob p_148070_, float p_148074_, boolean p_148075_) 
	{
		super(p_148070_);
		this.outsideWaterSpeedModifier = p_148074_;
		this.applyGravity = p_148075_;
	}

	@Override
	public void tick()
	{
		IDeepAbyssMob mob = (IDeepAbyssMob) this.mob;
		if(this.applyGravity && this.mob.isInWater()) 
		{
			this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
		}
		if(this.operation == MoveControl.Operation.MOVE_TO && !this.mob.getNavigation().isDone()) 
		{
			if(this.mob.tickCount % 60 == 0)
			{
				this.generateNewTarget();
			}
			double d0 = this.targetX - this.mob.getX();
			double d1 = this.targetY - this.mob.getY();
			double d2 = this.targetZ - this.mob.getZ();
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			if(d3 < (double) 2.5000003E-7F) 
			{
				this.mob.setZza(0.0F);
			}
			else 
			{
				float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
				this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, (float) mob.maxTurnY()));
				this.mob.yBodyRot = this.mob.getYRot();
				this.mob.yHeadRot = this.mob.getYRot();
				float f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
				if(this.mob.isInWater())
				{
					this.mob.setSpeed(f1 * mob.insideWaterSpeed());
					double d4 = Math.sqrt(d0 * d0 + d2 * d2);
					if(Math.abs(d1) > (double) 1.0E-5F || Math.abs(d4) > (double) 1.0E-5F)
					{
						float f2 = -((float) (Mth.atan2(d1, d4) * (double) (180.0F / (float) Math.PI)));
						f2 = Mth.clamp(Mth.wrapDegrees(f2), (float) (-mob.maxTurnX()), (float) mob.maxTurnX());
						this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f2, 5.0F));
					}
					float f4 = Mth.cos(this.mob.getXRot() * ((float) Math.PI / 180.0F));
					float f3 = Mth.sin(this.mob.getXRot() * ((float) Math.PI / 180.0F));
					this.mob.zza = f4 * f1;
					this.mob.yya = -f3 * f1;
				}
				else 
				{
					this.mob.setSpeed(f1 * this.outsideWaterSpeedModifier);
				}
			}
		} 
		else 
		{
			this.mob.setSpeed(0.0F);
			this.mob.setXxa(0.0F);
			this.mob.setYya(0.0F);
			this.mob.setZza(0.0F);
		}
	}
	
    private void generateNewTarget() 
    {
        Level world = this.mob.level;
        int radius = ((IDeepAbyssMob)this.mob).getSwimRadius();
        for(int i = 0; i < 10; i++)
        {
        	Vec3 pos = BTAUtil.getSpreadPosition(this.mob, radius);
        	HitResult hitResult = this.mob.level.clip(new ClipContext(this.mob.position(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob));
        	if(hitResult instanceof BlockHitResult blockHit)
        	{
                BlockPos targetPos = blockHit.getBlockPos();
                BlockState blockState = world.getBlockState(targetPos);
                if(blockState.is(Blocks.WATER))
                {
                	this.targetX = targetPos.getX();
                	this.targetY = targetPos.getY();
                	this.targetZ = targetPos.getZ();
                	break;
                }
        	}
        }
    }
}