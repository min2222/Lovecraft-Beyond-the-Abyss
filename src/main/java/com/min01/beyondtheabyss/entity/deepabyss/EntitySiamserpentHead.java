package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.AbstractBTAMob;
import com.min01.beyondtheabyss.entity.BTAEntities;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentBlaster;
import com.min01.beyondtheabyss.entity.model.ModelSiamserpentSlasher;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.KinematicChain;
import com.min01.beyondtheabyss.util.KinematicChain.ChainSegment;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntitySiamserpentHead extends AbstractOwnableDeepAbyssMob<EntitySiamserpentHead>
{
	public static final EntityDataAccessor<Integer> HEAD_TYPE = SynchedEntityData.defineId(EntitySiamserpentHead.class, EntityDataSerializers.INT);
	public final KinematicChain chain = new KinematicChain(this);
	
	public EntitySiamserpentHead(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(15);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 60)
    			.add(Attributes.MOVEMENT_SPEED, 1.2F)
        		.add(Attributes.FOLLOW_RANGE, 30);
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(HEAD_TYPE, 0);
    }

	@Override
	public EntityPartBuilder<? extends AbstractBTAMob> createBuilder()
	{
		EntityPartBuilder<EntitySiamserpentHead> partBuilder = new EntityPartBuilder<EntitySiamserpentHead>(this);
		return partBuilder;
	}

	@OnlyIn(Dist.CLIENT)
    @Override
    public HierarchicalModel<? extends AbstractBTAMob> getModel()
    {
		ModelSiamserpentSlasher slasherModel = new ModelSiamserpentSlasher(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelSiamserpentSlasher.LAYER_LOCATION));
		ModelSiamserpentBlaster blasterModel = new ModelSiamserpentBlaster(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelSiamserpentBlaster.LAYER_LOCATION));
    	return this.getHeadType() == HeadType.SLASHER ? slasherModel : blasterModel;
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		for(int i = 0; i < 20; i++)
		{
			this.chain.tick();
		}
		
		if(this.getOwner() != null)
		{
    		ChainSegment segment = this.getOwner().chain.getSegments()[this.getOwner().chain.getSegments().length - 1];
    		Vec2 rot = segment.getRot();
			this.setPos(segment.getPos());
    		this.setXRot(-rot.x);
    		this.setYRot(rot.y + 180.0F);
    		this.setYHeadRot(rot.y + 180.0F);
    		this.setYBodyRot(rot.y + 180.0F);
    		this.setCanLookOrMove(false);
		}
	}

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag p_21484_) 
	{
		super.addAdditionalSaveData(p_21484_);
		p_21484_.putInt("HeadType", this.getHeadType().ordinal());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag p_21450_) 
	{
		super.readAdditionalSaveData(p_21450_);
		if(p_21450_.contains("HeadType"))
		{
			this.setHeadType(HeadType.values()[p_21450_.getInt("HeadType")]);
		}
	}
	
	public void setHeadType(HeadType value)
	{
		this.entityData.set(HEAD_TYPE, value.ordinal());
	}
	
	public HeadType getHeadType()
	{
		return HeadType.values()[this.entityData.get(HEAD_TYPE)];
	}
	
	@Override
	protected Component getTypeName()
	{
		switch(this.getHeadType())
		{
		case SLASHER:
			return Component.translatable("entity.beyondtheabyss.siamserpent_slasher");
		case BLASTER:
			return Component.translatable("entity.beyondtheabyss.siamserpent_blaster");
		}
		return super.getTypeName();
	}
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_)
	{
		if(this.getOwner() == null)
		{
			if(Math.random() <= 0.5F)
			{
				this.setHeadType(HeadType.BLASTER);
			}
			
			EntitySiamserpentBone bone = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
			bone.setOwner(this);
			bone.setIndex(0);
			this.level.addFreshEntity(bone);
			
			EntitySiamserpentBone bone2 = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
			bone2.setOwner(this);
			bone2.setIndex(11);
			this.level.addFreshEntity(bone2);
			
			EntitySiamserpentHead head = new EntitySiamserpentHead(BTAEntities.SIAMSERPENT_HEAD.get(), this.level);
			head.setOwner(this);
			head.setHeadType(this.getHeadType() == HeadType.SLASHER ? HeadType.BLASTER : HeadType.SLASHER);
			this.level.addFreshEntity(head);
			
			for(int i = 1; i < 11; i++)
			{
				EntitySiamserpentBone bone1 = new EntitySiamserpentBone(BTAEntities.SIAMSERPENT_BONE.get(), this.level);
				bone1.setOwner(this);
				bone1.setIndex(i);
				bone1.setVariant(this.level.random.nextInt(1, 3));
				this.level.addFreshEntity(bone1);
			}
		}
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	
	public static enum HeadType
	{
		SLASHER,
		BLASTER
	}
}
