package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.ArrayList;
import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
	public static final EntityDataAccessor<Boolean> IS_EXPOSED = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> BODY_LENGTH = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Direction> ATTACHED_DIRECTION = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.DIRECTION);
	public static final EntityDataAccessor<BlockPos> ATTACHED_POS = SynchedEntityData.defineId(EntitySpineWormHead.class, EntityDataSerializers.BLOCK_POS);

	public final List<EntitySpineWormBody> bodies = new ArrayList<>();
	
	public EntitySpineWormHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.setNoGravity(true);
		this.setCanMove(false);
		this.posArray = new Vec3[10];
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
    	this.entityData.define(IS_EXPOSED, false);
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
		this.tickPos();
		this.resetFallDistance();
		
		if(!this.hasTarget())
		{
			this.setXRot(this.getAttachedDirection().toYRot());
			this.setDeltaMovement(BTAUtil.fromToVector(this.position(), Vec3.atCenterOf(this.getAttachedPos()), 0.5F));
			this.setExposed(false);
			this.setCanLook(false);
		}
		
		//TODO
		if(this.getTarget() != null)
		{
			if(this.distanceTo(this.getTarget()) < 9.0F)
			{
				if(this.getAnimationTick() <= 0)
				{
					if(!this.isExposed())
					{
						this.setDeltaMovement(BTAUtil.fromToVector(this.position(), this.getTarget().position(), 0.5F));
						this.setExposed(true);
						this.setAnimationTick(30);
					}
					else
					{
						this.setDeltaMovement(BTAUtil.fromToVector(this.position(), Vec3.atCenterOf(this.getAttachedPos()), 0.5F));
						this.setExposed(false);
						this.setAnimationTick(30);
					}
				}
				this.setCanLook(true);
			}
		}
		
		Vec3 attachedPos = Vec3.atCenterOf(this.getAttachedPos());
		if(attachedPos.distanceTo(this.position()) > 6)
		{
            Vec3 vec3 = attachedPos.subtract(this.position());
            float dist = (float) attachedPos.distanceTo(this.position());
            double length = vec3.length();
            if(length > dist) 
            {
            	double scale = (length / dist) * 0.5D;
            	this.setDeltaMovement(this.getDeltaMovement().add(vec3.scale(1.0D / length).scale(scale)));
            }
		}
		else
		{
            Vec3 vec3 = this.position().subtract(this.position());
            float dist = (float) attachedPos.distanceTo(this.position());
            double length = vec3.length();
            if(length > dist)
            {
            	double scale = (length / dist) * 0.5D;
            	this.setDeltaMovement(this.getDeltaMovement().add(vec3.scale(1.0D / length).scale(scale)));
            }
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) 
	{
		this.setAttachedPos(this.blockPosition().below());
		this.setYRot(0.0F);
		this.setYHeadRot(0.0F);
		this.setYBodyRot(0.0F);
		this.setXRot(this.getAttachedDirection().toYRot());
		
		for(int i = 0; i < 10; i++)
		{
			EntitySpineWormBody body = new EntitySpineWormBody(BTAEntities.SPINE_WORM_BODY.get(), this.level);
			body.setPos(this.position());
			body.setHead(this);
			body.setIndex(i);
			if(!this.bodies.isEmpty())
			{
				body.setOwner(this.bodies.get(i - 1));
			}
			else
			{
				body.setOwner(this);
			}
			this.bodies.add(i, body);
			this.level.addFreshEntity(body);
		}
		
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	public void tickPos()
	{
        Vec3 from = this.position();
        Vec3 to = Vec3.atCenterOf(this.getAttachedPos());
        Vec3 pos = to.subtract(from);
        Vec3 currentSegmentButt = Vec3.ZERO;
        int segmentCount = 0;
        while(segmentCount < 10)
        {
            double remainingDistance = Math.min(currentSegmentButt.distanceTo(pos), 0.6F);
            Vec3 linearVec = pos.subtract(currentSegmentButt);
            Vec3 powVec = new Vec3(this.modifyVecAngle(linearVec.x), this.modifyVecAngle(linearVec.y), this.modifyVecAngle(linearVec.z));
            Vec3 next = powVec.normalize().scale(remainingDistance).add(currentSegmentButt);
            this.posArray[segmentCount] = next.add(this.position());
            currentSegmentButt = next;
            segmentCount++;
        }
	}
	
    public double modifyVecAngle(double dimension)
    {
        float abs = (float) Math.abs(dimension);
        return Math.signum(dimension) * Mth.clamp(Math.pow(abs * 2, 0.1) * 2, 0.05 * abs, abs);
    }
	
    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_)
    {
    	return super.isInvulnerableTo(p_20122_) || p_20122_.is(DamageTypes.IN_WALL)  || p_20122_.is(DamageTypeTags.IS_FALL);
    }
    
    @Override
    protected void doPush(Entity p_20971_) 
    {
    	
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) 
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putBoolean("isExposed", this.isExposed());
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
    	if(p_21450_.contains("isExposed"))
    	{
    		this.setExposed(p_21450_.getBoolean("isExposed"));
    	}
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
    
	public void setExposed(boolean value)
	{
		this.entityData.set(IS_EXPOSED, value);
	}
	
	public boolean isExposed()
	{
		return this.entityData.get(IS_EXPOSED);
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
