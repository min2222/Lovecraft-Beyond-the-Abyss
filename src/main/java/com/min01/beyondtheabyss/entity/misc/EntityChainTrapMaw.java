package com.min01.beyondtheabyss.entity.misc;

import java.util.Optional;
import java.util.UUID;

import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.KinematicChain;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class EntityChainTrapMaw extends Entity
{
	public static final EntityDataAccessor<Vec3> CHAIN_POS = SynchedEntityData.defineId(EntityChainTrapMaw.class, BTAEntityDataSerializers.VEC3.get());
	public static final EntityDataAccessor<Optional<UUID>> TARGET_UUID = SynchedEntityData.defineId(EntityChainTrapMaw.class, EntityDataSerializers.OPTIONAL_UUID);
	public KinematicChain chain = new KinematicChain(this, 5, 0.925F);
	
	public EntityChainTrapMaw(EntityType<?> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
		this.noCulling = true;
	}

	@Override
	protected void defineSynchedData() 
	{
		this.entityData.define(CHAIN_POS, Vec3.ZERO);
		this.entityData.define(TARGET_UUID, Optional.empty());
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.getChainPos() != Vec3.ZERO)
		{
			this.chain.setAnchorPos(this.getChainPos());
		}
		if(this.getTarget() != null)
		{
			this.chain.setTarget(this.getTarget().getEyePosition());
			this.moveTo(this.chain.getTipSegment().getPos());
			if(this.getTarget().getBoundingBox().contains(this.position()))
			{
				this.getTarget().setDeltaMovement(Vec3.ZERO);
			}
			else
			{
				this.chain.tick();
			}
		}
		else
		{
			this.discard();
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_20052_) 
	{
		
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_20139_)
	{
		
	}
	
	public void setTarget(Entity entity)
	{
		this.entityData.set(TARGET_UUID, Optional.of(entity.getUUID()));
	}
	
	public Entity getTarget()
	{
		if(this.entityData.get(TARGET_UUID).isPresent())
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(TARGET_UUID).get());
		}
		return null;
	}
	
	public void setChainPos(Vec3 pos)
	{
		this.entityData.set(CHAIN_POS, pos);
	}
	
	public Vec3 getChainPos()
	{
		return this.entityData.get(CHAIN_POS);
	}

	@Override
	public Packet<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
