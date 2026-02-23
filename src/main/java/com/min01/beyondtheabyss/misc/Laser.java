package com.min01.beyondtheabyss.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Laser
{
    public Vec3 endPos = Vec3.ZERO;
    public Vec3 collidePos = Vec3.ZERO;
    
    public void calculateEndPos(Vec3 pos, double radius, float yaw, float pitch)
    {
        this.endPos = pos.add(radius * Math.cos(yaw) * Math.cos(pitch), radius * Math.sin(pitch), radius * Math.sin(yaw) * Math.cos(pitch));
    }
    
    public float getLaserLength()
    {
        return (float) Math.sqrt(Math.pow(this.collidePos.x, 2) + Math.pow(this.collidePos.y, 2) + Math.pow(this.collidePos.z, 2));
    }
	
    public LaserHitResult raytrace(Level world, Vec3 pos, Vec3 from, Vec3 to, double radius, float yaw, float pitch, Predicate<? super Entity> predicate, @Nullable Entity entity) 
    {
    	this.calculateEndPos(pos, radius, yaw, pitch);
    	LaserHitResult result = new LaserHitResult();
        result.setBlockHit(world.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)));
        if(result.blockHit != null)
        {
            this.collidePos = result.blockHit.getLocation();
        }
        else 
        {
        	this.collidePos = this.endPos;
        }
        AABB aabb = new AABB(Math.min(pos.x, this.collidePos.x), Math.min(pos.y, this.collidePos.y), Math.min(pos.z, this.collidePos.z), Math.max(pos.x, this.collidePos.x), Math.max(pos.y, this.collidePos.y), Math.max(pos.z, this.collidePos.z));
        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, aabb.inflate(1.0F), predicate);
        for(LivingEntity living : entities)
        {
            float pad = living.getPickRadius() + 0.5F;
            AABB aabb2 = living.getBoundingBox().inflate(pad);
            Optional<Vec3> hit = aabb2.clip(from, to);
            if(aabb2.contains(from))
            {
                result.addEntityHit(living);
            }
            else if(hit.isPresent()) 
            {
                result.addEntityHit(living);
            }
        }
        return result;
    }
	
    public static class LaserHitResult
    {
    	public BlockHitResult blockHit;
    	public final List<LivingEntity> entities = new ArrayList<>();

        public void setBlockHit(HitResult hitResult) 
        {
            if(hitResult.getType() == HitResult.Type.BLOCK)
            {
                this.blockHit = (BlockHitResult) hitResult;
            }
        }

        public void addEntityHit(LivingEntity entity) 
        {
            this.entities.add(entity);
        }
    }
}
