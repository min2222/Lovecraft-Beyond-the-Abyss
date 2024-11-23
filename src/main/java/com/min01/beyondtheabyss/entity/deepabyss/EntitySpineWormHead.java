package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAUtil;
import com.min01.beyondtheabyss.util.WormChain;
import com.min01.beyondtheabyss.util.WormSegmentController;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.phys.Vec3;

public class EntitySpineWormHead extends AbstractDeepAbyssMonster
{
	public static final EntityDataAccessor<Integer> BODY_LENGTH = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Direction> ATTACHED_DIRECTION = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.DIRECTION);
	public static final EntityDataAccessor<BlockPos> ATTACHED_POS = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.BLOCK_POS);

	public final List<EntitySpineWormBody> bodies = new ArrayList<>();
	
	public final WormChain chain = new WormChain(this, 10);
	
	public EntitySpineWormHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.setNoGravity(true);
		this.setCanMove(false);
		this.noPhysics = true;
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 30.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.0F)
        		.add(Attributes.FOLLOW_RANGE, 25.0F);
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(BODY_LENGTH, 0);
    	this.entityData.define(ATTACHED_DIRECTION, Direction.DOWN);
    	this.entityData.define(ATTACHED_POS, this.blockPosition());
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
	public boolean canBreathOutsideWater()
	{
		return true;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		this.resetFallDistance();
		
		if(!this.bodies.isEmpty())
		{
			EntitySpineWormBody body = this.bodies.get(this.bodies.size() - 1);
    		WormSegmentController.tickForward(this, body, 0.6F, 0.5F);
    		
    		if(this.getTarget() != null)
    		{
    			float distance = this.distanceTo(this.getTarget());
    			if(distance > 2.0F)
    			{
    				int dist = Mth.floor(distance);
    				if(this.getBodyLength() < 10 && this.getBodyLength() != dist)
    				{
    					this.addBody(this.bodies.size(), this.bodies.get(this.bodies.size() - 1));
    					this.setBodyLength(this.getBodyLength() + 1);
    				}
    			}
    		}
		}
		
		this.chain.tick();
		this.chain.setTargetPos(this.position());
	}
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
	{
		this.setAttachedPos(this.blockPosition().below());
		this.setYRot(0.0F);
		this.setYHeadRot(0.0F);
		this.setYBodyRot(0.0F);

		EntitySpineWormBody body = new EntitySpineWormBody(BTAEntities.SPINE_WORM_BODY.get(), this.level);
		body.setIndex(0);
		body.setPos(Vec3.atCenterOf(this.getAttachedPos()));
		body.setHead(this);
		this.bodies.add(0, body);
		this.level.addFreshEntity(body);
		
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	public void addBody(int index, EntitySpineWormBody owner)
	{
		Vec3 lookPos = BTAUtil.getLookPos(owner.getRotationVector(), owner.position(), 0.0F, 0.0F, 0.6F);
		EntitySpineWormBody body = new EntitySpineWormBody(BTAEntities.SPINE_WORM_BODY.get(), this.level);
		body.setIndex(index);
		body.setPos(lookPos);
		body.setOwner(owner);
		body.setHead(this);
		body.setXRot(owner.getXRot());
		body.setYRot(owner.getYRot());
		body.setYHeadRot(owner.yHeadRot);
		body.setYBodyRot(owner.yBodyRot);
		this.bodies.add(index, body);
		this.level.addFreshEntity(body);
	}
	
    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_)
    {
    	return super.isInvulnerableTo(p_20122_) || p_20122_ == DamageSource.IN_WALL || p_20122_.isFall();
    }
    
    @Override
    protected void doPush(Entity p_20971_) 
    {
    	
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) 
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putInt("BodyLength", this.getBodyLength());
    	p_21484_.putInt("AttachedDirection", this.getAttachedDirection().ordinal());
    	p_21484_.putInt("AttachedPosX", this.getAttachedPos().getX());
    	p_21484_.putInt("AttachedPosY", this.getAttachedPos().getY());
    	p_21484_.putInt("AttachedPosZ", this.getAttachedPos().getZ());
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_)
    {
    	super.readAdditionalSaveData(p_21450_);
    	if(p_21450_.contains("BodyLength"))
    	{
    		this.setBodyLength(p_21450_.getInt("BodyLength"));
    	}
    	if(p_21450_.contains("AttachedDirection"))
    	{
    		this.setAttachedDirection(Direction.values()[p_21450_.getInt("AttachedDirection")]);
    	}
    	if(p_21450_.contains("AttachedPosX") && p_21450_.contains("AttachedPosY") && p_21450_.contains("AttachedPosZ"))
    	{
    		this.setAttachedPos(new BlockPos(p_21450_.getInt("AttachedPosX"), p_21450_.getInt("AttachedPosY"), p_21450_.getInt("AttachedPosZ")));
    	}
    }
    
	public void setBodyLength(int value)
	{
		this.entityData.set(BODY_LENGTH, value);
	}
	
	public int getBodyLength()
	{
		return this.entityData.get(BODY_LENGTH);
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
