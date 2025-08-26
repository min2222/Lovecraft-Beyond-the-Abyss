package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractWormPart;
import com.min01.beyondtheabyss.entity.ai.goal.AbstractBTASkillGoal;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class AbstractTwinserpentPart extends AbstractWormPart<AbstractTwinserpentPart>
{
	public static final EntityDataAccessor<Boolean> IS_SWAP = SynchedEntityData.defineId(AbstractTwinserpentPart.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Optional<UUID>> HEAD_UUID2 = SynchedEntityData.defineId(AbstractTwinserpentPart.class, EntityDataSerializers.OPTIONAL_UUID);
	public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID2 = SynchedEntityData.defineId(AbstractTwinserpentPart.class, EntityDataSerializers.OPTIONAL_UUID);

	public Class<? extends AbstractBTASkillGoal<?>> goal;
	
	public AbstractTwinserpentPart(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(IS_SWAP, false);
		this.entityData.define(HEAD_UUID2, Optional.empty());
		this.entityData.define(OWNER_UUID2, Optional.empty());
	}
	
	@Override
	protected void doPush(Entity p_21294_)
	{
		if(!(p_21294_ instanceof AbstractTwinserpentPart))
		{
			super.doPush(p_21294_);
		}
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		boolean flag = this.isHead() ? this.getHealth() <= this.getMaxHealth() / 2.0F : this.getHead() != null && this.getHead().getHealth() <= this.getHead().getMaxHealth() / 2.0F;
		if(!this.isSwap() && flag)
		{
			List<Integer> list = Arrays.asList(12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
			Collections.reverse(list);
			Integer index = list.get(this.getIndex());
			if(!(this instanceof EntityTwinserpentHead))
			{
				this.setIndex(index);
			}
			else if(this.isHead() && this.getHead() != null)
			{
				EntityTwinserpentHead head = (EntityTwinserpentHead) this;
				EntityTwinserpentHead head2 = (EntityTwinserpentHead) this.getHead();
				head2.setIndex(0);
				head.setIndex(12);
				head2.setHead(true);
				head.setHead(false);
			}
			this.setSwap(true);
		}
	}
	
	@Override
	public int getChainLength() 
	{
		return 13;
	}
	
	@Override
	public float getChainSpeed() 
	{
		return 0.35F;
	}
	
	@Override
	public float getSegmentDistance(int index) 
	{
		return 1.0F;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_37265_) 
	{
		super.addAdditionalSaveData(p_37265_);
		if(this.entityData.get(OWNER_UUID2).isPresent())
		{
			p_37265_.putUUID("Owner2", this.entityData.get(OWNER_UUID2).get());
		}
		if(this.entityData.get(HEAD_UUID2).isPresent())
		{
			p_37265_.putUUID("Head2", this.entityData.get(HEAD_UUID2).get());
		}
		p_37265_.putBoolean("isSwap", this.isSwap());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_37262_) 
	{
		super.readAdditionalSaveData(p_37262_);
		if(p_37262_.hasUUID("Owner2")) 
		{
			this.entityData.set(OWNER_UUID2, Optional.of(p_37262_.getUUID("Owner2")));
		}
		if(p_37262_.hasUUID("Head2")) 
		{
			this.entityData.set(HEAD_UUID2, Optional.of(p_37262_.getUUID("Head2")));
		}
		this.setSwap(p_37262_.getBoolean("isSwap"));
	}
	
	@Override
	public AbstractTwinserpentPart getOwner() 
	{
		if(this.isSwap())
		{
			return this.getOwner2();
		}
		return super.getOwner();
	}
	
	public void setOwner2(AbstractTwinserpentPart owner)
	{
		if(owner == null)
		{
			this.entityData.set(OWNER_UUID2, Optional.empty());
		}
		else
		{
			this.entityData.set(OWNER_UUID2, Optional.of(owner.getUUID()));
		}
	}
	
	@Nullable
	public AbstractTwinserpentPart getOwner2() 
	{
		if(this.entityData.get(OWNER_UUID2).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(OWNER_UUID2).get());
		}
		return null;
	}
	
	public void setHead2(AbstractTwinserpentPart p_37263_)
	{
		this.entityData.set(HEAD_UUID2, Optional.of(p_37263_.getUUID()));
	}
	
	@Nullable
	public AbstractTwinserpentPart getHead2() 
	{
		if(this.entityData.get(HEAD_UUID2).isPresent()) 
		{
			return BTAUtil.getEntityByUUID(this.level, this.entityData.get(HEAD_UUID2).get());
		}
		return null;
	}
	
	public void setSwap(boolean value)
	{
		this.entityData.set(IS_SWAP, value);
	}
	
	public boolean isSwap()
	{
		return this.entityData.get(IS_SWAP);
	}
}