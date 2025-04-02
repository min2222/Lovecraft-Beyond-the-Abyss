package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.entity.IBoid;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.Boid;
import com.min01.beyondtheabyss.misc.Boid.Bounds;
import com.min01.beyondtheabyss.misc.Boid.Obstacle;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class EntityGloomfish extends AbstractDeepAbyssCreature implements IBoid<EntityGloomfish>
{
	public static final EntityDataAccessor<Optional<UUID>> LEADER_UUID = SynchedEntityData.defineId(EntityGloomfish.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(EntityGloomfish.class, EntityDataSerializers.BOOLEAN);

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
    		
    		@Override
    		public float getWaterOffset() 
    		{
    			return 0.25F;
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
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		DeepAbyssUtil.spawnWithBoid(this, 9);
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	@Override
	public Vec3 getBoundSize()
	{
		return new Vec3(8, 8, 8);
	}
	
	@Override
	public Map<EntityGloomfish, Boid> getBoid() 
	{
		return this.boids;
	}
	
	@Override
	public Collection<Obstacle> getObstacle() 
	{
		return this.obstacles;
	}
	
	@Override
	public Bounds getBoidBounds() 
	{
		return this.bounds;
	}
    
	@Override
	public void setBound(Bounds bounds)
	{
		this.bounds = bounds;
	}
	
	@Override
    public void setLeader(boolean value)
    {
    	this.entityData.set(IS_LEADER, value);
    }

	@Override
    public boolean isLeader()
    {
    	return this.entityData.get(IS_LEADER);
    }
	
	@Override
	public void setLeader(EntityGloomfish leader)
	{
		if(leader == null) 
		{
			this.entityData.set(LEADER_UUID, Optional.empty());
		}
		else
		{
			this.entityData.set(LEADER_UUID, Optional.of(leader.getUUID()));
		}
	}
	
	@Nullable
	@Override
	public EntityGloomfish getLeader() 
	{
		if(this.entityData.get(LEADER_UUID).isPresent()) 
		{
			return (EntityGloomfish) BTAUtil.getEntityByUUID(this.level, this.entityData.get(LEADER_UUID).get());
		}
		return null;
	}
}
