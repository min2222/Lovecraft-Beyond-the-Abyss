package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDeepAbyssWormPart<T extends AbstractDeepAbyssWormPart<T>> extends AbstractOwnableDeepAbyssMonster<T>
{
	public static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(AbstractDeepAbyssWormPart.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Optional<UUID>> HEAD_UUID = SynchedEntityData.defineId(AbstractDeepAbyssWormPart.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Boolean> UNLOADED = SynchedEntityData.defineId(AbstractDeepAbyssWormPart.class, EntityDataSerializers.BOOLEAN);
	public Worm[] worms;
	
	public AbstractDeepAbyssWormPart(EntityType<? extends Monster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(INDEX, 0);
		this.entityData.define(HEAD_UUID, Optional.empty());
		this.entityData.define(UNLOADED, false);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		this.resetFallDistance();
		this.setupWorms();
		
		if(this.getHead() != null)
		{
			T head = this.getHead();
			this.setUnloaded(head.touchingUnloadedChunk());
			if(!this.isHead())
			{
	    		this.hurtTime = head.hurtTime;
	    		this.deathTime = head.deathTime;
			}
			if(this.isWormChain())
			{
				this.tickWorms(head);
			}
    		if(this.deathTime >= 19 && head.getLastDamageSource() != null)
    		{
    			this.die(head.getLastDamageSource());
    		}
		}
		else if(!this.isHead() && !this.isUnloaded())
		{
			if(!this.getType().is(BTATags.BTAEntity.FORCE_TICKING))
			{
				this.discard();
			}
		}
		if(this.getType().is(BTATags.BTAEntity.FORCE_TICKING))
		{
			if(!this.level.isClientSide)
			{
				if(this.getHead() == null)
				{
					if(!this.isHead() && !this.isUnloaded())
					{
				    	for(ServerPlayer player : this.getServer().getPlayerList().getPlayers()) 
				    	{
				    		player.connection.send(new ClientboundRemoveEntitiesPacket(this.getId()));
				    	}
						this.discard();
					}
				}
			}
		}
	}
	
    @Override
    public boolean isInvulnerableTo(DamageSource pSource)
    {
    	return super.isInvulnerableTo(pSource) || pSource.is(DamageTypes.IN_WALL) || pSource.is(DamageTypeTags.IS_FALL);
    }
    
    @Override
    public boolean canLook() 
    {
    	return super.canLook() && this.isHead();
    }
    
    @Override
    public boolean canMove() 
    {
    	return super.canMove() && this.isHead();
    }
	
	@Override
	protected void playStepSound(BlockPos pPos, BlockState pState) 
	{
		
	}
	
	@Override
	protected void playSwimSound(float pVolume) 
	{
		
	}
	
	public void tickWorms(T head)
	{
		if(head.worms != null && head.isHead())
		{
			Worm worm = head.worms[this.getIndex()];
			if(worm != null)
			{
				Vec3 pos = head.position().add(worm.position());
				Vec2 rot = worm.getRot(1.0F);
				this.setPos(pos);
				this.setXRot(rot.x);
				this.setYRot(rot.y);
				this.setYHeadRot(rot.y);
				this.setYBodyRot(rot.y);
				
				this.xRotO = rot.x;
				this.yRotO = rot.y;
				this.yHeadRotO = rot.y;
				this.yBodyRotO = rot.y;
			}
		}
	}
	
	public void setupWorms()
	{
		if(this.isHead() && this.isWormChain())
		{
			if(this.worms == null)
			{
				Worm[] worms = new Worm[this.getChainLength()];
				for(int i = 0; i < worms.length; i++) 
				{
				    worms[i] = new Worm();
				}
				this.worms = worms;
			}
			else
			{
				for(int i = 0; i < this.worms.length; i++)
				{
					float speed = this.getChainSpeed();
					float distance = this.getSegmentDistance(i);
					Worm worm = this.worms[i];
					if(worm != null)
					{
						worm.setOldPosAndRot();
						if(i == 0)
						{
							WormChain.tick(worm, this, distance, speed);
						}
						else
						{
							Worm parent = this.worms[i - 1];
							if(parent != null)
							{
								WormChain.tick(worm, parent, distance, speed);
							}
						}
					}
				}
			}
		}
	}
	
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) 
    {
    	if(!this.isHead())
    	{
        	if(!this.isInvulnerableTo(pSource) && this.getOwner() != null)
        	{
        		this.getOwner().hurt(pSource, pAmount);
        	}
        	return false;
    	}
    	return super.hurt(pSource, pAmount);
    }
    
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("Index", this.getIndex());
		pCompound.putBoolean("Unloaded", this.isUnloaded());
		if(this.entityData.get(HEAD_UUID).isPresent())
		{
			pCompound.putUUID("Head", this.entityData.get(HEAD_UUID).get());
		}
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) 
	{
		super.readAdditionalSaveData(pCompound);
		if(pCompound.contains("Index"))
		{
			this.setIndex(pCompound.getInt("Index"));
		}
		if(pCompound.hasUUID("Head")) 
		{
			this.entityData.set(HEAD_UUID, Optional.of(pCompound.getUUID("Head")));
		}
		if(pCompound.contains("Unloaded"))
		{
			this.setUnloaded(pCompound.getBoolean("Unloaded"));
		}
	}
	
	public abstract int getChainLength();
	
	public abstract float getChainSpeed();
	
	public abstract float getSegmentDistance(int index);
	
	public boolean isWormChain()
	{
		return true;
	}
	
	public boolean isHead()
	{
		return false;
	}
	
	@Override
	public boolean isAlliedTo(Entity pEntity)
	{
		return super.isAlliedTo(pEntity) || pEntity == this.getHead() || (pEntity instanceof AbstractDeepAbyssWormPart<?> worm && worm.getHead() == this.getHead());
	}
	
	public void setUnloaded(boolean value)
	{
		this.entityData.set(UNLOADED, value);
	}
	
	public boolean isUnloaded()
	{
		return this.entityData.get(UNLOADED);
	}
	
	public void setHead(T head)
	{
		this.entityData.set(HEAD_UUID, Optional.of(head.getUUID()));
	}
	
	@SuppressWarnings("unchecked")
	@Nullable
	public T getHead() 
	{
		if(this.entityData.get(HEAD_UUID).isPresent()) 
		{
			if(this.getType().is(BTATags.BTAEntity.FORCE_TICKING))
			{
				for(Entity entity : BTAUtil.getAllEntities(this.level))
				{
					if(!entity.getUUID().equals(this.entityData.get(HEAD_UUID).get()))
					{
						continue;
					}
					return (T) entity;
				}
			}
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(HEAD_UUID).get());
		}
		return null;
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
