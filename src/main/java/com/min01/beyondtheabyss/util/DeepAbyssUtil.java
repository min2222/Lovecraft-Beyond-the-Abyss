package com.min01.beyondtheabyss.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.min01.beyondtheabyss.entity.IBoid;
import com.min01.beyondtheabyss.entity.IDeepAbyssMob;
import com.min01.beyondtheabyss.misc.Boid;
import com.min01.beyondtheabyss.misc.Boid.Bounds;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DeepAbyssUtil 
{
	public static <T extends LivingEntity & IBoid<T>> void transferLeader(T entity) 
	{
		if(!entity.level.isClientSide)
		{
			if(entity.isLeader())
			{
				Set<T> set = entity.getBoid().keySet();
				List<T> list = new ArrayList<>(set);
				list.removeIf(t -> !t.isAlive());
				if(!list.isEmpty())
				{
					T fish = list.get(list.size() - 1);
					fish.setLeader(true);
					fish.setLeader(null);
					fish.setBound(entity.getBoidBounds());
					for(Entry<T, Boid> entry : entity.getBoid().entrySet())
					{
						fish.getBoid().put(entry.getKey(), entry.getValue());
						if(!entry.getKey().isLeader())
						{
							entry.getKey().setLeader(fish);
						}
					}
				}
			}
		}
	}
	
	public static <T extends LivingEntity & IBoid<T>> void loadBoid(T entity) 
	{
		if(!entity.level.isClientSide)
		{
			if(entity.getLeader() != null)
			{
				T leader = entity.getLeader();
				if(!leader.getBoid().containsKey(entity))
				{
					Bounds bounds = Bounds.fromCenter(leader.position(), entity.getBoundSize());
					Vec3 pos = new Vec3(bounds.minX() + Math.random() * bounds.size.x, bounds.minY() + Math.random() * bounds.size.y, bounds.minZ() + Math.random() * bounds.size.z);
					leader.getBoid().put(entity, new Boid(pos, bounds));
				}
			}
			
			if(entity.isLeader())
			{
				if(!entity.getBoid().containsKey(entity))
				{
					Bounds bounds = Bounds.fromCenter(entity.position(), entity.getBoundSize());
					Vec3 pos = new Vec3(bounds.minX() + Math.random() * bounds.size.x, bounds.minY() + Math.random() * bounds.size.y, bounds.minZ() + Math.random() * bounds.size.z);
					entity.setBound(bounds);
					entity.getBoid().put(entity, new Boid(pos, bounds));
				}
			}
		}
	}
	
    public static <T extends LivingEntity & IDeepAbyssMob & IBoid<T>> void tickBoid(T entity, Bounds bounds, Map<T, Boid> boids) 
    {
		for(Entry<T, Boid> entry : boids.entrySet())
		{
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
			T fish = entry.getKey();
			if(fish.isInWater())
			{
				Boid boid = entry.getValue();
				Vec3 direction = boid.direction;
				boid.update(boids.values(), entity.getObstacle(), true, true, true, 2.5F, 0.25F);
				boid.bounds = bounds;
				if(fish.rotLerp())
				{
					float yRot = -(float)(Mth.atan2(direction.x, direction.z) * (double)(180.0F / (float)Math.PI));
					float xRot = -(float)(Mth.atan2(direction.y, direction.horizontalDistance()) * (double)(180.0F / (float)Math.PI));
					fish.setYRot(BTAUtil.rotlerp(fish.getYRot(), yRot, (float)fish.maxTurnY()));
					fish.setYHeadRot(fish.getYRot());
					fish.setYBodyRot(fish.getYRot());
					fish.setXRot(BTAUtil.rotlerp(fish.getXRot(), xRot, fish.maxTurnX()));
					if(!mutable.equals(BlockPos.ZERO))
					{
						boolean lerpDone = Math.abs(fish.getYRot() - yRot) < 0.01F && Math.abs(fish.getXRot() - xRot) < 0.01F;
						if(lerpDone)
						{
							fish.setDeltaMovement(direction);
							mutable.set(BlockPos.ZERO);
						}
					}
					else
					{
						fish.setDeltaMovement(direction);
					}
				}
				else
				{
					fish.setDeltaMovement(direction);
					fish.setYRot(-(float)(Mth.atan2(direction.x, direction.z) * (double)(180.0F / (float)Math.PI)));
					fish.setYHeadRot(fish.getYRot());
					fish.setYBodyRot(fish.getYRot());
					fish.setXRot(-(float)(Mth.atan2(direction.y, direction.horizontalDistance()) * (double)(180.0F / (float)Math.PI)));
				}
			}
			
			for(int x = -1; x < 1; x++) 
			{
				for(int y = -1; y < 5; y++)
				{
					for(int z = -1; z < 1; z++)
					{
						BlockPos pos = fish.blockPosition().offset(x, y, z);
						if(entity.level.getBlockState(pos).isCollisionShapeFullBlock(entity.level, pos) || entity.level.getBlockState(pos).isAir()) 
						{
							entity.getObstacle().add(new Boid.Obstacle(Vec3.atCenterOf(pos), 5, 0.1F));
							mutable.set(pos);
						}
					}
				}
			}
		}
    }
    
    public static <T extends LivingEntity & IBoid<T>> void recreateBounds(T entity, int radius) 
    {
        Level world = entity.level;
        
        for(int i = 0; i < 10; i++)
        {
        	Vec3 pos = BTAUtil.getRandomPosition(entity, radius);
        	HitResult hitResult = entity.level.clip(new ClipContext(entity.position(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        	if(hitResult instanceof BlockHitResult blockHit)
        	{
                BlockPos targetPos = blockHit.getBlockPos();
                BlockState blockState = world.getBlockState(targetPos);
                
                if(blockState.is(Blocks.WATER))
                {
                	Vec3 size = entity.getBoundSize();
                    Bounds newBound = Bounds.fromCenter(Vec3.atCenterOf(targetPos), size);
                    if(isBoundInWater(world, newBound)) 
                    {
                        entity.setBound(newBound);
                        break;
                    }
                }
        	}
        }
    }
    
    public static boolean isBoundInWater(Level world, Bounds bound) 
    {
        BlockPos min = new BlockPos(Mth.floor(bound.minX()), Mth.floor(bound.minY()), Mth.floor(bound.minZ()));
        BlockPos max = new BlockPos(Mth.ceil(bound.maxX()), Mth.ceil(bound.maxY()), Mth.ceil(bound.maxZ()));
        for(BlockPos pos : BlockPos.betweenClosed(min, max)) 
        {
            if (!world.getBlockState(pos).is(Blocks.WATER)) 
            {
                return false;
            }
        }
        return true;
    }
    
	public static <T extends LivingEntity & IBoid<T>> void spawnWithBoid(T entity, int schoolSize)
	{
		entity.setLeader(true);
		Bounds bounds = Bounds.fromCenter(entity.position(), entity.getBoundSize());
		Vec3 pos = new Vec3(bounds.minX() + Math.random() * bounds.size.x, bounds.minY() + Math.random() * bounds.size.y, bounds.minZ() + Math.random() * bounds.size.z);
		entity.getBoid().put(entity, new Boid(pos, bounds));
		entity.setBound(bounds);
		createBoid(pos, bounds, schoolSize, entity);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends LivingEntity & IBoid<T>> void createBoid(Vec3 pos, Bounds bounds, int schoolSize, T entity)
	{
		for(int i = 0; i < schoolSize; i++)
		{
			T fish = (T) entity.getType().create(entity.level);
			fish.setPos(entity.position());
			fish.setLeader(entity);
			entity.getBoid().put(fish, new Boid(pos, bounds));
			entity.level.addFreshEntity(fish);
		}
	}
	
	public static void fishFlopping(LivingEntity entity)
	{
		fishFlopping(entity, SoundEvents.COD_FLOP, 1.0F, 0.5F);
	}
	
	public static void fishFlopping(LivingEntity entity, SoundEvent flopSound, float volume, float yMotion)
	{
        if(!entity.isInWater() && entity.isOnGround() && entity.verticalCollision) 
        {
        	entity.setDeltaMovement(entity.getDeltaMovement().add((double)((entity.getRandom().nextFloat() * 2.0F - 1.0F) * 0.05F), yMotion, (double)((entity.getRandom().nextFloat() * 2.0F - 1.0F) * 0.05F)));
        	entity.setOnGround(false);
        	entity.hasImpulse = true;
        	entity.playSound(flopSound, volume, entity.getVoicePitch());
        }
	}
}
