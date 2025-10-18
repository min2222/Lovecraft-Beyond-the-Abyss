package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMonster;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.ai.control.BTASwimmingMoveControl;
import com.min01.beyondtheabyss.misc.BTAEntityDataSerializers;
import com.min01.beyondtheabyss.misc.KinematicChain;
import com.min01.beyondtheabyss.misc.KinematicChain.ChainSegment;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePosArrayPacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
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

public class EntityForneusHead extends AbstractForneusPart
{
	public static final EntityDataAccessor<Vec3> WANTED_POS = SynchedEntityData.defineId(EntityForneusHead.class, BTAEntityDataSerializers.VEC3.get());
	public KinematicChain chain;
	
	public EntityForneusHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = 1000 + this.random.nextInt(1000);
		this.noPhysics = true;
		this.setNoGravity(true);
		this.posArray = new Vec3[1];
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
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
			Vec3 pos = ((BTASwimmingMoveControl)this.moveControl).getTargetPos();
			if(!pos.equals(Vec3.ZERO))
			{
				this.posArray[0] = pos;
				BTANetwork.sendToAll(new UpdatePosArrayPacket(this, pos, 0));
			}
			if(this.posArray[0] != null)
			{
		    	this.chain.setTarget(this.posArray[0]);
			}
	    	if(!this.chain.getTarget().equals(Vec3.ZERO))
	    	{
				ChainSegment segment = this.chain.getTipSegment();
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
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		AbstractForneusPart prev = this;
		for(int i = 0; i < this.getChainLength(); i++)
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
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
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
	
	@Override
	public int targetSettingInterval() 
	{
		return 10;
	}
	
	@Override
	public Vec3 getMoveRadius()
	{
		return new Vec3(150, 30, 150);
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