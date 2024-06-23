package com.min01.beyondtheabyss.entity.ai.control;

import com.min01.beyondtheabyss.entity.deepabyss.AbstractDeepAbyssMob;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraftforge.common.ForgeMod;

public class AbyssFishMoveControl extends MoveControl
{
    protected final AbstractDeepAbyssMob fish;
    protected final int maxTurnY;
    protected final float inWaterSpeedModifier;

    public AbyssFishMoveControl(AbstractDeepAbyssMob p_27501_, int p_148072_, float p_148073_) 
    {
    	super(p_27501_);
    	this.fish = p_27501_;
        this.maxTurnY = p_148072_;
        this.inWaterSpeedModifier = p_148073_;
    }

    @Override
    public void tick() 
    {
    	if(this.fish.isEyeInFluidType(ForgeMod.WATER_TYPE.get()))
    	{
    		this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
    	}

    	if(this.operation == MoveControl.Operation.MOVE_TO && !this.fish.getNavigation().isDone()) 
    	{
    		float f = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
    		this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), f * this.inWaterSpeedModifier));
    		double d0 = this.wantedX - this.fish.getX();
    		double d1 = this.wantedY - this.fish.getY();
    		double d2 = this.wantedZ - this.fish.getZ();
    		if(d1 != 0.0D)
    		{
    			double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    			this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, (double)this.fish.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
    		}

    		if(d0 != 0.0D || d2 != 0.0D)
    		{
    			float f1 = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
    			this.fish.setYRot(this.rotlerp(this.fish.getYRot(), f1, this.maxTurnY));
    			this.fish.yBodyRot = this.fish.getYRot();
    			this.fish.yHeadRot = this.mob.getYRot();
    		}
    	}
    	else
    	{
    		this.fish.setSpeed(0.0F);
    	}
    }
}
