package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.Boid;
import com.min01.beyondtheabyss.misc.Boid.Bounds;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityGloomfish extends AbstractDeepAbyssCreature
{
	public static final EntityDataAccessor<Optional<UUID>> LEADER_UUID = SynchedEntityData.defineId(EntityGloomfish.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(EntityGloomfish.class, EntityDataSerializers.BOOLEAN);

	public static final Vec3 BOUND_SIZE = new Vec3(8, 8, 8);
	
	public Bounds bounds;
	public final Collection<Boid.Obstacle> obstacles = new ArrayList<Boid.Obstacle>();
	public final Map<EntityGloomfish, Boid> boids = new HashMap<EntityGloomfish, Boid>();
	
	public EntityGloomfish(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 2.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.7F);
    }
    
    @Override
    protected void defineSynchedData()
    {
    	super.defineSynchedData();
    	this.entityData.define(LEADER_UUID, Optional.empty());
    	this.entityData.define(IS_LEADER, false);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTACreature> createBuilder()
	{
    	EntityPartBuilder<EntityGloomfish> partBuilder = new EntityPartBuilder<EntityGloomfish>(this)
    	{
    		@Override
    		public boolean isInWater() 
    		{
    			return true;
    		}
    	};
    	return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.PASSIVE; 
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		DeepAbyssUtil.fishFlopping(this);
		
		if(this.isLeader() && this.isInWater())
		{
			if(this.tickCount % 60 == 0)
			{
				this.recreateBounds();
			}
			for(Entry<EntityGloomfish, Boid> entry : this.boids.entrySet())
			{
				EntityGloomfish fish = entry.getKey();
				Boid boid = entry.getValue();
				Vec3 direction = boid.direction;
				boid.update(this.boids.values(), this.obstacles, true, true, true, 2.5F, 0.25F);
				if(this.bounds != null)
				{
					boid.bounds = this.bounds;
				}
				fish.setDeltaMovement(direction);
				fish.setYRot(-(float)(Mth.atan2(direction.x, direction.z) * (double)(180.0F / (float)Math.PI)));
				fish.setYHeadRot(fish.getYRot());
				fish.setYBodyRot(fish.getYRot());
				fish.setXRot(-(float)(Mth.atan2(direction.y, direction.horizontalDistance()) * (double)(180.0F / (float)Math.PI)));
			}
		}
		
		if(this.bounds != null)
		{	
			for(int x = (int)this.bounds.minX(); x < this.bounds.maxX(); x++) 
			{
				for(int y = (int)this.bounds.minY(); y < this.bounds.maxY(); y++)
				{
					for(int z = (int)this.bounds.minZ(); z < this.bounds.maxZ(); z++)
					{
						BlockPos pos = new BlockPos(x, y, z);
						if(this.level.getBlockState(pos).isCollisionShapeFullBlock(this.level, pos) || this.level.getBlockState(pos).isAir()) 
						{
							this.obstacles.add(new Boid.Obstacle(Vec3.atCenterOf(pos), 5, 0.1F));
						}
					}
				}
			}
		}
		
		if(this.getLeader() != null && !this.level.isClientSide)
		{
			EntityGloomfish leader = this.getLeader();
			if(!leader.boids.containsKey(this))
			{
				Bounds bounds = Bounds.fromCenter(leader.position(), BOUND_SIZE);
				Vec3 pos = new Vec3(bounds.minX() + Math.random() * bounds.size.x, bounds.minY() + Math.random() * bounds.size.y, bounds.minZ() + Math.random() * bounds.size.z);
				leader.boids.put(this, new Boid(pos, bounds));
			}
		}
	}
	
    public void recreateBounds() 
    {
        Level world = this.level;
        int radius = 8;
        
        for(int i = 0; i < 10; i++)
        {
        	Vec3 pos = BTAUtil.getRandomPosition(this, radius);
        	HitResult hitResult = this.level.clip(new ClipContext(this.position(), pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        	if(hitResult instanceof BlockHitResult blockHit)
        	{
                BlockPos targetPos = blockHit.getBlockPos();
                BlockState blockState = world.getBlockState(targetPos);
                BlockState blockState2 = world.getBlockState(targetPos.above());
                
                if(blockState.is(Blocks.WATER) && blockState2.is(Blocks.WATER))
                {
    				this.bounds = Bounds.fromCenter(pos, BOUND_SIZE);
                	break;
                }
        	}
        }
    }
	
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_)
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putBoolean("isLeader", this.isLeader());
		if(this.entityData.get(LEADER_UUID).isPresent())
		{
			p_21484_.putUUID("Leader", this.entityData.get(LEADER_UUID).get());
		}
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) 
    {
    	super.readAdditionalSaveData(p_21450_);
    	if(p_21450_.contains("isLeader"))
    	{
    		this.setLeader(p_21450_.getBoolean("isLeader"));
    	}
		if(p_21450_.hasUUID("Leader")) 
		{
			this.entityData.set(LEADER_UUID, Optional.of(p_21450_.getUUID("Leader")));
		}
    }
	
	@Override
	public boolean canSwim() 
	{
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		this.setLeader(true);
		Bounds bounds = Bounds.fromCenter(this.position(), BOUND_SIZE);
		Vec3 pos = new Vec3(bounds.minX() + Math.random() * bounds.size.x, bounds.minY() + Math.random() * bounds.size.y, bounds.minZ() + Math.random() * bounds.size.z);
		this.boids.put(this, new Boid(pos, bounds));
		this.bounds = bounds;
		this.createBoid(pos, bounds);
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	public void createBoid(Vec3 pos, Bounds bounds)
	{
		for(int i = 0; i < 9; i++)
		{
			EntityGloomfish fish = new EntityGloomfish(BTAEntities.GLOOMFISH.get(), this.level);
			fish.setPos(this.position());
			fish.setLeader(this);
			this.boids.put(fish, new Boid(pos, bounds));
			this.level.addFreshEntity(fish);
		}
	}
	
    public void setLeader(boolean value)
    {
    	this.entityData.set(IS_LEADER, value);
    }
	
    public boolean isLeader()
    {
    	return this.entityData.get(IS_LEADER);
    }
	
	public void setLeader(EntityGloomfish leader)
	{
		this.entityData.set(LEADER_UUID, Optional.of(leader.getUUID()));
	}
	
	@Nullable
	public EntityGloomfish getLeader() 
	{
		if(this.entityData.get(LEADER_UUID).isPresent()) 
		{
			return (EntityGloomfish) BTAUtil.getEntityByUUID(this.level, this.entityData.get(LEADER_UUID).get());
		}
		return null;
	}
}
