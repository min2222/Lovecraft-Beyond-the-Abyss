package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.util.BTAUtil;

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
	public static final EntityDataAccessor<Integer> CHAIN_LENGTH = SynchedEntityData.defineId(EntityChainTrapMaw.class, EntityDataSerializers.INT);
	public KinematicChain chain;
	
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
		this.entityData.define(CHAIN_LENGTH, 5);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.chain == null)
		{
			this.chain = new KinematicChain(this, this.getChainLength(), 0.925F);
		}
		else
		{
			this.chain.setOldPosAndRot();
			if(this.getChainPos() != Vec3.ZERO)
			{
				this.chain.setAnchorPos(this.getChainPos());
			}
			if(this.getTarget() != null)
			{
				Entity target = this.getTarget();
				Vec3 pos = this.chain.getTipSegment().getPos();
				this.chain.setTarget(target.getEyePosition());
				if(this.tickCount >= 5)
				{
					this.moveTo(pos);
				}
				if(pos.distanceTo(target.position()) <= 1.0F)
				{
					this.getTarget().setDeltaMovement(BTAUtil.fromToVector(target.position(), pos, 0.1F));
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
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_20052_) 
	{
		
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_20139_)
	{
		
	}
	
	public void setChainLength(int length)
	{
		this.entityData.set(CHAIN_LENGTH, length);
	}
	
	public int getChainLength()
	{
		return this.entityData.get(CHAIN_LENGTH);
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
