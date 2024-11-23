package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractOwnableEntity;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.WormSegmentController;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityMutavoreTentacle extends AbstractOwnableEntity<EntityMutavoreTentacle>
{
	public static final EntityDataAccessor<Float> SYNC_X_ROT = SynchedEntityData.defineId(EntityMutavoreTentacle.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> SYNC_Y_ROT = SynchedEntityData.defineId(EntityMutavoreTentacle.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Double> SYNC_X = SynchedEntityData.defineId(EntityMutavoreTentacle.class, BTAEntityDataSerializers.DOUBLE.get());
	public static final EntityDataAccessor<Double> SYNC_Y = SynchedEntityData.defineId(EntityMutavoreTentacle.class, BTAEntityDataSerializers.DOUBLE.get());
	public static final EntityDataAccessor<Double> SYNC_Z = SynchedEntityData.defineId(EntityMutavoreTentacle.class, BTAEntityDataSerializers.DOUBLE.get());
	public static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(EntityMutavoreTentacle.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> IS_EDGE = SynchedEntityData.defineId(EntityMutavoreTentacle.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Optional<UUID>> BODY_UUID = SynchedEntityData.defineId(EntityMutavoreTentacle.class, EntityDataSerializers.OPTIONAL_UUID);
	
	public EntityMutavoreTentacle(EntityType<?> p_19870_, Level p_19871_) 
	{
		super(p_19870_, p_19871_);
		this.updateSyncPosAndRot();
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(SYNC_X_ROT, 0.0F);
		this.entityData.define(SYNC_Y_ROT, 0.0F);
		this.entityData.define(SYNC_X, 0.0D);
		this.entityData.define(SYNC_Y, 0.0D);
		this.entityData.define(SYNC_Z, 0.0D);
		this.entityData.define(INDEX, 0);
		this.entityData.define(IS_EDGE, false);
		this.entityData.define(BODY_UUID, Optional.empty());
	}
	
	@Override
    public void onAddedToWorld() 
    {
        super.onAddedToWorld();
        this.updateSyncPosAndRot();
    }
	
	public void updateSyncPosAndRot() 
    {
    	if(!this.level.isClientSide)
    	{
    		this.entityData.set(SYNC_X, this.getX());
    		this.entityData.set(SYNC_Y, this.getY());
    		this.entityData.set(SYNC_Z, this.getZ());
    		this.entityData.set(SYNC_X_ROT, this.getXRot());
    		this.entityData.set(SYNC_Y_ROT, this.getYRot());
    	}
    	else
    	{
    		this.setXRot(this.entityData.get(SYNC_X_ROT));
    		this.setYRot(this.entityData.get(SYNC_Y_ROT));
            this.setPos(this.getSyncPos());
    	}
    }
	
	public Vec3 getSyncPos()
	{
		return new Vec3(this.entityData.get(SYNC_X), this.entityData.get(SYNC_Y), this.entityData.get(SYNC_Z));
	}
	
	@Override
	public void moveTo(double p_20108_, double p_20109_, double p_20110_, float p_20111_, float p_20112_) 
	{
		super.moveTo(p_20108_, p_20109_, p_20110_, p_20111_, p_20112_);
		if(!this.level.isClientSide)
		{
    		this.entityData.set(SYNC_X, p_20108_);
    		this.entityData.set(SYNC_Y, p_20109_);
    		this.entityData.set(SYNC_Z, p_20110_);
    		this.entityData.set(SYNC_X_ROT, p_20112_);
    		this.entityData.set(SYNC_Y_ROT, p_20111_);
		}
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		if(!this.level.isClientSide)
		{
            this.updateSyncPosAndRot();
		}
		else
		{
            this.updateSyncPosAndRot();
		}
		
		if(this.getBody() != null)
		{
			EntityMutavore body = this.getBody();
    		if(!body.tentacles.contains(this))
    		{
    			body.tentacles.add(this);
    		}
    		
    		if(this.getOwner() != null)
    		{
    			WormSegmentController.tick(this, this.getOwner(), 1.75F, 0.5F);
    		}
    		else if(body.posArray[this.getIndex()] != null)
    		{
    			this.setXRot(body.getXRot());
    			this.setYRot(body.getYRot());
    			this.setPos(body.posArray[this.getIndex()]);
    		}
		}
		else
		{
			this.discard();
		}
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putInt("Index", this.getIndex());
		if(this.entityData.get(BODY_UUID).isPresent())
		{
			p_37265_.putUUID("Body", this.entityData.get(BODY_UUID).get());
		}
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_) 
	{
		super.readAdditionalSaveData(p_37262_);
		if(p_37262_.contains("Index"))
		{
			this.setIndex(p_37262_.getInt("Index"));
		}
		if(p_37262_.hasUUID("Body")) 
		{
			this.entityData.set(BODY_UUID, Optional.of(p_37262_.getUUID("Body")));
		}
	}
	
	public void setBody(EntityMutavore p_37263_)
	{
		this.entityData.set(BODY_UUID, Optional.of(p_37263_.getUUID()));
	}
	
	@Nullable
	public EntityMutavore getBody() 
	{
		if(this.entityData.get(BODY_UUID).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(BODY_UUID).get());
		}
		return null;
	}
	
	public void setEdge(boolean value)
	{
		this.entityData.set(IS_EDGE, value);
	}
	
	public boolean isEdge()
	{
		return this.entityData.get(IS_EDGE);
	}
	
	public void setIndex(int value)
	{
		this.entityData.set(INDEX, value);
	}
	
	public int getIndex()
	{
		return this.entityData.get(INDEX);
	}
}
