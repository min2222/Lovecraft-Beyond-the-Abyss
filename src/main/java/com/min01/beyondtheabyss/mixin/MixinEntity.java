package com.min01.beyondtheabyss.mixin;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList;
import com.min01.beyondtheabyss.multipart.CompoundOrientedBox;
import com.min01.beyondtheabyss.multipart.IMultipart;
import com.min01.beyondtheabyss.multipart.OrientedBox;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;
import com.min01.beyondtheabyss.util.MirroredCityUtil;
import com.min01.beyondtheabyss.world.BTAWorlds;
import com.min01.solomonlib.gravity.GravityAPI;
import com.min01.solomonlib.gravity.GravityCapabilityImpl;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

@Mixin(value = Entity.class, priority = -20000)
public abstract class MixinEntity
{
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci)
    {
    	Entity entity = Entity.class.cast(this);
		if(entity instanceof ItemEntity item)
		{
			if(item.level.dimension() == BTAWorlds.DEEP_ABYSS)
			{
				item.setDeltaMovement(item.getDeltaMovement().subtract(0, 0.01F, 0));
			}
		}
		if(MirroredCityUtil.isUpsideDown(entity))
		{
			GravityCapabilityImpl cap = GravityAPI.getGravityComponent(entity);
			cap.noAnimation = entity.tickCount <= 2;
			cap.noPositionAdjust = entity.tickCount <= 2;
			cap.applyGravityDirectionEffect(Direction.UP, null, Double.MAX_VALUE);
		}
    }

	@Inject(method = "tick", at = @At("TAIL"))
	private void tickTail(CallbackInfo ci) 
	{
		BTAUtil.updateGravity(Entity.class.cast(this));
	}
	
	@Inject(method = "checkBelowWorld", at = @At("HEAD"), cancellable = true)
	private void checkBelowWorld(CallbackInfo ci) 
	{
		if(Entity.class.cast(this).level.dimension() == BTAWorlds.OUTER_SPACE)
		{
			ci.cancel();
		}
	}
	
    @Inject(method = "getBoundingBox", at = @At("RETURN"), cancellable = true)
    private void getBoundingBox(CallbackInfoReturnable<AABB> cir)
    {
        if(Entity.class.cast(this) instanceof IMultipart multipart)
        {
            cir.setReturnValue(multipart.getCompoundBoundingBox(cir.getReturnValue()));
        }
    }

    @Inject(method = "isInWater", at = @At("HEAD"), cancellable = true)
    private void isInWater(CallbackInfoReturnable<Boolean> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		if(BTAUtil.canSwimInAir(living))
    		{
    			cir.setReturnValue(true);
    		}
    		if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(false);
    		}
    	}
    }
    
    @Inject(method = "getEyeInFluidType", at = @At("TAIL"), cancellable = true, remap = false)
    private void getEyeInFluidType(CallbackInfoReturnable<FluidType> cir)
    {
    	if(Entity.class.cast(this) instanceof LivingEntity living)
    	{
    		if(BTAUtil.canSwimInAir(living))
    		{
    			cir.setReturnValue(ForgeMod.WATER_TYPE.get());
    		}
    		if(DeepAbyssUtil.isInsideSubmarine(living))
    		{
    			cir.setReturnValue(ForgeMod.EMPTY_TYPE.get());
    		}
    	}
    }

    @ModifyVariable(method = "collide", at = @At("HEAD"), argsOnly = true)
    private Vec3 collide(Vec3 originalMovement) 
    {
        Entity entity = Entity.class.cast(this);
        AABB aabb = entity.getBoundingBox();
        List<OrientedBox> obbList = this.getOBBEntityCollisions(entity.level, entity, aabb.expandTowards(originalMovement));
        if(obbList.isEmpty())
        {
            return originalMovement;
        }
        return this.collideWithOrientedBoxes(originalMovement, aabb, obbList);
    }

    private Vec3 collideWithOrientedBoxes(Vec3 movement, AABB entityAABB, List<OrientedBox> obbs)
    {
        if(obbs.isEmpty() || movement.equals(Vec3.ZERO)) 
        {
            return movement;
        }

        double moveX = movement.x;
        double moveY = movement.y;
        double moveZ = movement.z;
        AABB currentAABB = entityAABB;

        if(moveY != 0.0D) 
        {
            for(OrientedBox obb : obbs) 
            {
                moveY = obb.collide(Direction.Axis.Y, currentAABB, moveY);
            }
            if(Math.abs(moveY) > 1.0E-7D) 
            {
                currentAABB = currentAABB.move(0.0D, moveY, 0.0D);
            }
        }

        boolean checkZFirst = Math.abs(moveX) < Math.abs(moveZ);
        if(checkZFirst && moveZ != 0.0D) 
        {
            for(OrientedBox obb : obbs)
            {
                moveZ = obb.collide(Direction.Axis.Z, currentAABB, moveZ);
            }
            if(Math.abs(moveZ) > 1.0E-7D) 
            {
                currentAABB = currentAABB.move(0.0D, 0.0D, moveZ);
            }
        }

        if(moveX != 0.0D) 
        {
            for(OrientedBox obb : obbs)
            {
                moveX = obb.collide(Direction.Axis.X, currentAABB, moveX);
            }
            if(Math.abs(moveX) > 1.0E-7D) 
            {
                currentAABB = currentAABB.move(moveX, 0.0D, 0.0D);
            }
        }

        if(!checkZFirst && moveZ != 0.0D) 
        {
            for(OrientedBox obb : obbs) 
            {
                moveZ = obb.collide(Direction.Axis.Z, currentAABB, moveZ);
            }
        }

        return new Vec3(moveX, moveY, moveZ);
    }
    
    private List<OrientedBox> getOBBEntityCollisions(Level level, @Nullable Entity pEntity, AABB pCollisionBox) 
    {
    	if(pCollisionBox.getSize() < 1.0E-7D)
        {
        	return List.of();
        } 
        else 
        {
        	Predicate<Entity> predicate = EntitySelector.NO_SPECTATORS.and(t -> t instanceof IMultipart multipart && !multipart.getCollidePart().isEmpty());
        	if(pEntity != null)
        	{
        		predicate = predicate.and(t -> !pEntity.isPassengerOfSameVehicle(t));
        	}
        	List<Entity> list = level.getEntities(pEntity, pCollisionBox.inflate(1.0E-7D), predicate);
        	if(list.isEmpty())
        	{
        		return List.of();
        	}
        	else
        	{
        		ImmutableList.Builder<OrientedBox> builder = ImmutableList.builderWithExpectedSize(list.size());
        		for(Entity entity : list) 
        		{
        			if(entity.getBoundingBox() instanceof CompoundOrientedBox compoundBox)
        			{
        				Collection<OrientedBox> boxes = compoundBox.boxes;
        				boxes.removeIf(t -> !t.collide);
        				builder.addAll(boxes);
        			}
        		}
        		return builder.build();
        	}
        }
    }
}
