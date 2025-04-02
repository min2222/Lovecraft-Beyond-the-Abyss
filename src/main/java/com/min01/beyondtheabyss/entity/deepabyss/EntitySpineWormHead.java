package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EntitySpineWormHead extends AbstractSpineWormPart
{
	public static final EntityDataAccessor<Direction> ATTACHED_DIRECTION = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.DIRECTION);
	public static final EntityDataAccessor<BlockPos> ATTACHED_POS = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.BLOCK_POS);
	public static final EntityDataAccessor<Integer> COOLDOWN = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.INT);
	public KinematicChain chain;
	
	public EntitySpineWormHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.setCanMove(false);
		this.setCanLook(false);
		this.posArray = new Vec3[1];
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 30.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.0F)
        		.add(Attributes.FOLLOW_RANGE, 10.0F)
        		.add(Attributes.KNOCKBACK_RESISTANCE, 100.0F);
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(ATTACHED_DIRECTION, Direction.DOWN);
    	this.entityData.define(ATTACHED_POS, this.blockPosition());
    	this.entityData.define(COOLDOWN, 100);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder() 
	{
		EntityPartBuilder<EntitySpineWormHead> partBuilder = new EntityPartBuilder<EntitySpineWormHead>(this);
		return partBuilder;
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}

	@Override
	public boolean isSwimable() 
	{
		return false;
	}
	
	@Override
	public boolean isHead() 
	{
		return true;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.chain == null)
		{
			this.chain = new KinematicChain(this, 12, 0.6F);
			this.chain.setInitialRot(new Vec2(this.getAttachedDirection().toYRot(), 0.0F));
		}
		else
		{
			this.chain.setOldPosAndRot();
			this.chain.tickBobbit();
			this.chain.setAnchorPos(Vec3.atBottomCenterOf(this.getAttachedPos()));

			if(this.getTarget() != null && this.canExtend())
			{
				this.posArray[0] = this.getTarget().position();
				BTANetwork.sendToAll(new UpdatePosArrayPacket(this, this.getTarget().position(), 0));
			}

			if(!this.level.isClientSide && this.getTarget() == null)
			{
				this.posArray[0] = Vec3.ZERO;
				BTANetwork.sendToAll(new UpdatePosArrayPacket(this, Vec3.ZERO, 0));
			}
			
			if(this.posArray[0] != null && this.canExtend())
			{
				if(Math.sqrt(this.posArray[0].distanceTo(this.position())) <= 1.5F)
				{
					this.chain.setTarget(Vec3.ZERO);
					this.setCooldown(100);
					if(this.getTarget() != null)
					{
						this.getTarget().startRiding(this);
					}
				}
				else
				{
					this.chain.setTarget(this.posArray[0]);
				}
			}
			
			if(this.getCooldown() > 0)
			{
				this.setCooldown(this.getCooldown() - 1);
			}
			
			ChainSegment segment = this.chain.getTipSegment();
			Vec3 pos = segment.getPos();
			Vec2 rot = segment.getRot();
			this.setPos(pos);
			this.setXRot(rot.x);
			this.setYRot(rot.y);
			this.setYBodyRot(rot.y);
			this.setYHeadRot(rot.y);
			
			this.xRotO = rot.x;
			this.yRotO = rot.y;
			this.yHeadRotO = rot.y;
			this.yBodyRotO = rot.y;
		}
	}
	
	@Override
	public void positionRider(Entity p_20312_) 
	{
		if(p_20312_ == this.getTarget())
		{
			p_20312_.moveTo(BTAUtil.getLookPos(this.getRotationVector(), this.position(), 0.0F, 0.0F, 0.2F));
		}
	}
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
	{
		AbstractSpineWormPart prev = this;
		this.setAttachedPos(this.blockPosition());
		this.setYRot(0.0F);
		this.setYHeadRot(0.0F);
		this.setYBodyRot(0.0F);
		this.setXRot(this.getAttachedDirection().toYRot());
		
		for(int i = 0; i < 10; i++)
		{
			EntitySpineWormBody body = new EntitySpineWormBody(BTAEntities.SPINE_WORM_BODY.get(), this.level);
			body.setPos(this.position());
			body.setHead(this);
			body.setOwner(prev);
			body.setIndex(10 - i);
			prev = body;
			this.level.addFreshEntity(body);
		}
		
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
    
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) 
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putInt("AttachedDirection", this.getAttachedDirection().ordinal());
    	p_21484_.putInt("AttachedPosX", this.getAttachedPos().getX());
    	p_21484_.putInt("AttachedPosY", this.getAttachedPos().getY());
    	p_21484_.putInt("AttachedPosZ", this.getAttachedPos().getZ());
    	p_21484_.putInt("Cooldown", this.getCooldown());
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_)
    {
    	super.readAdditionalSaveData(p_21450_);
    	if(p_21450_.contains("AttachedDirection"))
    	{
    		this.setAttachedDirection(Direction.values()[p_21450_.getInt("AttachedDirection")]);
    	}
    	if(p_21450_.contains("AttachedPosX") && p_21450_.contains("AttachedPosY") && p_21450_.contains("AttachedPosZ"))
    	{
    		this.setAttachedPos(new BlockPos(p_21450_.getInt("AttachedPosX"), p_21450_.getInt("AttachedPosY"), p_21450_.getInt("AttachedPosZ")));
    	}
    	if(p_21450_.contains("Cooldown"))
    	{
    		this.setCooldown(p_21450_.getInt("Cooldown"));
    	}
    }
    
    public boolean canExtend()
    {
    	return this.getCooldown() <= 0;
    }
    
    public void setCooldown(int value)
    {
    	this.entityData.set(COOLDOWN, value);
    }
    
    public int getCooldown()
    {
    	return this.entityData.get(COOLDOWN);
    }
	
	public void setAttachedDirection(Direction value)
	{
		this.entityData.set(ATTACHED_DIRECTION, value);
	}
	
	public Direction getAttachedDirection()
	{
		return this.entityData.get(ATTACHED_DIRECTION);
	}
	
	public void setAttachedPos(BlockPos value)
	{
		this.entityData.set(ATTACHED_POS, value);
	}
	
	public BlockPos getAttachedPos()
	{
		return this.entityData.get(ATTACHED_POS);
	}
}
