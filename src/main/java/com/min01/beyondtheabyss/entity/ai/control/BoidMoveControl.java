package com.min01.beyondtheabyss.entity.ai.control;

import com.min01.beyondtheabyss.entity.IDeepAbyssMob;
import com.min01.beyondtheabyss.misc.Boid;
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

public class BoidMoveControl extends MoveControl 
{
	private final float outsideWaterSpeedModifier;
	private final boolean applyGravity;
	private boolean forceTarget;
	public Vec3 targetPos = Vec3.ZERO;
	public final Boid boid;

	public BoidMoveControl(Mob mob, float outsideWaterSpeedModifier, boolean applyGravity)
	{
		super(mob);
		this.outsideWaterSpeedModifier = outsideWaterSpeedModifier;
		this.applyGravity = applyGravity;
		this.boid = new Boid(mob);
	}

	@Override
	public void tick() 
	{
		IDeepAbyssMob mob = (IDeepAbyssMob) this.mob;
		if(this.applyGravity && this.mob.isInWater()) 
		{
			this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
		}
		if(this.operation == MoveControl.Operation.MOVE_TO) 
		{
        	if(!this.forceTarget)
        	{
    	        if(this.mob.tickCount % 60 == 0 || this.targetPos.equals(Vec3.ZERO))
    	        {
    	        	this.generateNewTarget();
    	        }
        	}
			this.boid.tick();
	        this.stayInWater();
			Vec3 direction = this.mob.getDeltaMovement();
			double d0 = direction.x;
			double d1 = direction.y;
			double d2 = direction.z;
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			if(d3 < (double) 2.5000003E-7F) 
			{
				this.mob.setZza(0.0F);
			}
			else 
			{
				float f = (float) (Mth.atan2(d2, d0) * (double) (180.0F / (float) Math.PI)) - 90.0F;
				this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, 2.0F));
				this.mob.yBodyRot = this.mob.getYRot();
				this.mob.yHeadRot = this.mob.getYRot();
				float f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
				if(this.mob.isInWater()) 
				{
					this.mob.setSpeed(f1 * mob.insideWaterSpeed());
					double d4 = Math.sqrt(d0 * d0 + d2 * d2);
					if(Math.abs(d1) > (double) 1.0E-5F || Math.abs(d4) > (double) 1.0E-5F) 
					{
						float f3 = -((float) (Mth.atan2(d1, d4) * (double) (180.0F / (float) Math.PI)));
						f3 = Mth.clamp(Mth.wrapDegrees(f3), (float) (-mob.maxTurnX()), (float) mob.maxTurnX());
						this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f3, 2.0F));
					}
					float f6 = Mth.cos(this.mob.getXRot() * ((float) Math.PI / 180.0F));
					float f4 = Mth.sin(this.mob.getXRot() * ((float) Math.PI / 180.0F));
					this.mob.zza = f6 * f1;
					this.mob.yya = -f4 * f1;
				}
				else
				{
					float f5 = Math.abs(Mth.wrapDegrees(this.mob.getYRot() - f));
					float f2 = this.getTurningSpeedFactor(f5);
					this.mob.setSpeed(f1 * this.outsideWaterSpeedModifier * f2);
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
                	this.targetPos = blockHit.getLocation();
                	break;
                }
        	}
        }
    }

	private float getTurningSpeedFactor(float p_249853_) 
	{
		return 1.0F - Mth.clamp((p_249853_ - 10.0F) / 50.0F, 0.0F, 1.0F);
	}
    
    public void stayInWater()
    {
		if(this.mob.isInWater())
		{
	        BlockPos blockPos = this.mob.blockPosition();
	        BlockState blockAbove = this.mob.level.getBlockState(blockPos.above(2));
	        BlockState blockBelow = this.mob.level.getBlockState(blockPos.below(1));
	        float amount = this.amount();
	        if(blockBelow.getFluidState().isEmpty()) 
	        {
	        	this.mob.addDeltaMovement(new Vec3(0, amount, 0));
	        }
	        if(blockAbove.getFluidState().isEmpty())
	        {
	        	this.mob.addDeltaMovement(new Vec3(0, -amount, 0));
	        }
		}
    }
    
    public float amount() 
    {
        float amount = 0.05F;
        float dY = Mth.abs((float) this.mob.getDeltaMovement().y);
        if(dY > amount) 
        {
            amount = dY;
        }
        return amount;
    }
	
    public void setTargetPos(Vec3 pos)
    {
    	this.targetPos = pos;
    }
    
    public void setForceTarget(Vec3 pos)
    {
    	this.targetPos = pos;
    	this.forceTarget = true;
    }
    
    public void setForceTarget(boolean forceTarget)
    {
    	this.forceTarget = forceTarget;
    	if(!forceTarget)
    	{
    		this.generateNewTarget();
    	}
    }
    
    public Vec3 getTargetPos()
    {
    	return this.targetPos;
    }
}