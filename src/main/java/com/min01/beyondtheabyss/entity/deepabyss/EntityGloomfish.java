package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.entity.IBoid;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.Boid;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class EntityGloomfish extends AbstractDeepAbyssCreature implements IBoid
{
	public static final EntityDataAccessor<Optional<UUID>> LEADER_UUID = SynchedEntityData.defineId(EntityGloomfish.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(EntityGloomfish.class, EntityDataSerializers.BOOLEAN);

	public Boid boid;
	public final List<Boid> boids = new ArrayList<>();
	public final List<Boid.Obstacle> obstacles = new ArrayList<>();
	
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
    public void onAddedToWorld() 
    {
    	super.onAddedToWorld();
    	this.boid = new Boid(this, new Vec3(8, 8, 8));
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
		for(int x = -1; x < 1; x++) 
		{
			for(int y = -1; y < 5; y++)
			{
				for(int z = -1; z < 1; z++)
				{
					BlockPos pos = this.blockPosition().offset(x, y, z);
					if(this.level.getBlockState(pos).isCollisionShapeFullBlock(this.level, pos) || this.level.getBlockState(pos).isAir()) 
					{
						this.obstacles.add(new Boid.Obstacle(Vec3.atCenterOf(pos), 5, 0.1F));
					}
				}
			}
		}
		List<EntityGloomfish> list = this.level.getEntitiesOfClass(EntityGloomfish.class, this.getBoundingBox().inflate(5.0F));
		list.forEach(t -> 
		{
			if(!this.boids.contains(t.boid))
			{
				this.boids.add(t.boid);
			}
		});
		this.boid.update(this.boids, this.obstacles, true, true, true, 5.0F, 0.5F);
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
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
		if(p_21437_ == null)
		{
			p_21437_ = new GloomfishSpawnGroupData(this);
		} 
		else
		{
			this.setLeader(((GloomfishSpawnGroupData)p_21437_).leader);
		}
		return p_21437_;
	}
	
	public static class GloomfishSpawnGroupData implements SpawnGroupData 
	{
		public final EntityGloomfish leader;

		public GloomfishSpawnGroupData(EntityGloomfish gloomfish)
		{
			gloomfish.setLeader(true);
			this.leader = gloomfish;
		}
	}
	
	public static boolean checkGloomfishSpawnRules(EntityType<? extends AbstractDeepAbyssCreature> type, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) 
    {
		return pPos.getY() >= 10 && pPos.getY() <= 40 && pServerLevel.getBlockState(pPos.below()).is(Blocks.WATER) && pServerLevel.getBlockState(pPos.above()).is(Blocks.WATER);
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
	public EntityGloomfish getLeader() 
	{
		if(this.entityData.get(LEADER_UUID).isPresent()) 
		{
			return (EntityGloomfish) BTAUtil.getEntityByUUID(this.level, this.entityData.get(LEADER_UUID).get());
		}
		return null;
	}

	@Override
	public Vec3 getBoidDirection()
	{
		return this.boid.direction;
	}
}
