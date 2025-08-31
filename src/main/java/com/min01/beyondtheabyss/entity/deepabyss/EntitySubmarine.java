package com.min01.beyondtheabyss.entity.deepabyss;

import java.util.List;

import com.min01.beyondtheabyss.entity.AbstractBTACreature;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.util.BTAUtil;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class EntitySubmarine extends AbstractBTACreature
{
	public static final EntityDataAccessor<Boolean> HATCH_OPENED = SynchedEntityData.defineId(EntitySubmarine.class, EntityDataSerializers.BOOLEAN);
	
	public final SmoothAnimationState openHatchAnimationState = new SmoothAnimationState();
	public final SmoothAnimationState closeHatchAnimationState = new SmoothAnimationState();
	
    public float brightness;	
    public float brightnessOld;
    public int glowingTicks;
    
	public EntitySubmarine(EntityType<? extends AbstractBTACreature> p_19870_, Level p_19871_)
	{
		super(p_19870_, p_19871_);
		this.noCulling = true;
		this.setNoAi(true);
	}
	
	@Override
	public BTAMobType getBTAMobType() 
	{
		return BTAMobType.MISC;
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(HATCH_OPENED, false);
	}
	
	@Override
	public EntityPartBuilder<? extends AbstractBTACreature> createBuilder()
	{
		EntityPartBuilder<EntitySubmarine> partBuilder = new EntityPartBuilder<EntitySubmarine>(this);
		return partBuilder;
	}
	
    @Override
    public void tick() 
    {
    	super.tick();
    	this.deathTime = 0;
        if(this.level.isClientSide) 
        {
        	this.openHatchAnimationState.updateWhen(this.getAnimationState() == 1, this.tickCount);
        	this.closeHatchAnimationState.updateWhen(this.getAnimationState() == 2, this.tickCount);
            ++this.glowingTicks;
            this.brightness += (0.0F - this.brightness) * 0.8F;
        }
    }
    
    @Override
    public void travel(Vec3 vec3) 
    {
    	if(this.getFirstPassenger() instanceof Player player)
    	{
    		if(this.isInWater())
    		{
            	Vec3 motion = vec3;
            	boolean jumping = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class, player, "f_20899_");
                if(player.xxa != 0 || player.zza != 0)
                {
                	if(!this.level.isClientSide)
                	{
                    	this.setXRot(BTAUtil.rotlerp(this.getXRot(), player.getXRot(), 15));
                    	this.setYRot(BTAUtil.rotlerp(this.getYRot(), player.getYRot(), 15));
                    	this.setYHeadRot(BTAUtil.rotlerp(this.getYHeadRot(), player.getYHeadRot(), 15));
                    	this.setYBodyRot(BTAUtil.rotlerp(this.yBodyRot, player.yBodyRot, 15));
                	}
                	Vec3 lookPos = BTAUtil.getLookPos(this.getRotationVector(), Vec3.ZERO, 0.0F, 0.0F, 2.5F);
                	motion = motion.add(lookPos);
                }
            	if(jumping)
            	{
            		motion = motion.add(0, 1.5F, 0);
            	}
            	this.setDeltaMovement(motion.scale(0.1F));
            	super.travel(motion);
    		}
    	}
    	else
    	{
        	super.travel(vec3);
    	}
    }
    
    @Override
    public Vec3 getFluidFallingAdjustedMovement(double p_20995_, boolean p_20996_, Vec3 p_20997_)
    {
    	Vec3 motion = super.getFluidFallingAdjustedMovement(p_20995_, p_20996_, p_20997_);
    	return new Vec3(motion.x, 0.0F, motion.z);
    }
	
	@Override
	public void positionRider(Entity entity, MoveFunction fuction) 
	{
    	Vec3 pos = BTAUtil.getLookPos(this.getRotationVector(), this.position(), 0.0F, 1.75F, 2.0F);
		fuction.accept(entity, pos.x, pos.y, pos.z);
	}
	
	@Override
	public boolean showVehicleHealth() 
	{
		return false;
	}
    
    @Override
    public void push(double x, double y, double z) 
    {
    	
    }
    
    @Override
    protected void pushEntities()
    {
    	
    }
    
    @Override
    public boolean canDrownInFluidType(FluidType type)
    {
    	return false;
    }
    
    @Override
    public boolean isPushable() 
    {
    	return false;
    }
    
    @Override
    public boolean isPushedByFluid(FluidType type)
    {
    	return false;
    }

    @Override
    public boolean isPushedByFluid() 
    {
        return false;
    }
    
    @Override
    protected void tickDeath() 
    {
        if(!this.level.isClientSide && !this.isRemoved())
        {
        	this.remove(Entity.RemovalReason.KILLED);
        }
    }
    
    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity p_20123_) 
    {
    	return new Vec3(this.getX(), this.getBoundingBox().minY + 2.0F, this.getZ());
    }
    
    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_)
    {
    	if(!p_21016_.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
    	{
    		return false;
    	}
    	return super.hurt(p_21016_, p_21017_);
    }
	
	@Override
	public List<String> getCollidePart()
	{
		return List.of("bottom", "r_wall", "l_wall", "back", "hatch", "top", "front");
	}
	
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) 
	{
        String part = BTAUtil.getMultiPart(this.getBounds(), player);
        if(part != null)
        {
        	if(part.equals("controller") && this.getFirstPassenger() == null)
        	{
    			player.startRiding(this);
        	}
        	if(part.equals("hatch") || part.equals("valve"))
        	{
    			if(this.getAnimationTick() <= 0)
    			{
    				int state = this.getAnimationState() == 1 ? 2 : 1;
    				this.setAnimationState(state);
        			this.setHatchOpened(!this.hatchOpened());
        			this.setAnimationTick(30);
    			}
        	}
			return InteractionResult.SUCCESS;
        }
		return super.mobInteract(player, hand);
	}

	public void setHatchOpened(boolean value)
	{
		this.entityData.set(HATCH_OPENED, value);
	}
	
	public boolean hatchOpened()
	{
		return this.entityData.get(HATCH_OPENED);
	}
}
