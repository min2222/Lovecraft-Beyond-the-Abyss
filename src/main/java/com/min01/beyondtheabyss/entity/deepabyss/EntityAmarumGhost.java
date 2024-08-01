package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.model.ModelAmarumGhost;
import com.min01.beyondtheabyss.entity.multipart.ClientEntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityAmarumGhost extends AbstractDeepAbyssMob
{
	public static final EntityDataAccessor<Boolean> IS_CONTACTED = SynchedEntityData.defineId(EntityAmarumGhost.class, EntityDataSerializers.BOOLEAN);
	   
	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	
	public EntityAmarumGhost(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(5);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 15)
    			.add(Attributes.MOVEMENT_SPEED, 0.3F)
        		.add(Attributes.FOLLOW_RANGE, 10);
    }
    
    @Override
    protected void defineSynchedData() 
    {
    	super.defineSynchedData();
    	this.entityData.define(IS_CONTACTED, false);
    }
    
	@Override
	public EntityPartBuilder<EntityAmarumGhost> createBuilder() 
	{
    	EntityPartBuilder<EntityAmarumGhost> partBuilder = new EntityPartBuilder<EntityAmarumGhost>(this);
		return partBuilder;
	}
	
    @OnlyIn(Dist.CLIENT)
    @Override
    public ClientEntityPartBuilder<EntityAmarumGhost> getClientPartBuilder()
    {
    	ModelAmarumGhost model = new ModelAmarumGhost(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelAmarumGhost.LAYER_LOCATION));
    	ClientEntityPartBuilder<EntityAmarumGhost> clientBuilder = new ClientEntityPartBuilder<EntityAmarumGhost>(this, model);
    	return clientBuilder;
    }
    
    @Override
    public void tick() 
    {
    	super.tick();
        if(this.isAlive())
        {
            this.oldSwell = this.swell;
            
            if(this.isContacted())
            {
            	this.swell += 1;
            }

            if(this.swell >= this.maxSwell) 
            {
            	this.swell = this.maxSwell;
            	this.explode();
            }
        }
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_)
    {
    	super.addAdditionalSaveData(p_21484_);
    	p_21484_.putShort("MaxSwell", (short)this.maxSwell);
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) 
    {
    	super.readAdditionalSaveData(p_21450_);
    	if(p_21450_.contains("MaxSwell", 99))
        {
    		this.maxSwell = p_21450_.getShort("MaxSwell");
        }
    }
    
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_)
    {
		if(!this.isContacted())
		{
			this.setContacted(true);
		}
    	return super.hurt(p_21016_, p_21017_);
    }
    
    @Override
    protected void doPush(Entity p_20971_) 
    {
    	super.doPush(p_20971_);
    	if(!this.level.isClientSide)
    	{
        	if(this.getTarget() != null)
        	{
        		if(p_20971_ == this.getTarget())
        		{
        			if(!this.isContacted())
        			{
        				this.setContacted(true);
        			}
        		}
        	}
        	else
        	{
        		if(p_20971_ instanceof Mob mob)
        		{
        			if(mob.getTarget() != null)
        			{
        				if(mob.getTarget() == this)
        				{
                			if(!this.isContacted())
                			{
                				this.setContacted(true);
                			}
        				}
        			}
        		}
        		
        		if(p_20971_ instanceof Player player)
        		{
        			if(!player.getAbilities().instabuild)
        			{
            			if(!this.isContacted())
            			{
            				this.setContacted(true);
            			}
        			}
        		}
        	}
    	}
    }
    
    public void explode()
    {
    	if(!this.level.isClientSide)
    	{
            this.dead = true;
            this.discard();
            this.playSound(SoundEvents.GENERIC_EXPLODE);
            this.createSporeCloud();
    	}
    }
    
    public void createSporeCloud()
    {
    	AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        areaeffectcloud.setParticle(ParticleTypes.SNEEZE);
        areaeffectcloud.setRadius(3.0F);
        areaeffectcloud.setDuration(60);
        areaeffectcloud.setRadiusPerTick(0.05F);
        this.level.addFreshEntity(areaeffectcloud);
    }
    
    public float getSwelling(float partialTick) 
    {
    	return Mth.lerp(partialTick, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }
    
    public void setContacted(boolean value)
    {
    	this.entityData.set(IS_CONTACTED, value);
    }
    
    public boolean isContacted()
    {
    	return this.entityData.get(IS_CONTACTED);
    }

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
}
