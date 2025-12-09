package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.misc.BTATags;
import com.min01.beyondtheabyss.misc.WormChain;
import com.min01.beyondtheabyss.misc.WormChain.Worm;
import com.min01.beyondtheabyss.util.BTAUtil;

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
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDeepAbyssWormPart<T extends AbstractDeepAbyssWormPart<T>> extends AbstractOwnableDeepAbyssMonster<T>
{
	public static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(AbstractDeepAbyssWormPart.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Optional<UUID>> HEAD_UUID = SynchedEntityData.defineId(AbstractDeepAbyssWormPart.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Boolean> UNLOADED = SynchedEntityData.defineId(AbstractDeepAbyssWormPart.class, EntityDataSerializers.BOOLEAN);
	public Worm[] worms;
	
	public AbstractDeepAbyssWormPart(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
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
			if(!this.getType().is(BTATags.BTAEntity.FAR_RANGE_TICKING))
			{
				this.discard();
			}
		}
		if(this.getType().is(BTATags.BTAEntity.FAR_RANGE_TICKING))
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
    public boolean hurt(DamageSource p_21016_, float p_21017_) 
    {
    	if(!this.isHead())
    	{
        	if(!this.isInvulnerableTo(p_21016_) && this.getOwner() != null)
        	{
        		this.getOwner().hurt(p_21016_, p_21017_);
        	}
        	return false;
    	}
    	return super.hurt(p_21016_, p_21017_);
    }
    
    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_)
    {
    	return super.isInvulnerableTo(p_20122_) || p_20122_.is(DamageTypes.IN_WALL) || p_20122_.is(DamageTypeTags.IS_FALL);
    }
    
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		p_37265_.putInt("Index", this.getIndex());
		p_37265_.putBoolean("Unloaded", this.isUnloaded());
		if(this.entityData.get(HEAD_UUID).isPresent())
		{
			p_37265_.putUUID("Head", this.entityData.get(HEAD_UUID).get());
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
		if(p_37262_.hasUUID("Head")) 
		{
			this.entityData.set(HEAD_UUID, Optional.of(p_37262_.getUUID("Head")));
		}
		if(p_37262_.contains("Unloaded"))
		{
			this.setUnloaded(p_37262_.getBoolean("Unloaded"));
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
	public boolean canSwim() 
	{
		return super.canSwim() && this.isHead();
	}
	
	@Override
	public boolean isAlliedTo(Entity p_20355_)
	{
		return super.isAlliedTo(p_20355_) || p_20355_ == this.getHead() || (p_20355_ instanceof AbstractDeepAbyssWormPart<?> worm && worm.getHead() == this.getHead());
	}
	
	public void setUnloaded(boolean value)
	{
		this.entityData.set(UNLOADED, value);
	}
	
	public boolean isUnloaded()
	{
		return this.entityData.get(UNLOADED);
	}
	
	public void setHead(T p_37263_)
	{
		this.entityData.set(HEAD_UUID, Optional.of(p_37263_.getUUID()));
	}
	
	@Nullable
	public T getHead() 
	{
		if(this.entityData.get(HEAD_UUID).isPresent()) 
		{
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
