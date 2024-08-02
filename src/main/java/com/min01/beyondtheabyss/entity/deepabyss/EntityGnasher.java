package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.model.ModelGnasher;
import com.min01.beyondtheabyss.entity.model.ModelGnasherLeader;
import com.min01.beyondtheabyss.entity.multipart.ClientEntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.min01.beyondtheabyss.util.DeepAbyssUtil;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityGnasher extends AbstractDeepAbyssMob
{
	public static final EntityDataAccessor<Boolean> IS_LEADER = SynchedEntityData.defineId(EntityGnasher.class, EntityDataSerializers.BOOLEAN);
	
	public EntityGnasher(EntityType<? extends Monster> p_21683_, Level p_21684_) 
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(6);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 15)
    			.add(Attributes.MOVEMENT_SPEED, 0.8F)
        		.add(Attributes.ATTACK_DAMAGE, 2.5)
        		.add(Attributes.FOLLOW_RANGE, 10);
    }

	@Override
	public EntityPartBuilder<EntityGnasher> createBuilder() 
	{
    	EntityPartBuilder<EntityGnasher> partBuilder = new EntityPartBuilder<EntityGnasher>(this)
    	{
    		@Override
    		public boolean isInWater() 
    		{
    			return true;
    		}
    	};
    	return partBuilder;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public ClientEntityPartBuilder<EntityGnasher> getClientPartBuilder() 
	{
		ModelGnasher model = new ModelGnasher(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelGnasher.LAYER_LOCATION));
		ModelGnasherLeader leaderModel = new ModelGnasherLeader(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelGnasherLeader.LAYER_LOCATION));
    	ClientEntityPartBuilder<EntityGnasher> clientBuilder = new ClientEntityPartBuilder<EntityGnasher>(this, this.isLeader() ? leaderModel : model)
    	{
    		@Override
    		public boolean isInWater() 
    		{
    			return true;
    		}
    	};
    	return clientBuilder;
	}
	
	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(IS_LEADER, false);
	}
	
	@Override
	public EntityDimensions getDimensions(Pose p_21047_) 
	{
		return this.isLeader() ? EntityDimensions.scalable(1.25F, 1.0F) : super.getDimensions(p_21047_);
	}
	
	@Override
	public void onAddedToWorld() 
	{
		super.onAddedToWorld();
		if(Math.random() <= 0.1F)
		{
			this.setAsLeader();
		}
	}
	
    @Override
    public void aiStep() 
    {
        super.aiStep();
        this.refreshDimensions();
        DeepAbyssUtil.fishFlopping(this);
    }
    
    public void setAsLeader()
    {
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4);
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(15);
		this.setLeader(true);
    }
    
    public void setLeader(boolean value)
    {
    	this.entityData.set(IS_LEADER, value);
    }
    
    public boolean isLeader()
    {
    	return this.entityData.get(IS_LEADER);
    }

	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.HOSTILE;
	}
}
