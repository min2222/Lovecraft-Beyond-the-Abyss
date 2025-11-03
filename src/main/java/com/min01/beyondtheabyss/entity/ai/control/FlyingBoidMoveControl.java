package com.min01.beyondtheabyss.entity.ai.control;

import java.util.List;

import com.min01.beyondtheabyss.entity.IBTAMob;
import com.min01.beyondtheabyss.misc.FlyingBoid;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FlyingBoidMoveControl extends BoidMoveControl 
{
	public final FlyingBoid flyingBoid;
	
	public FlyingBoidMoveControl(Mob mob)
	{
		super(mob);
		this.flyingBoid = new FlyingBoid(mob);
	}

	@Override
	public void tick() 
	{
		IBTAMob mob = (IBTAMob) this.mob;
		if(this.operation == MoveControl.Operation.MOVE_TO || mob.ignoreOperation()) 
		{
	      	if(!this.forceTarget)
	    	{
		        if(this.mob.tickCount % mob.targetSettingInterval() == 0 || this.targetPos.equals(Vec3.ZERO) || this.targetPos.subtract(this.mob.position()).length() <= 2.5F)
		        {
		        	this.generateNewTarget();
		        }
	    	}
			this.flyingBoid.update(List.of(), true, true, true, 10.0F, 0.3F);
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
				this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f, mob.maxTurnY()));
				this.mob.yBodyRot = this.mob.getYRot();
				this.mob.yHeadRot = this.mob.getYRot();
				float f1 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
				this.mob.setSpeed(f1 * mob.moveSpeed());
				double d4 = Math.sqrt(d0 * d0 + d2 * d2);
				if(Math.abs(d1) > (double) 1.0E-5F || Math.abs(d4) > (double) 1.0E-5F) 
				{
					float f3 = -((float) (Mth.atan2(d1, d4) * (double) (180.0F / (float) Math.PI)));
					f3 = Mth.clamp(Mth.wrapDegrees(f3), (float) (-mob.maxTurnX()), (float) mob.maxTurnX());
					this.mob.setXRot(this.rotlerp(this.mob.getXRot(), f3, mob.maxTurnX()));
				}
				float f6 = Mth.cos(this.mob.getXRot() * ((float) Math.PI / 180.0F));
				float f4 = Mth.sin(this.mob.getXRot() * ((float) Math.PI / 180.0F));
				this.mob.zza = f6 * f1;
				this.mob.yya = -f4 * f1;
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
    
	@Override
    public void generateNewTarget() 
    {
        Level world = this.mob.level;
        Vec3 radius = ((IBTAMob)this.mob).getMoveRadius();
        for(int i = 0; i < 10; i++)
        {
        	Vec3 pos = BTAUtil.getSpreadPosition(this.mob, radius);
        	HitResult hitResult = world.clip(new ClipContext(this.mob.position(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob));
        	if(hitResult instanceof BlockHitResult blockHit)
        	{
                BlockPos targetPos = blockHit.getBlockPos();
                BlockState blockState = world.getBlockState(targetPos);
                if(blockState.isAir())
                {
                	this.targetPos = blockHit.getLocation();
                	break;
                }
        	}
        }
    }
}