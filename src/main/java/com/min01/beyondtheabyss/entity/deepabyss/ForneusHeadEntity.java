package com.min01.beyondtheabyss.entity.deepabyss;

import javax.annotation.Nullable;

import com.min01.beyondtheabyss.entity.BTACameraShakeEntity;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class ForneusHeadEntity extends AbstractForneusPart
{
	public static final EntityDataAccessor<Vec3> WANTED_POS = SynchedEntityData.defineId(ForneusHeadEntity.class, BTAEntityDataSerializers.VEC3.get());
	public KinematicChain chain;
	
	public ForneusHeadEntity(EntityType<? extends AbstractForneusPart> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.xpReward = 1000 + this.random.nextInt(1000);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
    			.add(Attributes.MAX_HEALTH, 1000.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.2F)
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
	public void tick()
	{
		super.tick();
		if(this.chain == null)
		{
			this.chain = new KinematicChain(this, this.getChainLength() + 1, this.getSegmentDistance(0));
			this.chain.lerpSpeed = 25.0F;
			this.chain.speed = 0.5F;
			this.chain.rotLerp = true;
		}
		else
		{
			this.chain.setOldPosAndRot();
			this.chain.tick();

			if(!this.getWantedPos().equals(Vec3.ZERO))
			{
				this.chain.setTarget(this.getWantedPos());
			}
			
			if(this.getWantedPos().equals(Vec3.ZERO) || this.getWantedPos().subtract(this.position()).length() <= this.getSegmentDistance(0) * 2.5F)
			{
				Vec3 spreadPos = BTAUtil.getSpreadPosition(this.random, this.position(), new Vec3(250, 250, 250));
				if(this.level.getBlockState(BlockPos.containing(spreadPos)).is(Blocks.WATER))
				{
					this.setWantedPos(spreadPos);
				}
				BTACameraShakeEntity.cameraShake(this.level, this.position(), 250.0F, 0.15F, 0, 20);
			}
			
			ChainSegment segment = this.chain.getTarget().equals(Vec3.ZERO) ? this.chain.getTipSegment() : this.chain.getLastSegment();
			this.chain.getLastSegment().setDistance(8.0F);
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
		
    	float turnX = 75;
    	float turnY = 5;
		if(!this.isTargetValid())
		{
			turnX = 55;
			turnY = 3;
		}
		this.movementData.swim.turn.set(turnX, turnY);
		
		BTAUtil.forceTick(this);
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
				ForneusBodyEntity body = new ForneusBodyEntity(BTAEntities.FORNEUS_BODY.get(), this.level);
				body.setPos(this.position());
				body.setOwner(prev);
				body.setIndex(i);
				body.setHead(this);
				prev = body;
				this.level.addFreshEntity(body);
			}
			else
			{
				ForneusTailEntity tail = new ForneusTailEntity(BTAEntities.FORNEUS_TAIL.get(), this.level);
				tail.setPos(this.position());
				tail.setOwner(prev);
				tail.setIndex(i);
				tail.setHead(this);
				this.level.addFreshEntity(tail);
			}
		}
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
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