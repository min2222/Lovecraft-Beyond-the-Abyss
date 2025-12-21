package com.min01.beyondtheabyss.entity.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EntityForneusHead extends AbstractForneusPart
{
	public static final EntityDataAccessor<Vec3> WANTED_POS = SynchedEntityData.defineId(EntityForneusHead.class, BTAEntityDataSerializers.VEC3.get());
	public KinematicChain chain;
	
	public EntityForneusHead(EntityType<? extends Monster> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = 1000 + this.random.nextInt(1000);
		this.posArray = new Vec3[1];
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 1000.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.85F)
        		.add(Attributes.FOLLOW_RANGE, 200.0F)
        		.add(Attributes.ARMOR, 20.0F);
    }
    
    @Override
    public boolean isHead()
    {
    	return true;
    }
    
    @Override
    protected void defineSynchedData()
    {
    	super.defineSynchedData();
    	this.entityData.define(WANTED_POS, Vec3.ZERO);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMonster> createBuilder()
	{
    	EntityPartBuilder<EntityForneusHead> partBuilder = new EntityPartBuilder<EntityForneusHead>(this)
    	{
    		@Override
    		public Vec3 getOffset()
    		{
    			return new Vec3(0.0F, 2.25F, 0.0F);
    		}
    		
    		@Override
    		public float getRenderScale() 
    		{
    			return 1.5F;
    		}
    	};
		return partBuilder;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(this.chain == null)
		{
			this.chain = new KinematicChain(this, this.getChainLength() + 1, this.getSegmentDistance(0));
		}
		else
		{
			this.chain.setOldPosAndRot();
			this.chain.tick();
			this.chain.rotLerp = true;
			
			MoveControl moveControl = this.getMoveControl();
			Vec3 pos = new Vec3(moveControl.getWantedX(), moveControl.getWantedY(), moveControl.getWantedZ());
			if(!pos.equals(Vec3.ZERO))
			{
				this.setWantedPos(pos);
			}
			if(!this.getWantedPos().equals(Vec3.ZERO))
			{
		    	this.chain.setTarget(this.getWantedPos());
			}
			ChainSegment segment = this.chain.getTarget().equals(Vec3.ZERO) ? this.chain.getTipSegment() : this.chain.getLastSegment();
			this.chain.getLastSegment().setDistance(8.0F);
			Vec2 rot = segment.getRot();
			this.setPos(segment.getPos());
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
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag)
	{
		AbstractForneusPart prev = this;
		for(int i = 1; i < this.getChainLength(); i++)
		{
			if(i < this.getChainLength() - 1)
			{
				EntityForneusBody body = new EntityForneusBody(BTAEntities.FORNEUS_BODY.get(), this.level);
				body.setPos(this.position());
				body.setOwner(prev);
				body.setIndex(i);
				body.setHead(this);
				prev = body;
				this.level.addFreshEntity(body);
			}
			else
			{
				EntityForneusTail tail = new EntityForneusTail(BTAEntities.FORNEUS_TAIL.get(), this.level);
				tail.setPos(this.position());
				tail.setOwner(prev);
				tail.setIndex(i);
				tail.setHead(this);
				this.level.addFreshEntity(tail);
			}
		}
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}
	
	@Override
	public int maxTurnX() 
	{
		return !this.hasTarget() ? 55 : 75;
	}
	
	@Override
	public int maxTurnY() 
	{
		return !this.hasTarget() ? 3 : 5;
	}
	
	public void setWantedPos(Vec3 pos)
	{
		this.entityData.set(WANTED_POS, pos);
	}
	
	public Vec3 getWantedPos()
	{
		return this.entityData.get(WANTED_POS);
	}
}